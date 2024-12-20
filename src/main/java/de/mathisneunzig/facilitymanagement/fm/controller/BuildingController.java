package de.mathisneunzig.facilitymanagement.fm.controller;

import java.util.NoSuchElementException;
import java.util.UUID;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

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
import de.mathisneunzig.facilitymanagement.fm.printer.POS;
import de.mathisneunzig.facilitymanagement.fm.printer.POSBarcode;
import de.mathisneunzig.facilitymanagement.fm.printer.POSPrinter;
import de.mathisneunzig.facilitymanagement.fm.printer.POSQRCode;
import de.mathisneunzig.facilitymanagement.fm.printer.POSReceipt;
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
            
            PrintService printerService = findPrintService("Printer");

            if (printerService == null) {
                System.out.println("Printer not found");
                return new ResponseEntity<Object>("No printer found", HttpStatus.NOT_FOUND);
            }

            // Create a new POSPrinter instance
            POSPrinter posPrinter = new POSPrinter();

            // Create a new receipt
            POSReceipt receipt = new POSReceipt();
            receipt.setTitle("Cat Shop 24");
            receipt.setAddress("Europaplatz 17\n69115 Heidelberg");
            receipt.setPhone("01749885992");

            // Add some items to the receipt
            receipt.addItem("Snackies", 1.99);
            receipt.addItem("Cat Milk", 2.99);

            // Create and add a barcode to the receipt
            POSBarcode barcode = new POSBarcode(4012345678901L, POS.BarcodeType.JAN13_EAN13);
            barcode.setHeight(162);
            barcode.setWidth(POS.BarWidth.DEFAULT);
            receipt.addBarcode(barcode);
            
            // Set a footer for the receipt
            receipt.setFooterLine("Thank you for shopping!");

            // Print the receipt using the POSPrinter
            posPrinter.print(receipt, printerService);
            
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
    
    public static PrintService findPrintService(String printerName) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }

}
