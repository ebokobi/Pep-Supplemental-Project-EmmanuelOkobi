# Planetarium

The Planetarium is a web service that allows Astronomers (Users) to record planets and associated moons to a central database as they map the night sky. Users must register an account to participate, and those who do will be able to associate themselves with the planets and moons they add to the database. Much of the infrastructure has already been created: it is your job to finish writing the implementation code for this application.

## Project Focus
- Java
- SQL

## Key Terminology
- **Project**
  - A software application built for some company/entity
  - Examples
    - Employee paid time off Scheduler
    - Helicopter Navigation System
    - To-Do task manager
- **Sprint**
    - a term used to describe a short period of development work, typically no more than a few weeks of time
- **Minimum Viable Product**
    - a phrase used to describe a project that has the minimum number of features and functionality applied to make the sprint considered successful
- **Requirement**
    - a functional or conceptual need
        - functional examples
            - usernames should be between 5-15 characters
            - a user's online shopping cart should not allow for purchings a negative number of items
        - conceptual examples
            - users should be able to log in
            - users should be able to purchase items

## Business Requirements
- Users should be able to open a new account with the Planetarium                                   // check
- Users should be able to securely log in to their account                                          // check
- Users should be able to add new Planets to the Planetarium
- Users should be able to remove Planets from the Planetarium they previously added
- Users should be able to add Moons to the Planetarium associated with their Planets
- Users should be able to remove Moons from the Planetarium they previously added
- Users should be able to view Planet and Moons they have added to the Planetarium

## Software Requirements  
- Users should have unique usernames                                                                // check
- Usernames and passwords should not be longer than 30 characters                                   // check
- Users should only see Planet and Moon data for resources they have added to the Planetarium
- Planet and Moon names should not have more than 30 characters                                     // check
- Planets and moons should have unique names                                                        // check
- Planets should be “owned” by the user that added it to the Planetarium
- Moons should be “owned” by the Planet the User adding the moon associated it with

## Development Requirements
Each class in the list below has one or more unimplemented methods you will need to complete to achieve MVP requirements:
- Repository package
    - UserDao
    - PlanetDao
    - MoonDao
- Service package
    - UserService
    - PlanetService
    - MoonService
- Controller package
    - UserController
    - PlanetController
    - MoonController
- Miscelanious
    - MainDriver

# MVP Requirements
- Development
    - all provided methods in the "repository" package are implemented to meet business and software requirements
    - all provided methods in the "service" package are implemented to meet business and software requirements
    - all provided methods in the "controller" package are implemented to meet business and software requirements
    - The "MainDriver" class has the "main" method implemented to interact with the application
        - The system should keep track of who is logged in
        - Only a logged in user should be able to interact with the planetarium beyond creating an account or logging in
