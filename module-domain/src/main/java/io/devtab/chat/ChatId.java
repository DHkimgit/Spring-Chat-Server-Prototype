package io.devtab.chat;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatId implements Serializable {

    private String id;
    private Long articleId;
    private Long chatRoomIdId;

    @Override
    public String toString() {
        return id + ":" + articleId + ":" + chatRoomIdId;
    }

    public static ChatId fromString(String str) {
        String[] parts = str.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid ChatId format: " + str);
        }
        return new ChatId(parts[0], Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }
}
