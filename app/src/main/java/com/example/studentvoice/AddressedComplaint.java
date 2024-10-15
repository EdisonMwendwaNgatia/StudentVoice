package com.example.studentvoice;

public class AddressedComplaint {
    private final String id;
    private final String details;

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
