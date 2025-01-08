package io.devtab.chat.message;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

    public static ChatMessageRedisEntity toRedisEntity(ChatMessageEntity message) {
        return ChatMessageRedisEntity.builder()
            .id(message.getId())
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
