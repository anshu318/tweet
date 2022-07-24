package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.domain.Tweet;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Repository("tweet-repository")
public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByUsername(String username);
}
