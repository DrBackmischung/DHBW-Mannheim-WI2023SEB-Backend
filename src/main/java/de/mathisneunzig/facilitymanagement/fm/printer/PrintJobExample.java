package de.mathisneunzig.facilitymanagement.fm.printer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class PrintJobExample {

    public static void main(String[] args) {
        try {
            // Find the printer by name
            PrintService printerService = findPrintService("Printer");

            if (printerService == null) {
                System.out.println("Printer not found");
                return;
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
            receipt.addItem("CatMilk", 2.99);

            // Create and add a barcode to the receipt
            POSBarcode barcode = new POSBarcode(4012345678901L, POS.BarcodeType.JAN13_EAN13);
            barcode.setHeight(162);
            barcode.setWidth(POS.BarWidth.DEFAULT);
            receipt.addBarcode(barcode);
            
            POSQRCode qrcode = new POSQRCode("www.google.com", POS.ErrorCorrection.PERCENT_15, POS.QrCodeSize.EXTRA_LARGE);
            receipt.addQRCode(qrcode);;
            
            // Set a footer for the receipt
            receipt.setFooterLine("Thank you for shopping!");

            // Print the receipt using the POSPrinter
            posPrinter.print(receipt, printerService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to find a printer by name
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
