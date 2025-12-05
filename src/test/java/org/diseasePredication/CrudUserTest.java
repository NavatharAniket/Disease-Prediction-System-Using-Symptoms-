package org.diseasePredication;

import java.util.List;

import org.diseasePredication.helper.ServiceHelper;
import org.diseasePredication.model.UserModel;
import org.junit.jupiter.api.*;

public class CrudUserTest {

	@BeforeEach
	public void setup() {
		// Ensure clean start
		ServiceHelper.UserRepository.isDeleteUser("test@gmail.com");

		// Insert a fresh user for all tests
		UserModel user = new UserModel();
		user.setName("Test");
		user.setEmail("test@gmail.com");
		user.setPassword("12345");
		ServiceHelper.UserRepository.isAddUser(user);
	}

	@AfterEach
	public void cleanup() {
		ServiceHelper.UserRepository.isDeleteUser("test@gmail.com");
	}

	@Test
	public void insertUserTest() {
		UserModel u = new UserModel();
		u.setName("A");
		u.setEmail("anotherTest@gmail.com");
		u.setPassword("12345");

		boolean result = ServiceHelper.UserRepository.isAddUser(u);
		Assertions.assertEquals(true, result, "user added successfully");

		// clean up
		ServiceHelper.UserRepository.isDeleteUser("anotherTest@gmail.com");
	}

	@Test
	public void dublicateUserTest() {
		UserModel userModel = new UserModel();
		userModel.setEmail("test@gmail.com");

		boolean result = ServiceHelper.UserRepository.isUserPresent(userModel);
		Assertions.assertEquals(true, result, "user Already Present");
	}

	@Test
	public void updateUserTest() {
		UserModel userModel = new UserModel();
		userModel.setEmail("test@gmail.com");
		userModel.setName("Updated");
		userModel.setPassword("newpass");

		UserModel updated = ServiceHelper.UserRepository.updateUser(userModel);

		boolean result = updated != null;
		Assertions.assertEquals(true, result, "User Updated Successfully");
	}

	@Test
	public void deleteUserTest() {
		boolean result = ServiceHelper.UserRepository.isDeleteUser("test@gmail.com");
		Assertions.assertEquals(true, result, "user Deleted Successfully");
	}

	@Test
	public void displayAllUsers() {
		List<UserModel> list = ServiceHelper.UserRepository.getAllUsers();
		boolean result = list != null;
		Assertions.assertEquals(true, result, "users displayed Successfully");
	}
}
