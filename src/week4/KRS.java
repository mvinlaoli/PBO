package week4;

public class KRS {
    private Mahasiswa mhs;
    private Matakuliah matkul;
    private double nilai;

    public KRS(Mahasiswa mhs, Matakuliah matkul, double nilai) {
        this.mhs = mhs;
        this.matkul = matkul;
        this.nilai = nilai;
    }

    public Mahasiswa getMhs() { return mhs; }
    public void setMhs(Mahasiswa mhs) { this.mhs = mhs; }

    public Matakuliah getMatkul() { return matkul; }
    public void setMatkul(Matakuliah  matkul) { this.matkul = matkul; }

    public double getNilai() { return nilai; }
    public void setNilai(double nilai) { this.nilai = nilai; }
}