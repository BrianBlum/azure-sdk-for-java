// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.eventhubs;

import com.azure.core.amqp.Retry;
import com.azure.core.amqp.exception.AmqpException;
import com.azure.core.amqp.exception.ErrorCondition;
import com.azure.eventhubs.implementation.AmqpSendLink;
import com.azure.eventhubs.implementation.ApiTestBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventSenderTest extends ApiTestBase {

    @Rule
    public TestName testName = new TestName();

    @Override
    protected String testName() {
        return testName.getMethodName();
    }

    /**
     * Verifies that sending multiple events will result in calling sender.sendBatch(List<Message>).
     */
    @Test
    public void sendBatch() {
        // Arrange
        final Flux<EventData> testData = Flux.range(0, 4).flatMap(number -> {
            final EventData data = new EventData(CONTENTS.getBytes(UTF_8));
            return Flux.just(data);
        });

        final AmqpSendLink sendLink = mock(AmqpSendLink.class);
        when(sendLink.sendBatch(any())).thenReturn(Mono.empty());

        final int maxMessageSize = 16 * 1024;
        final EventBatchingOptions options = new EventBatchingOptions().maximumSizeInBytes(maxMessageSize);
        final EventSenderOptions senderOptions = new EventSenderOptions().retry(Retry.getNoRetry()).timeout(Duration.ofSeconds(30));
        final EventSender sender = new EventSender(Mono.just(sendLink), senderOptions);

        // Act & Assert
        StepVerifier.create(sender.send(testData, options))
            .verifyComplete();
    }

    /**
     * Verifies that sending a single event data will result in calling sender.send(Message).
     */
    @Test
    public void sendSingleMessage() {
        // Arrange
        final EventData data = new EventData(CONTENTS.getBytes(UTF_8));
        final Flux<EventData> testData = Flux.just(data);
        final AmqpSendLink sendLink = mock(AmqpSendLink.class);
        when(sendLink.send(any())).thenReturn(Mono.empty());

        final int maxMessageSize = 16 * 1024;
        final EventBatchingOptions options = new EventBatchingOptions().maximumSizeInBytes(maxMessageSize);
        final EventSenderOptions senderOptions = new EventSenderOptions().retry(Retry.getNoRetry()).timeout(Duration.ofSeconds(30));
        final EventSender sender = new EventSender(Mono.just(sendLink), senderOptions);

        // Act & Assert
        StepVerifier.create(sender.send(testData, options))
            .verifyComplete();
    }

    @Test
    public void sendBatchTooManyEvents() {
        final Flux<EventData> testData = Flux.range(0, 20).flatMap(number -> {
            final EventData data = new EventData(CONTENTS.getBytes(UTF_8));
            return Flux.just(data);
        });

        final AmqpSendLink sendLink = mock(AmqpSendLink.class);
        final int maxMessageSize = 16 * 1024;
        final EventBatchingOptions options = new EventBatchingOptions().maximumSizeInBytes(maxMessageSize);
        final EventSenderOptions senderOptions = new EventSenderOptions().retry(Retry.getNoRetry()).timeout(Duration.ofSeconds(30));
        final EventSender sender = new EventSender(Mono.just(sendLink), senderOptions);

        StepVerifier.create(sender.send(testData, options))
            .verifyErrorMatches(error -> {
                return error instanceof AmqpException && ((AmqpException) error).getErrorCondition() == ErrorCondition.LINK_PAYLOAD_SIZE_EXCEEDED;
            });
    }

    private static final String CONTENTS = "SSLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vehicula posuere lobortis. Aliquam finibus volutpat dolor, faucibus pellentesque ipsum bibendum vitae. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut sit amet urna hendrerit, dapibus justo a, sodales justo. Mauris finibus augue id pulvinar congue. Nam maximus luctus ipsum, at commodo ligula euismod ac. Phasellus vitae lacus sit amet diam porta placerat. \n"
        + "Ut sodales efficitur sapien ut posuere. Morbi sed tellus est. Proin eu erat purus. Proin massa nunc, condimentum id iaculis dignissim, consectetur et odio. Cras suscipit sem eu libero aliquam tincidunt. Nullam ut arcu suscipit, eleifend velit in, cursus libero. Ut eleifend facilisis odio sit amet feugiat. Phasellus at nunc sit amet elit sagittis commodo ac in nisi. Fusce vitae aliquam quam. Integer vel nibh euismod, tempus elit vitae, pharetra est. Duis vulputate enim a elementum dignissim. Morbi dictum enim id elit scelerisque, in elementum nulla pharetra. \n"
        + "Aenean aliquet aliquet condimentum. Proin dapibus dui id libero tempus feugiat. Sed commodo ligula a lectus mattis, vitae tincidunt velit auctor. Fusce quis semper dui. Phasellus eu efficitur sem. Ut non sem sit amet enim condimentum venenatis id dictum massa. Nullam sagittis lacus a neque sodales, et ultrices arcu mattis. Aliquam erat volutpat. \n"
        + "Aenean fringilla quam elit, id mattis purus vestibulum nec. Praesent porta eros in dapibus molestie. Vestibulum orci libero, tincidunt et turpis eget, condimentum lobortis enim. Fusce suscipit ante et mauris consequat cursus nec laoreet lorem. Maecenas in sollicitudin diam, non tincidunt purus. Nunc mauris purus, laoreet eget interdum vitae, placerat a sapien. In mi risus, blandit eu facilisis nec, molestie suscipit leo. Pellentesque molestie urna vitae dui faucibus bibendum. \n"
        + "Donec quis ipsum ultricies, imperdiet ex vel, scelerisque eros. Ut at urna arcu. Vestibulum rutrum odio dolor, vitae cursus nunc pulvinar vel. Donec accumsan sapien in malesuada tempor. Maecenas in condimentum eros. Sed vestibulum facilisis massa a iaculis. Etiam et nibh felis. Donec maximus, sem quis vestibulum gravida, turpis risus congue dolor, pharetra tincidunt lectus nisi at velit.";
}
