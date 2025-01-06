package io.devtab.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String userNickname;
    private Long messageFrom;
    private String content;
}
