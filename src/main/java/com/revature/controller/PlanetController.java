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
		currentUserId = MainDriver.loggedInUserId;
		System.out.println("\n" + MainDriver.loggedInUsername + "'s Planets:\n ");
		for (Planet pl : planetService.getAllPlanets(currentUserId)){
			System.out.println(pl);
		}
	}

	public void getPlanetByName(int currentUserId, String name) {
		currentUserId = MainDriver.loggedInUserId;
		planetService.getPlanetByName(currentUserId, name);
	}

	public void getPlanetByID(int currentUserId, int planetId) {
		currentUserId = MainDriver.loggedInUserId;
		planetService.getPlanetById(currentUserId, planetId);
	}

	public void createPlanet(int currentUserId, Planet requestPlanet) {
		currentUserId = MainDriver.loggedInUserId;
		Planet validUserPlanet = planetService.createPlanet(currentUserId, requestPlanet);
		if (validUserPlanet.getId() != 0){
			System.out.println(String.format("\nPlanet '%s' has been spawned and is now yours :)  \nIf this planet has moons, you can create them when prompted.", requestPlanet.getName()));
		} 
	}

	public void deletePlanet(int currentUserId, int planetId) {
		currentUserId = MainDriver.loggedInUserId;
		boolean deletedPlanet = planetService.deletePlanetById(currentUserId, planetId);
		if (deletedPlanet == true){
			System.out.println("\nPlanet deleted successfully! View Planets to reflect changes.");
		} else {
			System.out.println(new PlanetFailException("\n\nFailed to delete Planet, make sure you created/own this Planet."));
		}
	}
}
