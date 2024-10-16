package com.example.studentvoice;

public class AddressedComplaint {
    private String id;      // Firebase requires a no-argument constructor
    private String details;

    // No-argument constructor required for Firebase
    public AddressedComplaint() {
        // Firebase will use this constructor to create an instance
    }

    // Constructor with parameters
    public AddressedComplaint(String id, String details) {
        this.id = id;
        this.details = details;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Getter for details
    public String getDetails() {
        return details;
    }

    // Optional: Setter for id (if you plan to modify the id after creation)
    public void setId(String id) {
        this.id = id;
    }

    // Optional: Setter for details (if you plan to modify details after creation)
    public void setDetails(String details) {
        this.details = details;
    }
}
