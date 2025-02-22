package io.devtab.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import io.devtab.chat.ChatMessageCommand;
import io.devtab.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat/{articleId}/{chatRoomId}")
    @SendTo("/topic/chat/{articleId}/{chatRoomId}")
    public void handleChatMessage(
        @DestinationVariable Long articleId,
        @DestinationVariable Long chatRoomId,
        ChatMessageCommand message
    ) {
        chatService.saveMessage(articleId, chatRoomId, message);
        simpMessageSendingOperations.convertAndSend("/topic/chat/" + articleId + "/" + chatRoomId, message);
    }
}
