package de.mathisneunzig.facilitymanagement.fm.printer;

import java.io.ByteArrayInputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.PrintException;

public class POSPrinter {

    public void print(POSDocument document, PrintService printerService)  {
        // Convert the POSDocument to byte array for printing
    	
    	byte[] emptyBufferSpace = new byte[100];  // You can increase the size of the buffer space
        for (int i = 0; i < emptyBufferSpace.length; i++) {
            emptyBufferSpace[i] = ' ';  // Fill with spaces or use '\n' for line feeds
        }
        
        byte[] printData = concat(
            new byte[]{POS.Command.ESC, POS.Command.INIT},   // Printer initialization command
            emptyBufferSpace,
            document.toBytes(),                             // The document data
            new byte[]{POS.Command.LINE_FEED},              // The document data
            POS.Command.ESC_CUT);                          // Cut the page at the end

        // Send the data to the printer
        DocPrintJob job = printerService.createPrintJob();
        Doc doc = new SimpleDoc(new ByteArrayInputStream(printData), DocFlavor.INPUT_STREAM.AUTOSENSE, null);

        try {
            job.print(doc, null);
            System.out.println("Print job sent to printer");
        } catch (PrintException e) {
            System.err.println("Error during printing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to concatenate multiple byte arrays
    public static byte[] concat(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            totalLength += array.length;
        }

        byte[] result = new byte[totalLength];
        int currentPos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, currentPos, array.length);
            currentPos += array.length;
        }

        return result;
    }
}
