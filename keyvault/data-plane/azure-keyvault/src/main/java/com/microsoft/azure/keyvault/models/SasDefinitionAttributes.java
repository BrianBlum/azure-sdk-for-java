// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator

package com.microsoft.azure.keyvault.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The SAS definition management attributes.
 */
public class SasDefinitionAttributes {
    /**
     * the enabled state of the object.
     */
    @JsonProperty(value = "enabled")
    private Boolean enabled;

    /**
     * Creation time in UTC.
     */
    @JsonProperty(value = "created", access = JsonProperty.Access.WRITE_ONLY)
    private Long created;

    /**
     * Last updated time in UTC.
     */
    @JsonProperty(value = "updated", access = JsonProperty.Access.WRITE_ONLY)
    private Long updated;

    /**
     * Reflects the deletion recovery level currently in effect for SAS
     * definitions in the current vault. If it contains 'Purgeable' the SAS
     * definition can be permanently deleted by a privileged user; otherwise,
     * only the system can purge the SAS definition, at the end of the
     * retention interval. Possible values include: 'Purgeable',
     * 'Recoverable+Purgeable', 'Recoverable',
     * 'Recoverable+ProtectedSubscription'.
     */
    @JsonProperty(value = "recoveryLevel", access = JsonProperty.Access.WRITE_ONLY)
    private DeletionRecoveryLevel recoveryLevel;

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the SasDefinitionAttributes object itself.
     */
    public SasDefinitionAttributes withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the created value.
     *
     * @return the created value
     */
    public DateTime created() {
        if (this.created == null) {
            return null;
        }
        return new DateTime(this.created * 1000L, DateTimeZone.UTC);
    }

    /**
     * Get the updated value.
     *
     * @return the updated value
     */
    public DateTime updated() {
        if (this.updated == null) {
            return null;
        }
        return new DateTime(this.updated * 1000L, DateTimeZone.UTC);
    }

    /**
     * Get the recoveryLevel value.
     *
     * @return the recoveryLevel value
     */
    public DeletionRecoveryLevel recoveryLevel() {
        return this.recoveryLevel;
    }

}
