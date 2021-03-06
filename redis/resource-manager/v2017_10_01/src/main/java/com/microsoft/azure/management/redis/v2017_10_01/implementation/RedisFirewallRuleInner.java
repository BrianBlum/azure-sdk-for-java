/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.redis.v2017_10_01.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.ProxyResource;

/**
 * A firewall rule on a redis cache has a name, and describes a contiguous
 * range of IP addresses permitted to connect.
 */
@JsonFlatten
public class RedisFirewallRuleInner extends ProxyResource {
    /**
     * lowest IP address included in the range.
     */
    @JsonProperty(value = "properties.startIP", required = true)
    private String startIP;

    /**
     * highest IP address included in the range.
     */
    @JsonProperty(value = "properties.endIP", required = true)
    private String endIP;

    /**
     * Get the startIP value.
     *
     * @return the startIP value
     */
    public String startIP() {
        return this.startIP;
    }

    /**
     * Set the startIP value.
     *
     * @param startIP the startIP value to set
     * @return the RedisFirewallRuleInner object itself.
     */
    public RedisFirewallRuleInner withStartIP(String startIP) {
        this.startIP = startIP;
        return this;
    }

    /**
     * Get the endIP value.
     *
     * @return the endIP value
     */
    public String endIP() {
        return this.endIP;
    }

    /**
     * Set the endIP value.
     *
     * @param endIP the endIP value to set
     * @return the RedisFirewallRuleInner object itself.
     */
    public RedisFirewallRuleInner withEndIP(String endIP) {
        this.endIP = endIP;
        return this;
    }

}
