package week3;

public class Utility {

    public static void printNamaMatkul(KRS k) {
        System.out.println(
                k.mhs.nama + " - " +
                        k.matkul.nama);
    }

    public static void printNimKode(KRS k) {
        System.out.println(
                k.mhs.nim + " - " +
                        k.matkul.kode);
    }
}
