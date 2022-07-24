package com.tweetapp.service;

import com.tweetapp.domain.UserModel;
import com.tweetapp.exception.PasswordMisMatchException;
import com.tweetapp.exception.UsernameAlreadyExists;
import com.tweetapp.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Log4j2
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserOperationsServiceTest {

    @InjectMocks
    private UserOperationsService userOperationsService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindUserByName() {
        final String username = "ikalyan183@gmail.com";
        final UserModel user = UserModel.builder()
                .username("ikalyan183@gmail.com")
                .firstName("kalyan")
                .lastName("says")
                .email("ikalyan183@gmail.com")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(user);
        UserModel userModel = userOperationsService.findByUsername(username);
        Assert.assertEquals(user.getUsername(), userModel.getUsername());
        log.debug("Tested - #MethodName: findUserByName() successfully.");

    }

    @Test
    public void testChangePassword() throws PasswordMisMatchException {

        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
                .firstName("henry")
                .lastName("ic")
                .contactNum("1234567890")
                .email("henry@yahoo.com").build();

        when(userRepository.findByUsername("henry@yahoo.com")).thenReturn(model);
        when(userRepository.save(model)).thenReturn(model);
        UserModel userModel = userOperationsService.changePassword("henry@yahoo.com", "newPassword", "1234567890");
        Assert.assertEquals(userModel.getContactNum(), model.getContactNum());
        log.debug("Tested - #MethodName: changePassword() successfully.");
    }

    @Test
    public void testSearchByUserName() {
        final String userName = "kalyan";

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder()
                        .username("ikalyan183@gmail.com")
                        .firstName("kalyan")
                        .lastName("says")
                        .email("ikalyan183@gmail.com")
                        .build(),
                UserModel.builder()
                        .username("kalyan@gmail.com")
                        .firstName("kalyan")
                        .lastName("here")
                        .email("kalyan@gmail.com")
                        .build()
        );
        when(userRepository.searchByUsername(userName)).thenReturn(userModelList);
        final List<UserModel> listOfUsers = userOperationsService.getUsersByUsername(userName);
        Assert.assertEquals(userModelList.size(), listOfUsers.size());
        log.debug("Tested - #MethodName: searchByUserName() successfully.");
    }

    @Test
    public void testGetAllUsers() {

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder().username("ikalyan183@gmail.com").firstName("kalyan").lastName("says").email("ikalyan183@gmail.com").build(),
                UserModel.builder().username("henry@yahoo.com").firstName("henry").lastName("ic").email("henry@yahoo.com").build(),
                UserModel.builder().username("mathew@outloook.com").firstName("math").lastName("ew").email("mathew@outloook.com").build(),
                UserModel.builder().username("johndoe@smpt.com").firstName("jon").lastName("doe").email("johndoe@smpt.com").build(),
                UserModel.builder().username("heysup@posal.com").firstName("hey").lastName("sup").email("heysup@posal.com").build()
        );
        when(userRepository.findAll()).thenReturn(userModelList);
        final List<UserModel> listOfUsers = userOperationsService.getAllUsers();
        Assert.assertEquals(userModelList.size(), listOfUsers.size());
        log.debug("Tested - #MethodName: getAllUsers() successfully.");
    }

    @Test
    public void testCreateUserThrowingUsernameAlreadyExists() {
        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
                .firstName("henry")
                .lastName("ic")
                .contactNum("1234567890")
                .email("henry@yahoo.com").build();

        when(userRepository.findByUsername("henry@yahoo.com")).thenReturn(model);
        assertThrows(UsernameAlreadyExists.class, () -> userOperationsService.createUser(model));
    }
}
