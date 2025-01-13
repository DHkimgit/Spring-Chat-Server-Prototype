package io.devtab.chat;

public record MessageArriveEvent(
    Long articleId,
    Long chatRoomId,
    ChatMessageCommand message
) {
}
