/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.iothub.v2018_12_01_preview;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.iothub.v2018_12_01_preview.implementation.DevicesManager;
import com.microsoft.azure.management.iothub.v2018_12_01_preview.implementation.CertificateListDescriptionInner;
import com.microsoft.azure.management.iothub.v2018_12_01_preview.implementation.CertificateDescriptionInner;
import java.util.List;

/**
 * Type representing CertificateListDescription.
 */
public interface CertificateListDescription extends HasInner<CertificateListDescriptionInner>, HasManager<DevicesManager> {
    /**
     * @return the value value.
     */
    List<CertificateDescriptionInner> value();

}