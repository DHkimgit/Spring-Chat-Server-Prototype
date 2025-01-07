package io.devtab.chat;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageHelper messageHelper;
    private final MessageAppender messageAppender;

    public void saveMessage(Long articleId, Long chatRoomId, ChatMessageCommand message) {
        ChatMessageEntity newMessage = ChatMessageEntity.create(articleId, chatRoomId, message);

        messageHelper.generateMessageId(newMessage);

        messageAppender.append(newMessage);
    }
}
