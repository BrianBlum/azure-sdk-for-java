/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.kusto.v2019_01_21;

import com.microsoft.azure.arm.collection.SupportsCreating;
import rx.Completable;
import rx.Observable;
import com.microsoft.azure.management.kusto.v2019_01_21.implementation.AttachedDatabaseConfigurationsInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing AttachedDatabaseConfigurations.
 */
public interface AttachedDatabaseConfigurations extends SupportsCreating<AttachedDatabaseConfiguration.DefinitionStages.Blank>, HasInner<AttachedDatabaseConfigurationsInner> {
    /**
     * Returns an attached database configuration.
     *
     * @param resourceGroupName The name of the resource group containing the Kusto cluster.
     * @param clusterName The name of the Kusto cluster.
     * @param attachedDatabaseConfigurationName The name of the attached database configuration.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<AttachedDatabaseConfiguration> getAsync(String resourceGroupName, String clusterName, String attachedDatabaseConfigurationName);

    /**
     * Deletes the attached database configuration with the given name.
     *
     * @param resourceGroupName The name of the resource group containing the Kusto cluster.
     * @param clusterName The name of the Kusto cluster.
     * @param attachedDatabaseConfigurationName The name of the attached database configuration.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Completable deleteAsync(String resourceGroupName, String clusterName, String attachedDatabaseConfigurationName);

}
