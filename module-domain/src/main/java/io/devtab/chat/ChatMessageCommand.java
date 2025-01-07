package io.devtab.chat;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 클라이언트에서 전달 되는 채팅 메시지 객체
 */
@Getter
@Setter
public class ChatMessageCommand {

    private Long userId; // user 테이블 PK
    private String userNickname; // 체팅방에 표시되는 user 이름
    private String content; // 채팅 메시지
    private LocalDateTime timeStamp; // 시간
}
