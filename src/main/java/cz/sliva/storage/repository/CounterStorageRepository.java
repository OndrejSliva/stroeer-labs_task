package cz.sliva.storage.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CounterStorageRepository implements StorageRepository {

    @Override
    public void store(final String jsonString) {

    }

    public int getCount() {
        return -1;
    }
}
