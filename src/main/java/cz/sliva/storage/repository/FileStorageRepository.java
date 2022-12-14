package cz.sliva.storage.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Repository
public class FileStorageRepository implements StorageRepository {

    private final String filePath;

    public FileStorageRepository(@Value("${storage.flat-file.path}") final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void store(final JSONObject json) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true))) {
            writer.write(json.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
