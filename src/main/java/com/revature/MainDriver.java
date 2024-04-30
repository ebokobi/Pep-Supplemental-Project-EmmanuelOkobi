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
    public static MoonService moonService = new MoonService(moonDao,planetDao);
    public static MoonController moonController = new MoonController(moonService);


    public static void main(String[] args) {
        // We are using a Try with Resources block to auto close our scanner when we are done with it
        try (Scanner scanner = new Scanner(System.in)){
            while (loggedInUserId == 0){
                System.out.print("\n>>> Hello! Welcome to the Planetarium! <<< \n\n To Enter/Exit, Select: \n1. Register an account \n2. Login to your account \n3. Leave the Planetarium \n\nClick: ");
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
                        System.out.print("\nUsername already in use, if you meant to login then select return. If not, try again and create a unique name! \n\n1. Try again \n2. Return \n\nClick: " );
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
                    User credentials = new User();
                    System.out.println("\n>>> Planetarium Account Login <<<"); 
                    System.out.print("\nPlease enter your username: ");
                    String username = scanner.nextLine();
                    credentials.setUsername(username);

                    System.out.print("\nPlease enter your password: ");
                    String password = scanner.nextLine();
                    credentials.setPassword(password);

                    if (userDao.getUserByUsername(username).getUsername() != null && userDao.getUserByUsername(username).getUsername().equals(credentials.getUsername())){ 
                        userController.authenticate(credentials);
                    } else { 
                        System.out.print("\n\nNo account with this username exists yet! If you meant to create an account, do so when prompted.\n");
                    }
                
                }
                 
                else if (anonUserChoice.equals("3")) { // user has chosen to exit 
                    System.out.println("\n*Thanks for visiting the Planetarium, Goodbye!*");
                    break;
                } 
                else {
                    System.out.println("\nInvalid choice, please try again.");
                }
            }

            while (loggedInUserId > 0){
                System.out.println(String.format("\n\n>>> Wizard of Worlds: - %s - <<<", loggedInUsername));
                System.out.print("\nExercise Powers: \n\n1. Create a Planet  \n2. Create a Moon \n3. View Planets \n4. View Moons \n5. Delete a Planet \n6. Delete a Moon \n7. Logout \n\nClick: ");
                String loggedInUserChoice = scanner.nextLine();
                if (loggedInUserChoice.equals("1")){ // user has chosen to create a planet
                    Planet userNewPlanet = new Planet();
                    System.out.print("\n>>> Create a Planet <<< \n\nName Your Planet: ");
                    String userNewPlanetName = scanner.nextLine();
                    if (planetDao.getPlanetByName(userNewPlanetName).getName() != userNewPlanetName && planetDao.getPlanetByName(userNewPlanetName).getOwnerId() == userNewPlanet.getOwnerId()){  //yk the drill, if both null name = available (.equalsIgnorCase: possible fix for alphabet case issue instead of == operator)
                        userNewPlanet.setName(userNewPlanetName);
                        planetController.createPlanet(loggedInUserId, userNewPlanet);
                        System.out.println("\nYour new Planet's id: " + planetDao.getPlanetByName(userNewPlanetName).getId());
                    }
                    else { 
                        System.out.print("\nThat Planet has already been created, please try a unique name! \n\n1. Try again \n2. Return \n\nClick: ");
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
                else if (loggedInUserChoice.equals("2")){
                    Moon userNewMoon = new Moon();
                    System.out.print("\n>>> Create a Moon <<< \n\nWhat Planet id will it orbit around?: ");
                    String userPlanetId = scanner.nextLine();
                    if (planetDao.getPlanetById(Integer.parseInt(userPlanetId)).getOwnerId() == loggedInUserId){ //planet is owned by user
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
                    } else { System.out.println("\nMake sure you created/own this Planet."); tryAgainMn();}
                }
                else if (loggedInUserChoice.equals("3")){
                    System.out.print("\n>>> View Planets <<< \n\n1. View All of your Planets \n2. View a specific Planet and its Moons \n3. Return \n\nClick: ");
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        if (!planetDao.getAllPlanets(loggedInUserId).isEmpty()) {
                            planetController.getAllPlanets(loggedInUserId);
                        } else {
                            System.out.println("\nYou have no Planets created, do so when promted then try again.");
                        }

                    }
                    else if (userAction.equals("2")){
                        System.out.print("\nWhat Planet would you like to observe?: ");
                        String userPlanet = scanner.nextLine();
                        if (planetDao.getPlanetByName(userPlanet).getOwnerId() == loggedInUserId){ //user owns planet
                            System.out.println("\nHere's your Planet, " + planetDao.getPlanetByName(userPlanet).getName() + " and its Moons: ");
                            moonController.getPlanetMoons(planetDao.getPlanetByName(userPlanet).getId());
                        } else { 
                            System.out.print("\nMake sure you created/own this Planet. \n\n1. Try again \n2. Return \n\nClick: ");
                            String userAction2 = scanner.nextLine();
                            if (userAction2.equals("1")){
                                reEnterVwPl();
                            } 
                            else if (userAction2.equals("2")) { 
                                continue;
                            }
                            else { 
                                System.out.println("\nInvalid action, try again \n\nClick: "); 
                                userAction2 = scanner.nextLine();
                            }
                        }
                    }
                    else if (userAction.equals("3")){
                        continue;
                    }
                    else { System.out.println("\nInvalid action, try 'View Planets' again or another option.");}
                }
                else if (loggedInUserChoice.equals("4")){
                    System.out.print("\n>>> View Moons <<< \n\n1. View All of your Moons \n2. Return \n\nClick: ");
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        moonController.getAllMoons(loggedInUserId);
                    }
                    else if (userAction.equals("2")){
                        main(args);
                    } else {
                        System.out.println("\nInvalid action, try 'View Moons' again or another option.");
                    }
                }
                else if (loggedInUserChoice.equals("5")){
                    System.out.print("\n>>> Delete a Planet <<< \n\nWhat Planet do you want to eradicate?: ");
                    String userPlanetName = scanner.nextLine();
                    if (planetDao.getPlanetByName(userPlanetName).getName() != null && planetDao.getPlanetByName(userPlanetName).getOwnerId() == loggedInUserId){
                        System.out.print("\nAre you sure? All of its orbiting Moons will also be eradicated. \n\n1. Thanos Snap* \n2. Cancel & Return \n\nClick: ");
                        String userAction = scanner.nextLine();
                        if (userAction.equals("1")){
                            planetController.deletePlanet(loggedInUserId, planetDao.getPlanetByName(userPlanetName).getId());
                        }
                        else if (userAction.equals("2")){
                            continue;
                        }
                        else {
                            System.out.println("\nInvalid action, try 'Delete a Planet' again or another option.");
                        }
                    } else { System.out.println("\nMake sure you created/own this planet."); }
                }
                else if (loggedInUserChoice.equals("6")){
                    System.out.print("\n>>> Delete a Moon <<< \n\nWhat Moon do you want to eradicate?: ");
                    String userMoonName = scanner.nextLine();
                    System.out.print("\nAre you sure? This Moon will stop orbiting its Planet. \n\n1. Thanos Snap* \n2. Cancel & Return \n\nClick: ");
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        moonController.deleteMoon(userMoonName);
                    }
                    else if (userAction.equals("2")){
                        continue;
                    }
                    else {
                        System.out.println("\nInvalid action, try 'Delete a Moon' again or another option.");
                    }
                }
                else if (loggedInUserChoice.equals("7")){
                    System.out.print("\nConfirm Logout? \n\n1. Yes \n2. No \n\nClick: ");
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        userController.logout();
                        main(args);
                    } else { continue; }
                }
                else {
                    System.out.println("\nDoes " + loggedInUserChoice + "look like an option we provided? -_- ");
                }

            }
        }
    }

    public static void tryAgainVwPl() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Try again \n2. Main Menu \n\nClick: " );
            String userAction = scanner.nextLine();
            if (userAction.equals("1")){
                reEnterVwPl();
            } 
            else if (userAction.equals("2")) { 
                main(null);
            } else {
                tryAgainVwPl();
            }
        }
    }

    public static void reEnterVwPl() {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.print("\nWhat Planet would you like to observe?: ");
            String userPlanet = scanner.nextLine();
            if (planetDao.getPlanetByName(userPlanet).getOwnerId() == loggedInUserId){ //user owns planet
                System.out.println("\nHere's your Planet, " + planetDao.getPlanetByName(userPlanet).getName() + " and its Moons: ");
                moonController.getPlanetMoons(planetDao.getPlanetByName(userPlanet).getId());
                main(null);
            } else { 
                System.out.print("\nMake sure you created/own this Planet. \n\n1. Try again \n2. Main Menu \n\nClick: ");
                            String userAction2 = scanner.nextLine();
                            if (userAction2.equals("1")){
                                reEnterVwPl();
                            } 
                            else if (userAction2.equals("2")) { 
                                main(null);
                            }
                            else {
                                System.out.println("\nInvalid action, try again.");
                                tryAgainVwPl();
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
                System.out.println("Your new Planet's id: " + userNewPlanet.getId());
                main(null);
            }
            else { 
                System.out.print("\nThat planet has already been created, please try a unique name! \n\n1. Try again \n2. Return \n\nClick: ");
                String userAction = scanner.nextLine();
                if (userAction.equals("1")){
                    reEnterCreatePl();
                } 
                else if (userAction.equals("2")) { 
                    main(null);
                }
                else { 
                    System.out.println("\nInvalid action, try again \n\nClick: "); 
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
                        System.out.print("\nInvalid action, try again.");
                        tryAgainMn();
                    }
                }
            } else { System.out.println("\nMake sure you created/own this Planet."); tryAgainMn();}
        }
    }

    public static void reEnterRegistration(){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Retry Registration \n2. Return \n\nClick: ");
            String userClick = scanner.nextLine();
            if(userClick.equals("1")){
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
                    System.out.print("\n1. Retry Registration \n2. Return \n\nClick: ");
                    String userAction = scanner.nextLine();
                    if (userAction.equals("1")){
                        reEnterRegistration();
                    } 
                    else if (userAction.equals("2")) { 
                        main(null);
                    } else {
                        System.out.print("\nInvalid action, try again \n\nClick: "); 
                        userAction = scanner.nextLine();
                    }
                }
            }
            else if (userClick.equals("2")){
                main(null);
            }
            else {
                System.out.println("\nInvalid choice, try again.");
                tryAgain();
            }
        }
    }

    public static void tryAgain(){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n1. Retry Registration \n2. Return \n\nClick: " );
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


