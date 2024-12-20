package de.mathisneunzig.facilitymanagement.fm.printer;

import java.util.ArrayList;
import java.util.List;

public class POSDocument {
    private List<POSComponent> components = new ArrayList<>();
    private List<POSStyle> currentStyles = new ArrayList<>();

    public void addComponent(POSComponent component) {
        components.add(component);
    }

    public void addFeed(int lines) {
        for (int i = 0; i < lines; i++) {
            addComponent(() -> "\n".getBytes());
        }
    }

    public void addBarcode(POSBarcode barcode) {
        addComponent(barcode);
    }

    public void addQRCode(POSQRCode qrcode) {
        addComponent(qrcode);
    }

    public void addText(String text) {
        addComponent(() -> (text + "\n").getBytes());
    }

    public void addHeading(String heading) {
        addStyle(POSStyle.BOLD);
        addStyle(POSStyle.BIG);
        addComponent(() -> (heading + "\n").getBytes());
        resetStyle();
    }

    public void addStyle(POSStyle style) {
        currentStyles.add(style);
        // Logic to apply the correct ESC/P command based on the style
        switch (style) {
            case BOLD:
                addComponent(() -> POS.Command.ESC_BOLD_ON);
                break;
            case ITALIC:
                addComponent(() -> POS.Command.ESC_ITALIC_ON);
                break;
            case UNDERLINE:
                addComponent(() -> POS.Command.ESC_UNDERLINE_ON);
                break;
            case BIG:
                addComponent(() -> POS.Command.ESC_DOUBLE_HEIGHT_ON);
                break;
            case SMALL:
                addComponent(() -> POS.Command.ESC_DOUBLE_HEIGHT_OFF); // Small is the default height, we reset the "BIG" style
                break;
            case CENTER:
                addComponent(() -> POS.Command.ESC_ALIGN_CENTER);
                break;
            case RIGHT:
                addComponent(() -> POS.Command.ESC_ALIGN_RIGHT);
                break;
            case LEFT:
                addComponent(() -> POS.Command.ESC_ALIGN_LEFT);
                break;
        }
    }

    public void resetStyle() {
        // Clear the styles and send commands to reset them
        for (POSStyle style : currentStyles) {
            switch (style) {
                case BOLD:
                    addComponent(() -> POS.Command.ESC_BOLD_OFF);
                    break;
                case ITALIC:
                    addComponent(() -> POS.Command.ESC_ITALIC_OFF);
                    break;
                case UNDERLINE:
                    addComponent(() -> POS.Command.ESC_UNDERLINE_OFF);
                    break;
                case BIG:
                    addComponent(() -> POS.Command.ESC_DOUBLE_HEIGHT_OFF);
                    break;
                case CENTER:
                case RIGHT:
                case LEFT:
                    addComponent(() -> POS.Command.ESC_ALIGN_LEFT); // Reset alignment to left
                    break;
                // No need to reset small; it's the default
            }
        }
        currentStyles.clear();
    }

    // New method to cut the page
    public void cutPage() {
        addComponent(() -> POS.Command.ESC_CUT);  // Add the cut command to the document's components
    }

    // New method to convert all components into a byte array for printing
    public byte[] toBytes() {
        List<Byte> byteList = new ArrayList<>();

        // Iterate through each component and add its byte representation to the byte list
        for (POSComponent component : components) {
            byte[] componentBytes = component.toBytes();
            for (byte b : componentBytes) {
                byteList.add(b);
            }
        }

        // Convert the list to a byte array
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }

        return byteArray;
    }
}
