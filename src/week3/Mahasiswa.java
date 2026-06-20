package week3;


public class Mahasiswa extends User{


    String nim;
    String nama;
    boolean status;

    int tahunmasuk;
    int tahunkelulusan;



    public Mahasiswa(
        String nim,
        String nama,
        boolean status,
        int tahunmasuk,
        int tahunkelulusan
    ){

        super(
        "",
        "",
        TypeUser.MAHASISWA
        );


        this.nim=nim;
        this.nama=nama;
        this.status=status;
        this.tahunmasuk=tahunmasuk;
        this.tahunkelulusan=tahunkelulusan;

    }



    public double hitungIP(){

    double totalBobot = 0;
    int jumlahMK = 0;


    for(KRS k : Appp.daftarkrs){


        if(k.mhs == this){


            double bobot = 0;


            if(k.nilai >= 85){
                bobot = 4.0;
            }
            else if(k.nilai >= 80){
                bobot = 3.5;
            }
            else if(k.nilai >= 70){
                bobot = 3.0;
            }
            else if(k.nilai >= 65){
                bobot = 2.5;
            }
            else if(k.nilai >= 60){
                bobot = 2.0;
            }
            else{
                bobot = 0;
            }



            totalBobot += bobot;

            jumlahMK++;


        }

    }



    if(jumlahMK == 0){

        return 0;

    }



    return totalBobot / jumlahMK;

}


}