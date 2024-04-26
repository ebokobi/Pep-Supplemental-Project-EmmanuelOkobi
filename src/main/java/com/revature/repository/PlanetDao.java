package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets(int userId) {
		List<Planet> allPlanets = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE planet_owner_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Planet planet = new Planet();
				planet.setId(rs.getInt("planet_id"));
				planet.setName(rs.getString("planet_name"));
				planet.setOwnerId(rs.getInt("planet_owner_id"));
				allPlanets.add(planet);
			}

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return allPlanets;
	}

	public Planet getPlanetByName(String planetName) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE planet_name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			ResultSet rs = ps.executeQuery();
			Planet retrievedPlanet = new Planet();
			if (rs.next()){
				retrievedPlanet.setId(rs.getInt("planet_id"));
				retrievedPlanet.setName(rs.getString("planet_name"));
				retrievedPlanet.setOwnerId(rs.getInt("planet_owner_id"));
			}
			return retrievedPlanet;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}			
	}

	public Planet getPlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE planet_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			Planet retrievedPlanet = new Planet();
			if (rs.next()){
				retrievedPlanet.setId(rs.getInt("planet_id"));
				retrievedPlanet.setName(rs.getString("planet_name"));
				retrievedPlanet.setOwnerId(rs.getInt("planet_owner_id"));
			}
			return retrievedPlanet;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Planet createPlanet(Planet p) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "INSERT INTO planets (planet_name, planet_owner_id) VALUES (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, p.getName());
			ps.setInt(2,p.getOwnerId());
			ps.executeUpdate();
			Planet retrievedPlanet = new Planet();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				retrievedPlanet.setId(rs.getInt(1));
				retrievedPlanet.setName(p.getName());
				retrievedPlanet.setOwnerId(p.getOwnerId());
			}
			return retrievedPlanet;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public boolean deletePlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "DELETE FROM planets WHERE planet_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			if(ps.executeUpdate() > 0){ //if the change is reflected in the db table
				return true;
			}
			
			return false;

		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
}
