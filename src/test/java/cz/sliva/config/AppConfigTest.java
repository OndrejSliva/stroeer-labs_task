package cz.sliva.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    private final AppConfig appConfiguration = new AppConfig();

    @Test
    public void shouldCreateJedisConnectionFactoryWithGivenParams() {
        final String host = "example.host";
        final int port = 12345;
        final RedisConnectionFactory redisConnectionFactory = appConfiguration.redisConnectionFactory(host, port);
        assertInstanceOf(LettuceConnectionFactory.class, redisConnectionFactory);

        final LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisConnectionFactory;

        assertEquals(host, lettuceConnectionFactory.getHostName());
        assertEquals(port, lettuceConnectionFactory.getPort());
    }

    @Test
    public void shouldCreateRedisTemplate() {
        final StringRedisTemplate stringRedisTemplate = appConfiguration.redisTemplate(Mockito.mock(RedisConnectionFactory.class));
    }

    @Test
    public void shouldGetRedisValueOperations() {
        final ValueOperations<String, String> stringStringValueOperations = appConfiguration.valueOperations(Mockito.mock(StringRedisTemplate.class));
    }
}