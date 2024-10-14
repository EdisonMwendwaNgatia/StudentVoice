package com.example.studentvoice;

public class Complaint {
    private String id;  // Add this field for the complaint's unique ID
    private String type;
    private String details;
    private int likes;

    public Complaint() {}

    public Complaint(String id, String type, String details, int likes) {
        this.id = id;  // Initialize the id field
        this.type = type;
        this.details = details;
        this.likes = likes;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
