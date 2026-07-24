package com.music.music_splitter.controller;

import com.music.music_splitter.model.AudioResponse;
import com.music.music_splitter.service.DemucsService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class UploadController {

    private final DemucsService demucsService;

    public UploadController(DemucsService demucsService) {
        this.demucsService = demucsService;
    }



    @PostMapping("/upload")
    public AudioResponse uploadAudio(@RequestParam("file") MultipartFile file) {


        if (file.isEmpty()) {
            return new AudioResponse(
                    false,
                    "Please select an audio file",
                    null
            );
        }


        try {

            String uploadPath = System.getProperty("user.home")
                    + "/Music-Splitter/uploads/";


            File folder = new File(uploadPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }


            File saveFile = new File(
                    uploadPath + file.getOriginalFilename()
            );


            file.transferTo(saveFile);


            String result = demucsService.splitAudio(
                    saveFile.getAbsolutePath()
            );


            String songName = file.getOriginalFilename()
                    .replace(".mp3", "")
                    .replace(".wav", "");


            return new AudioResponse(
                    true,
                    result,
                    songName
            );


        } catch (IOException e) {


            return new AudioResponse(
                    false,
                    "Upload failed: " + e.getMessage(),
                    null
            );

        }

    }
}