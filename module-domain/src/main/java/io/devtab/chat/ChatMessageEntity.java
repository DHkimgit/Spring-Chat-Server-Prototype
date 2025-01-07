package io.devtab.chat;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageEntity {

    private Long id;

    private Long articleId;

    private Long chatRoomId;

    private Long userId;

    private Boolean isRead;

    private Boolean isDeleted;

    private String nickName;

    private String contents;

    private LocalDateTime createdAt;

    public void addMessageId(Long id) {
        this.id = id;
    }

    public static ChatMessageEntity create(Long articleId, Long chatRoomId, ChatMessageCommand message) {
        return ChatMessageEntity.builder()
            .articleId(articleId)
            .chatRoomId(chatRoomId)
            .userId(message.getUserId())
            .isRead(false)
            .isDeleted(false)
            .nickName(message.getUserNickname())
            .contents(message.getContent())
            .createdAt(message.getTimeStamp())
            .build();
    }
}
