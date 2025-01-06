package io.devtab.chat.redis.repository;

import io.devtab.chat.ChatMessage;
import io.devtab.chat.redis.domain.ChatMessageEntity;

public interface ChatMessageRepository {

    ChatMessageEntity save(ChatMessageEntity message);
}
