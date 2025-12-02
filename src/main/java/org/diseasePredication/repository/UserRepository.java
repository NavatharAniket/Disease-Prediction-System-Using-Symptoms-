package org.diseasePredication.repository;

import java.util.List;

import org.diseasePredication.model.UserModel;

public interface UserRepository {
	boolean isAddUser(UserModel user);
	public List<UserModel>getAllUsers();
	public UserModel updateUser(UserModel userModel);
	public boolean isDeleteUser(String email);
	public boolean isUserPresent(UserModel userModel);
}
