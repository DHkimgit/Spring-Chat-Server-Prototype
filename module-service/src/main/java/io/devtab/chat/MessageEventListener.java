package io.devtab.chat;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final MessageHelper messageHelper;
    private final MessageAppender messageAppender;

    @EventListener
    public void handleMessageArrivedEvent(MessageArriveEvent event) {
        ChatMessageEntity newMessage = ChatMessageEntity.create(event.articleId(), event.chatRoomId(), event.message());

        messageHelper.generateMessageId(newMessage);

        messageAppender.append(newMessage, "redis");
    }
}
