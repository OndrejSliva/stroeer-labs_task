package cz.sliva.storage.repository;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class CounterStorageRepository implements StorageRepository {

    private static final String COUNT_KEY = "count";

    private int count = 0;

    @Override
    public void store(final String jsonString) {
        final JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has(COUNT_KEY)) {
            count += jsonObject.getInt(COUNT_KEY);
        }
    }

    public int getCount() {
        return count;
    }
}
