// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.keyvault.keys;

import com.azure.core.exception.ResourceModifiedException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.HttpClient;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.keyvault.keys.models.DeletedKey;
import com.azure.keyvault.keys.models.Key;
import com.azure.keyvault.keys.models.KeyBase;
import com.azure.keyvault.keys.models.KeyCreateOptions;
import com.azure.keyvault.keys.models.webkey.KeyType;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class KeyAsyncClientTest extends KeyClientTestBase {

    private KeyAsyncClient client;

    @Override
    protected void beforeTest() {
        beforeTestSetup();

        if (interceptorManager.isPlaybackMode()) {
            client = clientSetup(credentials -> KeyAsyncClient.builder()
                    .credential(credentials)
                    .endpoint(getEndpoint())
                    .httpClient(interceptorManager.getPlaybackClient())
                    .httpLogDetailLevel(HttpLogDetailLevel.BODY_AND_HEADERS)
                    .build());
        } else {
            client = clientSetup(credentials -> KeyAsyncClient.builder()
                    .credential(credentials)
                    .endpoint(getEndpoint())
                    .httpClient(HttpClient.createDefault().wiretap(true))
                    .httpLogDetailLevel(HttpLogDetailLevel.BODY_AND_HEADERS)
                    .addPolicy(interceptorManager.getRecordPolicy())
                    .addPolicy(new RetryPolicy())
                    .build());
        }
    }

    @Override
    protected void afterTest() {

       // for (KeyBase key : client.listKeys()) {
            //   client.deleteKey(key.name());
      //  }
    }

    /**
     * Tests that a key can be created in the key vault.
     */
    public void setKey() {

        setKeyRunner((expected) -> StepVerifier.create(client.createKey(expected))
                .assertNext(response -> assertKeyEquals(expected, response))
                .verifyComplete());
    }

    /**
     * Tests that we cannot create a key when the key is an empty string.
     */
    public void setKeyEmptyName() {
        StepVerifier.create(client.createKey("", KeyType.RSA))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceModifiedException.class, HttpResponseStatus.BAD_REQUEST.code()));
    }

    /**
     * Tests that we can create keys when value is not null or an empty string.
     */
    public void setKeyNullType() {
        setKeyEmptyValueRunner((key) -> {

            StepVerifier.create(client.createKey(key))
                    .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceModifiedException.class, HttpResponseStatus.BAD_REQUEST.code()));

        });
    }

    /**
     * Verifies that an exception is thrown when null key object is passed for creation.
     */
    public void setKeyNull() {
        assertRunnableThrowsException(() -> client.createKey(null), NullPointerException.class);
    }

    /**
     * Tests that a key is able to be updated when it exists.
     */
    public void updateKey() {
        updateKeyRunner((original, updated) -> {
            StepVerifier.create(client.createKey(original))
                    .assertNext(response -> assertKeyEquals(original, response))
                    .verifyComplete();
            Key keyToUpdate = client.getKey(original.name()).block().value();

            StepVerifier.create(client.updateKey(keyToUpdate.expires(updated.expires())))
                    .assertNext(response -> {
                        assertNotNull(response.value());
                        assertEquals(original.name(), response.value().name());
                    }).verifyComplete();

            StepVerifier.create(client.getKey(original.name()))
                    .assertNext(updatedKeyResponse -> assertKeyEquals(updated, updatedKeyResponse))
                    .verifyComplete();
        });
    }

    /**
     * Tests that a key is not able to be updated when it is disabled. 403 error is expected.
     */
    public void updateDisabledKey() {
        updateDisabledKeyRunner((original, updated) -> {
            StepVerifier.create(client.createKey(original))
                    .assertNext(response -> assertKeyEquals(original, response))
                    .verifyComplete();
            Key keyToUpdate = client.getKey(original.name()).block().value();

            StepVerifier.create(client.updateKey(keyToUpdate.expires(updated.expires())))
                    .assertNext(response -> {
                        assertNotNull(response.value());
                        assertEquals(original.name(), response.value().name());
                    }).verifyComplete();

            StepVerifier.create(client.getKey(original.name()))
                    .assertNext(updatedKeyResponse -> assertKeyEquals(updated, updatedKeyResponse))
                    .verifyComplete();
        });
    }


    /**
     * Tests that an existing key can be retrieved.
     */
    public void getKey() {
        getKeyRunner((original) -> {
            client.createKey(original);
            StepVerifier.create(client.getKey(original.name()))
                    .assertNext(response -> assertKeyEquals(original, response))
                    .verifyComplete();
        });
    }

    /**
     * Tests that a specific version of the key can be retrieved.
     */
    public void getKeySpecificVersion() {
        getKeySpecificVersionRunner((key, keyWithNewVal) -> {
            final Key keyVersionOne = client.createKey(key).block().value();
            final Key keyVersionTwo = client.createKey(keyWithNewVal).block().value();

            StepVerifier.create(client.getKey(key.name(), keyVersionOne.version()))
                    .assertNext(response -> assertKeyEquals(key, response))
                    .verifyComplete();

            StepVerifier.create(client.getKey(keyWithNewVal.name(), keyVersionTwo.version()))
                    .assertNext(response -> assertKeyEquals(keyWithNewVal, response))
                    .verifyComplete();
        });
    }

    /**
     * Tests that an attempt to get a non-existing key throws an error.
     */
    public void getKeyNotFound() {
        StepVerifier.create(client.getKey("non-existing"))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceNotFoundException.class, HttpResponseStatus.NOT_FOUND.code()));
    }


    /**
     * Tests that an existing key can be deleted.
     */
    public void deleteKey() {
        deleteKeyRunner((keyToDelete) -> {
            StepVerifier.create(client.createKey(keyToDelete))
                    .assertNext(keyResponse -> {
                        assertKeyEquals(keyToDelete, keyResponse.value());
                    }).verifyComplete();

            StepVerifier.create(client.deleteKey(keyToDelete.name()))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey.deletedDate());
                        assertNotNull(deletedKey.recoveryId());
                        assertNotNull(deletedKey.scheduledPurgeDate());
                        assertEquals(keyToDelete.name(), deletedKey.name());
                    }).verifyComplete();
            sleepInRecordMode(30000);

            StepVerifier.create(client.purgeDeletedKey(keyToDelete.name()))
                    .assertNext(voidResponse -> {
                        assertEquals(HttpResponseStatus.NO_CONTENT.code(), voidResponse.statusCode());
                    }).verifyComplete();
            sleepInRecordMode(15000);
        });
    }

    public void deleteKeyNotFound() {
        StepVerifier.create(client.deleteKey("non-existing"))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceNotFoundException.class, HttpResponseStatus.NOT_FOUND.code()));
    }

    /**
     * Tests that a deleted key can be retrieved on a soft-delete enabled vault.
     */
    public void getDeletedKey() {
        getDeletedKeyRunner((keyToDeleteAndGet) -> {
            StepVerifier.create(client.createKey(keyToDeleteAndGet))
                    .assertNext(keyResponse -> {
                        assertKeyEquals(keyToDeleteAndGet, keyResponse.value());
                    }).verifyComplete();

            StepVerifier.create(client.deleteKey(keyToDeleteAndGet.name()))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey);
                        pollOnKeyDeletion(keyToDeleteAndGet.name());
                    }).verifyComplete();
            sleep(30000);

            StepVerifier.create(client.getDeletedKey(keyToDeleteAndGet.name()))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey.deletedDate());
                        assertNotNull(deletedKey.recoveryId());
                        assertNotNull(deletedKey.scheduledPurgeDate());
                        assertEquals(keyToDeleteAndGet.name(), deletedKey.name());
                    }).verifyComplete();

            StepVerifier.create(client.purgeDeletedKey(keyToDeleteAndGet.name()))
                    .assertNext(voidResponse -> {
                        assertEquals(HttpResponseStatus.NO_CONTENT.code(), voidResponse.statusCode());
                        pollOnKeyPurge(keyToDeleteAndGet.name());
                    }).verifyComplete();
            sleep(15000);
        });
    }

    /**
     * Tests that an attempt to retrieve a non existing deleted key throws an error on a soft-delete enabled vault.
     */
    public void getDeletedKeyNotFound() {
        StepVerifier.create(client.getDeletedKey("non-existing"))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceNotFoundException.class, HttpResponseStatus.NOT_FOUND.code()));
    }

    /**
     * Tests that a deleted key can be recovered on a soft-delete enabled vault.
     */
    public void recoverDeletedKey() {
        recoverDeletedKeyRunner((keyToDeleteAndRecover) -> {
            StepVerifier.create(client.createKey(keyToDeleteAndRecover))
                    .assertNext(keyResponse -> {
                        assertKeyEquals(keyToDeleteAndRecover, keyResponse.value());
                    }).verifyComplete();

            StepVerifier.create(client.deleteKey(keyToDeleteAndRecover.name()))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey);
                    }).verifyComplete();
            sleepInRecordMode(30000);

            StepVerifier.create(client.recoverDeletedKey(keyToDeleteAndRecover.name()))
                    .assertNext(keyResponse -> {
                        Key recoveredKey = keyResponse.value();
                        assertEquals(keyToDeleteAndRecover.name(), recoveredKey.name());
                        assertEquals(keyToDeleteAndRecover.notBefore(), recoveredKey.notBefore());
                        assertEquals(keyToDeleteAndRecover.expires(), recoveredKey.expires());
                    }).verifyComplete();
        });
    }

    /**
     * Tests that an attempt to recover a non existing deleted key throws an error on a soft-delete enabled vault.
     */
    public void recoverDeletedKeyNotFound() {
        StepVerifier.create(client.recoverDeletedKey("non-existing"))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceNotFoundException.class, HttpResponseStatus.NOT_FOUND.code()));
    }

    /**
     * Tests that a key can be backed up in the key vault.
     */
    public void backupKey() {
        backupKeyRunner((keyToBackup) -> {
            StepVerifier.create(client.createKey(keyToBackup))
                    .assertNext(keyResponse -> {
                        assertKeyEquals(keyToBackup, keyResponse.value());
                    }).verifyComplete();

            StepVerifier.create(client.backupKey(keyToBackup.name()))
                    .assertNext(response -> {
                        byte[] backupBytes = response.value();
                        assertNotNull(backupBytes);
                        assertTrue(backupBytes.length > 0);
                    }).verifyComplete();
        });
    }

    /**
     * Tests that an attempt to backup a non existing key throws an error.
     */
    public void backupKeyNotFound() {
        StepVerifier.create(client.backupKey("non-existing"))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceNotFoundException.class, HttpResponseStatus.NOT_FOUND.code()));
    }

    /**
     * Tests that a key can be backed up in the key vault.
     */
    public void restoreKey() {
        restoreKeyRunner((keyToBackupAndRestore) -> {
            StepVerifier.create(client.createKey(keyToBackupAndRestore))
                    .assertNext(keyResponse -> {
                        assertKeyEquals(keyToBackupAndRestore, keyResponse.value());
                    }).verifyComplete();
            byte[] backup = client.backupKey(keyToBackupAndRestore.name()).block().value();

            StepVerifier.create(client.deleteKey(keyToBackupAndRestore.name()))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey);
                    }).verifyComplete();
            pollOnKeyDeletion(keyToBackupAndRestore.name());

            StepVerifier.create(client.purgeDeletedKey(keyToBackupAndRestore.name()))
                    .assertNext(voidResponse -> {
                        assertEquals(HttpResponseStatus.NO_CONTENT.code(), voidResponse.statusCode());
                    }).verifyComplete();
            pollOnKeyPurge(keyToBackupAndRestore.name());

            StepVerifier.create(client.restoreKey(backup))
                    .assertNext(response -> {
                        Key restoredKey = response.value();
                        assertEquals(keyToBackupAndRestore.name(), restoredKey.name());
                        assertEquals(keyToBackupAndRestore.notBefore(), restoredKey.notBefore());
                        assertEquals(keyToBackupAndRestore.expires(), restoredKey.expires());
                    }).verifyComplete();
        });
    }

    /**
     * Tests that an attempt to restore a key from malformed backup bytes throws an error.
     */
    public void restoreKeyFromMalformedBackup() {
        byte[] keyBackupBytes = "non-existing".getBytes();
        StepVerifier.create(client.restoreKey(keyBackupBytes))
                .verifyErrorSatisfies(ex -> assertRestException(ex, ResourceModifiedException.class, HttpResponseStatus.BAD_REQUEST.code()));
    }

    /**
     * Tests that deleted keys can be listed in the key vault.
     */
    @Override
    public void listDeletedKeys() {
        listDeletedKeysRunner((keys) -> {
            HashMap<String, KeyCreateOptions> keysToDelete = keys;
            List<DeletedKey> deletedKeys = new ArrayList<>();
            for (KeyCreateOptions key : keysToDelete.values()) {
                client.createKey(key).subscribe(keyResponse -> assertKeyEquals(key, keyResponse.value()));
                sleepInRecordMode(1000);
            }

            for (KeyCreateOptions key : keysToDelete.values()) {
                StepVerifier.create(client.deleteKey(key.name()))
                        .assertNext(deletedKeyResponse -> {
                            DeletedKey deletedKey = deletedKeyResponse.value();
                            assertNotNull(deletedKey);
                            pollOnKeyDeletion(key.name());
                        }).verifyComplete();
            }

            sleep(30000);
            client.listDeletedKeys().subscribe(deletedKeys::add);
            sleep(30000);

            for (DeletedKey deletedKey : deletedKeys) {
                StepVerifier.create(client.purgeDeletedKey(deletedKey.name()))
                        .assertNext(voidResponse -> {
                            assertEquals(HttpResponseStatus.NO_CONTENT.code(), voidResponse.statusCode());
                            pollOnKeyPurge(deletedKey.name());
                        }).verifyComplete();
            }
            return deletedKeys;
        });
    }

    /**
     * Tests that key versions can be listed in the key vault.
     */
    @Override
    public void listKeyVersions() {
        listKeyVersionsRunner((keys) -> {
            List<KeyCreateOptions> keyVersions = keys;
            List<KeyBase> output = new ArrayList<>();
            String keyName = null;
            for (KeyCreateOptions key : keyVersions) {
                keyName = key.name();
                client.createKey(key).subscribe(keyResponse -> assertKeyEquals(key, keyResponse.value()));
                sleepInRecordMode(1000);
            }
            sleep(30000);
            client.listKeyVersions(keyName).subscribe(output::add);
            sleep(30000);

            StepVerifier.create(client.deleteKey(keyName))
                    .assertNext(deletedKeyResponse -> {
                        DeletedKey deletedKey = deletedKeyResponse.value();
                        assertNotNull(deletedKey);
                    }).verifyComplete();
            pollOnKeyDeletion(keyName);


            StepVerifier.create(client.purgeDeletedKey(keyName))
                    .assertNext(voidResponse -> {
                        assertEquals(HttpResponseStatus.NO_CONTENT.code(), voidResponse.statusCode());
                    }).verifyComplete();
            pollOnKeyPurge(keyName);
            return output;
        });

    }

    /**
     * Tests that keys can be listed in the key vault.
     */
    public void listKeys() {
        listKeysRunner((keys) -> {
            List<KeyCreateOptions> keysToList = keys;
            List<KeyBase> output = new ArrayList<>();
            for (KeyCreateOptions key : keysToList) {
                client.createKey(key).subscribe(keyResponse -> assertKeyEquals(key, keyResponse.value()));
                sleepInRecordMode(1000);
            }
            sleep(30000);
            client.listKeys().subscribe(output::add);
            sleep(30000);
            return output;
        });
    }

    private DeletedKey pollOnKeyDeletion(String keyName) {
        int pendingPollCount = 0;
        while (pendingPollCount < 30) {
            DeletedKey deletedKey = null;
            try {
                deletedKey = client.getDeletedKey(keyName).block().value();
            } catch (ResourceNotFoundException e) {
            }
            if (deletedKey == null) {
                sleepInRecordMode(2000);
                pendingPollCount += 1;
                continue;
            } else {
                return deletedKey;
            }
        }
        System.err.printf("Deleted Key %s not found \n", keyName);
        return null;
    }

    private DeletedKey pollOnKeyPurge(String keyName) {
        int pendingPollCount = 0;
        while (pendingPollCount < 10) {
            DeletedKey deletedKey = null;
            try {
                deletedKey = client.getDeletedKey(keyName).block().value();
            } catch (ResourceNotFoundException e) {
            }
            if (deletedKey != null) {
                sleepInRecordMode(2000);
                pendingPollCount += 1;
                continue;
            } else {
                return deletedKey;
            }
        }
        System.err.printf("Deleted Key %s was not purged \n", keyName);
        return null;
    }
}
