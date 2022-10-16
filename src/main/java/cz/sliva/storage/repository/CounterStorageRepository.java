package cz.sliva.storage.repository;

import org.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CounterStorageRepository implements StorageRepository {

    private static final String COUNT_KEY = "count";
    private static final String REDIS_COUNTER_KEY = "count";

    private final StringRedisTemplate stringRedisTemplate;

    public CounterStorageRepository(final StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void store(final String jsonString) {
        final JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has(COUNT_KEY)) {
            int count = jsonObject.getInt(COUNT_KEY);
            this.stringRedisTemplate.opsForValue().increment(REDIS_COUNTER_KEY, count);
        }
    }

    public Optional<Integer> getCount() {
        String redisValue = this.stringRedisTemplate.opsForValue().get(REDIS_COUNTER_KEY);
        if (redisValue == null) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(redisValue));
    }
}
