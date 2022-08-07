package com.example.ratepay.bug_traker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long Id;

    @Column(nullable = false)
    private String name;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private User user;
    private String Description;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;
        public Bug(String name,User user,String description,String status){
            this(name,user,description);
            this.setStatus(Status.valueOf(status));

        }
    public Bug(String name,User user,String description){
        this.setName(name);
        this.setDescription(description);
        this.setUser(user);
    }
}
