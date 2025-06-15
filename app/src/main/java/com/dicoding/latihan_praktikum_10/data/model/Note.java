package com.dicoding.latihan_praktikum_10.data.model;

public class Note {
    private String id;
    private String userId;
    private String title;
    private String content;
    private long timestamp;
    private String imagePath;  // <--- Tambahkan ini

    public Note() {}

    public Note(String id, String userId, String title, String content, long timestamp, String imagePath) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.imagePath = imagePath;
    }

    public Note(String title, String content, long timestamp, String imagePath) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.imagePath = imagePath;
    }

    // Getter Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
