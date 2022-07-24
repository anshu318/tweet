package com.tweetapp.exception;

public class TweetDoesNotExistException extends RuntimeException {
	
	public TweetDoesNotExistException(String msg) {
		super(msg);
	}
}
