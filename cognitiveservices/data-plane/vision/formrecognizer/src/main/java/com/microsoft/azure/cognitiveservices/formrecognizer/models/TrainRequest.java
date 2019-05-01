/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.cognitiveservices.formrecognizer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contract to initiate a train request.
 */
public class TrainRequest {
    /**
     * Get or set source path.
     */
    @JsonProperty(value = "source", required = true)
    private String source;

    /**
     * Get get or set source path.
     *
     * @return the source value
     */
    public String source() {
        return this.source;
    }

    /**
     * Set get or set source path.
     *
     * @param source the source value to set
     * @return the TrainRequest object itself.
     */
    public TrainRequest withSource(String source) {
        this.source = source;
        return this;
    }

}