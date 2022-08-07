package com.example.ratepay.bug_traker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long Id;

    private String name;
    private String email;
    private String role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Bug> bugs = new ArrayList();

    public void addBug(Bug bug) {
        bugs.add(bug);
    }

    public void removeBug(Bug bug) {
        bugs.remove(bug);
    }
}
