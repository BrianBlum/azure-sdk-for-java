/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.notificationhubs.v2014_09_01.implementation;

import com.microsoft.azure.arm.resources.models.implementation.GroupableResourceCoreImpl;
import com.microsoft.azure.management.notificationhubs.v2014_09_01.NamespaceResource;
import rx.Observable;
import com.microsoft.azure.management.notificationhubs.v2014_09_01.NamespaceCreateOrUpdateParameters;
import com.microsoft.azure.management.notificationhubs.v2014_09_01.NamespaceProperties;
import rx.functions.Func1;

class NamespaceResourceImpl extends GroupableResourceCoreImpl<NamespaceResource, NamespaceResourceInner, NamespaceResourceImpl, NotificationHubsManager> implements NamespaceResource, NamespaceResource.Definition, NamespaceResource.Update {
    private NamespaceCreateOrUpdateParameters createOrUpdateParameter;
    NamespaceResourceImpl(String name, NamespaceResourceInner inner, NotificationHubsManager manager) {
        super(name, inner, manager);
        this.createOrUpdateParameter = new NamespaceCreateOrUpdateParameters();
    }

    @Override
    public Observable<NamespaceResource> createResourceAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        this.createOrUpdateParameter.withLocation(inner().location());
        this.createOrUpdateParameter.withTags(inner().getTags());
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.createOrUpdateParameter)
            .map(new Func1<NamespaceResourceInner, NamespaceResourceInner>() {
               @Override
               public NamespaceResourceInner call(NamespaceResourceInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<NamespaceResource> updateResourceAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.createOrUpdateParameter)
            .map(new Func1<NamespaceResourceInner, NamespaceResourceInner>() {
               @Override
               public NamespaceResourceInner call(NamespaceResourceInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<NamespaceResourceInner> getInnerAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        return client.getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    private void resetCreateUpdateParameters() {
        this.createOrUpdateParameter = new NamespaceCreateOrUpdateParameters();
    }

    @Override
    public NamespaceProperties properties() {
        return this.inner().properties();
    }

    @Override
    public NamespaceResourceImpl withProperties(NamespaceProperties properties) {
        this.createOrUpdateParameter.withProperties(properties);
        return this;
    }

}
