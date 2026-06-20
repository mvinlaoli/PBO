package week4;
import week4.enumsv2.UserStatusv2;
import week4.enumsv2.UserTypev2;


public class Mahasiswa extends User {
    private Jurusan jurusan; 

    public Mahasiswa(String nomorInduk, String email, String password, UserStatusv2 status, String nama, String tanggalMasuk, String tanggalKeluar, Jurusan jurusan) {
        super(nomorInduk, email, password, status, UserTypev2.MAHASISWA, nama, tanggalMasuk, tanggalKeluar);
        this.jurusan = jurusan;
    }

    public Jurusan getJurusan() { return jurusan; }
    public void setJurusan(Jurusan jurusan) { this.jurusan = jurusan; }


    public double hitungIP() {
        double totalBobot = 0;
        int jumlahMK = 0;

        for (KRS k : Appp.daftarkrs) {
            if (k.getMhs() == this) {
                double bobot = 0;
                if (k.getNilai() >= 85) bobot = 4.0;
                else if (k.getNilai() >= 80) bobot = 3.5;
                else if (k.getNilai() >= 70) bobot = 3.0;
                else if (k.getNilai() >= 60) bobot = 2.0;
                else if (k.getNilai() >= 50) bobot = 1.0;
                
                totalBobot += bobot;
                jumlahMK++;
            }
        }
        return jumlahMK == 0 ? 0 : totalBobot / jumlahMK;
    }
}