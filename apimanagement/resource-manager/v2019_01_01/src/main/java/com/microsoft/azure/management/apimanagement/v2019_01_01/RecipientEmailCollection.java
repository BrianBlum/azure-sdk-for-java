/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.v2019_01_01;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.apimanagement.v2019_01_01.implementation.ApiManagementManager;
import com.microsoft.azure.management.apimanagement.v2019_01_01.implementation.RecipientEmailCollectionInner;
import com.microsoft.azure.management.apimanagement.v2019_01_01.implementation.RecipientEmailContractInner;
import java.util.List;

/**
 * Type representing RecipientEmailCollection.
 */
public interface RecipientEmailCollection extends HasInner<RecipientEmailCollectionInner>, HasManager<ApiManagementManager> {
    /**
     * @return the nextLink value.
     */
    String nextLink();

    /**
     * @return the value value.
     */
    List<RecipientEmailContractInner> value();

}
