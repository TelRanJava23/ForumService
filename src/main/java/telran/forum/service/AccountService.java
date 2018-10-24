package telran.forum.service;

import java.util.Set;

import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;

public interface AccountService {

	public UserProfileDto addUser(UserRegisterDto userRegDto, String auth);

	public UserProfileDto editUser(UserRegisterDto userRegDto, String auth);

	public UserProfileDto removeUser(String id, String auth);
	
	public Set<String> addRole(String id, String role, String auth);
	
	public Set<String> removeRole(String id, String role, String auth);
	
	public void changePassword(String password, String auth);

}
