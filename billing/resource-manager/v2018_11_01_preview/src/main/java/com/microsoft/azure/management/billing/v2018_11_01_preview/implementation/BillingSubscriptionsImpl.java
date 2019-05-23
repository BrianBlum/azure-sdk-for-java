/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 *
 */

package com.microsoft.azure.management.billing.v2018_11_01_preview.implementation;

import com.microsoft.azure.arm.model.implementation.WrapperImpl;
import com.microsoft.azure.management.billing.v2018_11_01_preview.BillingSubscriptions;
import rx.Observable;
import rx.functions.Func1;
import com.microsoft.azure.Page;
import com.microsoft.azure.management.billing.v2018_11_01_preview.BillingSubscriptionsListResult;
import com.microsoft.azure.management.billing.v2018_11_01_preview.BillingSubscriptionSummary;
import com.microsoft.azure.management.billing.v2018_11_01_preview.TransferBillingSubscriptionResult;
import com.microsoft.azure.management.billing.v2018_11_01_preview.TransferBillingSubscriptionRequestProperties;

class BillingSubscriptionsImpl extends WrapperImpl<BillingSubscriptionsInner> implements BillingSubscriptions {
    private final BillingManager manager;

    BillingSubscriptionsImpl(BillingManager manager) {
        super(manager.inner().billingSubscriptions());
        this.manager = manager;
    }

    public BillingManager manager() {
        return this.manager;
    }

    private BillingSubscriptionSummaryImpl wrapModel(BillingSubscriptionSummaryInner inner) {
        return  new BillingSubscriptionSummaryImpl(inner, manager());
    }

    @Override
    public Observable<BillingSubscriptionsListResult> listByBillingProfileNameAsync(String billingAccountName, String billingProfileName) {
        BillingSubscriptionsInner client = this.inner();
        return client.listByBillingProfileNameAsync(billingAccountName, billingProfileName)
        .map(new Func1<BillingSubscriptionsListResultInner, BillingSubscriptionsListResult>() {
            @Override
            public BillingSubscriptionsListResult call(BillingSubscriptionsListResultInner inner) {
                return new BillingSubscriptionsListResultImpl(inner, manager());
            }
        });
    }

    @Override
    public Observable<BillingSubscriptionsListResult> listByInvoiceSectionNameAsync(String billingAccountName, String invoiceSectionName) {
        BillingSubscriptionsInner client = this.inner();
        return client.listByInvoiceSectionNameAsync(billingAccountName, invoiceSectionName)
        .map(new Func1<BillingSubscriptionsListResultInner, BillingSubscriptionsListResult>() {
            @Override
            public BillingSubscriptionsListResult call(BillingSubscriptionsListResultInner inner) {
                return new BillingSubscriptionsListResultImpl(inner, manager());
            }
        });
    }

    @Override
    public Observable<BillingSubscriptionSummary> getAsync(String billingAccountName, String invoiceSectionName, String billingSubscriptionName) {
        BillingSubscriptionsInner client = this.inner();
        return client.getAsync(billingAccountName, invoiceSectionName, billingSubscriptionName)
        .map(new Func1<BillingSubscriptionSummaryInner, BillingSubscriptionSummary>() {
            @Override
            public BillingSubscriptionSummary call(BillingSubscriptionSummaryInner inner) {
                return new BillingSubscriptionSummaryImpl(inner, manager());
            }
        });
    }

    @Override
    public Observable<TransferBillingSubscriptionResult> transferAsync(String billingAccountName, String invoiceSectionName, String billingSubscriptionName, TransferBillingSubscriptionRequestProperties parameters) {
        BillingSubscriptionsInner client = this.inner();
        return client.transferAsync(billingAccountName, invoiceSectionName, billingSubscriptionName, parameters)
        .map(new Func1<TransferBillingSubscriptionResultInner, TransferBillingSubscriptionResult>() {
            @Override
            public TransferBillingSubscriptionResult call(TransferBillingSubscriptionResultInner inner) {
                return new TransferBillingSubscriptionResultImpl(inner, manager());
            }
        });
    }

    @Override
    public Observable<BillingSubscriptionSummary> listByBillingAccountNameAsync(final String billingAccountName) {
        BillingSubscriptionsInner client = this.inner();
        return client.listByBillingAccountNameAsync(billingAccountName)
        .flatMapIterable(new Func1<Page<BillingSubscriptionSummaryInner>, Iterable<BillingSubscriptionSummaryInner>>() {
            @Override
            public Iterable<BillingSubscriptionSummaryInner> call(Page<BillingSubscriptionSummaryInner> page) {
                return page.items();
            }
        })
        .map(new Func1<BillingSubscriptionSummaryInner, BillingSubscriptionSummary>() {
            @Override
            public BillingSubscriptionSummary call(BillingSubscriptionSummaryInner inner) {
                return wrapModel(inner);
            }
        });
    }

}