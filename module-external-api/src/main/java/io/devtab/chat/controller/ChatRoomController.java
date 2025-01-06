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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // @GetMapping("/chatroom/{articleId}/{chatRoomId}")
    // public ResponseEntity<List<ChatCache>> getChatMessages(
    //     @PathVariable Long articleId,
    //     @PathVariable Long chatRoomId) {
    //
    //     List<ChatCache> chatMessages = chatService.getChatMessages(articleId, chatRoomId);
    //     return ResponseEntity.ok(chatMessages);
    // }
}
