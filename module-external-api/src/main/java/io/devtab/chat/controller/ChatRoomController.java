package io.devtab.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.devtab.chat.ChatCache;
import io.devtab.chat.ChatService;
import io.devtab.chat.dto.ChatMessageResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/chatroom/{articleId}/{chatRoomId}/redis")
    public ResponseEntity<?> getChatMessagesFromRedis(
        @PathVariable Long articleId,
        @PathVariable Long chatRoomId) {
        var result = chatService.readMessages(articleId, chatRoomId, "redis").stream()
            .map(ChatMessageResponse::toResponse)
            .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/chatroom/{articleId}/{chatRoomId}/rdb")
    public ResponseEntity<?> getChatMessagesFromRdb(
        @PathVariable Long articleId,
        @PathVariable Long chatRoomId) {
        var result = chatService.readMessages(articleId, chatRoomId, "rdb").stream()
            .map(ChatMessageResponse::toResponse)
            .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/chatroom/{articleId}/{chatRoomId}/mongo")
    public ResponseEntity<?> getChatMessagesFromMongo(
        @PathVariable Long articleId,
        @PathVariable Long chatRoomId) {
        var result = chatService.readMessages(articleId, chatRoomId, "mongoRepository").stream()
            .map(ChatMessageResponse::toResponse)
            .toList();
        return ResponseEntity.ok(result);
    }
}
