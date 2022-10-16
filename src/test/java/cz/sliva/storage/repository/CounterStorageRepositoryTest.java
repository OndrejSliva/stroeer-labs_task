package cz.sliva.storage.repository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(OutputCaptureExtension.class)
class CounterStorageRepositoryTest {

    private ValueOperations<String, String> redisValueOperationsMock;
    private CounterStorageRepository counterStorageRepository;

    @BeforeEach
    public void init() {
        redisValueOperationsMock = mock(ValueOperations.class);
        counterStorageRepository = new CounterStorageRepository(redisValueOperationsMock);
    }

    @Test
    public void shouldIncrementValueIfJsonContainsKey() {
        final int value = 8;
        final JSONObject jsonObject = new JSONObject("{\"count\": " + value + "}");

        counterStorageRepository.store(jsonObject);

        verify(redisValueOperationsMock, times(1)).increment("count", value);
    }

    @Test
    public void shouldNotStoreNothingWhenJsonKeyIsNotPresent() {
        final JSONObject jsonObject = new JSONObject("{}");

        counterStorageRepository.store(jsonObject);

        verify(redisValueOperationsMock, times(0)).increment(any());
    }

    @Test
    public void shouldNotStoreNonIntValue(final CapturedOutput capturedOutput) {
        final JSONObject jsonObject = new JSONObject("{\"count\": \"value\"}");

        counterStorageRepository.store(jsonObject);

        verify(redisValueOperationsMock, times(0)).increment(any());
        assertTrue(capturedOutput.getOut().contains("Cannot store non integer field count to redis."));
    }

    @Test
    public void shouldReturnValueIfPresent() {
        final int value = 8;

        when(redisValueOperationsMock.get("count")).thenReturn(String.valueOf(value));

        final Optional<Integer> count = counterStorageRepository.getCount();

        assertTrue(count.isPresent());
        assertEquals(value, count.get());
    }

    @Test
    public void shouldReturnEmptyWhenValueIsNotPresent() {
        when(redisValueOperationsMock.get("count")).thenReturn(null);

        final Optional<Integer> count = counterStorageRepository.getCount();

        assertTrue(count.isEmpty());
    }

}