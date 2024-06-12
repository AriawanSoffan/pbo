package gradle.models;


public class Mahasiswa {
    private String nim;
    private String nama;
    private double ipk;
    private int totalSks;

    // Constructor, getters, and setters
    public Mahasiswa(String nim, String nama, int totalSks, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.ipk = ipk;
        this.totalSks = totalSks;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public int getTotalSks() {
        return totalSks;
    }

    public void setTotalSks(int totalSks) {
        this.totalSks = totalSks;
    }

    @Override
    public String toString() {
        return "[" + nim + "] " + nama + " : " + ipk + " (" + totalSks + " SKS)";
    }
}