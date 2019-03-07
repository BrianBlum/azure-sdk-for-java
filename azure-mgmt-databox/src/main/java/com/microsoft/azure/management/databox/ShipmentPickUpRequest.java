/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.databox;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Shipment pick up request details.
 */
public class ShipmentPickUpRequest {
    /**
     * Minimum date after which the pick up should commence, this must be in
     * local time of pick up area.
     */
    @JsonProperty(value = "startTime", required = true)
    private DateTime startTime;

    /**
     * Maximum date before which the pick up should commence, this must be in
     * local time of pick up area.
     */
    @JsonProperty(value = "endTime", required = true)
    private DateTime endTime;

    /**
     * Shipment Location in the pickup place. Eg.front desk.
     */
    @JsonProperty(value = "shipmentLocation", required = true)
    private String shipmentLocation;

    /**
     * Get minimum date after which the pick up should commence, this must be in local time of pick up area.
     *
     * @return the startTime value
     */
    public DateTime startTime() {
        return this.startTime;
    }

    /**
     * Set minimum date after which the pick up should commence, this must be in local time of pick up area.
     *
     * @param startTime the startTime value to set
     * @return the ShipmentPickUpRequest object itself.
     */
    public ShipmentPickUpRequest withStartTime(DateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get maximum date before which the pick up should commence, this must be in local time of pick up area.
     *
     * @return the endTime value
     */
    public DateTime endTime() {
        return this.endTime;
    }

    /**
     * Set maximum date before which the pick up should commence, this must be in local time of pick up area.
     *
     * @param endTime the endTime value to set
     * @return the ShipmentPickUpRequest object itself.
     */
    public ShipmentPickUpRequest withEndTime(DateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get shipment Location in the pickup place. Eg.front desk.
     *
     * @return the shipmentLocation value
     */
    public String shipmentLocation() {
        return this.shipmentLocation;
    }

    /**
     * Set shipment Location in the pickup place. Eg.front desk.
     *
     * @param shipmentLocation the shipmentLocation value to set
     * @return the ShipmentPickUpRequest object itself.
     */
    public ShipmentPickUpRequest withShipmentLocation(String shipmentLocation) {
        this.shipmentLocation = shipmentLocation;
        return this;
    }

}