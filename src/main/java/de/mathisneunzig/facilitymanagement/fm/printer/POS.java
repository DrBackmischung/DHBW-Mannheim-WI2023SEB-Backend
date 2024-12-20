package de.mathisneunzig.facilitymanagement.fm.printer;

public class POS {
    public static class Command {
        public static final byte ESC = 0x1B;
        public static final byte GS = 0x1D;
        public static final byte INIT = 0x40;
        public static final byte PAPER_CUT = 0x56;
        public static final byte STYLE_MODE = 0x21;
        public static final byte ALIGNMENT = 0x61;
        public static final byte LINE_FEED = 0x0A;
        public static final byte FEED_LINES = 0x64;
        public static final byte BARCODE_PRINT = 0x6B;
        public static final byte SET_BARCODE_HEIGHT = 0x68;
        public static final byte SET_BAR_WIDTH = 0x77;
        public static final byte SET_BAR_LABEL_POSITION = 0x48;
        public static final byte SET_BAR_LABEL_FONT = 0x66;

        // ESC/P commands for styles
        public static final byte[] ESC_BOLD_ON = new byte[]{0x1B, 0x45, 0x01};   // ESC E
        public static final byte[] ESC_BOLD_OFF = new byte[]{0x1B, 0x45, 0x00};  // ESC E off
        public static final byte[] ESC_ITALIC_ON = new byte[]{0x1B, 0x34};       // ESC 4
        public static final byte[] ESC_ITALIC_OFF = new byte[]{0x1B, 0x35};      // ESC 5
        public static final byte[] ESC_UNDERLINE_ON = new byte[]{0x1B, 0x2D, 0x01};  // ESC - n (n=1 for underline)
        public static final byte[] ESC_UNDERLINE_OFF = new byte[]{0x1B, 0x2D, 0x00}; // ESC - n (n=0 for no underline)
        public static final byte[] ESC_DOUBLE_HEIGHT_ON = new byte[]{0x1B, 0x21, 0x10};  // ESC ! n (n=0x10 for double height)
        public static final byte[] ESC_DOUBLE_HEIGHT_OFF = new byte[]{0x1B, 0x21, 0x00}; // Reset character size

        // ESC/P commands for text alignment
        public static final byte[] ESC_ALIGN_LEFT = new byte[]{0x1B, 0x61, 0x00};  // ESC a 0
        public static final byte[] ESC_ALIGN_CENTER = new byte[]{0x1B, 0x61, 0x01}; // ESC a 1
        public static final byte[] ESC_ALIGN_RIGHT = new byte[]{0x1B, 0x61, 0x02};  // ESC a 2
        
        public static final byte[] ESC_CUT = new byte[]{Command.ESC, 0x69};  // ESC i (full cut)
    }

    public enum Justification {
        LEFT(0), CENTER(1), RIGHT(2);

        private final int value;

        Justification(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum PrintStyle {
        NONE(0), FONT_B(1), BOLD(8), DOUBLE_HEIGHT(16), DOUBLE_WIDTH(32), UNDERLINE(128);

        private final int value;

        PrintStyle(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum BarcodeType {
        UPC_A(0x41), UPC_E(0x42), JAN13_EAN13(0x43), JAN8_EAN8(0x44), CODE39(0x45), ITF(0x46), CODABAR_NW_7(0x47),
        CODE93(0x48), CODE128(0x49), GS1_128(0x4A), GS1_DATABAR_OMNIDIRECTIONAL(0x4B), GS1_DATABAR_TRUNCATED(0x4C),
        GS1_DATABAR_LIMITED(0x4D), GS1_DATABAR_EXPANDED(0x4E);

        private final int value;

        BarcodeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum BarWidth {
        THINNEST(2), THIN(3), DEFAULT(4), THICK(5), THICKEST(6);

        private final int value;

        BarWidth(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum BarLabelPosition {
        NONE(0), ABOVE(1), BELOW(2), BOTH(3);

        private final int value;

        BarLabelPosition(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum QrCodeSize {
        SMALL(2), MEDIUM(3), LARGE(4), EXTRA_LARGE(5);

        private final int value;

        QrCodeSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ErrorCorrection {
        PERCENT_7(48), PERCENT_15(49), PERCENT_25(50), PERCENT_30(51);

        private final int value;

        ErrorCorrection(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
