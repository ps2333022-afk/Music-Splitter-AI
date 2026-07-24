package com.music.music_splitter.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class DownloadController {


    @GetMapping("/download/{song}/{stem}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String song,
            @PathVariable String stem
    ) {


        String filePath =
                System.getProperty("user.home")
                + "/Music-Splitter/separated/htdemucs/"
                + song
                + "/"
                + stem
                + ".wav";


        File file = new File(filePath);


        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }


        Resource resource = new FileSystemResource(file);


        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getName()
                )
                .body(resource);

    }
}