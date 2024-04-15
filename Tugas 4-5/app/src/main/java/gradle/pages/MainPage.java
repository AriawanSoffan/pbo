package gradle.pages;

import gradle.components.inputs.ListPasswordPage;
import gradle.components.inputs.SelectInput;
import gradle.components.outputs.HLine;
import gradle.components.outputs.Label;
import gradle.components.outputs.Space;

public class MainPage {
    public String title;
    public int width;
    private final HLine hline;
    private final Space space;
    private final Label label;

    public MainPage(String title, int width) {
        this.width = width;
        this.title = title;
        this.hline = new HLine(width);
        this.space = new Space(width);
        this.label = new Label(width, title.toUpperCase());
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

    private void drawContent() {
        String[] pages = { "Input Password", "Tampil Password", "Keluar Aplikasi" };
        SelectInput pageSelect = new SelectInput("Pilih halaman berikut:",
                this.width, pages);
        int select;
        this.label.text = "Selamat datang di aplikasi Password Vault";
        this.label.draw();
        this.label.text = "Simpan password anda dengan aman di sini";
        this.label.draw();
        this.space.draw();
        pageSelect.draw();
        select = pageSelect.getValue() - 1;

        if (select >= 0 && select < pages.length) {
            switch (select) {
                case 0:
                    drawFooter();
                    new InputPage("Inputan Password", this.width).draw();
                    break;
                case 1:
                    drawFooter();
                    new ListPasswordPage("List Password Tersimpan", this.width).draw();
                    break;
                case 2:
                    new Label(this.width, "Terima kasih telah menggunakan aplikasi").draw();
                    break;
                default:
                    new MainPage(this.title, this.width).draw();
            }
        } else {
            new Label(this.width, "Pilihan tidak valid. Silakan pilih nomor yang tersedia.").draw();
        }
    }

    public void drawFooter() {
        this.space.draw();
        this.hline.draw();
    }
}