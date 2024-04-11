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
		return null;
	}

	public Moon getMoonByName(String moonName) {
		// TODO: implement
		return null;
	}

	public Moon getMoonById(int moonId) {
		// TODO: implement
		return null;
	}

	public Moon createMoon(Moon m) {
		// TODO: implement
		return null;
	}

	public boolean deleteMoonById(int moonId) {
		// TODO: implement
		return false;
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		// TODO: implement
		return null;
	}
}
