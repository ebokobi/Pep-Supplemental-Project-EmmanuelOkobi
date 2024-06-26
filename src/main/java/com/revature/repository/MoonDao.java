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
		List<Moon> allMoons = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt("id"));
				moon.setName(rs.getString("name"));
				moon.setMyPlanetId(rs.getInt("myPlanetId"));
				allMoons.add(moon);
			}
			return allMoons;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonByName(String moonName) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			Moon retrievedMoon = new Moon();
			if (rs.next()){
				retrievedMoon.setId(rs.getInt("id"));
				retrievedMoon.setName(rs.getString("name"));
				retrievedMoon.setMyPlanetId(rs.getInt("myPlanetId"));
			}
			return retrievedMoon;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}	
	}

	public Moon getMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			ResultSet rs = ps.executeQuery();
			Moon retrievedMoon = new Moon();
			if (rs.next()){
				retrievedMoon.setId(rs.getInt("id"));
				retrievedMoon.setName(rs.getString("name"));
				retrievedMoon.setMyPlanetId(rs.getInt("myPlanetId"));
			}
			return retrievedMoon;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public Moon createMoon(Moon m) {
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, m.getName());
			ps.setInt(2,m.getMyPlanetId());
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
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "DELETE FROM moons WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			if(ps.executeUpdate() > 0){
				return true;
			}
			return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} 
	}

	public List<Moon> getMoonsFromPlanet(int myplanetId) {
		List<Moon> moonsFromPlanet = new ArrayList<>();
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, myplanetId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Moon moon = new Moon();
				
				moon.setId(rs.getInt("id"));
				moon.setName(rs.getString("name"));
				moon.setMyPlanetId(rs.getInt("myPlanetId"));
				moonsFromPlanet.add(moon);
			}
			return moonsFromPlanet;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
