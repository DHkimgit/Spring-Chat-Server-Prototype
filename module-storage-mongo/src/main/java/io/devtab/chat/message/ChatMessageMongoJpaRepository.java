package io.devtab.chat.message;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface ChatMessageMongoJpaRepository extends Repository<ChatMessageMongoJpaRepository, String> {

    Optional<ChatMessageMongoEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId);

    void save(ChatMessageMongoEntity document);
}
