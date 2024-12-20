package de.mathisneunzig.facilitymanagement.fm.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import de.mathisneunzig.facilitymanagement.fm.config.UuidIdentifiedEntity;

@Document
public class Printer extends UuidIdentifiedEntity {
    
    @Field("name")
    private String name;
    
    @Field("color")
    private boolean canPrintColor;
    
    @Field("building")
    @DBRef
    private Building building;

	public Printer(String name, boolean canPrintColor, Building building) {
		super();
		this.name = name;
		this.canPrintColor = canPrintColor;
		this.building = building;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanPrintColor() {
		return canPrintColor;
	}

	public void setCanPrintColor(boolean canPrintColor) {
		this.canPrintColor = canPrintColor;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

}
