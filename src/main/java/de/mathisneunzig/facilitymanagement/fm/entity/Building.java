package de.mathisneunzig.facilitymanagement.fm.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import de.mathisneunzig.facilitymanagement.fm.config.UuidIdentifiedEntity;

@Document
public class Building extends UuidIdentifiedEntity {
    
    @Field("name")
    private String name;
    
    @Field("nrOfDesks")
    private int nrOfDesks;
    
    @Field("address")
    @DBRef
    private Address address;

	public Building(String name, int nrOfDesks, Address address) {
		super();
		this.name = name;
		this.nrOfDesks = nrOfDesks;
		this.address = address;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
