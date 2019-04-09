/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.deploymentmanager.v2018-09-01-preview.implementation;

import com.microsoft.azure.arm.collection.InnerSupportsGet;
import com.microsoft.azure.arm.collection.InnerSupportsDelete;
import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.CloudException;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import com.microsoft.rest.Validator;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in ArtifactSources.
 */
public class ArtifactSourcesInner implements InnerSupportsGet<ArtifactSourceInner>, InnerSupportsDelete<Void> {
    /** The Retrofit service to perform REST calls. */
    private ArtifactSourcesService service;
    /** The service client containing this operation class. */
    private AzureDeploymentManagerImpl client;

    /**
     * Initializes an instance of ArtifactSourcesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ArtifactSourcesInner(Retrofit retrofit, AzureDeploymentManagerImpl client) {
        this.service = retrofit.create(ArtifactSourcesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for ArtifactSources to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ArtifactSourcesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.deploymentmanager.v2018-09-01-preview.ArtifactSources createOrUpdate" })
        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DeploymentManager/artifactSources/{artifactSourceName}")
        Observable<Response<ResponseBody>> createOrUpdate(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("artifactSourceName") String artifactSourceName, @Query("api-version") String apiVersion, @Body ArtifactSourceInner artifactSourceInfo, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.deploymentmanager.v2018-09-01-preview.ArtifactSources getByResourceGroup" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DeploymentManager/artifactSources/{artifactSourceName}")
        Observable<Response<ResponseBody>> getByResourceGroup(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("artifactSourceName") String artifactSourceName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.deploymentmanager.v2018-09-01-preview.ArtifactSources delete" })
        @HTTP(path = "subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DeploymentManager/artifactSources/{artifactSourceName}", method = "DELETE", hasBody = true)
        Observable<Response<ResponseBody>> delete(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("artifactSourceName") String artifactSourceName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ArtifactSourceInner object if successful.
     */
    public ArtifactSourceInner createOrUpdate(String resourceGroupName, String artifactSourceName) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName).toBlocking().single().body();
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ArtifactSourceInner> createOrUpdateAsync(String resourceGroupName, String artifactSourceName, final ServiceCallback<ArtifactSourceInner> serviceCallback) {
        return ServiceFuture.fromResponse(createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName), serviceCallback);
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ArtifactSourceInner> createOrUpdateAsync(String resourceGroupName, String artifactSourceName) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName).map(new Func1<ServiceResponse<ArtifactSourceInner>, ArtifactSourceInner>() {
            @Override
            public ArtifactSourceInner call(ServiceResponse<ArtifactSourceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ServiceResponse<ArtifactSourceInner>> createOrUpdateWithServiceResponseAsync(String resourceGroupName, String artifactSourceName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (artifactSourceName == null) {
            throw new IllegalArgumentException("Parameter artifactSourceName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final ArtifactSourceInner artifactSourceInfo = null;
        return service.createOrUpdate(this.client.subscriptionId(), resourceGroupName, artifactSourceName, this.client.apiVersion(), artifactSourceInfo, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ArtifactSourceInner>>>() {
                @Override
                public Observable<ServiceResponse<ArtifactSourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ArtifactSourceInner> clientResponse = createOrUpdateDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param artifactSourceInfo Source object that defines the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ArtifactSourceInner object if successful.
     */
    public ArtifactSourceInner createOrUpdate(String resourceGroupName, String artifactSourceName, ArtifactSourceInner artifactSourceInfo) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName, artifactSourceInfo).toBlocking().single().body();
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param artifactSourceInfo Source object that defines the resource.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ArtifactSourceInner> createOrUpdateAsync(String resourceGroupName, String artifactSourceName, ArtifactSourceInner artifactSourceInfo, final ServiceCallback<ArtifactSourceInner> serviceCallback) {
        return ServiceFuture.fromResponse(createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName, artifactSourceInfo), serviceCallback);
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param artifactSourceInfo Source object that defines the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ArtifactSourceInner> createOrUpdateAsync(String resourceGroupName, String artifactSourceName, ArtifactSourceInner artifactSourceInfo) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, artifactSourceName, artifactSourceInfo).map(new Func1<ServiceResponse<ArtifactSourceInner>, ArtifactSourceInner>() {
            @Override
            public ArtifactSourceInner call(ServiceResponse<ArtifactSourceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Creates or updates an artifact source.
     * Synchronously creates a new artifact source or updates an existing artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param artifactSourceInfo Source object that defines the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ServiceResponse<ArtifactSourceInner>> createOrUpdateWithServiceResponseAsync(String resourceGroupName, String artifactSourceName, ArtifactSourceInner artifactSourceInfo) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (artifactSourceName == null) {
            throw new IllegalArgumentException("Parameter artifactSourceName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        Validator.validate(artifactSourceInfo);
        return service.createOrUpdate(this.client.subscriptionId(), resourceGroupName, artifactSourceName, this.client.apiVersion(), artifactSourceInfo, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ArtifactSourceInner>>>() {
                @Override
                public Observable<ServiceResponse<ArtifactSourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ArtifactSourceInner> clientResponse = createOrUpdateDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ArtifactSourceInner> createOrUpdateDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ArtifactSourceInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(201, new TypeToken<ArtifactSourceInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ArtifactSourceInner object if successful.
     */
    public ArtifactSourceInner getByResourceGroup(String resourceGroupName, String artifactSourceName) {
        return getByResourceGroupWithServiceResponseAsync(resourceGroupName, artifactSourceName).toBlocking().single().body();
    }

    /**
     * Gets an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ArtifactSourceInner> getByResourceGroupAsync(String resourceGroupName, String artifactSourceName, final ServiceCallback<ArtifactSourceInner> serviceCallback) {
        return ServiceFuture.fromResponse(getByResourceGroupWithServiceResponseAsync(resourceGroupName, artifactSourceName), serviceCallback);
    }

    /**
     * Gets an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ArtifactSourceInner> getByResourceGroupAsync(String resourceGroupName, String artifactSourceName) {
        return getByResourceGroupWithServiceResponseAsync(resourceGroupName, artifactSourceName).map(new Func1<ServiceResponse<ArtifactSourceInner>, ArtifactSourceInner>() {
            @Override
            public ArtifactSourceInner call(ServiceResponse<ArtifactSourceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ArtifactSourceInner object
     */
    public Observable<ServiceResponse<ArtifactSourceInner>> getByResourceGroupWithServiceResponseAsync(String resourceGroupName, String artifactSourceName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (artifactSourceName == null) {
            throw new IllegalArgumentException("Parameter artifactSourceName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.getByResourceGroup(this.client.subscriptionId(), resourceGroupName, artifactSourceName, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ArtifactSourceInner>>>() {
                @Override
                public Observable<ServiceResponse<ArtifactSourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ArtifactSourceInner> clientResponse = getByResourceGroupDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ArtifactSourceInner> getByResourceGroupDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ArtifactSourceInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ArtifactSourceInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Deletes an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void delete(String resourceGroupName, String artifactSourceName) {
        deleteWithServiceResponseAsync(resourceGroupName, artifactSourceName).toBlocking().single().body();
    }

    /**
     * Deletes an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> deleteAsync(String resourceGroupName, String artifactSourceName, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromResponse(deleteWithServiceResponseAsync(resourceGroupName, artifactSourceName), serviceCallback);
    }

    /**
     * Deletes an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<Void> deleteAsync(String resourceGroupName, String artifactSourceName) {
        return deleteWithServiceResponseAsync(resourceGroupName, artifactSourceName).map(new Func1<ServiceResponse<Void>, Void>() {
            @Override
            public Void call(ServiceResponse<Void> response) {
                return response.body();
            }
        });
    }

    /**
     * Deletes an artifact source.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param artifactSourceName The name of the artifact source.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<ServiceResponse<Void>> deleteWithServiceResponseAsync(String resourceGroupName, String artifactSourceName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (artifactSourceName == null) {
            throw new IllegalArgumentException("Parameter artifactSourceName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.delete(this.client.subscriptionId(), resourceGroupName, artifactSourceName, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Void>>>() {
                @Override
                public Observable<ServiceResponse<Void>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<Void> clientResponse = deleteDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<Void> deleteDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<Void, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<Void>() { }.getType())
                .register(204, new TypeToken<Void>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}