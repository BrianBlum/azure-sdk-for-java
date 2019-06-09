/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 *
 */

package com.microsoft.azure.management.network.v2019_04_01.implementation;

import com.microsoft.azure.arm.model.implementation.WrapperImpl;
import com.microsoft.azure.management.network.v2019_04_01.ExpressRouteLinks;
import rx.Observable;
import rx.functions.Func1;
import com.microsoft.azure.Page;
import com.microsoft.azure.management.network.v2019_04_01.ExpressRouteLink;

class ExpressRouteLinksImpl extends WrapperImpl<ExpressRouteLinksInner> implements ExpressRouteLinks {
    private final NetworkManager manager;

    ExpressRouteLinksImpl(NetworkManager manager) {
        super(manager.inner().expressRouteLinks());
        this.manager = manager;
    }

    public NetworkManager manager() {
        return this.manager;
    }

    private ExpressRouteLinkImpl wrapModel(ExpressRouteLinkInner inner) {
        return  new ExpressRouteLinkImpl(inner, manager());
    }

    @Override
    public Observable<ExpressRouteLink> listAsync(final String resourceGroupName, final String expressRoutePortName) {
        ExpressRouteLinksInner client = this.inner();
        return client.listAsync(resourceGroupName, expressRoutePortName)
        .flatMapIterable(new Func1<Page<ExpressRouteLinkInner>, Iterable<ExpressRouteLinkInner>>() {
            @Override
            public Iterable<ExpressRouteLinkInner> call(Page<ExpressRouteLinkInner> page) {
                return page.items();
            }
        })
        .map(new Func1<ExpressRouteLinkInner, ExpressRouteLink>() {
            @Override
            public ExpressRouteLink call(ExpressRouteLinkInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public Observable<ExpressRouteLink> getAsync(String resourceGroupName, String expressRoutePortName, String linkName) {
        ExpressRouteLinksInner client = this.inner();
        return client.getAsync(resourceGroupName, expressRoutePortName, linkName)
        .map(new Func1<ExpressRouteLinkInner, ExpressRouteLink>() {
            @Override
            public ExpressRouteLink call(ExpressRouteLinkInner inner) {
                return wrapModel(inner);
            }
       });
    }

}
