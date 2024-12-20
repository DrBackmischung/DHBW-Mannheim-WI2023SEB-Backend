package de.mathisneunzig.facilitymanagement.fm.dto;

import java.util.UUID;

public class BuildingDTO {
	
	private String name;
	private int nrOfDesks;
	private UUID addressID;
	
	public BuildingDTO(String name, int nrOfDesks, UUID addressID) {
		super();
		this.name = name;
		this.nrOfDesks = nrOfDesks;
		this.addressID = addressID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNrOfDesks() {
		return nrOfDesks;
	}

	public void setNrOfDesks(int nrOfDesks) {
		this.nrOfDesks = nrOfDesks;
	}

	public UUID getAddressID() {
		return addressID;
	}

	public void setAddressID(UUID addressID) {
		this.addressID = addressID;
	}

}
