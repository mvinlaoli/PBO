package week2;

public class DosenHonorer extends Karyawan{


    double honorSKS;
    int jumlahSKS;


    public DosenHonorer(
        String email,
        String password,
        String NIK,
        double honorSKS,
        int jumlahSKS
    ){

        super(email,password,
        TypeUser.DOSEN_HONORER,
        NIK);


        this.honorSKS=honorSKS;
        this.jumlahSKS=jumlahSKS;

    }


    public double hitungGaji(){

        return honorSKS * jumlahSKS;

    }


}