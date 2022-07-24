package com.tweetapp.service;

import com.tweetapp.dto.Comment;
import com.tweetapp.dto.TweetResponse;
import com.tweetapp.domain.Tweet;
import com.tweetapp.exception.InvalidUsernameException;
import com.tweetapp.repository.TweetRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import org.mockito.Mockito;
import java.util.List;

import static org.mockito.Mockito.doNothing;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Log4j2
@ExtendWith(SpringExtension.class)
public class TweetServiceTest {

	@Mock
	private TweetRepository tweetRepository;

	@InjectMocks
	private TweetService tweetService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllTweets() {
		final List<Tweet> tweets = Arrays.asList(
				new Tweet("1", "user1", "tweet1", "first", "last", Instant.now().toString(),
						Collections.singletonList("2"),
						Arrays.asList(new Comment("user2", "comment1"), new Comment("user2", "comment2"),
								new Comment("user2", "comment2"))),

				new Tweet("2", "user2", "tweet2", "second", "last", Instant.now().toString(),
						Collections.singletonList("2"), Arrays.asList(new Comment("user3", "comment1"),
								new Comment("user3", "comment2"), new Comment("user1", "comment1"))));

		Mockito.when(tweetRepository.findAll()).thenReturn(tweets);
		List<Tweet> fetchTweets = tweetService.getAllTweets();
		Assertions.assertEquals(fetchTweets.size(), tweets.size());
		log.debug("Tested - #MethodName: getAllTweets() successfully.");

	}

	@Test
	public void testGetUserTweets() throws InvalidUsernameException {
		final String username = "ikalyan183@gmail.com";
		final String loggedInUser = "ikalyan183@gmail.com";

		final List<Tweet> tweets = Collections.singletonList(
				new Tweet("1", "ikalyan183@gmail.com", "tweet1", "first", "last", Instant.now().toString(),
						Collections.singletonList("2"), Arrays.asList(new Comment("user2", "comment1"),
								new Comment("user2", "comment2"), new Comment("user2", "comment2"))));

		Mockito.when(tweetRepository.findByUsername(username)).thenReturn(tweets);

		List<TweetResponse> fetchTweets = tweetService.getUserTweets(username, loggedInUser);
		Assertions.assertEquals(fetchTweets.get(0).getUsername(), tweets.get(0).getUsername());
		log.debug("Tested - #MethodName: getUserTweets() successfully.");
	}

	@Test
	public void testDeleteTweet() {
		final String tweetId = "123";
		Mockito.when(tweetRepository.existsById(tweetId)).thenReturn(true);
		doNothing().when(tweetRepository).deleteById(tweetId);
		log.debug("Tested - #MethodName: deleteTweet() successfully.");
	}
}
