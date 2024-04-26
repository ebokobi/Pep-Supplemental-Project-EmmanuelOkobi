package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.Moon;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons() {
		// TODO: implement
		moonService.getAllMoons();
	}

	public void getMoonByName(int myPlanetId, String requestMoonName) {
		// TODO: implement
		moonService.getMoonByName(myPlanetId, requestMoonName);
	}

	public void getMoonById(int myPlanetId, int requestMoonId) {
		// TODO: implement
		moonService.getMoonById(myPlanetId, requestMoonId);
	}

	public void createMoon(Moon requestMoon) {
		// TODO: implement
		Moon validUserMoon = moonService.createMoon(requestMoon);
		if (validUserMoon.getId() != 0){
			System.out.println("Moon created successfully!");
		} else {
			System.out.println("Failed to create Moon, please make sure your moon name is unique and under 30 characters.");
		}
	}

	public void deleteMoon(int requestMoonId) {
		// TODO: implement
		moonService.deleteMoonById(requestMoonId);
	}
	
	public void getPlanetMoons(int myPlanetId) {
		// TODO: implement
		moonService.getMoonsFromPlanet(myPlanetId);
	}
}
