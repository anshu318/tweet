package com.tweetapp.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tweetapp.dto.Comment;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "tweets")
public class Tweet {

	@Id
	private String tweetId;
	private String username;
	private String tweetText;
	private String firstName;
	private String lastName;
	private String tweetDate = Instant.now().toString();
	private List<String> likes = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();

}
