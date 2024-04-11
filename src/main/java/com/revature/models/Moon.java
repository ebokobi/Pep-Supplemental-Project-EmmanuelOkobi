package com.revature.models;

public class Moon {
	
	private int id;
	private String name; 
	private int myPlanetId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMyPlanetId() {
		return myPlanetId;
	}
	public void setMyPlanetId(int myPlanetId) {
		this.myPlanetId = myPlanetId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moon other = (Moon) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Moon [id=" + id + ", name=" + name + ", myPlanetId=" + myPlanetId + "]";
	}

}
