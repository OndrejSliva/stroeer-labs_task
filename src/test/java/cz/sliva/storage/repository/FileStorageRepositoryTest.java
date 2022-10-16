package cz.sliva.storage.repository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageRepositoryTest {

    private final String FIRST_JSON = "{\"foo\":\"bar\"}";
    private final String SECOND_JSON = "{\"another\":\"json\"}";

    @TempDir
    private Path outputDir;
    private Path outputFile;
    private FileStorageRepository fileStorageRepository;

    @BeforeEach
    public void init() {
        outputFile = outputDir.resolve("outputFile.json");
        fileStorageRepository = new FileStorageRepository(outputFile.toString());
    }

    @Test
    public void contentShouldBeStoredToFile() throws IOException {
        assertTrue(Files.exists(outputDir));
        assertFalse(Files.exists(outputFile));

        fileStorageRepository.store(new JSONObject(FIRST_JSON));

        assertTrue(Files.exists(outputFile));

        final String fileContent = Files.readAllLines(outputFile).get(0);
        assertEquals(FIRST_JSON, fileContent);
    }

    @Test
    public void anotherContentShouldBeAppendedToFile() throws IOException {
        assertTrue(Files.exists(outputDir));
        assertFalse(Files.exists(outputFile));

        fileStorageRepository.store(new JSONObject(FIRST_JSON));
        fileStorageRepository.store(new JSONObject(SECOND_JSON));

        assertTrue(Files.exists(outputFile));

        final String firstLine = Files.readAllLines(outputFile).get(0);
        final String secondLine = Files.readAllLines(outputFile).get(1);
        assertEquals(FIRST_JSON, firstLine);
        assertEquals(SECOND_JSON, secondLine);
    }
}