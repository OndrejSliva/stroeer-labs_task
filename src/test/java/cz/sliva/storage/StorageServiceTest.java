package cz.sliva.storage;

import cz.sliva.storage.repository.CounterStorageRepository;
import cz.sliva.storage.repository.FileStorageRepository;
import cz.sliva.storage.repository.StorageRepository;
import cz.sliva.utils.json.exception.InvalidJsonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageServiceTest {

    private CounterStorageRepository counterStorageRepositoryMock;
    private FileStorageRepository fileStorageRepositoryMock;
    private StorageRepository storageRepositoryMock;
    private StorageService storageService;

    @BeforeEach
    public void init() {
        counterStorageRepositoryMock = mock(CounterStorageRepository.class);
        fileStorageRepositoryMock = mock(FileStorageRepository.class);
        storageRepositoryMock = mock(StorageRepository.class);

        List<StorageRepository> storageRepositories = List.of(
                counterStorageRepositoryMock, fileStorageRepositoryMock, storageRepositoryMock
        );
        storageService = new StorageService(counterStorageRepositoryMock, storageRepositories);
    }

    @Test
    public void allRepositoriesShouldBeCallWhenJsonIsValid() {
        storageService.store("{\"foo\":\"bar\"}");

        verify(counterStorageRepositoryMock, times(1)).store(any());
        verify(fileStorageRepositoryMock, times(1)).store(any());
        verify(storageRepositoryMock, times(1)).store(any());
    }

    @Test
    public void exceptionShouldBeThrownWhenJsonIsInvalid() {
        assertThrows(InvalidJsonException.class, () -> {
            storageService.store("no json format here");
        });
    }

    private static Stream<Arguments> countShouldReturnRepositoryValueDataProvider() {
        return Stream.of(
                Arguments.of(Optional.empty()),
                Arguments.of(Optional.of(8))
        );
    }

    @ParameterizedTest
    @MethodSource("countShouldReturnRepositoryValueDataProvider")
    public void countShouldReturnRepositoryValue(final Optional<Integer> count) {
        when(counterStorageRepositoryMock.getCount()).thenReturn(count);
        final Optional<Integer> result = storageService.getCount();

        assertEquals(count, result);
    }

}