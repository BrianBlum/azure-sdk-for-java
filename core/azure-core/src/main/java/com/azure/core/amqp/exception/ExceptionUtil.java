// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.amqp.exception;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to map AMQP status codes and error conditions to an exception.
 */
public final class ExceptionUtil {
    private static final String AMQP_REQUEST_FAILED_ERROR = "status-code: %s, status-description: %s";
    private static final Pattern ENTITY_NOT_FOUND_PATTERN = Pattern.compile("The messaging entity .* could not be found");

    /**
     * Creates an {@link AmqpException} or Exception based on the {@code errorCondition} from the AMQP request.
     *
     * @param errorCondition The error condition string.
     * @param description The error message.
     * @return An exception that maps to the {@code errorCondition} provided.
     * @see ErrorCondition
     */
    public static Exception toException(String errorCondition, String description) {
        if (errorCondition == null) {
            throw new IllegalArgumentException("'null' errorCondition cannot be translated to EventHubException");
        }

        final ErrorCondition condition = ErrorCondition.fromString(errorCondition);

        if (condition == null) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "'%s' is not a known ErrorCondition.", errorCondition));
        }

        boolean isTransient;
        switch (condition) {
            case TIMEOUT_ERROR:
            case SERVER_BUSY_ERROR:
            case INTERNAL_ERROR:
            case LINK_DETACH_FORCED:
                isTransient = true;
                break;
            case ENTITY_DISABLED_ERROR:
            case LINK_STOLEN:
            case UNAUTHORIZED_ACCESS:
            case LINK_PAYLOAD_SIZE_EXCEEDED:
            case ARGUMENT_ERROR:
            case ARGUMENT_OUT_OF_RANGE_ERROR:
            case PARTITION_NOT_OWNED_ERROR:
            case STORE_LOCK_LOST_ERROR:
            case RESOURCE_LIMIT_EXCEEDED:
                isTransient = false;
                break;
            case NOT_IMPLEMENTED:
            case NOT_ALLOWED:
                return new UnsupportedOperationException(description);
            case NOT_FOUND:
                return distinguishNotFound(description);
            default:
                throw new IllegalArgumentException(String.format(Locale.ROOT, "This condition '%s' is not known.", condition));
        }

        return new AmqpException(isTransient, condition, description);
    }

    /**
     * Given an AMQP response code, it maps it to an exception.
     *
     * @param statusCode AMQP response code.
     * @param statusDescription Message associated with response.
     * @return An exception that maps to that status code.
     */
    public static Exception amqpResponseCodeToException(final int statusCode, final String statusDescription) {
        final AmqpResponseCode amqpResponseCode = AmqpResponseCode.fromValue(statusCode);
        final String message = String.format(AMQP_REQUEST_FAILED_ERROR, statusCode, statusDescription);

        if (amqpResponseCode == null) {
            return new AmqpException(true, message);
        }

        switch (amqpResponseCode) {
            case BAD_REQUEST:
                return new IllegalArgumentException(message);
            case NOT_FOUND:
                return ExceptionUtil.distinguishNotFound(statusDescription);
            case FORBIDDEN:
                return new AmqpException(false, ErrorCondition.RESOURCE_LIMIT_EXCEEDED, message);
            case UNAUTHORIZED:
                return new AmqpException(false, ErrorCondition.UNAUTHORIZED_ACCESS, message);
            default:
                return new AmqpException(true, message);
        }
    }

    private static AmqpException distinguishNotFound(final String message) {
        final Matcher m = ENTITY_NOT_FOUND_PATTERN.matcher(message);
        if (m.find()) {
            return new AmqpException(false, ErrorCondition.NOT_FOUND, message);
        } else {
            return new AmqpException(true, ErrorCondition.NOT_FOUND, String.format(AMQP_REQUEST_FAILED_ERROR, AmqpResponseCode.NOT_FOUND, message));
        }
    }
}
