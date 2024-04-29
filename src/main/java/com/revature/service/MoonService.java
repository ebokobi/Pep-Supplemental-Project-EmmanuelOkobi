package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.MainDriver;
import com.revature.exceptions.MoonFailException;
import com.revature.exceptions.PlanetFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;

public class MoonService {

	private MoonDao dao;
	private PlanetDao pDao;

	public MoonService(MoonDao dao, PlanetDao pDao) {
		this.dao = dao;
		this.pDao = pDao;
	}

	public List<Moon> getAllMoons() {
		return dao.getAllMoons();
	}

	public List<Moon> getAllMoons(int currentUserId) {
		List<Planet> userAllPlanets = pDao.getAllPlanets(currentUserId);
		List<Moon> userAllMoons = new ArrayList<>();
		for (Planet p : userAllPlanets){
			List<Moon> pMoons = dao.getMoonsFromPlanet(p.getId());
			userAllMoons.addAll(pMoons);
		}
		if (!userAllMoons.isEmpty()){ //user has moons created
			return userAllMoons;
		} else {
			System.out.println("\nYou have no Moons created, do so when promted then try again.");
		}
		return new ArrayList<>();
	}

	public Moon getMoonByName(int myPlanetId, String requestMoonName) {
		Moon databaseData = dao.getMoonByName(requestMoonName);
		if (databaseData != null){
			if (myPlanetId == databaseData.getMyPlanetId()){
				return databaseData;
			} 
			else {
				System.out.println("\nMake sure.............");
			}
		} else { System.out.println(new MoonFailException("\n\nSomething went wrong, try again"));}
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

	public boolean deleteMoonByName(String requestMoonName) {
		Moon databaseData = dao.getMoonByName(requestMoonName);
		if (databaseData != null){
			if (pDao.getPlanetById(databaseData.getMyPlanetId()).getOwnerId() == MainDriver.loggedInUserId){ //if this moon's planet's owner is logged in
				return deleteMoonById(databaseData.getId());
			}
		} else { 
			System.out.println(new MoonFailException("Something went wrong, try again."));
			return false;
		}
		return false;
	}

	public List<Moon> getMoonsFromPlanet(int requestPlanetId) throws MoonFailException{
		List<Planet> userAllPlanets = pDao.getAllPlanets(MainDriver.loggedInUserId);
		if (userAllPlanets != null){
			for (Planet p : userAllPlanets){
				if(p.getId() == requestPlanetId){
					if (dao.getMoonsFromPlanet(p.getId()).isEmpty()){
						System.out.println("\nThis Planet has no Moons, add them when prompted.");
					} 
					else {
						System.out.println(dao.getMoonsFromPlanet(p.getId()));
					}
				}
			}
		} else { System.out.println(new MoonFailException("\n\nSomething went wrong, try again.")); }

		List<Moon> returnMoons = new ArrayList<>();
		return returnMoons; //either return moons or empty list
	}

}
