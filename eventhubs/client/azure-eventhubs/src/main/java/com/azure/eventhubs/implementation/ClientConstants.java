// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.eventhubs.implementation;

import java.time.Duration;

public final class ClientConstants {
    public static final String NOT_APPLICABLE = "n/a";
    public static final int HTTPS_PORT = 443;

    public static final int MAX_EVENTHUB_AMQP_HEADER_SIZE_BYTES = 512;
    public static final Duration TOKEN_REFRESH_INTERVAL = Duration.ofMinutes(5); // renew every 5 minutes, which expires 20 minutes
    public static final Duration TOKEN_VALIDITY = Duration.ofMinutes(20);
    public static final int SERVER_BUSY_BASE_SLEEP_TIME_IN_SECS = 4;
    public static final int MGMT_CHANNEL_MIN_RETRY_IN_MILLIS = 5;
    public static final String PRODUCT_NAME = "MSJavaClient";
    public static final String CURRENT_JAVACLIENT_VERSION = "2.3.1";
    public static final String PLATFORM_INFO = getOSInformation();
    public static final String FRAMEWORK_INFO = getFrameworkInfo();

    /**
     * Gets the USER AGENT string as defined in:
     * $/core/azure-core/src/main/java/com/azure/core/http/policy/UserAgentPolicy.java
     * TODO (conniey): Extract logic from UserAgentPolicy into something we can use here.
     */
    public static final String USER_AGENT = String.format("azsdk-java-eventhubs/%s %s;%s",
        CURRENT_JAVACLIENT_VERSION, System.getProperty("java.version"), PLATFORM_INFO);
    public static final String MANAGEMENT_PARTITION_ENTITY_TYPE = AmqpConstants.VENDOR + ":partition";
    public static final String MANAGEMENT_STATUS_CODE_KEY = "status-code";
    public static final String MANAGEMENT_STATUS_DESCRIPTION_KEY = "status-description";
    public static final String MANAGEMENT_RESPONSE_ERROR_CONDITION = "error-condition";
    public static final String TOKEN_AUDIENCE_FORMAT = "amqp://%s/%s";
    public static final String HTTPS_URI_FORMAT = "https://%s:%s";
    public static final String ENDPOINT_FORMAT = "sb://%s.%s";

    public static final String COMMUNICATION_EXCEPTION_GENERIC_MESSAGE = "A communication error has occurred. "
        + "This may be due to an incorrect host name in your connection string or a problem with your network connection.";

    private ClientConstants() {
    }

    private static String getOSInformation() {
        return String.join(" ", System.getProperty("os.name"), System.getProperty("os.version"));
    }

    private static String getFrameworkInfo() {
        final Package javaRuntimeClassPkg = Runtime.class.getPackage();

        return "jre:" + javaRuntimeClassPkg.getImplementationVersion()
            + ";vendor:" + javaRuntimeClassPkg.getImplementationVendor()
            + ";jvm" + System.getProperty("java.vm.version");
    }
}
