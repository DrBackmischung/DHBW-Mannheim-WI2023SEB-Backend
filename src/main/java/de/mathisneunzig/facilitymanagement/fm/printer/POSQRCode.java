package de.mathisneunzig.facilitymanagement.fm.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mathisneunzig.facilitymanagement.fm.printer.POS.ErrorCorrection;
import de.mathisneunzig.facilitymanagement.fm.printer.POS.QrCodeSize;

public class POSQRCode implements POSComponent {
    private final String data;
    private final List<byte[]> commands = new ArrayList<>();

    public POSQRCode(String data, ErrorCorrection percent15, QrCodeSize medium) {
        this.data = data;
        commands.add(new byte[]{POS.Command.GS, 0x28, 0x6B, 0x04, 0x00, 0x31, 0x41, 0x32, 0x00});
        commands.add(new byte[]{POS.Command.GS, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x43, (byte) medium.getValue()});
        commands.add(new byte[]{POS.Command.GS, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x45, (byte) percent15.getValue()});
        commands.add(new byte[]{POS.Command.GS, 0x28, 0x6B, (byte) (data.length() + 3), 0x00, 0x31, 0x50, 0x30});
        commands.add(data.getBytes());
        commands.add(new byte[]{POS.Command.GS, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x51, 0x30});
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (byte[] command : commands) {
            output.writeBytes(command);
        }
        output.write(POS.Command.LINE_FEED);
        try {
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return output.toByteArray();
    }
}
