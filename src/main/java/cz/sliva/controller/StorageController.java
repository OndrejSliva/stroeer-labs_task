package cz.sliva.controller;

import cz.sliva.storage.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    private final StorageService storageService;

    public StorageController(final StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/track")
    public void track(@RequestBody final String requestBody) {
        storageService.store(requestBody);
    }

    @GetMapping("/count")
    public int count() {
        return storageService.getCount();
    }


}
