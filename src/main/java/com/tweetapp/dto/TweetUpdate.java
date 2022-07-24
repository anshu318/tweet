package com.tweetapp.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetUpdate {
    private String tweetId;
    private String tweetText;
    private String tweetDate = Instant.now().toString();
}
