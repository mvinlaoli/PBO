package week2;

import java.util.ArrayList;
import java.util.Scanner;

public class Appp {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Mahasiswa> daftarmahasiswa = new ArrayList<>();
    static ArrayList<Matakuliah> daftarmatakuliah = new ArrayList<>();
    static ArrayList<KRS> daftarkrs = new ArrayList<>();

    public static void main(String[] args) {
        dataDummy();

        int pilihan;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Mata Kuliah");
            System.out.println("2. Mahasiswa");
            System.out.println("3. Mahasiswa - Mata Kuliah");
            System.out.println("4. List Berdasarkan Mata Kuliah");
            System.out.println("5. List Berdasarkan Mahasiswa");
            System.out.println("0. Keluar");
            System.out.print("Pilih : ");
            pilihan = sc.nextInt();

            switch (pilihan) {
                case 1:
                    menuMatakuliah();
                    break;
                case 2:
                    menuMahasiswa();
                    break;
                case 3:
                    menuRelasi();
                    break;
                case 4:
                    listBerdasarkanMatakuliah();
                    break;
                case 5:
                    listBerdasarkanMahasiswa();
                    break;
            }
        } while (pilihan != 0);
    }

    static void dataDummy() {

        Mahasiswa m1 = new Mahasiswa(
                "1124035",
                "Jolvin",
                true);

        Mahasiswa m2 = new Mahasiswa(
                "1124036",
                "Mychle",
                false);

        Mahasiswa m3 = new Mahasiswa(
                "1124033",
                "Laoli",
                true);

        daftarmahasiswa.add(m1);
        daftarmahasiswa.add(m2);
        daftarmahasiswa.add(m3);

        Matakuliah mk1 = new Matakuliah(
                "IF101",
                "Algoritma",
                true);

        Matakuliah mk2 = new Matakuliah(
                "IF201",
                "Struktur Data",
                true);

        Matakuliah mk3 = new Matakuliah(
                "IF301",
                "PBO",
                false);

        daftarmatakuliah.add(mk1);
        daftarmatakuliah.add(mk2);
        daftarmatakuliah.add(mk3);

        daftarkrs.add(
                new KRS(
                        m1,
                        mk1,
                        85));

        daftarkrs.add(
                new KRS(
                        m3,
                        mk2,
                        90));
    }

    static void menuMahasiswa() {

        System.out.println("1. Tambah");
        System.out.println("2. Edit");

        int pil = sc.nextInt();

        if (pil == 1) {

            sc.nextLine();

            System.out.print("NIM : ");
            String nim = sc.nextLine();

            System.out.print("Nama : ");
            String nama = sc.nextLine();

            System.out.print("Status (true/false) : ");
            boolean status = sc.nextBoolean();

            daftarmahasiswa.add(
                    new Mahasiswa(
                            nim,
                            nama,
                            status));

            System.out.println("Mahasiswa berhasil ditambahkan");
        }

        else if (pil == 2) {

            tampilDataMahasiswa();

            System.out.print("Pilih : ");
            int index = sc.nextInt() - 1;

            if (index < 0 ||
                    index >= daftarmahasiswa.size()) {

                System.out.println("Data tidak ditemukan");
                return;
            }

            sc.nextLine();

            System.out.print("Nama Baru : ");
            daftarmahasiswa.get(index).nama = sc.nextLine();

            System.out.print("Status Baru : ");
            daftarmahasiswa.get(index).status = sc.nextBoolean();

            System.out.println("Data berhasil diubah");
        }
    }

    static void menuMatakuliah() {

        System.out.println("1. Tambah");
        System.out.println("2. Edit");

        int pil = sc.nextInt();

        if (pil == 1) {

            sc.nextLine();

            System.out.print("Kode : ");
            String kode = sc.nextLine();

            System.out.print("Nama : ");
            String nama = sc.nextLine();

            System.out.print("Status : ");
            boolean status = sc.nextBoolean();

            daftarmatakuliah.add(
                    new Matakuliah(
                            kode,
                            nama,
                            status));

            System.out.println("Matakuliah berhasil ditambahkan");
        }

        else if (pil == 2) {

            tampilDataMatakuliah();

            System.out.print("Pilih : ");
            int index = sc.nextInt() - 1;

            if (index < 0 ||
                    index >= daftarmatakuliah.size()) {

                System.out.println("Data tidak ditemukan");
                return;
            }

            sc.nextLine();

            System.out.print("Nama Baru : ");
            daftarmatakuliah.get(index).nama = sc.nextLine();

            System.out.print("Status Baru : ");
            daftarmatakuliah.get(index).status = sc.nextBoolean();

            System.out.println("Data berhasil diubah");
        }
    }

    static void menuRelasi() {

        System.out.println("1. Tambah Relasi");
        System.out.println("2. Edit Nilai");

        int pil = sc.nextInt();

        if (pil == 1) {

            tampilDataMahasiswa();

            System.out.print("Pilih Mahasiswa : ");
            int mhs = sc.nextInt() - 1;

            tampilDataMatakuliah();

            System.out.print("Pilih Matakuliah : ");
            int mk = sc.nextInt() - 1;

            Mahasiswa mahasiswa = daftarmahasiswa.get(mhs);

            Matakuliah matakuliah = daftarmatakuliah.get(mk);

            if (!mahasiswa.status) {

                System.out.println(
                        "Mahasiswa tidak aktif");

                return;
            }

            if (!matakuliah.status) {

                System.out.println(
                        "Matakuliah tidak aktif");

                return;
            }

            for (KRS k : daftarkrs) {

                if (k.mhs == mahasiswa &&
                        k.matkul == matakuliah) {

                    System.out.println(
                            "Relasi sudah ada");

                    return;
                }
            }

            System.out.print("Nilai : ");
            double nilai = sc.nextDouble();

            daftarkrs.add(
                    new KRS(
                            mahasiswa,
                            matakuliah,
                            nilai));

            System.out.println(
                    "Relasi berhasil ditambahkan");
        }

        else if (pil == 2) {

            editNilai();
        }
    }

    static void editNilai() {

        for (int i = 0; i < daftarkrs.size(); i++) {

            KRS k = daftarkrs.get(i);

            System.out.println(
                    (i + 1)
                            + ". "
                            + k.mhs.nama
                            + " - "
                            + k.matkul.nama
                            + " | "
                            + k.nilai);
        }

        System.out.print("Pilih : ");
        int pilih = sc.nextInt() - 1;

        System.out.print("Nilai Baru : ");
        daftarkrs.get(pilih).nilai = sc.nextDouble();
    }

    static void listBerdasarkanMahasiswa() {
        tampilDataMahasiswa();
        System.out.println("Pilih Mahasiswa : ");
        int pilih = sc.nextInt() - 1;
        Mahasiswa m = daftarmahasiswa.get(pilih);
        System.out.println("\nDaftar Matakuliah " + m.nama);
        for (KRS k : daftarkrs) {
            if (k.mhs == m) {
                System.out.println(k.matkul.nama + " | Nilai : " + k.nilai);

            }
        }

    }

    static void listBerdasarkanMatakuliah() {
        tampilDataMatakuliah();
        System.out.println("Pilih Mahasiswa : ");
        int pilih = sc.nextInt() - 1;
        Matakuliah mk = daftarmatakuliah.get(pilih);
        System.out.println("\nDaftar Mahasiwa " + mk.nama);
        for (KRS k : daftarkrs) {
            if (k.matkul == mk) {
                System.out.println(k.mhs.nama + "| Nilai : " + k.nilai);
            }
        }

    }

    static void tampilDataMahasiswa() {
        for (int i = 0; i < daftarmahasiswa.size(); i++) {
            System.out.println((i + 1) + ". " + daftarmahasiswa.get(i).nama);
        }
    }

    static void tampilDataMatakuliah() {
        for (int i = 0; i < daftarmatakuliah.size(); i++) {
            System.out.println((i + 1) + ". " + daftarmatakuliah.get(i).nama);
        }
    }

    static void tampilMahasiwaAktif() {
        for (int i = 0; i < daftarmahasiswa.size(); i++) {
            if (daftarmahasiswa.get(i).status) {
                System.out.println((i + 1) + ". " + daftarmahasiswa.get(i).nama);

            }
        }

    }

    static void tampilMatakuliahAktif() {
        for (int i = 0; i < daftarmatakuliah.size(); i++) {
            if (daftarmatakuliah.get(i).status) {
                System.out.println((i + 1) + ". " + daftarmatakuliah.get(i).nama);

            }
        }

    }
}
