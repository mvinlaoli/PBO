package week4;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import week4.databases.Database;
import week4.enumsv2.UserStatusv2;
import week4.enumsv2.UserTypev2;

public class Appp {

    static Scanner sc = new Scanner(System.in);

    // Penampung data lokal Java (ArrayList)
    static ArrayList<Mahasiswa> daftarmahasiswa = new ArrayList<>();
    static ArrayList<Matakuliah> daftarmatakuliah = new ArrayList<>();
    static ArrayList<KRS> daftarkrs = new ArrayList<>();
    static ArrayList<User> daftarkaryawan = new ArrayList<>();
    static ArrayList<Jurusan> daftarjurusan = new ArrayList<>();

    public static void main(String[] args) {

        // Tarik data secara live dari pgAdmin saat pertama dibuka
        sinkronisasiDataDariDB();

        int pilihan;

        do {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Menu Mata Kuliah");
            System.out.println("2. Menu Mahasiswa");
            System.out.println("3. Input KRS / Nilai");
            System.out.println("4. List Berdasarkan Mata Kuliah");
            System.out.println("5. List Berdasarkan Mahasiswa");
            System.out.println("6. Menu Karyawan");
            System.out.println("7. Hitung IP Mahasiswa");
            System.out.println("8. Menu Jurusan (BARU)"); 
            System.out.println("0. Keluar");

            System.out.print("Pilih : ");
            while (!sc.hasNextInt()) {
                System.out.println("Input harus berupa angka!");
                sc.next();
                System.out.print("Pilih : ");
            }
            pilihan = sc.nextInt();

            switch (pilihan) {
                case 1:
                    menuMatakuliah();
                    break;
                case 2: 
                    menuMahasiswa();
                    break;
                case 3:
                    menuKRS();
                    break;
                case 4:
                    listBerdasarkanMatakuliah();
                    break;
                case 5:
                    listBerdasarkanMahasiswa();
                    break;
                case 6:
                    menuKaryawan();
                    break;
                case 7:
                    hitungIPMahasiswa();
                    break;
                case 8:
                    menuJurusan(); 
                    break;
            }

        } while (pilihan != 0);
    }

    // ============================================================
    // SINKRONISASI DATA LIVE DARI PGADMIN KE ARRAYLIST JAVA
    // ============================================================
    static void sinkronisasiDataDariDB() {
        daftarmahasiswa.clear();
        daftarmatakuliah.clear();
        daftarkrs.clear();
        daftarkaryawan.clear();
        daftarjurusan.clear();

        try (Connection conn = Database.connect()) {
            if (conn == null) {
                System.out.println("[DB Log] Koneksi database gagal!");
                return;
            }

            // 1. Ambil Data Jurusan
            String sqlJurusan = "SELECT * FROM jurusan_v2";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlJurusan)) {
                while (rs.next()) {
                    daftarjurusan.add(new Jurusan(rs.getString("kode_jurusan"), rs.getString("nama")));
                }
            }

