package io.devtab.chat;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageReader {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessageCommand> recentMessage(Long articleId, Long chatRoomId) {
        return chatMessageRepository.findByArticleIdAndChatRoomId(articleId, chatRoomId, 1).stream()
            .map(ChatMessageCommand::toCommand)
            .toList();
    }

}
