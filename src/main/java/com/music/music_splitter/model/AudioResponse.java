package com.music.music_splitter.model;

public class AudioResponse {

    private boolean success;
    private String message;
    private String songName;

    public AudioResponse() {
    }

    public AudioResponse(boolean success, String message, String songName) {
        this.success = success;
        this.message = message;
        this.songName = songName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
