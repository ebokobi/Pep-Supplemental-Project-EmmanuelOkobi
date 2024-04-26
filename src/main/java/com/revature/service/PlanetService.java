package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.PlanetFailException;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets(int requestOwnerId) {
		List<Planet> dbPlanets = dao.getAllPlanets(requestOwnerId);
		for (Planet i : dbPlanets){
			if(i.getOwnerId() != requestOwnerId){
				dbPlanets.remove(i);
			}
		}
		return dbPlanets;
	}

	public Planet getPlanetByName(int requestOwnerId, String requestPlanetName) {
		Planet databaseData = dao.getPlanetByName(requestPlanetName);
		if (databaseData != null && requestOwnerId == databaseData.getOwnerId()){
			return dao.getPlanetByName(requestPlanetName);
		}
		return new Planet();
	}

	public Planet getPlanetById(int requestOwnerId, int planetId) {
		Planet databaseData = dao.getPlanetById(planetId);
		if (databaseData != null && requestOwnerId == databaseData.getOwnerId()){ 
			return dao.getPlanetById(planetId);
		}
		return new Planet();
	}

	public Planet createPlanet(int requestOwnerId, Planet requestedPlanet) throws PlanetFailException {
		if (requestedPlanet.getName().length() <= 30){ // (Software Requirement): Planet names should not have more than 30 characters
			Planet databaseData = dao.getPlanetByName(requestedPlanet.getName());
			if (databaseData != null){
				if (!requestedPlanet.getName().equals(databaseData.getName())){ // (Software Requirement): Planets should have unique names
					requestedPlanet.setOwnerId(requestOwnerId); // assign the requested planet's ownerid to the request ownerid 
					Planet uniquePlanet = new Planet();
					return dao.createPlanet(uniquePlanet);
				} else { throw new PlanetFailException("That planet has already been conquered <:( please try a different name\n");}
			}
		} else { throw new PlanetFailException("Your username is exceeds 30 characters, try again.");}
		return new Planet(); // return an empty planet with id=0 to controller if requirements are not met
	}

	public boolean deletePlanetById(int requestOwnerId, int planetId) {
		Planet databaseData = dao.getPlanetById(planetId);
		if (databaseData != null && databaseData.getOwnerId() == requestOwnerId){ // if this planet is owned by the current user
			return dao.deletePlanetById(planetId);
		}
		return false; // return false if planet is not deleted
	}
}
