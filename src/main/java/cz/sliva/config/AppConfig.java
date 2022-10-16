package cz.sliva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class AppConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
            @Value("${redis.host}") final String host,
            @Value("${redis.port}") final int port
    ) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(final JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

    @Bean
    public ValueOperations<String, String> valueOperations(final StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForValue();
    }
}
