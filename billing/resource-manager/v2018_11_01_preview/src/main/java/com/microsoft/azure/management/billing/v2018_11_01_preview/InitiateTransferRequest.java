/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.billing.v2018_11_01_preview;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Request parameters to initiate transfer.
 */
@JsonFlatten
public class InitiateTransferRequest {
    /**
     * Target Usage context for devTest subscriptions.
     */
    @JsonProperty(value = "properties.billingProfileId")
    private String billingProfileId;

    /**
     * Email Id of recipient for transfer.
     */
    @JsonProperty(value = "properties.recipientEmailId")
    private String recipientEmailId;

    /**
     * Get target Usage context for devTest subscriptions.
     *
     * @return the billingProfileId value
     */
    public String billingProfileId() {
        return this.billingProfileId;
    }

    /**
     * Set target Usage context for devTest subscriptions.
     *
     * @param billingProfileId the billingProfileId value to set
     * @return the InitiateTransferRequest object itself.
     */
    public InitiateTransferRequest withBillingProfileId(String billingProfileId) {
        this.billingProfileId = billingProfileId;
        return this;
    }

    /**
     * Get email Id of recipient for transfer.
     *
     * @return the recipientEmailId value
     */
    public String recipientEmailId() {
        return this.recipientEmailId;
    }

    /**
     * Set email Id of recipient for transfer.
     *
     * @param recipientEmailId the recipientEmailId value to set
     * @return the InitiateTransferRequest object itself.
     */
    public InitiateTransferRequest withRecipientEmailId(String recipientEmailId) {
        this.recipientEmailId = recipientEmailId;
        return this;
    }

}