package de.mathisneunzig.facilitymanagement.fm.repo;

import java.util.Optional;

import de.mathisneunzig.facilitymanagement.fm.config.CustomMongoRepository;
import de.mathisneunzig.facilitymanagement.fm.entity.Building;

public interface BuildingRepository extends CustomMongoRepository<Building> {
	
	Optional<Building> findByName(String name);

}
