package de.mathisneunzig.facilitymanagement.fm.dto;

import java.util.UUID;

public class PrinterDTO {
	
	private String name;
	private boolean canPrintColor;
	private UUID buildingID;
	
	public PrinterDTO(String name, boolean canPrintColor, UUID buildingID) {
		super();
		this.name = name;
		this.canPrintColor = canPrintColor;
		this.buildingID = buildingID;
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

	public UUID getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(UUID buildingID) {
		this.buildingID = buildingID;
	}

}
