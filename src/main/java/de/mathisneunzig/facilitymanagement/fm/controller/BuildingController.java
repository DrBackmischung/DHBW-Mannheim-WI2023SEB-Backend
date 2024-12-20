package de.mathisneunzig.facilitymanagement.fm.controller;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.mathisneunzig.facilitymanagement.fm.dto.BuildingDTO;
import de.mathisneunzig.facilitymanagement.fm.entity.Building;
import de.mathisneunzig.facilitymanagement.fm.factory.BuildingFactory;
import de.mathisneunzig.facilitymanagement.fm.repo.BuildingRepository;

@Controller
@RestController
@RequestMapping("/buildings")
public class BuildingController {
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingFactory factory;

    @GetMapping("") // localhost:8081/buildings
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<Object>(buildingRepository.findAll(), HttpStatus.OK); // Recap: 200 means "OK"
    }

    @GetMapping("/{id}") // localhost:8081/buildings/h9n73f-qdh27-1028-aedh73
    public ResponseEntity<Object> getById(@PathVariable UUID id){
        try {
            return new ResponseEntity<Object>(buildingRepository.findById(id).get(), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Building with id " + id + " could not be found", HttpStatus.NOT_FOUND); // Recap: 404 means "Not found"
        }
    }

    @GetMapping("/name/{name}") // localhost:8081/buildings/name/BER01
    public ResponseEntity<Object> getByName(@PathVariable String name){
        try {
            return new ResponseEntity<Object>(buildingRepository.findByName(name).get(), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Building with name " + name + " could not be found", HttpStatus.NOT_FOUND); 
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> add(@RequestBody BuildingDTO dao){
        try {
            Building b = factory.create(dao);
            return new ResponseEntity<Object>(buildingRepository.save(b), HttpStatus.CREATED); // Recap: 201 means "Created"
        } catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Building could not be created since Address with id " + dao.getAddressID() + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        try{
        	buildingRepository.delete(buildingRepository.findById(id).get());
            return new ResponseEntity<Object>("Building id " + id + " deleted", HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Building id " + id + " could not be found", HttpStatus.NOT_FOUND);
        }
    }

}
