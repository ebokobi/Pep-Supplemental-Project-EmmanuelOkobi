package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.exceptions.UserFailException;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.Moon;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.controller.PlanetController;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.MoonService;
import com.revature.repository.MoonDao;
import com.revature.controller.MoonController;
import com.revature.service.PlanetService;
import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {

    public static int loggedInUserId = 0;
    public static String loggedInUsername = "";

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);

    public static PlanetDao planetDao = new PlanetDao();
    public static PlanetService planetService = new PlanetService(planetDao);
    public static PlanetController planetController = new PlanetController(planetService);

    public static MoonDao moonDao = new MoonDao();
    public static MoonService moonService = new MoonService(moonDao);
    public static MoonController moonController = new MoonController(moonService);

    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        // We are using a Try with Resources block to auto close our scanner when we are done with it
        try (Scanner scanner = new Scanner(System.in)){
            while (loggedInUserId == 0){
                System.out.print("\n>>> Hello! Welcome to the Planetarium! <<< \n\n To Enter, Select: \n1. Register an account \n2. Login to your account. \n3. Leave the Planetarium. \n\nClick: ");
                String anonUserChoice = scanner.nextLine();
                if (anonUserChoice.equals("1")){
                    // remind the user of the choice they made
                    System.out.println("\n>>> Planetarium Account Registration <<<");
                    // get the prospective username of the new user
                    System.out.print("\nPlease enter your desired username (maximum 30 characters): ");
                    String potentialUsername = scanner.nextLine();

                    // get the prospective password of the new user
                    System.out.print("\nPlease enter your desired password (maximum 30 characters): ");
                    String potentialPassword = scanner.nextLine();

                    // create a User object and provide it with the username and password
                    // keep in mind the id will be set by the database if the username and password
                    // are valid
                    User potentialUser = new User();

                    // pass the data into the service layer for validation
                    if(userDao.getUserByUsername(potentialUsername).getUsername() == potentialUser.getUsername()){ //if both currently null the username is available
                        potentialUser.setUsername(potentialUsername);
                        potentialUser.setPassword(potentialPassword);
                        userController.register(potentialUser);
                    }
                    else {
                        System.out.print("\nUsername already in use, if you meant to login then select return. If not, try to create a unique one! \n\n1. Try again \n2. Return \n\nClick: " );
                        String userAction = scanner.nextLine();
                        if (userAction.equals("1")){
                            reEnterRegistration();
                        } 
                        else if (userAction.equals("2")) { 
                            continue;
                        }
                        else { System.out.println("\nInvalid action, try again");  tryAgain();}
                    }
                }

                else if (anonUserChoice.equals("2")){ // user has chosen to log in
                    System.out.println(">>> Planetarium Account Login <<<"); 
                    System.out.print("Please enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Please enter your password: ");
                    String password = scanner.nextLine();

                    UsernamePasswordAuthentication credentials = new UsernamePasswordAuthentication();
                    credentials.setUsername(username);
                    credentials.setPassword(password);

                    userController.authenticate(credentials);
                }
                 
                else if (anonUserChoice.equals("3")) { // user has chosen to exit 
                    System.out.println("\n*Thanks for visiting the Planetarium, Goodbye*!");
                    break;
                } 
                else {
                    System.out.println("\nInvalid choice, please try again.");
                }
            }

            while (loggedInUserId > 0){
                System.out.println(String.format("\n>>> World Conqueror: %s <<<", loggedInUsername));
                System.out.println("\nExercise Authority: \n1. Create a Planet  \n2. Create a Moon \n3. View Planets \n4. View Moons \n5. Delete a planet \n6. Delete a moon \n7. Logout \nClick:");
                String loggedInUserChoice = scanner.nextLine();
                if (loggedInUserChoice.equals("1")){ // user has chosen to create a planet
                    Planet userNewPlanet = new Planet();
                    System.out.println("\n>>> Create a Planet <<< \nName Your Planet: ");
                    userNewPlanet.setName(scanner.nextLine());
                    planetController.createPlanet(loggedInUserId, userNewPlanet);
                }
            }

        }


    }

    public static void reEnterRegistration(){
        try (Scanner scanner = new Scanner(System.in)) {
            // get the prospective username of the new user
            System.out.print("\nPlease enter your desired username (maximum 30 characters): ");
            String potentialUsername = scanner.nextLine();

            // get the prospective password of the new user
            System.out.print("\nPlease enter your desired password (maximum 30 characters): ");
            String potentialPassword = scanner.nextLine();

            // create a User object and provide it with the username and password
            // keep in mind the id will be set by the database if the username and password
            // are valid
            User potentialUser = new User();

            // pass the data into the service layer for validation
            if(userDao.getUserByUsername(potentialUsername).getUsername() == potentialUser.getUsername()){
                potentialUser.setUsername(potentialUsername);
                potentialUser.setPassword(potentialPassword);
                userController.register(potentialUser);
                main(null);
            } else {
                System.out.print("\nUsername already in use, if you meant to login then select return. If not, try to create a unique one! \n\n1. Try again \n2. Return \n\nClick: " );
                String userAction = scanner.nextLine();
                if (userAction.equals("1")){
                    reEnterRegistration();
                } 
                else if (userAction.equals("2")) { 
                    main(null);
                } else {
                    System.out.println("Invalid action, try again \n\nClick: "); 
                    userAction = scanner.nextLine();
                }
            }
        }
    }

    public static void tryAgain(){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Try again \n2. Return \n\nClick: " );
            String userAction = scanner.nextLine();
            if (userAction.equals("1")){
                reEnterRegistration();
            } 
            else if (userAction.equals("2")) { 
                main(null);
            } else {
                tryAgain();
            }
        }
    }

    
}


