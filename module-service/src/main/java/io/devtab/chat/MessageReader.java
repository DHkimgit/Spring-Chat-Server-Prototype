package io.devtab.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageReader {


    private final Map<String, ChatMessageRepository> repositoryMap;

    public List<ChatMessageCommand> recentMessage(Long articleId, Long chatRoomId) {
        ChatMessageRepository chatMessageRepository = repositoryMap.get("redis");

        return chatMessageRepository.findByArticleIdAndChatRoomId(articleId, chatRoomId, 1).stream()
            .map(ChatMessageCommand::toCommand)
            .toList();
    }

}
