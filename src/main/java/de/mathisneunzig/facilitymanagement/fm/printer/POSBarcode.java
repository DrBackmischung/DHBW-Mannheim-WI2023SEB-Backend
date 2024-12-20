package de.mathisneunzig.facilitymanagement.fm.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POSBarcode implements POSComponent {
    private final POS.BarcodeType type;
    private final String data;
    private final List<byte[]> commands = new ArrayList<>();

    public POSBarcode(long data, POS.BarcodeType type) {
        this.data = String.valueOf(data);
        this.type = type;
    }

    public void setHeight(int height) {
        commands.add(new byte[]{POS.Command.GS, POS.Command.SET_BARCODE_HEIGHT, (byte) height});
    }

    public void setWidth(POS.BarWidth width) {
        commands.add(new byte[]{POS.Command.GS, POS.Command.SET_BAR_WIDTH, (byte) width.getValue()});
    }

    public void setLabelPosition(POS.BarLabelPosition position) {
        commands.add(new byte[]{POS.Command.GS, POS.Command.SET_BAR_LABEL_POSITION, (byte) position.getValue()});
    }

    public void setLabelFont(boolean fontB) {
        commands.add(new byte[]{POS.Command.GS, POS.Command.SET_BAR_LABEL_FONT, (byte) (fontB ? 1 : 0)});
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (byte[] command : commands) {
            output.writeBytes(command);
        }
        output.write(POS.Command.GS);
        output.write(POS.Command.BARCODE_PRINT);
        output.write(type.getValue());
        output.write(data.length());
        output.writeBytes(data.getBytes());
        output.write(POS.Command.LINE_FEED);
        try {
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return output.toByteArray();
    }
}
