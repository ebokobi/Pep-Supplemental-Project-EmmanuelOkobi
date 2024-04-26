package com.revature.service;

import com.revature.MainDriver;
import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// since this is where the credentials are actually authenticated we can use the username and newly finished
		// dao method to find users by username, and check to see if we get a user back
		User possibleUser = dao.getUserByUsername(loginRequestData.getUsername());
		if(possibleUser.getUsername() != null && possibleUser != null){ // if dao method doesnt return null and doesn't return an empty user then user exists
			boolean passwordsMatch = loginRequestData.getPassword().equals(possibleUser.getPassword());
			if (passwordsMatch){
				return possibleUser;
			} else { System.out.println(new UserFailException("Username and/or Password incorrect, please try again.\n"));}
		}  // else username is available
		return new User();
	}



	public User register(User registerRequestData) throws UserFailException{
		/*
			there are two software requirements I need to check in this method:
				username needs to be unique
				username/password need to be a maximum of 30 characters
		 */
		// we can check the length of the username and password without needing any information from the
		// database that has other user information. This is a simple if else check
		if (registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30){
			/*
				This block of code should only be reached if the username and password are the proper length. If
				that is the case, we can then check that the username is unique, since there is no point in making
				this check if the username and/or password is the incorrect length
			 */
			// NOTE: the dao method currently returns a null value
			User databaseData = dao.getUserByUsername(registerRequestData.getUsername());
			/*
				Using the dao method to grab any account that already has the username the registering user
				is trying to use, we can check if the username is already in use. If not, we can go through with
				creating the new account, but if it is in use, we send back an empty User object to inform the
				controller layer that the account could not be made.
			 */

			/*
				I am checking two things here:
					1. is the usernameFromDatabase object not null (meaning there is a getUsername method I can call)
					2. assuming a User object was returned, does it have the same username as the register request username

			 */

			// if the data from the database is null then something has gone wrong and I should give a fail
			// message to the user
			if(databaseData != null){
				// for the sake of readability I am saving the data I work with in its own variables
				// checking if the usernames are not the same
				if (!registerRequestData.getUsername().equals(databaseData.getUsername())){
					// if the data is not the same, then the credentials are valid and I can go through
					// with account registration. Note the registration method requires a new object:
					// a UsernamePasswordAuthentication object specifically
					UsernamePasswordAuthentication validUserCreds = new UsernamePasswordAuthentication();
					validUserCreds.setUsername(registerRequestData.getUsername());
					validUserCreds.setPassword(registerRequestData.getPassword());

					// We can shorten our code a bit and return the User data once it is persisted in the
					// database. We can return a User object with Data that has been initialized to inform
					// the controller layer the registration process was successful
					return dao.createUser(validUserCreds);
				} //else { //if nonempty user is returned by database then username is taken
					// if credentials are not the same then username is taken
					//System.out.print("\n\nUsername already in use, create something unique and try again!\n");
				//}
			} else {
				System.out.println(new UserFailException("\n\nSomething went wrong, please try again.\n")); //possible bugger
				return new User();
			}
		}
		else {
			System.out.println(new UserFailException("\n\nMake sure both your Username and Password don't exceed 30 characters."));
			MainDriver.reEnterRegistration();
		}
		// if any Business/Software requirements are not met, we return an empty user which we can use to
		// inform the controller layer that the registration process failed
		return new User();
	}
}
