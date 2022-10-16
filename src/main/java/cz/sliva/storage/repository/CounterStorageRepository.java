package cz.sliva.storage.repository;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CounterStorageRepository implements StorageRepository {

    private static final String COUNT_KEY = "count";
    private static final String REDIS_COUNTER_KEY = "count";

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterStorageRepository.class);

    private final ValueOperations<String, String> redisValueOperations;

    public CounterStorageRepository(final ValueOperations<String, String> redisValueOperations) {
        this.redisValueOperations = redisValueOperations;
    }

    @Override
    public void store(final JSONObject json) {
        if (!json.has(COUNT_KEY)) {
            return;
        }

        try {
            int count = json.getInt(COUNT_KEY);
            this.redisValueOperations.increment(REDIS_COUNTER_KEY, count);
        } catch (JSONException e) {
            LOGGER.warn("Cannot store non integer field {} to redis.", COUNT_KEY);
        }
    }

    public Optional<Integer> getCount() {
        String redisValue = this.redisValueOperations.get(REDIS_COUNTER_KEY);
        if (redisValue == null) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(redisValue));
    }
}
