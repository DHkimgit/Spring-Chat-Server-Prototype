package io.devtab.chat.message;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Repository;

import io.devtab.chat.ChatMessageEntity;
import io.devtab.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

@Repository("mongoRepository")
@RequiredArgsConstructor
public class ChatMessageRepositoryMongoImpl implements ChatMessageRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public ChatMessageEntity save(ChatMessageEntity message) {
        // articleId와 chatRoomId로 기존 문서 조회
        Query query = Query.query(Criteria.where("article_id").is(message.getArticleId())
            .and("chatroom_id").is(message.getChatRoomId()));
        Update update = new Update();



        // 새로운 메시지를 ChatMessageMongoEntity.Message로 변환
        ChatMessageMongoEntity.Message newMessage = ChatMessageMongoEntity.Message.builder()
            .tsid(message.getId().toString())
            .userId(message.getUserId())
            .isRead(false)
            .isDeleted(false)
            .nickName(message.getNickName())
            .contents(message.getContents())
            .createdAt(message.getCreatedAt())
            .build();

        // messages 필드에 새 메시지를 추가
        update.push("messages", newMessage);

        // 기존 문서가 없으면 새로 생성
        update.setOnInsert("article_id", message.getArticleId());
        update.setOnInsert("chatroom_id", message.getChatRoomId());

        // upsert(있으면 업데이트, 없으면 삽입)
        mongoTemplate.upsert(query, update, ChatMessageMongoEntity.class);

        return message;
    }

    @Override
    public List<ChatMessageEntity> findByArticleIdAndChatRoomId(Long articleId, Long chatRoomId, int limit) {
        // articleId와 chatRoomId로 조회
        Query query = new Query(Criteria.where("article_id").is(articleId)
            .and("chatroom_id").is(chatRoomId))
            .limit(limit)
            .with(Sort.by(Sort.Direction.DESC, "messages.created_at")); // 최신 메시지 기준 정렬

        // 조회
        ChatMessageMongoEntity result = mongoTemplate.findOne(query, ChatMessageMongoEntity.class);

        if (result == null || result.getMessageList() == null) {
            return Collections.emptyList();
        }

        // 결과를 ChatMessageEntity 리스트로 변환
        return result.getMessageList().stream()
            .map(message -> ChatMessageEntity.builder()
                .id(Long.parseLong(message.getTsid()))
                .articleId(articleId)
                .chatRoomId(chatRoomId)
                .userId(message.getUserId())
                .isRead(message.getIsRead())
                .isDeleted(message.getIsDeleted())
                .nickName(message.getNickName())
                .contents(message.getContents())
                .createdAt(message.getCreatedAt())
                .build())
            .limit(limit)
            .toList();
    }
}
