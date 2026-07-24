package com.music.music_splitter.controller;

import com.music.music_splitter.service.DemucsService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/download")
@CrossOrigin(origins = "*")
public class DownloadController {

    private final DemucsService demucsService;

    public DownloadController(DemucsService demucsService) {
        this.demucsService = demucsService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> download(
            @PathVariable String fileName) {

        String folderPath = demucsService.getLastOutputFolder();

        if (folderPath == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(folderPath + fileName + ".wav");

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getName() + "\""
                )
                .body(resource);
    }
}