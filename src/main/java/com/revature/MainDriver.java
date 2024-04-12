package com.revature;

import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.models.User;
import com.revature.repository.UserDao;
import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);

    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application

        // We are using a Try with Resources block to auto close our scanner when we are done with it
        try (Scanner scanner = new Scanner(System.in)){
            /*
                The userIsActive boolean is used to control our code loop. While the user is "active" the code
                will loop. When prompted, the user can enter "q" to quit the program
             */
            boolean userIsActive = true;
            while (userIsActive){
                System.out.println("\nHello! Welcome to the Planetarium! Enter 1 to register an account, 2 to log in, q to quit");
                String userChoice = scanner.nextLine();
                if (userChoice.equals("1")){
                    // remind the user of the choice they made
                    System.out.println("You chose to register an account!");

                    // get the prospective username of the new user
                    System.out.print("Please enter your desired username: ");
                    String potentialUsername = scanner.nextLine();

                    // get the prospective password of the new yser
                    System.out.print("Please enter your desired password: ");
                    String potentialPassword = scanner.nextLine();

                    // create a User object and provide it with the username and password
                    // keep in mind the id will be set by the database if the username and password
                    // are valid
                    User potentialUser = new User();
                    potentialUser.setUsername(potentialUsername);
                    potentialUser.setPassword(potentialPassword);

                    // pass the data into the service layer for validation
                    userController.register(potentialUser);

                } else if (userChoice.equals("2")){
                    System.out.println("You chose to log in!");
                } else if (userChoice.equals("q")) {
                    System.out.println("Goodbye!");
                    userIsActive = false;
                } else {
                    System.out.println("Invalid choice, please try again");
                }
            }

        }


    }

}
