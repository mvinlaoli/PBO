package week2;

public class Matakuliah {

    String kode;
    String nama;
    boolean status;

    public Matakuliah(String kode, String nama, boolean status) {
        this.kode = kode;
        this.nama = nama;
        this.status = status;
    }

    public void print() {
        System.out.println(
                kode + " | "
                        + nama + " | "
                        + (status ? "Aktif" : "Tidak Aktif"));
    }
}
