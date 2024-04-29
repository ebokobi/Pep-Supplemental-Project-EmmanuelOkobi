package com.revature.controller;

import java.util.List;

import com.revature.MainDriver;
import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons() {
		moonService.getAllMoons();
	}

	public void getAllMoons(int currentUserId) {
		moonService.getAllMoons(currentUserId);
		System.out.println("\n" + MainDriver.loggedInUsername + "'s Moons:\n ");
		for (Moon moonList : moonService.getAllMoons(currentUserId)){
			System.out.println(moonList);
		}
	}

	public void getMoonByName(int myPlanetId, String requestMoonName) {
		moonService.getMoonByName(myPlanetId, requestMoonName);
	}

	public void getMoonById(int myPlanetId, int requestMoonId) {
		moonService.getMoonById(myPlanetId, requestMoonId);
	}

	public void createMoon(int myPlanetId, Moon requestMoon) {
		Moon validUserMoon = moonService.createMoon(myPlanetId,requestMoon);
		if (validUserMoon.getId() != 0){
			System.out.println(String.format("\nMoon '%s' created successfully! View Moons when prompted.",validUserMoon.getName()));
		}
	}

	public void deleteMoon(int requestMoonId) {
		moonService.deleteMoonById(requestMoonId);
	}

	public void deleteMoon(String requestMoonName) {
		boolean deletedMoon = moonService.deleteMoonByName(requestMoonName);
		if (deletedMoon == true){
			System.out.println("\nMoon deleted successfully! View Moons to reflect changes.");
		} else {
			System.out.println(new MoonFailException("\n\nFailed to delete Moon, make sure you created/own this Moon."));
		}
		
	}
	
	public void getPlanetMoons(int myPlanetId) {
		moonService.getMoonsFromPlanet(myPlanetId);
	}
}
