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
    
    public List<Planet> getAllPlanets(int ownerId) {
		List<Planet> allPlanets = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE ownerId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,ownerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Planet planet = new Planet();
				planet.setId(rs.getInt("id"));
				planet.setName(rs.getString("name"));
				planet.setOwnerId(rs.getInt("ownerId"));
				allPlanets.add(planet);
			}
			return allPlanets;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetByName(String planetName) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			ResultSet rs = ps.executeQuery();
			Planet retrievedPlanet = new Planet();
			if (rs.next()){
				retrievedPlanet.setId(rs.getInt("id"));
				retrievedPlanet.setName(rs.getString("name"));
				retrievedPlanet.setOwnerId(rs.getInt("ownerId"));
			}
			return retrievedPlanet;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}			
	}

	public Planet getPlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			Planet retrievedPlanet = new Planet();
			if (rs.next()){
				retrievedPlanet.setId(rs.getInt("id"));
				retrievedPlanet.setName(rs.getString("name"));
				retrievedPlanet.setOwnerId(rs.getInt("ownerId"));
			}
			return retrievedPlanet;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Planet createPlanet(Planet p) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "INSERT INTO planets (name, ownerId) VALUES (?,?)";
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
			String sql = "DELETE FROM planets WHERE id = ?";
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
