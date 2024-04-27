package com.revature.service;

import java.util.List;

import com.revature.MainDriver;
import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public List<Moon> getAllMoons() {
		// TODO implement
		return dao.getAllMoons();
	}

	public Moon getMoonByName(int myPlanetId, String requestMoonName) {
		Moon databaseData = dao.getMoonByName(requestMoonName);
		if (databaseData != null && myPlanetId == databaseData.getMyPlanetId()){
			return databaseData;
		}
		return new Moon();
	}

	public Moon getMoonById(int myPlanetId, int requestMoonId) {
		Moon databaseData = dao.getMoonById(requestMoonId);
		if (databaseData != null && myPlanetId == databaseData.getMyPlanetId()){
			return dao.getMoonById(requestMoonId);
		}
		return new Moon();
	}

	public Moon createMoon(int myPlanetId, Moon m) {
		if (m.getName().length() <= 30){ //(Software Requirement): Moon names should not have more than 30 characters
			Moon databaseData = dao.getMoonByName(m.getName());
			if (databaseData != null){
				if (databaseData.getName() != (m.getName())){ // (Software Requirement): Moons should have unique names
					m.setMyPlanetId(myPlanetId);
					return dao.createMoon(m);
				} else { return new Moon(); }
			} else { System.out.println(new MoonFailException("\n\nSomething went wrong, please try again.\n")); return new Moon();}
		} 
		else {
			System.out.println(new MoonFailException("\n\nMake sure Moon name doesn't exceed 30 characters."));
			MainDriver.reEnterCreateMn();
		}
		return new Moon(); //Return an empty moon to controller if requirements are not met
	}

	public boolean deleteMoonById(int moonId) {
		return dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int requestPlanetId) {
		List<Moon> dbMoons = dao.getAllMoons();
		for (Moon i : dbMoons){
			if(i.getMyPlanetId() != requestPlanetId){
				dbMoons.remove(i);
			}
		}
		return dbMoons;
	}
}
