package com.shortentlinks.app.backend.controller;

import com.shortentlinks.app.backend.dto.ResDTO;
import com.shortentlinks.app.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCaching
@RequestMapping("/api/v1/shorten")
public class BackendController {

    private final BackendService backendService;

    @Autowired
    public BackendController(BackendService s) {
        this.backendService = s;
    }

    @PostMapping("")
    public ResponseEntity<ResDTO> getShortenLink(@RequestParam String originLink) {
        String shortLink = backendService.createShortenLinkHandler(originLink);
        return ResponseEntity.ok(new ResDTO(shortLink, originLink));
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<ResDTO> getOriginLink(@PathVariable String linkId) {
        String originLink = backendService.getOriginLinkHandler(linkId);
        return ResponseEntity.ok(new ResDTO(linkId, originLink));
    }
}
