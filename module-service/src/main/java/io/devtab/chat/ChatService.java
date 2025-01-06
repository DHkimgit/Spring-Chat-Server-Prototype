package io.devtab.chat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import io.devtab.chat.redis.domain.ChatMessageEntity;
import io.devtab.chat.redis.repository.ChatMessageJpaRepository;
import io.devtab.chat.redis.repository.ChatMessageRepository;
import io.devtab.chat.redis.repository.ChatMessageRepositoryImpl;
import io.devtab.chat.redis.util.TsidGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private static final String CHAT_MESSAGE_HASH_VALUE = "ChatMessage";
    private final TsidGenerator idGenerator;
    //private final ChatMessageJpaRepository chatMessageRepository;
    private final ChatMessageRepositoryImpl chatMessageRepository;
    private final TsidGenerator tsidGenerator;

    // public void saveMessage(Long articleId, Long chatRoomId, ChatMessage message) {
    //     ChatId chatId = new ChatId(UUID.randomUUID().toString(), articleId, chatRoomId);
    //     ChatCache chatCache = ChatCache.builder()
    //         .id(chatId)
    //         .messageFrom(message.getMessageFrom())
    //         .content(message.getContent())
    //         .createdAt(LocalDateTime.now())
    //         .read(false)
    //         .build();
    //
    //     HashOperations<String, String, ChatCache> opsHash = redisTemplate.opsForHash();
    //     opsHash.put(CHAT_HASH_KEY, chatId.toString(), chatCache);
    //
    //     String topic = "/topic/chatroom/" + articleId + "/" + chatRoomId;
    //     try {
    //         redisTemplate.convertAndSend(topic, objectMapper.writeValueAsString(chatCache));
    //     } catch (JsonProcessingException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    public void saveMessage(Long articleId, Long chatRoomId, ChatMessage message) {
        Long chatId = tsidGenerator.generate();

        ChatMessageEntity newMessage = new ChatMessageEntity(
            chatId, articleId, chatRoomId, message.getMessageFrom(), false, false, message.getUserNickname(),
            message.getContent(), LocalDateTime.now()
        );

        chatMessageRepository.save(newMessage);
    }

    // public List<ChatCache> getChatMessages(Long articleId, Long chatRoomId) {
    //     return chatMessageRepository.findByArticleIdAndChatRoomId(articleId, chatRoomId).stream().map(
    //         ChatMessageEntity::toDomain).toList();
    // }
}
