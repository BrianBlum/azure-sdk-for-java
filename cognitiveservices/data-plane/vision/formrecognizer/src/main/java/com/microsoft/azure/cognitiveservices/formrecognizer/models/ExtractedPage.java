/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.cognitiveservices.formrecognizer.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Extraction information of a single page in a
 * with a document.
 */
public class ExtractedPage {
    /**
     * Page number.
     */
    @JsonProperty(value = "number")
    private Integer number;

    /**
     * Height of the page (in pixels).
     */
    @JsonProperty(value = "height")
    private Integer height;

    /**
     * Width of the page (in pixels).
     */
    @JsonProperty(value = "width")
    private Integer width;

    /**
     * Cluster identifier.
     */
    @JsonProperty(value = "clusterId")
    private Integer clusterId;

    /**
     * List of Key-Value pairs extracted from the page.
     */
    @JsonProperty(value = "keyValuePairs")
    private List<ExtractedKeyValuePair> keyValuePairs;

    /**
     * List of Tables and their information extracted from the page.
     */
    @JsonProperty(value = "tables")
    private List<ExtractedTable> tables;

    /**
     * Get page number.
     *
     * @return the number value
     */
    public Integer number() {
        return this.number;
    }

    /**
     * Set page number.
     *
     * @param number the number value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withNumber(Integer number) {
        this.number = number;
        return this;
    }

    /**
     * Get height of the page (in pixels).
     *
     * @return the height value
     */
    public Integer height() {
        return this.height;
    }

    /**
     * Set height of the page (in pixels).
     *
     * @param height the height value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withHeight(Integer height) {
        this.height = height;
        return this;
    }

    /**
     * Get width of the page (in pixels).
     *
     * @return the width value
     */
    public Integer width() {
        return this.width;
    }

    /**
     * Set width of the page (in pixels).
     *
     * @param width the width value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withWidth(Integer width) {
        this.width = width;
        return this;
    }

    /**
     * Get cluster identifier.
     *
     * @return the clusterId value
     */
    public Integer clusterId() {
        return this.clusterId;
    }

    /**
     * Set cluster identifier.
     *
     * @param clusterId the clusterId value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withClusterId(Integer clusterId) {
        this.clusterId = clusterId;
        return this;
    }

    /**
     * Get list of Key-Value pairs extracted from the page.
     *
     * @return the keyValuePairs value
     */
    public List<ExtractedKeyValuePair> keyValuePairs() {
        return this.keyValuePairs;
    }

    /**
     * Set list of Key-Value pairs extracted from the page.
     *
     * @param keyValuePairs the keyValuePairs value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withKeyValuePairs(List<ExtractedKeyValuePair> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
        return this;
    }

    /**
     * Get list of Tables and their information extracted from the page.
     *
     * @return the tables value
     */
    public List<ExtractedTable> tables() {
        return this.tables;
    }

    /**
     * Set list of Tables and their information extracted from the page.
     *
     * @param tables the tables value to set
     * @return the ExtractedPage object itself.
     */
    public ExtractedPage withTables(List<ExtractedTable> tables) {
        this.tables = tables;
        return this;
    }

}