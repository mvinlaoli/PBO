package week2;

public abstract class Karyawan extends User {
   String NIK;

    public Karyawan(
            String email,
            String password,
            TypeUser type,
            String NIK
    ){

        super(email,password,type);
        this.NIK = NIK;
    }


    public abstract double hitungGaji();
    
}
