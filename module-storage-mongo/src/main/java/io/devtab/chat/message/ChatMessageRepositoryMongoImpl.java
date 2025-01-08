package io.devtab.chat.message;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import io.devtab.chat.ChatMessageEntity;
import io.devtab.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

@Repository("mongo")
@RequiredArgsConstructor
public class ChatMessageRepositoryMongoImpl implements ChatMessageRepository {

    private final ChatMessageMongoJpaRepository chatMessageMongoJpaRepository;

    @Override
    public ChatMessageEntity save(ChatMessageEntity message) {
        ChatMessageMongoEntity document = chatMessageMongoJpaRepository
            .findByArticleIdAndChatRoomId(message.getArticleId(), message.getChatRoomId())
            .orElseGet(() -> ChatMessageMongoEntity.createNewDocument(message.getArticleId(), message.getChatRoomId()));

        ChatMessageMongoEntity.Message newMessage = ChatMessageMongoEntity.Message.fromDomain(message);
        document.addMessage(newMessage);

        chatMessageMongoJpaRepository.save(document);

        return message;
    }

    @Override
    public List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId, int limit) {
        return chatMessageMongoJpaRepository
            .findByArticleIdAndChatRoomId(articleId, chatRoomId)
            .map(document -> document.getMessageList().stream()
                .limit(limit)
                .map(mongoMessage -> ChatMessageEntity.builder()
                    .id(Long.valueOf(mongoMessage.getId()))
                    .articleId(articleId)
                    .chatRoomId(chatRoomId)
                    .userId(mongoMessage.getUserId())
                    .isRead(mongoMessage.getIsRead())
                    .isDeleted(mongoMessage.getIsDeleted())
                    .nickName(mongoMessage.getNickName())
                    .contents(mongoMessage.getContents())
                    .createdAt(mongoMessage.getCreatedAt())
                    .build())
                .toList())
            .orElse(Collections.emptyList());
    }
}
