package io.devtab.chat;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageEventManager {

    private final ApplicationEventPublisher eventPublisher;


    public void messageArrived(Long articleId, Long chatRoomId, ChatMessageCommand message) {
        MessageArriveEvent event = new MessageArriveEvent(articleId, chatRoomId, message);
        eventPublisher.publishEvent(event);
    }

}
