// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.eventhubs;

import com.azure.core.amqp.Retry;
import com.azure.core.amqp.TransportType;
import com.azure.core.amqp.exception.AmqpException;
import com.azure.core.amqp.exception.ErrorCondition;
import com.azure.core.implementation.logging.ServiceLogger;
import com.azure.core.implementation.util.ImplUtils;
import com.azure.core.test.TestMode;
import com.azure.eventhubs.implementation.ApiTestBase;
import com.azure.eventhubs.implementation.ConnectionParameters;
import com.azure.eventhubs.implementation.ReactorHandlerProvider;
import com.azure.eventhubs.implementation.SharedAccessSignatureTokenProvider;
import com.azure.eventhubs.implementation.TokenProvider;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Tests scenarios on {@link EventHubClient}.
 */
public class EventHubClientTest extends ApiTestBase {
    private final ServiceLogger logger = new ServiceLogger(EventHubClientTest.class);

    private EventHubClient client;
    private ExpectedData data;
    private ReactorHandlerProvider handlerProvider;

    @Rule
    public TestName testName = new TestName();

    @Override
    protected void beforeTest() {
        logger.asInfo().log("[{}]: Performing test set-up.", testName.getMethodName());

        handlerProvider = new ReactorHandlerProvider(getReactorProvider());
        client = new EventHubClient(getConnectionParameters(), getReactorProvider(), handlerProvider);
        data = new ExpectedData(getTestMode(), getCredentialInfo());
    }

