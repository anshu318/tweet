package com.tweetapp;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class TweetAppApplication {

    public static void main(String[] args) {
        log.info("Initialization");
        TimeZone.setDefault(TimeZone.getDefault());
        SpringApplication.run(TweetAppApplication.class, args);
    }

}
