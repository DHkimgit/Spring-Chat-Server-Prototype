package io.devtab.chat.message;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;


import io.devtab.chat.ChatCache;
import io.devtab.chat.ChatId;
import io.devtab.chat.ChatMessageEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "chat_message")
public class ChatMessageRdbEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long messageId;

    private Long articleId;

    private Long chatRoomId;

    private Long userId;

    private Boolean isRead;

    private Boolean isDeleted;

    private String nickName;

    private String contents;

    private LocalDateTime createdAt;

    // 도메인 객체 -> 영속성 엔티티
    public static ChatMessageRdbEntity toRdbEntity(ChatMessageEntity message) {
        return ChatMessageRdbEntity.builder()
            .messageId(message.getId())
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

    public ChatMessageEntity toDomain() {
        return ChatMessageEntity.builder()
            .id(messageId)
            .articleId(articleId)
            .chatRoomId(chatRoomId)
            .userId(userId)
            .isRead(isRead)
            .isDeleted(isDeleted)
            .nickName(nickName)
            .contents(contents)
            .createdAt(createdAt)
            .build();
    }
}
