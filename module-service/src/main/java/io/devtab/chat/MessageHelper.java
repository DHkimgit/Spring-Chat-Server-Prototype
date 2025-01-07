package io.devtab.chat;

import org.springframework.stereotype.Component;

import io.devtab.chat.util.TsidGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageHelper {

    private final TsidGenerator tsidGenerator;

    public void generateMessageId(ChatMessageEntity message) {
        message.addMessageId(tsidGenerator.generate());
    }
}
