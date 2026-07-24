package com.music.music_splitter.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DemucsService {

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

            StringBuilder output = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("Demucs: " + line);
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            System.out.println("Exit code = " + exitCode);

            if (exitCode == 0) {
                System.out.println("Demucs finished successfully");
                return "Audio separated successfully!";
            } else {
                System.out.println("Demucs failed");
                return "Demucs failed:\n" + output;
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}