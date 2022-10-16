package cz.sliva.storage;

import cz.sliva.storage.repository.CounterStorageRepository;
import cz.sliva.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private final CounterStorageRepository counterStorageRepository;
    private final List<StorageRepository> storageRepositories;

    public StorageService(
            final CounterStorageRepository counterStorageRepository,
            final List<StorageRepository> storageRepositories
    ) {
        this.counterStorageRepository = counterStorageRepository;
        this.storageRepositories = storageRepositories;
    }

    public void store(final String jsonString) {
        this.storageRepositories.forEach(storageRepository -> storageRepository.store(jsonString));
    }

    public int getCount() {
        return this.counterStorageRepository.getCount();
    }
}
