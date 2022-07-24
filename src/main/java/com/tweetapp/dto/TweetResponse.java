package com.tweetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tweetId;
	private String username;
	private String tweetText;
	private String firstName;
	private String lastName;
	private String tweetDate;
	private Integer likesCount;
	private Integer commentsCount;
	private Boolean likeStatus;
	private List<Comment> comments = new ArrayList<>();

}
