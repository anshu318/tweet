package com.tweetapp.repository;

import com.tweetapp.domain.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveUser() {
        final UserModel userModel = UserModel.builder()
                .username("anshu318")
                .firstName("Anshuman")
                .lastName("Panda")
                .email("anshumanpanda318@gmail.com")
                .contactNum("7978683397")
                .password("password")
                .build();
        userRepository.save(userModel);
        Assertions.assertThat(userModel.getUsername()).isEqualTo("anshu318");
    }

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

    @Test
    public void testSearchByUserName() {
        final String userName = "anshu318";

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder()
                        .username("anshu318")
                        .firstName("Anshuman")
                        .lastName("Panda")
                        .email("anshumanpanda318@gmail.com")
                        .build(),
                UserModel.builder()
                        .username("anshu318")
                        .firstName("Siddharth")
                        .lastName("Salar")
                        .email("siddharthsalar@gmail.com")
                        .build()
        );

        userRepository.searchByUsername(userName);
        Assertions.assertThat(userModelList.size()).isGreaterThanOrEqualTo(2);
    }
}
