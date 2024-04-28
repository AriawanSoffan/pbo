package gradle.components.inputs;

import java.util.Scanner;

import gradle.components.outputs.Drawable;

public class Input implements Drawable {
    private String label;
    private Scanner input;
    private String value;

    public Input(String label) {
        this.label = label;
        this.input = new Scanner(System.in);
    }

    public void draw() {
        System.out.print(label + ": ");
    }

    public String getValue() {
        return value;
    }

    public int getValueInt() {
        return Integer.parseInt(value);
    }

    public double getValueDouble() {
        return Double.parseDouble(value);
    }

    public void readInput() {
        draw();
        value = input.nextLine();
    }
}
