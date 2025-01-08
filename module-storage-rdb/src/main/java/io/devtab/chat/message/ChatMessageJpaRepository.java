package io.devtab.chat.message;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ChatMessageJpaRepository extends Repository<ChatMessageRdbEntity, Long> {

    ChatMessageRdbEntity save(ChatMessageRdbEntity entity);

    @Query("SELECT m FROM ChatMessageRdbEntity m WHERE m.articleId= :articleId AND m.chatRoomId= :chatRoomId ORDER BY m.createdAt")
    List<ChatMessageRdbEntity> findAllByArticleIdAndAndChatRoomId(Long articleId, Long chatRoomId);
}
