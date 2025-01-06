package io.devtab.chat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ChatCache implements Serializable {

    private ChatId id;
    private Long messageFrom;
    private String content;
    private LocalDateTime createdAt;
    private boolean deleted;
    private boolean read;

    @Builder
    private ChatCache(ChatId id, Long messageFrom, String content, LocalDateTime createdAt, boolean deleted, boolean read) {
        if (content != null && content.length() > 5000) {
            throw new IllegalArgumentException("메시지는 5,000자 이하로 입력해주세요.");
        }

        this.id = Objects.requireNonNull(id);
        this.messageFrom = Objects.requireNonNull(messageFrom);
        this.content = content;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.deleted = deleted;
        this.read = read;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void markAsRead() {
        this.read = true;
    }
}
