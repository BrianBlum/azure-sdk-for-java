/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.advisor.v2017_04_19.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The list of metadata entities.
 */
public class MetadataEntityListResultInner {
    /**
     * The list of metadata entities.
     */
    @JsonProperty(value = "value")
    private List<MetadataEntityInner> value;

    /**
     * Get the list of metadata entities.
     *
     * @return the value value
     */
    public List<MetadataEntityInner> value() {
        return this.value;
    }

    /**
     * Set the list of metadata entities.
     *
     * @param value the value value to set
     * @return the MetadataEntityListResultInner object itself.
     */
    public MetadataEntityListResultInner withValue(List<MetadataEntityInner> value) {
        this.value = value;
        return this;
    }

}