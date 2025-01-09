package io.devtab.chat;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;

import io.devtab.chat.message.MongoPackageLocation;

@Configuration
@EntityScan(basePackageClasses = MongoPackageLocation.class)
@EnableMongoRepositories(basePackageClasses = MongoPackageLocation.class)
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "chattest");
    }

}
