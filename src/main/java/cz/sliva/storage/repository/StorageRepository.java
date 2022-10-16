package cz.sliva.storage.repository;

import org.json.JSONObject;

public interface StorageRepository {

    void store(final JSONObject json);

}
