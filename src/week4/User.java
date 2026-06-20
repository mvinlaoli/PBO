package week4;

import week4.enumsv2.UserStatusv2;
import week4.enumsv2.UserTypev2;

public class User {
    protected String nomorInduk;
    protected String email;
    protected String password;
    protected UserStatusv2 status;
    protected UserTypev2 type;
    protected String nama;
    protected String tanggalMasuk;
    protected String tanggalKeluar;

    // Constructor Utama
    public User(String nomorInduk, String email, String password, UserStatusv2 status, UserTypev2 type, String nama, String tanggalMasuk, String tanggalKeluar) {
        this.nomorInduk = nomorInduk;
        this.email = email;
        this.password = password;
        this.status = status;
        this.type = type;
        this.nama = nama;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
    }

    // Getter dan Setter
    public String getNomorInduk() { return nomorInduk; }
    public void setNomorInduk(String nomorInduk) { this.nomorInduk = nomorInduk; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserStatusv2 getStatus() { return status; }
    public void setStatus(UserStatusv2 status) { this.status = status; }

    public UserTypev2 getType() { return type; }
    public void setType(UserTypev2 type) { this.type = type; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(String tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }

    public String getTanggalKeluar() { return tanggalKeluar; }
    public void setTanggalKeluar(String tanggalKeluar) { this.tanggalKeluar = tanggalKeluar; }
}