package de.mathisneunzig.facilitymanagement.fm.factory;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mathisneunzig.facilitymanagement.fm.dto.BuildingDTO;
import de.mathisneunzig.facilitymanagement.fm.entity.Address;
import de.mathisneunzig.facilitymanagement.fm.entity.Building;
import de.mathisneunzig.facilitymanagement.fm.repo.AddressRepository;

@Service
public class BuildingFactory {
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Building create(BuildingDTO dao) {
		Address address;
		try {
			address = addressRepository.findById(dao.getAddressID()).get();
		} catch (NoSuchElementException e){
			throw new NoSuchElementException();
        }
		Building b = new Building(dao.getName(), dao.getNrOfDesks(), address);
				
		return b;
	}
	
}
