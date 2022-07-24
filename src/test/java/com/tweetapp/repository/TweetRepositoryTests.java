package com.tweetapp.repository;

import com.tweetapp.domain.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class TweetRepositoryTests {

    @Autowired
    private TweetRepository userRepository;

    @Test
    public void testFindUserByName() {
        final String username = "anshu318";
        final UserModel user = UserModel.builder()
                .username("anshu318")
                .firstName("Anshuman")
                .lastName("Panda")
                .email("anshumanpanda318@gmail.com")
                .build();
        userRepository.findByUsername(username);
        Assertions.assertThat(username).isEqualTo(user.getUsername());
    }

}
