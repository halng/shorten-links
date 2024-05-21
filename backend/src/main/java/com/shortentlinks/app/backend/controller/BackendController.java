package com.shortentlinks.app.backend.controller;

import com.shortentlinks.app.backend.dto.ResDTO;
import com.shortentlinks.app.backend.service.BackendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shorten")
public class BackendController {

    private final BackendService backendService;

    public BackendController() {
        this.backendService = new BackendService();
    }

    @GetMapping("")
    public ResponseEntity<ResDTO> getShortenLink(@RequestParam String originLink) {
        String shortLink = backendService.getUniqueStr(originLink);
        return ResponseEntity.ok(new ResDTO(shortLink, originLink));
    }
}
