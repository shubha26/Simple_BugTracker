package com.example.ratepay.bug_traker.controller;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class BugRequest {

    @NonNull
    private String name;
    private String description;
    @NonNull
    private String user;
    private String status;
    private Long timestamp;

    public BugRequest(@NonNull String name, String description, @NonNull String user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
