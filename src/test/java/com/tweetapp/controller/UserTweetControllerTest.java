package com.tweetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.dto.Comment;
import com.tweetapp.domain.Tweet;
import com.tweetapp.exception.InvalidUsernameException;
import com.tweetapp.service.TweetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTweetControllerTest {


    @Mock
    private TweetService tweetService;

    @InjectMocks
    private UserTweetController userTweetController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTweetController).build();

    }

    @Test
    public void testGetAllTweets() throws Exception {
        final List<Tweet> tweets = Arrays.asList(
                new Tweet("1", "user1", "tweet1", "first", "last",
                        new Date().toString(), Collections.singletonList("2"),
                        Arrays.asList(new Comment("user2", "comment1"),
                                new Comment("user2", "comment2"),
                                new Comment("user2", "comment2"))),

                new Tweet("2", "user2", "tweet2", "second", "last",
                        new Date().toString(), Collections.singletonList("2"),
                        Arrays.asList(new Comment("user3", "comment1"),
                                new Comment("user3", "comment2"),
                                new Comment("user1", "comment1")))
        );

        Mockito.doReturn(tweets).when(tweetService).getAllTweets();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tweets/all");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testPostTweet() throws Exception {
        Tweet postTweet = new Tweet("2", "user2", "tweet2", "second", "last",
                new Date().toString(), Collections.singletonList("2"),
                Arrays.asList(new Comment("user3", "comment1"),
                        new Comment("user3", "comment2"),
                        new Comment("user1", "comment1")));


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(postTweet);


        Mockito.doReturn(postTweet).when(tweetService).postNewTweet("user2", postTweet);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/tweets/{username}/add", "user2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testGetUserTweetsWithInvalidUserNameException() throws Exception {

        Mockito.doThrow(new InvalidUsernameException("Username provided is invalid"))
                .when(tweetService)
                .getUserTweets("user2", "loggedInUser");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tweets/{username}", "user2")
                .header("loggedInUser", "loggedInUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andReturn();
        Assert.assertTrue(HttpStatus.NOT_FOUND.is4xxClientError());
    }
}
