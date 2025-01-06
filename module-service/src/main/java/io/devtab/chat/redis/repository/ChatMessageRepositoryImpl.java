package io.devtab.chat.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.chat.redis.domain.ChatMessageEntity;
import io.devtab.chat.redis.util.TsidGenerator;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository{

    private static final int COUNTER_DIGITS = 4;
    private static final String SEPARATOR = "|";
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public ChatMessageEntity save(ChatMessageEntity message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            String chatRoomKey = getChatRoomKey(message.getArticleId(),message.getChatRoomId());

            String tsidKey = formatTsidKey(message.getId());

            redisTemplate.opsForZSet().add(chatRoomKey, tsidKey + SEPARATOR + messageJson, 0);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("ㅠ", e);
        }

        return message;
    }

    private String getChatRoomKey(Long articleId, Long roomId) {
        return "article:" + articleId +":chatroom:" + roomId + ":message";
    }

    private String formatTsidKey(long tsid) {
        String tsidStr = String.valueOf(tsid);

        String timestamp = tsidStr.substring(0, tsidStr.length() - COUNTER_DIGITS);
        String counter = tsidStr.substring(tsidStr.length() - COUNTER_DIGITS);

        return timestamp + ":" + counter;
    }
}
