package io.devtab.chat.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.devtab.chat.ChatMessageEntity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "chat_room")
public class ChatMessageMongoEntity {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field("article_id")
    private Long articleId;

    @Field("chatroom_id")
    private Long chatRoomId;

    @Field("messages")
    private List<Message> messageList;

    @Builder
    public ChatMessageMongoEntity(Long articleId, Long chatRoomId) {
        this.articleId = articleId;
        this.chatRoomId = chatRoomId;
        this.messageList = new ArrayList<>();
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Message {

        @Field("tsid")
        private String tsid;

        @Field("user_id")
        private Long userId;

        @Field("is_read")
        private Boolean isRead;

        @Field("is_deleted")
        private Boolean isDeleted;

        @Field("nickname")
        private String nickName;

        @Field("contents")
        private String contents;

        @Field("created_at")
        private LocalDateTime createdAt;

        public static Message fromDomain(ChatMessageEntity domainMessage) {
            return Message.builder()
                .tsid(domainMessage.getId().toString())
                .userId(domainMessage.getUserId())
                .isRead(domainMessage.getIsRead())
                .isDeleted(domainMessage.getIsDeleted())
                .nickName(domainMessage.getNickName())
                .contents(domainMessage.getContents())
                .createdAt(domainMessage.getCreatedAt())
                .build();
        }
    }

    public static ChatMessageMongoEntity createNewDocument(Long articleId, Long chatRoomId) {
        return ChatMessageMongoEntity.builder()
            .articleId(articleId)
            .chatRoomId(chatRoomId)
            .build();
    }
}
