package de.mathisneunzig.facilitymanagement.fm.factory;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mathisneunzig.facilitymanagement.fm.dto.PrinterDTO;
import de.mathisneunzig.facilitymanagement.fm.entity.Building;
import de.mathisneunzig.facilitymanagement.fm.entity.Printer;
import de.mathisneunzig.facilitymanagement.fm.repo.BuildingRepository;

@Service
public class PrinterFactory {
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	public Printer create(PrinterDTO dao) {
		Building building;
		try {
			building = buildingRepository.findById(dao.getBuildingID()).get();
		} catch (NoSuchElementException e){
			throw new NoSuchElementException();
        }
		Printer p = new Printer(dao.getName(), dao.isCanPrintColor(), building);
				
		return p;
	}
	
}
