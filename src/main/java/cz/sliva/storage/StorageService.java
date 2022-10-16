package cz.sliva.storage;

import cz.sliva.storage.repository.CounterStorageRepository;
import cz.sliva.storage.repository.StorageRepository;
import cz.sliva.utils.json.exception.InvalidJsonException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        final JSONObject jsonObject = getJson(jsonString);
        this.storageRepositories.forEach(storageRepository -> storageRepository.store(jsonObject));
    }

    public Optional<Integer> getCount() {
        return this.counterStorageRepository.getCount();
    }

    private JSONObject getJson(final String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new InvalidJsonException();
        }
    }
}
