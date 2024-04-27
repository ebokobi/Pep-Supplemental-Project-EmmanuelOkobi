package com.revature.controller;

import com.revature.MainDriver;
import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(UsernamePasswordAuthentication loginRequestData) {
		// since we are checking the credentials against the database we can simply pass them to the service layer
		User possibleUser = userService.authenticate(loginRequestData);
		if (possibleUser.getId() != 0){
			MainDriver.loggedInUserId = possibleUser.getId();
			MainDriver.loggedInUsername = possibleUser.getUsername();
			System.out.println(String.format("\n\nWelcome back to the Planetarium %s!", possibleUser.getUsername()));
		} 
	}

	public void register(User registerRequestData) throws UserFailException {
		User userResponse = userService.register(registerRequestData);
		if (userResponse.getId() != 0){
			System.out.println("\n\nRegistration successful! Login and enjoy using the Planetarium!");
		}
	}

	public void logout() {
		MainDriver.loggedInUserId = 0;
		MainDriver.loggedInUsername = "";
		System.out.println("\nGoodbye!");
	}
	
	public boolean checkAuthorization(int userId) {	
		if (MainDriver.loggedInUserId == userId){
			return true;
		}
		return false;
	}
}
