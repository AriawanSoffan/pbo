package gradle.pages;

import java.util.Scanner;

import gradle.components.inputs.Input;
import gradle.components.inputs.SelectInput;
import gradle.components.outputs.HLine;
import gradle.components.outputs.Label;
import gradle.components.outputs.Space;
import gradle.models.PasswordStore;
import gradle.models.DataPassword;

public class InputPage extends BasePage {
    Input inputName = new Input("Judul Password");
    Input inputUsername = new Input("Username");
    Input inputPass = new Input("Password");
    SelectInput inpCategory;
    PasswordStore pass;

    public InputPage(int width) {
        super("Inputan Password", width);
        this.inpCategory = new SelectInput("Kategori", this.width, PasswordStore.CATEGORIES);
        this.components.add(this.inputName);
        this.components.add(this.inputUsername);
        this.components.add(this.inputPass);
        this.components.add(this.inpCategory);
        this.components.add(new Label(this.width, "----- -----"));
        this.components.add(new Label(this.width, "Input password berhasil dibuat"));
    }

    public void draw() {
        this.drawHeader();
        this.space.draw();
        this.drawContent();
        this.drawFooter();
    }

    public void drawHeader() {
        this.hline.draw();
        this.space.draw();
        this.label.draw();
        this.space.draw();
        this.hline.draw();
    }

    public void drawContent() {
        // Scanner scanner = new Scanner(System.in);

        Input input = new Input("Nama Password");
        Input input2 = new Input("Username");
        Input input3 = new Input("Password");
        input.readInput();
        String name = input.getValue();
        input2.readInput();
        String user = input2.getValue();
        input3.readInput();
        String pass = input3.getValue();

        String[] choices = { "Belum terkategori", "Aplikasi Web", "Aplikasi Mobile", "Akun Lainnya" };
        SelectInput selectInput = new SelectInput("Kategori Password:", 70, choices);
        selectInput.draw();
        int category = selectInput.getValue();

        DataPassword.passData.add(new PasswordStore(name, user, pass, category));
        DataPassword.saveCSVData();
    }

    public void drawFooter() {
        new HLine(50).draw();
        new Space(50).draw();
        new Label(50, "Input Password berhasil dibuat").draw();
        new Space(50).draw();
        new HLine(50).draw();
        new MainPage(width).draw();
    }
}