    @Override
    protected void afterTest() {
        logger.asInfo().log("[{}]: Performing test clean-up.", testName.getMethodName());

        if (client != null) {
            client.close();
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullConstructor() {
        new EventHubClient(null, null, null);
    }

    /**
     * Verifies that we can get the metadata about an Event Hub
     */
    @Test
    public void getEventHubProperties() {
        skipIfNotRecordMode();

        // Act & Assert
        StepVerifier.create(client.getProperties())
            .assertNext(properties -> {
                Assert.assertNotNull(properties);
                Assert.assertEquals(data.getProperties().path(), properties.path());
                Assert.assertEquals(data.getProperties().partitionIds().length, properties.partitionIds().length);
            }).verifyComplete();
    }

    /**
     * Verifies that we can get the partition identifiers of an Event Hub.
     */
    @Test
    public void getPartitionIds() {
        skipIfNotRecordMode();

        // Act & Assert
        StepVerifier.create(client.getPartitionIds())
            .assertNext(ids -> Assert.assertEquals(data.properties.partitionIds().length, ids.length))
            .verifyComplete();
    }

    /**
     * Verifies that we can get partition information for each of the partitions in an Event Hub.
     */
    @Test
    public void getPartitionProperties() {
        skipIfNotRecordMode();

        // Act & Assert
        for (String partitionId : data.properties.partitionIds()) {
            StepVerifier.create(client.getPartitionProperties(partitionId))
                .assertNext(properties -> {
                    final PartitionProperties expected = data.getPartitionProperties(properties.id());
                    Assert.assertNotNull(expected);
                    Assert.assertEquals(expected.eventHubPath(), properties.eventHubPath());
                    Assert.assertEquals(partitionId, properties.id());
                })
                .verifyComplete();
        }
    }

    /**
     * Verifies that we can make multiple service calls one after the other. This is a typical user scenario when
     * consumers want to create a receiver.
     * 1. Gets information about the Event Hub
     * 2. Queries for partition information about each partition.
     */
    @Test
    public void getPartitionPropertiesMultipleCalls() {
        skipIfNotRecordMode();

        // Act
        final Flux<PartitionProperties> partitionProperties = client.getPartitionIds()
            .flatMapMany(ids -> {
                final List<Mono<PartitionProperties>> results = new ArrayList<>();
                for (String id : ids) {
                    results.add(client.getPartitionProperties(id));
                }

                return Flux.merge(results);
            });

        // Assert
        StepVerifier.create(partitionProperties)
            .assertNext(properties -> {
                final PartitionProperties expected = data.getPartitionProperties(properties.id());
                Assert.assertNotNull(expected);
                Assert.assertEquals(expected.eventHubPath(), properties.eventHubPath());
            })
            .assertNext(properties -> {
                final PartitionProperties expected = data.getPartitionProperties(properties.id());
                Assert.assertNotNull(expected);
                Assert.assertEquals(expected.eventHubPath(), properties.eventHubPath());
            })
            .verifyComplete();
    }

    /**
     * Verifies that error conditions are handled for fetching Event Hub metadata.
     */
    @Test
    public void getPartitionPropertiesInvalidToken() throws InvalidKeyException, NoSuchAlgorithmException {
        skipIfNotRecordMode();

        // Arrange
        final CredentialInfo original = getCredentialInfo();
        final CredentialInfo invalidCredentials = getCredentials(original.endpoint(), original.eventHubPath(),
            original.sharedAccessKeyName(), "invalid-sas-key-value");
        final TokenProvider badTokenProvider = new SharedAccessSignatureTokenProvider(invalidCredentials.sharedAccessKeyName(), invalidCredentials.sharedAccessKey());
        final ConnectionParameters connectionParameters = new ConnectionParameters(invalidCredentials, Duration.ofSeconds(45), badTokenProvider,
            TransportType.AMQP, Retry.getNoRetry(), ProxyConfiguration.SYSTEM_DEFAULTS, getConnectionParameters().scheduler());
        final EventHubClient client = new EventHubClient(connectionParameters, getReactorProvider(), handlerProvider);

        // Act & Assert
        StepVerifier.create(client.getProperties())
            .expectErrorSatisfies(error -> {
                Assert.assertTrue(error instanceof AmqpException);

                AmqpException exception = (AmqpException) error;
                Assert.assertEquals(ErrorCondition.UNAUTHORIZED_ACCESS, exception.getErrorCondition());
                Assert.assertFalse(exception.isTransient());
                Assert.assertFalse(ImplUtils.isNullOrEmpty(exception.getMessage()));
            })
            .verify();
    }

    /**
     * Verifies that error conditions are handled for fetching partition metadata.
     */
    @Test
    public void getPartitionPropertiesNonExistentHub() throws InvalidKeyException, NoSuchAlgorithmException {
        skipIfNotRecordMode();

        // Arrange
        final CredentialInfo credentials = getConnectionParameters().credentials();
        final CredentialInfo invalidCredentials = getCredentials(credentials.endpoint(), "nonExistentEventhub",
            credentials.sharedAccessKeyName(), credentials.sharedAccessKey());

        final TokenProvider badTokenProvider = new SharedAccessSignatureTokenProvider(invalidCredentials.sharedAccessKeyName(), invalidCredentials.sharedAccessKey());
        final ConnectionParameters connectionParameters = new ConnectionParameters(invalidCredentials, Duration.ofSeconds(45), badTokenProvider,
            TransportType.AMQP, Retry.getNoRetry(), ProxyConfiguration.SYSTEM_DEFAULTS, getConnectionParameters().scheduler());
        final EventHubClient client = new EventHubClient(connectionParameters, getReactorProvider(), handlerProvider);

        // Act & Assert
        StepVerifier.create(client.getPartitionIds())
            .expectErrorSatisfies(error -> {
                Assert.assertTrue(error instanceof AmqpException);

                AmqpException exception = (AmqpException) error;
                Assert.assertEquals(ErrorCondition.NOT_FOUND, exception.getErrorCondition());
                Assert.assertFalse(exception.isTransient());
                Assert.assertFalse(ImplUtils.isNullOrEmpty(exception.getMessage()));
            })
            .verify();
    }

    /**
     * Verifies that we can create and send a message to an Event Hub partition.
     */
    @Test
    public void sendMessageToPartition() throws IOException {
        skipIfNotRecordMode();

        // Arrange
        final EventSenderOptions senderOptions = new EventSenderOptions().partitionId("0");
        final List<EventData> events = Arrays.asList(
            new EventData("Event 1".getBytes(UTF_8)),
            new EventData("Event 2".getBytes(UTF_8)),
            new EventData("Event 3".getBytes(UTF_8)));

        // Act & Assert
        try (EventSender sender = client.createSender(senderOptions)) {
            StepVerifier.create(sender.send(events))
                .expectComplete()
                .verify();
        }
    }

    /**
     * Verifies that we can create an EventSender that does not care about partitions and lets the service distribute
     * the events.
     */
    @Test
    public void sendMessage() throws IOException {
        skipIfNotRecordMode();

        // Arrange
        final List<EventData> events = Arrays.asList(
            new EventData("Event 1".getBytes(UTF_8)),
            new EventData("Event 2".getBytes(UTF_8)),
            new EventData("Event 3".getBytes(UTF_8)));

        // Act & Assert
        try (EventSender sender = client.createSender()) {
            StepVerifier.create(sender.send(events))
                .expectComplete()
                .verify();
        }
    }

    @Override
    protected String testName() {
        return testName.getMethodName();
    }

    private static CredentialInfo getCredentials(URI endpoint, String eventHubPath, String sasKeyName, String sasKeyValue) {
        final String connectionString = String.format(Locale.ROOT,
            "Endpoint=%s;SharedAccessKeyName=%s;SharedAccessKey=%s;EntityPath=%s;", endpoint.toString(),
            sasKeyName, sasKeyValue, eventHubPath);

        return CredentialInfo.from(connectionString);
    }

    /**
     * Holds expected data based on the test-mode.
     */
    private static class ExpectedData {
        private final EventHubProperties properties;
        private final Map<String, PartitionProperties> partitionPropertiesMap;

        ExpectedData(TestMode testMode, CredentialInfo credentialInfo) {
            final String eventHubPath;
            final String[] partitionIds;
            switch (testMode) {
                case PLAYBACK:
                    eventHubPath = "test-event-hub";
                    partitionIds = new String[]{"test-1", "test-2"};
                    break;
                case RECORD:
                    eventHubPath = credentialInfo.eventHubPath();
                    partitionIds = new String[]{"0", "1"};
                    break;
                default:
                    throw new IllegalArgumentException("Test mode not recognized.");
            }

            final Instant now = Instant.now();
            this.properties = new EventHubProperties(eventHubPath, Instant.EPOCH, partitionIds);
            this.partitionPropertiesMap = new HashMap<>();

            for (int i = 0; i < partitionIds.length; i++) {
                final String key = String.valueOf(i);

                this.partitionPropertiesMap.put(key, new PartitionProperties(
                    eventHubPath, key, -1, -1,
                    "lastEnqueued", Instant.now(), true, now));
            }
        }

        EventHubProperties getProperties() {
            return properties;
        }

        PartitionProperties getPartitionProperties(String id) {
            return partitionPropertiesMap.get(id);
        }
    }
}
