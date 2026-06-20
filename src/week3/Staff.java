package week3;

public class Staff extends Karyawan{


    double gajiPokok;


    public Staff(
        String email,
        String password,
        String NIK,
        double gajiPokok
    ){

        super(email,password,
        TypeUser.STAFF,
        NIK);


        this.gajiPokok=gajiPokok;

    }


    public double hitungGaji(){

        return gajiPokok;

    }


}