package com.revature.controller;

import com.revature.models.Moon;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons(int currentUserId) {
		// TODO: implement
	}

	public void getMoonByName(int currentUserId, String name) {
		// TODO: implement
	}

	public void getMoonById(int currentUserId, int id) {
		// TODO: implement
	}

	public void createMoon(int currentUserId, Moon moon) {
		// TODO: implement
	}

	public void deleteMoon(int id) {
		// TODO: implement
	}
	
	public void getPlanetMoons(int myPlanetId) {
		// TODO: implement
	}
}
