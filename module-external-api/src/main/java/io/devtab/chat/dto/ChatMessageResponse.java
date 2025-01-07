package io.devtab.chat.dto;

import java.time.LocalDateTime;

import io.devtab.chat.ChatMessageCommand;

public record ChatMessageResponse(
    Long userId,
    String nickname,
    String content,
    LocalDateTime timestamp
) {

    public static ChatMessageResponse toResponse(ChatMessageCommand message) {
        return new ChatMessageResponse(message.getUserId(), message.getUserNickname(), message.getContent(),
            message.getTimeStamp());
    }
}
