import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import gradle.Encryptor;

public class PasswordStore {
    public String name, username;
    private String password, hashkey;
    private double score;
    private int category;

    // Konstanta untuk kategori
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;

    // Daftar kategori
    public static final String[] CATEGORIES = { "Belum terkategori", "Aplikasi Web", "Aplikasi Mobile", "Akun Lainnya" };

    // Konstruktor untuk password belum terenkripsi
    public PasswordStore(String name, String username, String plainPass) {
        this(name, username, plainPass, UNCATEGORIZED);
    }

    // Konstruktor untuk password terenkripsi
    public PasswordStore(String name, String username, String plainPass, int category) {
        try {
            this.hashkey = Encryptor.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.name = name;
        this.username = username;
        this.setPassword(plainPass);
        this.setCategory(category);
    }

    // Konstruktor untuk memuat data dari file CSV
    public PasswordStore(String name, String username, String encPass, int category, String hashKey, double score) {
        this.name = name;
        this.username = username;
        this.password = encPass;
        this.category = category;
        this.hashkey = hashKey;
        this.score = score;
    }

    // Setter untuk password terenkripsi dan kunci hash
    public void setEncryptedPass(String encryptedPass, String hashkey) {
        this.password = encryptedPass;
        this.hashkey = hashkey;
    }

    // Setter untuk kunci hash
    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    // Getter untuk kunci hash
    public String getHashkey() {
        return this.hashkey;
    }

    // Setter untuk password (enkripsi password)
    public void setPassword(String plainPass) {
        try {
            String encryptedPass = Encryptor.encrypt(plainPass, this.hashkey);
            this.password = encryptedPass;
            this.calculateScore(plainPass);
        } catch (Exception ex) {
            Logger.getLogger(PasswordStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Getter untuk password terenkripsi
    public String getEncPassword() {
        return this.password;
    }

    // Getter untuk password (dekripsi password)
    public String getPassword() {
        try {
            return Encryptor.decrypt(this.password, this.hashkey);
        } catch (Exception e) {
            Logger.getLogger(PasswordStore.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    // Setter untuk kategori
    public void setCategory(int category) {
        // Jika nilai kategori tidak valid, tetapkan kategori sebagai "Belum terkategori"
        if (category >= 0 && category <= 3) {
            this.category = category;
        } else {
            this.category = 0;
        }
    }

    // Getter untuk nama kategori
    public String getCategory() {
        return CATEGORIES[this.category];
    }

    // Getter untuk kode kategori
    public int getCategoryCode() {
        return this.category;
    }

    // Getter untuk skor
    public double getScore() {
        return this.score;
    }

    // Menghitung skor berdasarkan panjang password
    private void calculateScore(String plainPass) {
        double len = plainPass.length();
        if (len > 15) {
            this.score = 10;
        } else {
            this.score = (len / 15) * 10;
        }
    }

    // Representasi string dari objek PasswordStore
    @Override
    public String toString() {
        return this.username + " - " + this.password + " - " + this.hashkey + " - " + String.format("%,.2f", this.score);
    }
}
