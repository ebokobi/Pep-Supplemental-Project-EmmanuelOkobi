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
                    if(userDao.getUserByUsername(potentialUsername).getUsername() == (potentialUser.getUsername())){ //if both currently null the username is available
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
                    UsernamePasswordAuthentication credentials = new UsernamePasswordAuthentication();
                    System.out.println("\n>>> Planetarium Account Login <<<"); 
                    System.out.print("\nPlease enter your username: ");
                    String username = scanner.nextLine();
                    credentials.setUsername(username);

                    System.out.print("\nPlease enter your password: ");
                    String password = scanner.nextLine();
                    credentials.setPassword(password);

                    if (userDao.getUserByUsername(username).getUsername().equals(credentials.getUsername())){
                        userController.authenticate(credentials);
                    } else { 
                        System.out.print("\n\nNo account with this username exists yet! If you meant to create an account, do so when prompted.\n");
                    }
                
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
                System.out.println(String.format("\n>>> World Wizard: - %s - <<<", loggedInUsername));
                System.out.print("\nExercise Powers: \n\n1. Create a Planet  \n2. Create a Moon \n3. View Planets \n4. View Moons \n5. Delete a planet \n6. Delete a moon \n7. Logout \n\nClick: ");
                String loggedInUserChoice = scanner.nextLine();
                if (loggedInUserChoice.equals("1")){ // user has chosen to create a planet
                    Planet userNewPlanet = new Planet();
                    System.out.print("\n>>> Create a Planet <<< \n\nName Your Planet: ");
                    String userNewPlanetName = scanner.nextLine();
                    if (planetDao.getPlanetByName(userNewPlanetName).getName() == (userNewPlanet.getName())){  //yk the drill, if both null name = available (.equalsIgnorCase: possible fix for alphabet case issue instead of == operator)
                        userNewPlanet.setName(userNewPlanetName);
                        planetController.createPlanet(loggedInUserId, userNewPlanet);
                    }
                    else { 
                        System.out.print("\nThat planet has already been created, please try a unique name! \n\n1. Try again \n2. Return \n\nClick: ");
                        String userAction = scanner.nextLine();
                            if (userAction.equals("1")){
                                reEnterCreatePl();
                            } 
                            else if (userAction.equals("2")) { 
                                continue;
                            }
                            else { System.out.println("\nInvalid action, try again"); tryAgainPl();}
                    }
                }
                if (loggedInUserChoice.equals("2")){
                    Moon userNewMoon = new Moon();
                    System.out.print("\n>>> Create a Moon <<< \n\nWhat planet id will it orbit around?: ");
                    String userPlanetId = scanner.nextLine();
                    if (planetDao.getPlanetById(Integer.parseInt(userPlanetId)).getOwnerId() == loggedInUserId){ //moon is not owned by anyone
                        System.out.print("\nName Your Moon: ");
                        String userNewMoonName = scanner.nextLine();
                        if (moonDao.getMoonByName(userNewMoonName).getName() == (userNewMoon.getName())){ //if they are both null then moon name is available
                            userNewMoon.setName(userNewMoonName);
                            moonController.createMoon(Integer.parseInt(userPlanetId),userNewMoon);
                        } // else the moon name is taken and user should be prompted to try again
                        else {
                            System.out.print("\nSorry, that Moon name is already in use, try to create a unique one! \n\n1. Try again \n2. Return \n\nClick: " );
                            String userAction = scanner.nextLine();
                            if (userAction.equals("1")){
                                reEnterCreateMn();
                            } 
                            else if (userAction.equals("2")) { 
                                continue;
                            }
                            else { System.out.println("\nInvalid action, try again"); tryAgainMn();}
                        }
                    } else { System.out.println("Make sure you created/own this planet."); tryAgainMn();}
                }

            }
        }
    }

    public static void reEnterCreatePl() {
        try (Scanner scanner = new Scanner(System.in)){
            Planet userNewPlanet = new Planet();
            System.out.print("\nName Your Planet: ");
            String userNewPlanetName = scanner.nextLine();
            if (planetDao.getPlanetByName(userNewPlanetName).getName() == (userNewPlanet.getName())){  //yk the drill, if both null name = available (.equals/equalsIgnoreCase: possible fix for alphabet case issue instead of == operator)
                userNewPlanet.setName(userNewPlanetName);
                planetController.createPlanet(loggedInUserId, userNewPlanet);
                main(null);
            }
            else { 
                System.out.print("That planet has already been created, please try a unique name! \n\n1. Try again \n2. Return \n\nClick: ");
                String userAction = scanner.nextLine();
                if (userAction.equals("1")){
                    reEnterCreatePl();
                } 
                else if (userAction.equals("2")) { 
                    main(null);
                }
                else { 
                    System.out.println("Invalid action, try again \n\nClick: "); 
                    userAction = scanner.nextLine();
                }
            }
        }
    }

    public static void tryAgainPl() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Try again \n2. Return \n\nClick: " );
            String userAction = scanner.nextLine();
            if (userAction.equals("1")){
                reEnterCreatePl();
            } 
            else if (userAction.equals("2")) { 
                main(null);
            } else {
                tryAgainPl();
            }
        }
    }

    public static void tryAgainMn() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Try again \n2. Return \n\nClick: " );
            String userAction = scanner.nextLine();
            if (userAction.equals("1")){
                reEnterCreateMn();
            } 
            else if (userAction.equals("2")) { 
                main(null);
            } else {
                tryAgainMn();
            }
        }
    }

    public static void reEnterCreateMn() {
        try (Scanner scanner = new Scanner(System.in)){
            Moon userNewMoon = new Moon();
            System.out.print("\nRe-Enter Planet Id: ");
            String userPlanetId = scanner.nextLine();
            if (planetDao.getPlanetById(Integer.parseInt(userPlanetId)).getOwnerId() == loggedInUserId){
                System.out.print("\nName Your Moon: ");
                String userNewMoonName = scanner.nextLine();
                if (moonDao.getMoonByName(userNewMoonName).getName() == (userNewMoon.getName())){ //if they are both null then moon name is available
                    userNewMoon.setName(userNewMoonName);
                    moonController.createMoon(Integer.parseInt(userPlanetId),userNewMoon);
                    main(null);
                } // else the moon name is taken and user should be prompted to try again
                else {
                    System.out.print("\nSorry, that Moon name already exists, try to create a unique one! \n\n1. Try again \n2. Return \n\nClick: " );
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        reEnterCreateMn();
                    } 
                    else if (userAction.equals("2")) { 
                        main(null);
                    }
                    else {
                        System.out.println("Invalid action, try again \n\nClick: "); 
                        userAction = scanner.nextLine();
                    }
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
            if(userDao.getUserByUsername(potentialUsername).getUsername() == (potentialUser.getUsername())){
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


