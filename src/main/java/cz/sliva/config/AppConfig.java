package cz.sliva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class AppConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${redis-conf.host}") final String host,
            @Value("${redis-conf.port}") final int port
    ) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public StringRedisTemplate redisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public ValueOperations<String, String> valueOperations(final StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForValue();
    }
}
