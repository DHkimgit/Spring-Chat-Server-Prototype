package io.devtab.chat.message;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;

public interface ChatMessageMongoJpaRepository extends MongoRepository<ChatMessageMongoJpaRepository, ObjectId> {

    @Query("{ 'article_id': ?0, 'chatroom_id': ?1 }")
    Optional<ChatMessageMongoEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId);

    void save(ChatMessageMongoEntity document);
}
