package gradle.components.inputs;

import gradle.components.outputs.Drawable;

public class SelectInput implements Drawable {
    private String label;
    private int width;
    private String[] selection;
    private int value;
    private Input input;

    public SelectInput(String label, int width, String[] selection) {
        this.label = label;
        this.width = width;
        this.selection = selection;
        this.input = new Input("Pilihan");
    }

    public void draw() {
        System.out.println(label);
        int i = 1;
        for (String option : selection) {
            System.out.printf("[%d] %s%n", i++, option);
        }
        System.out.println();
        input.readInput();
        int choice = input.getValueInt();
        if (choice >= 0 && choice <= selection.length) {
            value = choice;
        } else {
            value = -1;
        }
    }

    public int getValue() {
        return value;
    }
}
