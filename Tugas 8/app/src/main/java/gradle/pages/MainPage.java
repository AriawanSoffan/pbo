package gradle.pages;

import gradle.components.inputs.ListPasswordPage;
import gradle.components.inputs.SelectInput;
import gradle.components.outputs.Drawable;
import gradle.components.outputs.HLine;
import gradle.components.outputs.Label;
import gradle.components.outputs.Space;
import gradle.models.DataPassword;

public class MainPage extends BasePage {
    SelectInput pageSelect;

    public MainPage(int width) {
        super("Aplikasi Penyimpanan Password", width);
        this.components.add(new Label(this.width, "Selamat datang di aplikasi Password Vault"));
        this.components.add(new Label(this.width, "Simpan password anda dengan aman di sini"));
        this.components.add(new Space(this.width));
        String[] pages = { "Input Password", "Tampil Password", "Keluar Aplikasi" };
        this.pageSelect = new SelectInput("Pilih halaman berikut:",
                this.width, pages);
        this.components.add(pageSelect);
    }

    @Override
    protected void drawContent() {
        int select;
        for (Drawable widget : this.components) {
            widget.draw();
        }
        select = this.pageSelect.getValue() - 1;
        switch (select) {
            case 0 -> {
                drawFooter();
                new InputPage(this.width).draw();
            }
            case 1 -> {
                drawFooter();
                new ListPasswordPage(this.width).draw();
            }
            case 2 -> {
                new Label(this.width, "Menyimpan data ... ...").draw();
                DataPassword.saveCSVData();
                new Label(this.width, "Terima kasih telah menggunakan aplikasi").draw();
                drawFooter();
                System.exit(0);
            }
            default -> {
                new MainPage(this.width).draw();
            }
        }
    }
}