package io.devtab.chat.message;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import io.devtab.chat.ChatCache;
import io.devtab.chat.ChatId;
import io.devtab.chat.ChatMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "ChatMessage")
public class ChatMessageRedisEntity {

    @Id
    private Long id;

    @Indexed
    private Long articleId;

    @Indexed
    private Long chatRoomId;

    private Long userId;

    private Boolean isRead;

    private Boolean isDeleted;

    private String nickName;

    private String contents;

    private LocalDateTime createdAt;

    public ChatCache toDomain() {

        ChatId chatId = new ChatId(String.valueOf(id), articleId, chatRoomId);
        return ChatCache.builder()
            .id(chatId)
            .messageFrom(userId)
            .content(contents)
            .createdAt(createdAt)
            .deleted(isDeleted)
            .read(isRead)
            .build();
    }

    public static ChatMessageRedisEntity toRedisEntity(ChatMessageEntity message) {
        return ChatMessageRedisEntity.builder()
            .articleId(message.getArticleId())
            .chatRoomId(message.getChatRoomId())
            .userId(message.getUserId())
            .isRead(message.getIsRead())
            .isDeleted(message.getIsDeleted())
            .nickName(message.getNickName())
            .contents(message.getContents())
            .createdAt(message.getCreatedAt())
            .build();
    }
}
