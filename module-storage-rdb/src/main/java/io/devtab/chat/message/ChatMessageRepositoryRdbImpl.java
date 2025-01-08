package io.devtab.chat.message;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.devtab.chat.ChatMessageEntity;
import io.devtab.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

@Repository("rdb")
@RequiredArgsConstructor
public class ChatMessageRepositoryRdbImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    public ChatMessageEntity save(ChatMessageEntity message) {
        ChatMessageRdbEntity messageEntity = ChatMessageRdbEntity.toRdbEntity(message);
        chatMessageJpaRepository.save(messageEntity);
        return message;
    }

    // TODO : ChatMessageRepositoryRdbImpl findByArticleIdAndChatRoomId limit 로직 구현
    @Override
    public List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId, int limit) {
        return chatMessageJpaRepository.findAllByArticleIdAndAndChatRoomId(articleId, chatRoomId).stream()
            .map(ChatMessageRdbEntity::toDomain)
            .toList();
    }
}
