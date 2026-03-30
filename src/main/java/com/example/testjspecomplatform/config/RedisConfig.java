package com.example.testjspecomplatform.config;

// Redis configuration disabled
// To enable Redis in the future, uncomment this file and uncomment the Redis dependency in pom.xml

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> RedisTemplate(RedisConnectionFactory redisConnectionFactory) 
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(),
            ObjectMapper.DefaultTyping.EVERYTHING,
            JsonTypeInfo.As.PROPERTY
        );

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();

        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        return template;
    }
}
*/
