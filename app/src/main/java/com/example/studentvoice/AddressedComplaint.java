package com.example.studentvoice;

public class AddressedComplaint {
    private String id;
    private String details;

    public AddressedComplaint() {}

    public AddressedComplaint(String id, String details) {
        this.id = id;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }
}
