package com.example.ratepay.bug_traker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class BugTrackerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app){
        return app.sources(BugTrackerApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(BugTrackerApplication.class, args);
    }
}
