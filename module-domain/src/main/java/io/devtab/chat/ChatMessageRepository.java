package io.devtab.chat;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface ChatMessageRepository {

    ChatMessageEntity save(ChatMessageEntity message);

    List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId, int limit);
}
