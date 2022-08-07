package com.example.ratepay.bug_traker.controller;

import com.example.ratepay.bug_traker.service.BugService;
import com.example.ratepay.bug_traker.model.Bug;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ratepay/tracker")
public class BugController {

    @Autowired
    private final BugService bugService;
    private  final Logger logger =  LoggerFactory.getLogger(BugController.class);
    @GetMapping("/bugs/{user}")
    public ResponseEntity<List<Bug>> findAllBugsForUser(@PathVariable String user) {

        List<Bug> allBugs;
        try {
            allBugs = bugService.getBugList(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(allBugs);
    }

    //add Bug (user)
    @PostMapping("/bugs")
    public ResponseEntity<?> addBug(@RequestBody BugRequest bugRequest) {
        Bug bug;
        try {
            bugService.createUpdateBug(bugRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{user}/bugs/{bugName}")
    public ResponseEntity<?> deleteBugByNameAndUser(@RequestBody BugRequest bugRequest) {
        try {
            bugService.deleteBug(bugRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bugs/{bugId}")
    public ResponseEntity<?> deleteBugId( @PathVariable String bugId) {
       try {
            bugService.deleteBugbyId(bugId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

}
