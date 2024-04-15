package gradle.components.outputs;

import org.checkerframework.checker.units.qual.min;

public class Label {
    private int width;
    public String text;

    public Label(int width, String text) {
        this.width = width;
        this.text = text;
    }

    public void draw() {
        StringBuilder line = new StringBuilder();
        int minWidth = width - 2;
        if (minWidth < 0) {
            minWidth = 0;
        }
        if (text.length() > minWidth) {
            line.append(text.substring(0, minWidth));
        } else {
            line.append(text);
            for (int i = text.length(); i < minWidth; i++) {
                line.append(" ");
            }
        }
        System.out.println("| " + line + " |");
    }

    public void drawToBuffer(StringBuilder lineBuffer) {
        int minWidth = width - 2;
        if (minWidth < 0) {
            minWidth = 0;
        }
        if (text.length() > minWidth) {
            lineBuffer.append(text.substring(0, minWidth));
        } else {
            lineBuffer.append(text);
            for (int i = text.length(); i < minWidth; i++) {
                lineBuffer.append(" ");
            }
        }
    }
}
