package cz.sliva.storage;

import cz.sliva.storage.repository.CounterStorageRepository;
import cz.sliva.storage.repository.StorageRepository;
import cz.sliva.utils.json.JsonValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private final CounterStorageRepository counterStorageRepository;
    private final List<StorageRepository> storageRepositories;
    private final JsonValidator jsonValidator;

    public StorageService(
            final CounterStorageRepository counterStorageRepository,
            final List<StorageRepository> storageRepositories,
            final JsonValidator jsonValidator
    ) {
        this.counterStorageRepository = counterStorageRepository;
        this.storageRepositories = storageRepositories;
        this.jsonValidator = jsonValidator;
    }

    public void store(final String jsonString) {
        if (!jsonValidator.isStringValid(jsonString)) {
            return;     // todo throw exception instead
        }

        this.storageRepositories.forEach(storageRepository -> storageRepository.store(jsonString));
    }

    public int getCount() {
        return this.counterStorageRepository.getCount();
    }
}
