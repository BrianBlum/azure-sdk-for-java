/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datafactoryv2.v2018_06_01;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for SftpAuthenticationType.
 */
public final class SftpAuthenticationType extends ExpandableStringEnum<SftpAuthenticationType> {
    /** Static value Basic for SftpAuthenticationType. */
    public static final SftpAuthenticationType BASIC = fromString("Basic");

    /** Static value SshPublicKey for SftpAuthenticationType. */
    public static final SftpAuthenticationType SSH_PUBLIC_KEY = fromString("SshPublicKey");

    /**
     * Creates or finds a SftpAuthenticationType from its string representation.
     * @param name a name to look for
     * @return the corresponding SftpAuthenticationType
     */
    @JsonCreator
    public static SftpAuthenticationType fromString(String name) {
        return fromString(name, SftpAuthenticationType.class);
    }

    /**
     * @return known SftpAuthenticationType values
     */
    public static Collection<SftpAuthenticationType> values() {
        return values(SftpAuthenticationType.class);
    }
}