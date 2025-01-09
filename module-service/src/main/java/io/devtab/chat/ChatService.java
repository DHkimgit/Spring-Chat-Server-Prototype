package io.devtab.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageHelper messageHelper;
    private final MessageAppender messageAppender;
    private final MessageReader messageReader;

    public void saveMessage(Long articleId, Long chatRoomId, ChatMessageCommand message) {
        ChatMessageEntity newMessage = ChatMessageEntity.create(articleId, chatRoomId, message);

        messageHelper.generateMessageId(newMessage);

        messageAppender.append(newMessage, "redis");
        messageAppender.append(newMessage, "rdb");
        messageAppender.append(newMessage, "mongoRepository");
    }

    public List<ChatMessageCommand> readMessages(Long articleId, Long chatRoomId, String storageType) {
        return messageReader.allMessages(articleId, chatRoomId, storageType);
    }
}
