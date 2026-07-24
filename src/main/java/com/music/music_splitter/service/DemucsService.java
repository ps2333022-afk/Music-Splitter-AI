package com.music.music_splitter.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class DemucsService {

    private String lastOutputFolder;

    public String splitAudio(String audioPath) {

        try {

            System.out.println("Starting Demucs...");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "/usr/bin/python3",
                    "-m",
                    "demucs",
                    audioPath
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("Demucs: " + line);
            }

            int exitCode = process.waitFor();

            System.out.println("Exit code = " + exitCode);


            if (exitCode == 0) {

                File audioFile = new File(audioPath);

                String songName = audioFile.getName()
                        .replace(".mp3", "")
                        .replace(".wav", "")
                        .replace(".m4a", "");

                lastOutputFolder =
                        System.getProperty("user.home")
                        + "/project/Music-Splitter/separated/htdemucs/"
                        + songName + "/";


                System.out.println("Output folder: " + lastOutputFolder);

                System.out.println("Demucs finished successfully");

                return "Audio separated successfully!";

            } else {

                System.out.println("Demucs failed");
                return "Demucs failed!";
            }

        } catch (Exception e) {

            return "Error: " + e.getMessage();

        }
    }


    public String getLastOutputFolder() {

        return lastOutputFolder;

    }
}