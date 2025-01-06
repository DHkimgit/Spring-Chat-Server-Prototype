package io.devtab.chat.redis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.devtab.chat.redis.domain.ChatMessageEntity;

@Repository
public interface ChatMessageJpaRepository extends CrudRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId);
}
