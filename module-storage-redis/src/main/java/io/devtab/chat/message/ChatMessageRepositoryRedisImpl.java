package io.devtab.chat.message;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.chat.ChatMessageEntity;
import io.devtab.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryRedisImpl implements ChatMessageRepository {

    private static final int COUNTER_DIGITS = 4;
    private static final String SEPARATOR = "|";
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public ChatMessageEntity save(ChatMessageEntity message) {
        try {
            ChatMessageRedisEntity messageEntity = ChatMessageRedisEntity.toRedisEntity(message);
            String messageJson = objectMapper.writeValueAsString(messageEntity);
            String chatRoomKey = getChatRoomKey(messageEntity.getArticleId(),messageEntity.getChatRoomId());

            String tsidKey = formatTSIDKey(messageEntity.getId());

            redisTemplate.opsForZSet().add(chatRoomKey, tsidKey + SEPARATOR + messageJson, 0);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("메시지 저장 실패", e);
        }

        return message;
    }

    @Override
    public List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId, int limit) {
        String key = getChatRoomKey(articleId, chatRoomId);

        //Set<String> messageJsonSet = redisTemplate.opsForZSet().reverseRangeByLex(key, Range.unbounded(), Limit.limit().count(limit));
        Set<String> messageJsonSet = redisTemplate.opsForZSet().reverseRange(key, 0, -1);

        return convertToMessages(messageJsonSet);
    }

    private String getChatRoomKey(Long articleId, Long roomId) {
        return "article:" + articleId +":chatroom:" + roomId + ":message";
    }

    private String formatTSIDKey(long tsid) {
        String tsidStr = String.valueOf(tsid);

        String timestamp = tsidStr.substring(0, tsidStr.length() - COUNTER_DIGITS);
        String counter = tsidStr.substring(tsidStr.length() - COUNTER_DIGITS);

        return timestamp + ":" + counter;
    }

    private List<ChatMessageEntity> convertToMessages(Set<String> messageJsonSet) {
        if (messageJsonSet == null || messageJsonSet.isEmpty()) {
            return Collections.emptyList();
        }

        return messageJsonSet.stream()
            .map(value -> {
                try {
                    String json = value.substring(value.indexOf(SEPARATOR) + 1);
                    return objectMapper.readValue(json, ChatMessageEntity.class);
                } catch (JsonProcessingException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toList();
    }
}