            // 2. Ambil Data Mata Kuliah
            String sqlMatkul = "SELECT * FROM matakuliah_v2";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlMatkul)) {
                while (rs.next()) {
                    Jurusan jur = cariJurusanLokal(rs.getString("kode_jurusan"));
                    daftarmatakuliah.add(new Matakuliah(rs.getString("kode_matakuliah"), rs.getString("nama"), rs.getInt("sks"), jur));
                }
            }

            // 3. Ambil Data User (Mahasiswa & Karyawan)
            String sqlUser = "SELECT * FROM user_v2";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlUser)) {
                while (rs.next()) {
                    String nomorInduk = rs.getString("nomor_induk");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    
                    // Ambil string enum dengan aman
                    String statusStr = rs.getString("status");
                    String typeStr = rs.getString("type");
                    
                    UserStatusv2 status = (statusStr != null) ? UserStatusv2.valueOf(statusStr.trim().toUpperCase()) : UserStatusv2.AKTIF;
                    UserTypev2 type = (typeStr != null) ? UserTypev2.valueOf(typeStr.trim().toUpperCase()) : UserTypev2.MAHASISWA;
                    
                    String nama = rs.getString("nama");
                    
                    // Gunakan rs.getDate().toString() agar terhindar dari error parsing format timestamp bpgadmin
                    Date dateMasuk = rs.getDate("tanggal_masuk");
                    String masuk = (dateMasuk != null) ? dateMasuk.toString() : "";
                    
                    Date dateKeluar = rs.getDate("tanggal_keluar");
                    String keluar = (dateKeluar != null) ? dateKeluar.toString() : "";

                    if (type == UserTypev2.MAHASISWA) {
                        Jurusan jur = cariJurusanLokal(rs.getString("kode_jurusan"));
                        daftarmahasiswa.add(new Mahasiswa(nomorInduk, email, password, status, nama, masuk, keluar, jur));
                    } else {
                        daftarkaryawan.add(new User(nomorInduk, email, password, status, type, nama, masuk, keluar));
                    }
                }
            }

            // 4. Ambil Data KRS
            String sqlKRS = "SELECT * FROM mahasiswa_matakuliah_v2";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlKRS)) {
                while (rs.next()) {
                    Mahasiswa mhs = cariMahasiswaLokal(rs.getString("nomor_induk"));
                    Matakuliah mk = cariMatkulLokal(rs.getString("kode_matakuliah"));
                    if (mhs != null && mk != null) {
                        daftarkrs.add(new KRS(mhs, mk, rs.getDouble("nilai")));
                    }
                }
            }
            System.out.println("[DB Log] Sinkronisasi data live dari pgAdmin BERHASIL!");
        } catch (SQLException e) {
            System.out.println("Error sinkronisasi database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ============================================================
    // MENU KHUSUS JURUSAN
    // ============================================================
    static void menuJurusan() {
        System.out.println("\n--- MENU JURUSAN ---");
        System.out.println("1. Tampilkan Daftar Jurusan");
        System.out.println("2. Tambah Jurusan Baru");
        System.out.print("Pilih : ");
        int pil = sc.nextInt();

        if (pil == 1) {
            System.out.println("\n=== DAFTAR JURUSAN ===");
            if (daftarjurusan.isEmpty()) {
                System.out.println("(Belum ada data jurusan di database)");
            } {
                for (int i = 0; i < daftarjurusan.size(); i++) {
                    System.out.println((i + 1) + ". " + daftarjurusan.get(i).getKodeJurusan() + " | " + daftarjurusan.get(i).getNama());
                }
            }
        } 
        else if (pil == 2) {
            sc.nextLine(); // clear buffer
            System.out.print("Masukkan Kode Jurusan (Misal: IF, SI) : ");
            String kode = sc.nextLine().toUpperCase().trim();
            System.out.print("Masukkan Nama Jurusan Baru : ");
            String nama = sc.nextLine().trim();

            if(kode.isEmpty() || nama.isEmpty()) {
                System.out.println("Kode atau Nama tidak boleh kosong!");
                return;
            }

            String sql = "INSERT INTO jurusan_v2 (kode_jurusan, nama) VALUES (?, ?)";

            try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, kode);
                pstmt.setString(2, nama);
                pstmt.executeUpdate();

                System.out.println("Sukses menyimpan data jurusan baru ke pgAdmin!");
                sinkronisasiDataDariDB(); 
            } catch (SQLException e) {
                System.out.println("Gagal insert jurusan: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // MENU TAMBAH MAHASISWA
    // ============================================================
    static void menuMahasiswa() {
        System.out.println("\n1. Tambah Mahasiswa Ke Database");
        System.out.print("Pilih : ");
        int pil = sc.nextInt();

        if (pil == 1) {
            sc.nextLine(); // clear buffer
            System.out.print("NIM : ");
            String nim = sc.nextLine().trim();
            System.out.print("Nama : ");
            String nama = sc.nextLine().trim();
            System.out.print("Email : ");
            String email = sc.nextLine().trim();
            System.out.print("Tahun Masuk (Format: YYYY-MM-DD, misal 2024-09-01) : ");
            String masuk = sc.nextLine().trim();

            if (daftarjurusan.isEmpty()) {
                System.out.println("Gagal: Harus menambahkan Jurusan terlebih dahulu di Menu 8!");
                return;
            }

            // Tampilkan list jurusan agar tidak salah input FK
            System.out.println("Pilihan Jurusan Tersedia:");
            for (int i = 0; i < daftarjurusan.size(); i++) {
                System.out.println((i + 1) + ". " + daftarjurusan.get(i).getKodeJurusan() + " - " + daftarjurusan.get(i).getNama());
            }
            System.out.print("Pilih Nomor Jurusan : ");
            int pilJur = sc.nextInt() - 1;
            
            if (pilJur < 0 || pilJur >= daftarjurusan.size()) {
                System.out.println("Pilihan jurusan tidak valid!");
                return;
            }
            String kodeJur = daftarjurusan.get(pilJur).getKodeJurusan();

            String sql = "INSERT INTO user_v2 (nomor_induk, email, password, status, type, nama, tanggal_masuk, kode_jurusan) VALUES (?, ?, 'password', 'AKTIF', 'MAHASISWA', ?, CAST(? AS DATE), ?)";

            try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nim);
                pstmt.setString(2, email);
                pstmt.setString(3, nama);
                pstmt.setString(4, masuk);
                pstmt.setString(5, kodeJur);
                pstmt.executeUpdate();

                System.out.println("Sukses menyimpan data mahasiswa baru ke pgAdmin!");
                sinkronisasiDataDariDB(); 
            } catch (SQLException e) {
                System.out.println("Gagal insert mahasiswa: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // MENU TAMBAH MATA KULIAH
    // ============================================================
    static void menuMatakuliah() {
        sc.nextLine(); // clear buffer
        System.out.print("Kode MK : ");
        String kode = sc.nextLine().trim();
        System.out.print("Nama MK : ");
        String nama = sc.nextLine().trim();
        System.out.print("Jumlah SKS : ");
        int sks = sc.nextInt();

        if (daftarjurusan.isEmpty()) {
            System.out.println("Gagal: Harus menambahkan Jurusan terlebih dahulu di Menu 8!");
            return;
        }

        System.out.println("Pilihan Jurusan Pengampu:");
        for (int i = 0; i < daftarjurusan.size(); i++) {
            System.out.println((i + 1) + ". " + daftarjurusan.get(i).getKodeJurusan());
        }
        System.out.print("Pilih Nomor Jurusan : ");
        int pilJur = sc.nextInt() - 1;
        
        if (pilJur < 0 || pilJur >= daftarjurusan.size()) {
            System.out.println("Pilihan jurusan tidak valid!");
            return;
        }
        String kodeJur = daftarjurusan.get(pilJur).getKodeJurusan();

        String sql = "INSERT INTO matakuliah_v2 (kode_matakuliah, nama, sks, kode_jurusan) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kode);
            pstmt.setString(2, nama);
            pstmt.setInt(3, sks);
            pstmt.setString(4, kodeJur);
            pstmt.executeUpdate();

            System.out.println("Sukses menyimpan data Mata Kuliah baru ke pgAdmin!");
            sinkronisasiDataDariDB();
        } catch (SQLException e) {
            System.out.println("Gagal insert mata kuliah: " + e.getMessage());
        }
    }

    // ============================================================
    // MENU TAMBAH INPUT KRS / NILAI
    // ============================================================
    static void menuKRS() {
        if (daftarmahasiswa.isEmpty() || daftarmatakuliah.isEmpty()) {
            System.out.println("Data mahasiswa atau mata kuliah masih kosong di database!");
            return;
        }

        tampilDataMahasiswa();
        System.out.print("Mahasiswa : ");
        int m = sc.nextInt() - 1;

        tampilDataMatakuliah();
        System.out.print("Matkul : ");
        int mk = sc.nextInt() - 1;

        if (m < 0 || m >= daftarmahasiswa.size() || mk < 0 || mk >= daftarmatakuliah.size()) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        System.out.print("Nilai : ");
        double nilai = sc.nextDouble();

        String sql = "INSERT INTO mahasiswa_matakuliah_v2 (nomor_induk, kode_matakuliah, nilai) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, daftarmahasiswa.get(m).getNomorInduk());
            pstmt.setString(2, daftarmatakuliah.get(mk).getKodeMatakuliah());
            pstmt.setDouble(3, nilai);
            pstmt.executeUpdate();

            System.out.println("Sukses menyimpan KRS baru ke pgAdmin!");
            sinkronisasiDataDariDB();
        } catch (SQLException e) {
            System.out.println("Gagal simpan KRS: " + e.getMessage());
        }
    }

    // ============================================================
    // MANAGEMENT MENU KARYAWAN
    // ============================================================
    static void menuKaryawan() {
        System.out.println("\n1. Tampilkan Karyawan");
        System.out.println("2. Hitung Gaji Karyawan");
        System.out.print("Pilih : ");
        int pil = sc.nextInt();

        if (pil == 1) {
            if (daftarkaryawan.isEmpty()) {
                System.out.println("(Belum ada data karyawan/dosen/staff di database)");
            } else {
                for (User k : daftarkaryawan) {
                    System.out.println(k.getNomorInduk() + " | " + k.getNama() + " [" + k.getType() + "]");
                }
            }
        } else if (pil == 2) {
            for (User k : daftarkaryawan) {
                System.out.println(k.getNomorInduk() + " - " + k.getNama() + " Gaji Terproses.");
            }
        }
    }

    static void hitungIPMahasiswa() {
        if(daftarmahasiswa.isEmpty()){
            System.out.println("Belum ada data mahasiswa.");
            return;
        }
        tampilDataMahasiswa();
        System.out.print("Pilih : ");
        int pilih = sc.nextInt() - 1;

        if (pilih >= 0 && pilih < daftarmahasiswa.size()) {
            Mahasiswa m = daftarmahasiswa.get(pilih);
            System.out.println("IP " + m.getNama() + " = " + m.hitungIP());
        }
    }

    static void listBerdasarkanMahasiswa() {
        if(daftarmahasiswa.isEmpty()){
            System.out.println("Belum ada data mahasiswa.");
            return;
        }
        tampilDataMahasiswa();
        System.out.print("Pilih : ");
        int pilih = sc.nextInt() - 1;
        
        if (pilih >= 0 && pilih < daftarmahasiswa.size()) {
            Mahasiswa m = daftarmahasiswa.get(pilih);
            boolean ada = false;
            for (KRS k : daftarkrs) {
                if (k.getMhs().getNomorInduk().equals(m.getNomorInduk())) {
                    System.out.println(k.getMatkul().getNama() + " Nilai : " + k.getNilai());
                    ada = true;
                }
            }
            if(!ada) System.out.println("Mahasiswa ini belum mengambil KRS.");
        }
    }

    static void listBerdasarkanMatakuliah() {
        if(daftarmatakuliah.isEmpty()){
            System.out.println("Belum ada data mata kuliah.");
            return;
        }
        tampilDataMatakuliah();
        System.out.print("Pilih : ");
        int pilih = sc.nextInt() - 1;
        
        if (pilih >= 0 && pilih < daftarmatakuliah.size()) {
            Matakuliah mk = daftarmatakuliah.get(pilih);
            boolean ada = false;
            for (KRS k : daftarkrs) {
                if (k.getMatkul().getKodeMatakuliah().equals(mk.getKodeMatakuliah())) {
                    System.out.println(k.getMhs().getNama());
                    ada = true;
                }
            }
            if(!ada) System.out.println("Belum ada mahasiswa yang mengambil mata kuliah ini.");
        }
    }

    static void tampilDataMahasiswa() {
        for (int i = 0; i < daftarmahasiswa.size(); i++) {
            System.out.println((i + 1) + ". " + daftarmahasiswa.get(i).getNama());
        }
    }

    static void tampilDataMatakuliah() {
        for (int i = 0; i < daftarmatakuliah.size(); i++) {
            System.out.println((i + 1) + ". " + daftarmatakuliah.get(i).getNama());
        }
    }

    // ============================================================
    // FUNGSI PENCARIAN UTILITY LOKAL
    // ============================================================
    private static Jurusan cariJurusanLokal(String kode) {
        if (kode == null) return null;
        for (Jurusan j : daftarjurusan) {
            if (j.getKodeJurusan().equalsIgnoreCase(kode.trim())) return j;
        }
        return null;
    }

    private static Mahasiswa cariMahasiswaLokal(String nim) {
        if (nim == null) return null;
        for (Mahasiswa m : daftarmahasiswa) {
            if (m.getNomorInduk().equalsIgnoreCase(nim.trim())) return m;
        }
        return null;
    }

    private static Matakuliah cariMatkulLokal(String kode) {
        if (kode == null) return null;
        for (Matakuliah mk : daftarmatakuliah) {
            if (mk.getKodeMatakuliah().equalsIgnoreCase(kode.trim())) return mk;
        }
        return null;
    }
}