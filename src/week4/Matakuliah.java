package week4;

public class Matakuliah {
    private String kodeMatakuliah;
    private String nama;
    private int sks;
    private Jurusan jurusan; 

    public Matakuliah(String kodeMatakuliah, String nama, int sks, Jurusan jurusan) {
        this.kodeMatakuliah = kodeMatakuliah;
        this.nama = nama;
        this.sks = sks;
        this.jurusan = jurusan;
    }

    public String getKodeMatakuliah() { return kodeMatakuliah; }
    public void setKodeMatakuliah(String kodeMatakuliah) { this.kodeMatakuliah = kodeMatakuliah; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getSks() { return sks; }
    public void setSks(int sks) { this.sks = sks; }

    public Jurusan getJurusan() { return jurusan; }
    public void setJurusan(Jurusan jurusan) { this.jurusan = jurusan; }

    public void print() {
        System.out.println(kodeMatakuliah + " | " + nama + " (" + sks + " SKS) | Jurusan: " + (jurusan != null ? jurusan.getNama() : "-"));
    }
}