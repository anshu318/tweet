package com.tweetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.domain.UserModel;
import com.tweetapp.dto.NewPassword;
import com.tweetapp.exception.PasswordMisMatchException;
import com.tweetapp.service.UserOperationsService;
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
import java.util.List;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

	@Mock
	private UserOperationsService userOperationsService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	private static String convertToJson(Object ob) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(ob);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testChangePassword() throws Exception {
		NewPassword newPassword = new NewPassword();
		newPassword.setNewPassword("password");
		newPassword.setContact("1234567890");

		final UserModel model = UserModel.builder().username("anshu318").password("password").firstName("Anshuman")
				.lastName("Panda").contactNum("7978683397").email("anshumanpanda318@gmail.com").build();

		Mockito.doReturn(model).when(userOperationsService).changePassword("anshu318", newPassword.getNewPassword(),
				newPassword.getContact());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tweets/{username}/forgot", "anshu318")
				.contentType(MediaType.APPLICATION_JSON).content(convertToJson(model))
				.accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetAllUsers() throws Exception {
		final List<UserModel> userModelList = Arrays.asList(
				UserModel.builder().username("anshu318").firstName("anshuman").lastName("panda")
						.email("anshuman@gmail.com").build(),
				UserModel.builder().username("henry").firstName("henry").lastName("ic")
						.email("henry@yahoo.com").build(),
				UserModel.builder().username("mathewjobin").firstName("jobin").lastName("mathew")
						.email("mathew@outloook.com").build(),
				UserModel.builder().username("johndoe").firstName("jon").lastName("doe")
						.email("johndoe@smpt.com").build(),
				UserModel.builder().username("bhanu").firstName("bhanu").lastName("ghantasala")
						.email("bhanu@ipready.com").build());

		Mockito.doReturn(userModelList).when(userOperationsService).getAllUsers();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tweets/users/all");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testChangePasswordForPassWordMismatchException() throws Exception {

		NewPassword newPassword = new NewPassword();
		newPassword.setNewPassword("newPassword");
		newPassword.setContact("1234567890");

		Mockito.doThrow(
				new PasswordMisMatchException("Dear User, New Password & Old Password didnt match. Please Try Again!"))
				.when(userOperationsService)
				.changePassword("user2", newPassword.getNewPassword(), newPassword.getContact());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tweets/{username}/forgot", "user2")
				.content(convertToJson(newPassword)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
}
