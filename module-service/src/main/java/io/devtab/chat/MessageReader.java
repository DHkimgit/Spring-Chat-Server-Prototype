package io.devtab.chat;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageReader {

    private final ChatMessageRepository chatMessageRepository;

}
