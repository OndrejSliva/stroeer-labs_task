package cz.sliva.controller;

import cz.sliva.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StorageController {

    private final StorageService storageService;

    public StorageController(final StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/track")
    public ResponseEntity<String> track(@RequestBody final String requestBody) {
        storageService.store(requestBody);
        return new ResponseEntity<>("Data stored", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<String> count() {
        final Optional<Integer> count = storageService.getCount();
        if (count.isEmpty()) {
            return new ResponseEntity<>("Value of counter was not initialized yet.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.valueOf(count.get()), HttpStatus.OK);
    }
}
