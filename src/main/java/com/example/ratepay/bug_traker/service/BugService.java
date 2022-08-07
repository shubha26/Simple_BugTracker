package com.example.ratepay.bug_traker.service;

import com.example.ratepay.bug_traker.controller.BugRequest;
import com.example.ratepay.bug_traker.model.Bug;
import com.example.ratepay.bug_traker.model.Status;
import com.example.ratepay.bug_traker.model.User;
import com.example.ratepay.bug_traker.repository.BugRepository;
import com.example.ratepay.bug_traker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BugService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;


    public List<Bug> getAllBugs(User user) {
        return bugRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("No Bugs Assigned to this user"));
    }

    public List<Bug> getBugList(String user) {
        User user1= getUser(user);
        return bugRepository.findByUser(user1)
                .orElseThrow(() -> new EntityNotFoundException("No Bugs Assigned to this user"));
    }
    @Transactional
    public boolean createUpdateBug(BugRequest bugRequest) {
        Optional<Bug> bug = getBug(bugRequest.getName(), bugRequest.getUser());

        Bug newBug;
        if (!bug.isPresent()) {//New Bug
            newBug = new Bug();
            newBug.setStatus(Status.TO_DO);
        } else {
            newBug = bug.get();
            newBug.setStatus(Status.valueOf(bugRequest.getStatus()));
        }
        User user1 = userRepository.findByName(bugRequest.getUser())
                .orElseThrow(() -> new EntityNotFoundException("Invalid User"));

        newBug.setName(bugRequest.getName());
        newBug.setUser(user1);
        newBug.setDescription(bugRequest.getDescription());
         Bug saveBug = bugRepository.save(newBug);
                user1.addBug(saveBug);
                userRepository.save(user1);
            return true;
    }
    @Transactional
    public void deleteBug(BugRequest bugRequest) {
        Optional<Bug> bug = getBug(bugRequest.getName(), bugRequest.getUser());
        User user = getUser(bugRequest.getUser());
        if (bug.isPresent()) {
           bugRepository.delete(bug.get());
                    user.removeBug(bug.get());
                    userRepository.save(user);

        } else throw new EntityNotFoundException("Bug does not exists");
    }

    private Optional<Bug> getBug(String name, String user) {
        return getAllBugs(getUser(user)).stream()
                .filter(b -> b.getName().equals(name)).findFirst();
    }

    private User getUser(String user) {
        return userRepository.findByName(user)
                .orElseThrow(() -> new EntityNotFoundException("Invalid User"));
    }
    @Transactional
    public void deleteBugbyId(String bugId) {
        Optional<Bug> bug = bugRepository.findById(Long.valueOf(bugId));
        if (bug.isPresent()) {
            User user = getUser(bug.get().getUser().getName());
           bugRepository.delete(bug.get());
                    user.removeBug(bug.get());
                    userRepository.save(user);
        }
        }
}