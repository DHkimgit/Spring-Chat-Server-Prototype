package io.devtab.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageReader {

    private final Map<String, ChatMessageRepository> repositoryMap;

    public List<ChatMessageCommand> allMessages(Long articleId, Long chatRoomId, String storageType) {
        ChatMessageRepository chatMessageRepository = repositoryMap.get(storageType);

        return chatMessageRepository.findByArticleIdAndChatRoomId(articleId, chatRoomId, 1).stream()
            .map(ChatMessageCommand::toCommand)
            .toList();
    }

}
