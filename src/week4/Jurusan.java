package week4;

public class Jurusan {
    private String kodeJurusan;
    private String nama;

    // Constructor
    public Jurusan(String kodeJurusan, String nama) {
        this.kodeJurusan = kodeJurusan;
        this.nama = nama;
    }

    // Getter dan Setter
    public String getKodeJurusan() {
        return kodeJurusan;
    }

    public void setKodeJurusan(String kodeJurusan) {
        this.kodeJurusan = kodeJurusan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void print() {
        System.out.println(kodeJurusan + " | " + nama);
    }
}