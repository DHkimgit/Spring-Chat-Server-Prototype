package io.devtab.chat;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageAppender {

    private final Map<String, ChatMessageRepository> repositoryMap;

    public void append(ChatMessageEntity message, String storageType) {
        ChatMessageRepository chatMessageRepository = repositoryMap.get(storageType);
        chatMessageRepository.save(message);
    }
}
