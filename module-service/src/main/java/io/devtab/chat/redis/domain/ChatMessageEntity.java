package io.devtab.chat.redis.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import io.devtab.chat.ChatCache;
import io.devtab.chat.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "ChatMessage")
public class ChatMessageEntity {

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

}
