package week2;

public class DosenTetap extends Karyawan{

    double gajiPokok;
    double honorSKS;
    int jumlahSKS;


    public DosenTetap(
        String email,
        String password,
        String NIK,
        double gajiPokok,
        double honorSKS,
        int jumlahSKS
    ){

        super(email,password,
        TypeUser.DOSEN_TETAP,
        NIK);


        this.gajiPokok=gajiPokok;
        this.honorSKS=honorSKS;
        this.jumlahSKS=jumlahSKS;

    }


    public double hitungGaji(){

        return gajiPokok + (honorSKS * jumlahSKS);

    }

}