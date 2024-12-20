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

import de.mathisneunzig.facilitymanagement.fm.dto.PrinterDTO;
import de.mathisneunzig.facilitymanagement.fm.entity.Printer;
import de.mathisneunzig.facilitymanagement.fm.factory.PrinterFactory;
import de.mathisneunzig.facilitymanagement.fm.repo.PrinterRepository;

@Controller
@RestController
@RequestMapping("/printers")
public class PrinterController {
	
	@Autowired
	private PrinterRepository printerRepository;
	
	@Autowired
	private PrinterFactory factory;

    @GetMapping("") // localhost:8081/printers
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<Object>(printerRepository.findAll(), HttpStatus.OK); // Recap: 200 means "OK"
    }

    @GetMapping("/{id}") // localhost:8081/printers/h9n73f-qdh27-1028-aedh73
    public ResponseEntity<Object> getById(@PathVariable UUID id){
        try {
            return new ResponseEntity<Object>(printerRepository.findById(id).get(), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Printer with id " + id + " could not be found", HttpStatus.NOT_FOUND); // Recap: 404 means "Not found"
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> add(@RequestBody PrinterDTO dto){
        try {
            Printer p = factory.create(dto);
            return new ResponseEntity<Object>(printerRepository.save(p), HttpStatus.CREATED); // Recap: 201 means "Created"
        } catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Printer could not be created since Building with id " + dto.getBuildingID() + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        try{
        	printerRepository.delete(printerRepository.findById(id).get());
            return new ResponseEntity<Object>("Printer id " + id + " deleted", HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Object>("Printer id " + id + " could not be found", HttpStatus.NOT_FOUND);
        }
    }

}
