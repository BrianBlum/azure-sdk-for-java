// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.eventhubs.implementation;

import com.azure.core.amqp.TransportType;
import com.azure.eventhubs.implementation.handler.ConnectionHandler;
import com.azure.eventhubs.implementation.handler.ReceiveLinkHandler;
import com.azure.eventhubs.implementation.handler.SendLinkHandler;
import com.azure.eventhubs.implementation.handler.SessionHandler;

import java.time.Duration;

class MockReactorHandlerProvider extends ReactorHandlerProvider {
    private final ConnectionHandler connectionHandler;
    private final SessionHandler sessionHandler;
    private final SendLinkHandler sendLinkHandler;
    private final ReceiveLinkHandler receiveLinkHandler;

    MockReactorHandlerProvider(ReactorProvider provider, ConnectionHandler connectionHandler, SessionHandler sessionHandler,
                               SendLinkHandler sendLinkHandler, ReceiveLinkHandler receiveLinkHandler) {
        super(provider);
        this.connectionHandler = connectionHandler;
        this.sessionHandler = sessionHandler;
        this.sendLinkHandler = sendLinkHandler;
        this.receiveLinkHandler = receiveLinkHandler;
    }

    @Override
    SessionHandler createSessionHandler(String connectionId, String host, String sessionName, Duration openTimeout) {
        return sessionHandler;
    }

    @Override
    ConnectionHandler createConnectionHandler(String connectionId, String hostname, TransportType transportType) {
        return connectionHandler;
    }

    @Override
    SendLinkHandler createSendLinkHandler(String connectionId, String host, String senderName) {
        return sendLinkHandler;
    }

    @Override
    ReceiveLinkHandler createReceiveLinkHandler(String connectionId, String host, String receiverName) {
        return receiveLinkHandler;
    }
}
