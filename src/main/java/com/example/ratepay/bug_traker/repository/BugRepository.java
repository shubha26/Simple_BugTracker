package com.example.ratepay.bug_traker.repository;

import com.example.ratepay.bug_traker.model.Bug;
import com.example.ratepay.bug_traker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BugRepository extends JpaRepository<Bug, Long> {
    Optional<List<Bug>> findByUser(User user);

    //void delete(Bug bug);
}


