/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.redis.v2018_03_01;

import com.microsoft.azure.arm.collection.SupportsCreating;
import rx.Completable;
import rx.Observable;
import com.microsoft.azure.management.redis.v2018_03_01.implementation.FirewallRulesInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing FirewallRules.
 */
public interface FirewallRules extends SupportsCreating<RedisFirewallRule.DefinitionStages.Blank>, HasInner<FirewallRulesInner> {
    /**
     * Gets a single firewall rule in a specified redis cache.
     *
     * @param resourceGroupName The name of the resource group.
     * @param cacheName The name of the Redis cache.
     * @param ruleName The name of the firewall rule.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<RedisFirewallRule> getAsync(String resourceGroupName, String cacheName, String ruleName);

    /**
     * Gets all firewall rules in the specified redis cache.
     *
     * @param resourceGroupName The name of the resource group.
     * @param cacheName The name of the Redis cache.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<RedisFirewallRule> listByRedisResourceAsync(final String resourceGroupName, final String cacheName);

    /**
     * Deletes a single firewall rule in a specified redis cache.
     *
     * @param resourceGroupName The name of the resource group.
     * @param cacheName The name of the Redis cache.
     * @param ruleName The name of the firewall rule.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Completable deleteAsync(String resourceGroupName, String cacheName, String ruleName);

}
