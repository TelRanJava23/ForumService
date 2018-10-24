package telran.forum.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountManagmentController {
	@Autowired
	AccountService accountService;
	
	@PostMapping("/register")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto,
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.addUser(userRegisterDto, auth);
	}
	
	@PutMapping
	public UserProfileDto edit(@RequestBody UserRegisterDto userRegisterDto, 
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.editUser(userRegisterDto, auth);
	}
	
	@DeleteMapping("/{id}")
	public UserProfileDto remove(@PathVariable String id, 
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.removeUser(id,auth);
	}
	
	@PutMapping("/{id}/{role}")
	public Set<String> addRole(@PathVariable String id, @PathVariable String role,
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.addRole(id, role, auth);
	}
	
	@DeleteMapping("/{id}/{role}")
	public Set<String> removeRole(@PathVariable String id, @PathVariable String role,
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.removeRole(id, role, auth);
	}
	
	@PutMapping("/password")
	public void changePassword(@RequestHeader(value = "X-Password") String password,
			@RequestHeader(value = "Authorization") String auth) {
		accountService.changePassword(password, auth);
	}

}
