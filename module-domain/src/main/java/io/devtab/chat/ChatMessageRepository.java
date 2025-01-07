package io.devtab.chat;

import org.springframework.stereotype.Component;

@Component
public interface ChatMessageRepository {

    ChatMessageEntity save(ChatMessageEntity message);
}
