package io.devtab.chat;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageAppender {

    private final ChatMessageRepository chatMessageRepository;

    public void append(ChatMessageEntity message) {
        chatMessageRepository.save(message);
    }
}
