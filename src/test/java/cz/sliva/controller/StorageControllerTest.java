package cz.sliva.controller;

import cz.sliva.storage.StorageService;
import cz.sliva.utils.json.exception.InvalidJsonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageControllerTest {

    private StorageService storageServiceMock;
    private StorageController storageController;

    @BeforeEach
    public void init() {
        storageServiceMock = mock(StorageService.class);
        storageController = new StorageController(storageServiceMock);
    }

    @Test
    public void shouldReturnSuccessWhenJsonIsStored() {
        final String jsonString = "{\"valid\":\"json\"}";
        final ResponseEntity<String> response = storageController.track(jsonString);

        verify(storageServiceMock, times(1)).store(jsonString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnErrorWhenJsonIsInvalid() {
        doThrow(InvalidJsonException.class).when(storageServiceMock).store(any());

        final ResponseEntity<String> response = storageController.track("invalid json");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnValueWhenCountContainsValue() {
        final int value = 8;
        when(storageServiceMock.getCount()).thenReturn(Optional.of(value));

        final ResponseEntity<String> response = storageController.count();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(value, Integer.valueOf(response.getBody()));
    }

    @Test
    public void shouldReturnErrorWhenCountIsEmpty() {
        when(storageServiceMock.getCount()).thenReturn(Optional.empty());

        final ResponseEntity<String> response = storageController.count();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}