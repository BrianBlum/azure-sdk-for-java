/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datafactoryv2.v2017_09_01_preview;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A copy activity Azure Search Index sink.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("AzureSearchIndexSink")
public class AzureSearchIndexSink extends CopySink {
    /**
     * Specify the write behavior when upserting documents into Azure Search
     * Index.
     */
    @JsonProperty(value = "writeBehavior")
    private Object writeBehavior;

    /**
     * Get specify the write behavior when upserting documents into Azure Search Index.
     *
     * @return the writeBehavior value
     */
    public Object writeBehavior() {
        return this.writeBehavior;
    }

    /**
     * Set specify the write behavior when upserting documents into Azure Search Index.
     *
     * @param writeBehavior the writeBehavior value to set
     * @return the AzureSearchIndexSink object itself.
     */
    public AzureSearchIndexSink withWriteBehavior(Object writeBehavior) {
        this.writeBehavior = writeBehavior;
        return this;
    }

}
