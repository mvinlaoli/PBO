package week2;


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

        double total=0;
        int jumlah=0;


        for(KRS k : Appp.daftarkrs){

            if(k.mhs == this){

                total += k.nilai;
                jumlah++;

            }

        }


        if(jumlah==0)
            return 0;


        return total/jumlah;

    }



    public void print(){

        System.out.println(
        nim+" | "+nama
        );

    }


}