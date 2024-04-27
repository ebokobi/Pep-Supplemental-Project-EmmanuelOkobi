package com.revature.controller;

import com.revature.MainDriver;
import com.revature.exceptions.PlanetFailException;
import com.revature.models.Planet;
import com.revature.service.PlanetService;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		// TODO: implement
		currentUserId = MainDriver.loggedInUserId;
		planetService.getAllPlanets(currentUserId);
	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
		currentUserId = MainDriver.loggedInUserId;
		planetService.getPlanetByName(currentUserId, name);
	}

	public void getPlanetByID(int currentUserId, int planetId) {
		// TODO: implement
		currentUserId = MainDriver.loggedInUserId;
		planetService.getPlanetById(currentUserId, planetId);
	}

	public void createPlanet(int currentUserId, Planet requestPlanet) {
		currentUserId = MainDriver.loggedInUserId;
		Planet validUserPlanet = planetService.createPlanet(currentUserId, requestPlanet);
		if (validUserPlanet.getId() != 0){
			System.out.println(String.format("\nPlanet %s has been spawned and is now yours >:)  \nIf this planet has moons, you can create them when prompted.\n", requestPlanet.getName()));
		} 
	}

	public void deletePlanet(int currentUserId, int planetId) {
		currentUserId = MainDriver.loggedInUserId;
		if (planetService.deletePlanetById(currentUserId, planetId) == true){
			System.out.println("Planet deleted successfully!");
		} else {
			System.out.println("Failed to delete Planet, please make sure it is owned by you.");
		}
	}
}
