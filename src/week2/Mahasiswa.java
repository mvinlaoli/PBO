package week2;

public class Mahasiswa {

    String nim;
    String nama;
    boolean status;

    public Mahasiswa(String nim, String nama, boolean status) {
        this.nim = nim;
        this.nama = nama;
        this.status = status;
    }

    public void print() {
        System.out.println(
                nim + " | "
                + nama + " | "
                + (status ? "Aktif" : "Tidak Aktif"));
    }
}
