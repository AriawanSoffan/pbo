package gradle.pages;

import java.util.ArrayList;

import gradle.components.outputs.Drawable;
import gradle.components.outputs.HLine;
import gradle.components.outputs.Label;
import gradle.components.outputs.Space;

public abstract class BasePage {
    public String title;
    public int width;
    protected final HLine hline;
    protected final Space space;
    protected final Label label;
    protected final ArrayList<Drawable> components = new ArrayList<>();

    public BasePage(String title, int width) {
        this.width = width;
        this.title = title;
        this.hline = new HLine(width);
        this.space = new Space(width);
        this.label = new Label(width, title);
    }

    public void draw() {
        this.drawHeader();
        this.space.draw();
        this.drawContent();
    }

    public void drawHeader() {
        this.hline.draw();
        this.space.draw();
        this.label.draw();
        this.space.draw();
        this.hline.draw();
    }

    protected abstract void drawContent();

    public void drawFooter() {
        this.space.draw();
        this.hline.draw();
    }
}
