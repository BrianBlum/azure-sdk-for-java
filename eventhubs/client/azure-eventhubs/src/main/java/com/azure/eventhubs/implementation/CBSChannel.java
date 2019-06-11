// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.eventhubs.implementation;

import com.azure.core.amqp.AmqpConnection;
import com.azure.core.amqp.CBSNode;
import com.azure.core.implementation.logging.ServiceLogger;
import org.apache.qpid.proton.Proton;
import org.apache.qpid.proton.amqp.messaging.AmqpValue;
import org.apache.qpid.proton.amqp.messaging.ApplicationProperties;
import org.apache.qpid.proton.message.Message;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class CBSChannel extends EndpointStateNotifierBase implements CBSNode {
    static final String SESSION_NAME = "cbs-session";
    static final String CBS_ADDRESS = "$cbs";

    private static final String LINK_NAME = "cbs";
    private static final String PUT_TOKEN_OPERATION = "operation";
    private static final String PUT_TOKEN_OPERATION_VALUE = "put-token";
    private static final String PUT_TOKEN_TYPE = "type";
    private static final String SAS_TOKEN_TYPE = "servicebus.windows.net:sastoken";
    private static final String PUT_TOKEN_AUDIENCE = "name";

    private final AmqpConnection connection;
    private final TokenProvider tokenProvider;
    private final Mono<RequestResponseChannel> cbsChannelMono;
    private final ReactorProvider provider;

    CBSChannel(AmqpConnection connection, TokenProvider tokenProvider, ReactorProvider provider, ReactorHandlerProvider handlerProvider) {
        super(new ServiceLogger(CBSChannel.class));

        Objects.requireNonNull(connection);
        Objects.requireNonNull(tokenProvider);
        Objects.requireNonNull(provider);

        this.connection = connection;
        this.tokenProvider = tokenProvider;
        this.provider = provider;
        this.cbsChannelMono = connection.createSession(SESSION_NAME)
            .cast(ReactorSession.class)
            .map(session -> new RequestResponseChannel(connection.getIdentifier(), connection.getHost(), LINK_NAME,
                CBS_ADDRESS, session.session(), handlerProvider));
    }

    @Override
    public Mono<Void> authorize(final String tokenAudience, final Duration duration) {
        final Message request = Proton.message();
        final Map<String, Object> properties = new HashMap<>();
        properties.put(PUT_TOKEN_OPERATION, PUT_TOKEN_OPERATION_VALUE);
        properties.put(PUT_TOKEN_TYPE, SAS_TOKEN_TYPE);
        properties.put(PUT_TOKEN_AUDIENCE, tokenAudience);
        final ApplicationProperties applicationProperties = new ApplicationProperties(properties);
        request.setApplicationProperties(applicationProperties);

        final String token;
        try {
            token = tokenProvider.getToken(tokenAudience, duration);
        } catch (UnsupportedEncodingException ex) {
            return Mono.error(ex);
        }

        request.setBody(new AmqpValue(token));

        return cbsChannelMono.flatMap(x -> x.sendWithAck(request, provider.getReactorDispatcher())).then();
    }

    @Override
    public void close() {
        final RequestResponseChannel channel = cbsChannelMono.block(Duration.ofSeconds(60));
        if (channel != null) {
            channel.close();
        }

        if (!connection.removeSession(SESSION_NAME)) {
            logger.asInfo().log("Unable to remove CBSChannel {} from connection", SESSION_NAME);
        }
    }
}
