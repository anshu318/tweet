package com.tweetapp.controller;

import com.tweetapp.dto.ErrorResponse;
import com.tweetapp.dto.Reply;
import com.tweetapp.dto.TweetUpdate;
import com.tweetapp.domain.Tweet;
import com.tweetapp.exception.InvalidUsernameException;
import com.tweetapp.exception.TweetDoesNotExistException;
import com.tweetapp.service.TweetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Log4j2
@RequestMapping("/tweets")
@RestController
public class UserTweetController {

	private final TweetService tweetService;

	public UserTweetController(@Qualifier("tweet-service") TweetService tweetService) {
		this.tweetService = tweetService;
	}

	// Method to post a new tweet
	@PostMapping(value = "/{username}/add", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE,
					MediaType.ALL_VALUE })
	public ResponseEntity<?> postNewTweet(@PathVariable("username") String username, @RequestBody Tweet newTweet) {
		try {
			Tweet tweet = tweetService.postNewTweet(username, newTweet);
			return new ResponseEntity<>(tweet, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Method to retrieve all tweets
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllTweets() {

		try {
			log.debug("getting all the tweets..");
			return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Method to get a user's tweets
	@GetMapping("/{username}")
	public ResponseEntity<?> getUserTweets(@PathVariable("username") String username,
			@RequestHeader(value = "loggedInUser") String loggedInUser) {
		try {
			log.debug("getting the tweets for user: {}", username);
			return new ResponseEntity<>(tweetService.getUserTweets(username, loggedInUser), HttpStatus.OK);
		} catch (InvalidUsernameException e) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{username}/{tweetId}")
	public ResponseEntity<?> getTweetDetails(@PathVariable("username") String username,
			@PathVariable("tweetId") String tweetId) {
		try {
			log.debug("getting the tweet details for user: {}", username);
			return new ResponseEntity<>(tweetService.getTweet(tweetId, username), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Method to update a tweet
	@PutMapping(value = "/{username}/update", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE,
					MediaType.ALL_VALUE })
	public ResponseEntity<?> updateTweet(@PathVariable("username") String userId,
			@RequestBody TweetUpdate tweetUpdate) {
		try {
			log.debug("updating the tweet for user: {}", userId);
			return new ResponseEntity<>(
					tweetService.updateTweet(userId, tweetUpdate.getTweetId(), tweetUpdate.getTweetText()),
					HttpStatus.OK);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Method to delete a tweet
	@DeleteMapping("/{username}/delete/{tweetId}")
	public ResponseEntity<?> deleteTweet(@PathVariable("username") String userId,
			@PathVariable(value = "tweetId") String tweetId) {
		try {
			log.debug("deleting tweet for user: {}", userId);
			return new ResponseEntity<>(tweetService.deleteTweet(tweetId), HttpStatus.NO_CONTENT);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Post tweets Like
	@PostMapping(value = "/{username}/like/{tweetId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE })
	public ResponseEntity<?> likeATweet(@PathVariable("username") String username,
			@PathVariable(value = "tweetId") String tweetId) {
		try {
			log.debug("liking tweet for user: {}", username);
			return new ResponseEntity<>(tweetService.likeTweet(username, tweetId), HttpStatus.OK);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// dislike a tweet
	@PostMapping(value = "/{username}/dislike/{tweetId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE })
	public ResponseEntity<?> dislikeATweet(@PathVariable("username") String username,
			@PathVariable(value = "tweetId") String tweetId) {
		try {
			log.debug("disliking tweet for user: {}", username);
			return new ResponseEntity<>(tweetService.dislikeTweet(username, tweetId), HttpStatus.OK);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Post tweet comment
	@PostMapping(value = "/{username}/reply/{tweetId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE })
	public ResponseEntity<?> replyToTweet(@PathVariable("username") String userId,
			@PathVariable("tweetId") String tweetId, @RequestBody Reply tweetReply) {
		try {
			log.debug("replying to the tweet for user: {}", userId);
			return new ResponseEntity<>(tweetService.replyTweet(userId, tweetId, tweetReply.getComment()),
					HttpStatus.OK);
		} catch (TweetDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
