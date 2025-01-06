package io.devtab.chat.redis.util;

import org.springframework.stereotype.Component;

import com.github.f4b6a3.tsid.TsidCreator;

@Component
public class TsidGenerator implements IdGenerator<Long>{

    @Override
    public Long generate() {
        return TsidCreator.getTsid256().toLong();
    }
}
