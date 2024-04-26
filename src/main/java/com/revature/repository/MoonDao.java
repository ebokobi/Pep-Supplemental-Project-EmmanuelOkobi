package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		// TODO: implement
		List<Moon> allMoons = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt("moon_id"));
				moon.setName(rs.getString("moon_name"));
				moon.setMyPlanetId(rs.getInt("myplanet_id"));
				allMoons.add(moon);
			}

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return allMoons;
	}

	public Moon getMoonByName(String moonName) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE moon_name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			Moon retrievedMoon = new Moon();
			if (rs.next()){
				retrievedMoon.setId(rs.getInt("moon_id"));
				retrievedMoon.setName(rs.getString("moon_name"));
				retrievedMoon.setMyPlanetId(rs.getInt("myplanet_id"));
			}
			return retrievedMoon;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}	
	}

	public Moon getMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE moon_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			ResultSet rs = ps.executeQuery();
			Moon retrievedMoon = new Moon();
			if (rs.next()){
				retrievedMoon.setId(rs.getInt("moon_id"));
				retrievedMoon.setName(rs.getString("moon_name"));
				retrievedMoon.setMyPlanetId(rs.getInt("myplanet_id"));
			}
			return retrievedMoon;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Moon createMoon(Moon m) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "INSERT INTO moons (moon_id, moon_name, myplanet_id) VALUES (?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, m.getId());
			ps.setString(2, m.getName());
			ps.setInt(3,m.getMyPlanetId());
			ps.executeUpdate();
			Moon retrievedMoon = new Moon();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				retrievedMoon.setId(rs.getInt(1));
				retrievedMoon.setName(m.getName());
				retrievedMoon.setMyPlanetId(m.getMyPlanetId());
			}
			return retrievedMoon;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "DELETE FROM planets WHERE planet_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			if(ps.executeUpdate() == 0){
				return true;
			}
			
			return false;

		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public List<Moon> getMoonsFromPlanet(int myplanetId) {
		// TODO: implement
		List<Moon> moonsFromPlanet = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE myplanet_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,myplanetId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt("moon_id"));
				moon.setName(rs.getString("moon_name"));
				moon.setMyPlanetId(rs.getInt("myplanet_id"));
				moonsFromPlanet.add(moon);
			}

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return moonsFromPlanet;
	}
}
