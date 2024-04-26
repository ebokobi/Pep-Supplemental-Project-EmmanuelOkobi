package com.revature.service;

import java.util.List;

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
		// TODO implement
		Moon databaseData = dao.getMoonByName(requestMoonName);
		if (databaseData != null && myPlanetId == databaseData.getMyPlanetId()){
			return dao.getMoonByName(requestMoonName);
		}
		return new Moon();
	}

	public Moon getMoonById(int myPlanetId, int requestMoonId) {
		// TODO implement
		Moon databaseData = dao.getMoonById(requestMoonId);
		if (databaseData != null && myPlanetId == databaseData.getMyPlanetId()){
			return dao.getMoonById(requestMoonId);
		}
		return new Moon();
	}

	public Moon createMoon(Moon m) {
		// TODO implement
		if (m.getName().length() <= 30){ //(Software Requirement): Moon names should not have more than 30 characters
			Moon databaseData = dao.getMoonByName(m.getName());
			if (databaseData != null){
				String moonNameFromDB = databaseData.getName();
				String moonNameFromRequest = m.getName();
				if (!moonNameFromDB.equals(moonNameFromRequest)){ // (Software Requirement): Moons should have unique names
					Moon uniqueMoon = new Moon();
					uniqueMoon.setName(moonNameFromRequest);
					uniqueMoon.setMyPlanetId(m.getMyPlanetId());
					return dao.createMoon(uniqueMoon);
				}
			}
		}
		return new Moon(); //Return an empty moon to controller if requirements are not met
	}

	public boolean deleteMoonById(int moonId) {
		return dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int requestPlanetId) {
		// TODO Auto-generated method stub
		List<Moon> dbMoons = dao.getAllMoons();
		for (Moon i : dbMoons){
			if(i.getMyPlanetId() != requestPlanetId){
				dbMoons.remove(i);
			}
		}
		return dbMoons;
	}
}
