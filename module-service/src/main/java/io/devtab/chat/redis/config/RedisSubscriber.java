package io.devtab.chat.redis.config;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.chat.ChatCache;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void onMessage(String message) {
        try {
            ChatCache chat = objectMapper.readValue(message, ChatCache.class);
            String topic = "/topic/chat/" + chat.getId().getArticleId() + "/" + chat.getId().getChatRoomIdId();
            messagingTemplate.convertAndSend(topic, chat);
        } catch (Exception e) {
            // Handle exception (e.g., logging)
        }
    }
}
