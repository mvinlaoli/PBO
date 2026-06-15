package week2;

import java.util.ArrayList;
import java.util.Scanner;

public class Appp {


    static Scanner sc = new Scanner(System.in);


    static ArrayList<Mahasiswa> daftarmahasiswa = new ArrayList<>();
    static ArrayList<Matakuliah> daftarmatakuliah = new ArrayList<>();
    static ArrayList<KRS> daftarkrs = new ArrayList<>();

    static ArrayList<Karyawan> daftarkaryawan = new ArrayList<>();



    public static void main(String[] args) {


        dataDummy();


        int pilihan;


        do{


            System.out.println("\n===== MENU UTAMA =====");

            System.out.println("1. Mata Kuliah");
            System.out.println("2. Mahasiswa");
            System.out.println("3. KRS");
            System.out.println("4. List Mata Kuliah");
            System.out.println("5. List Mahasiswa");
            System.out.println("6. Menu Karyawan");
            System.out.println("7. Hitung IP Mahasiswa");
            System.out.println("0. Keluar");


            System.out.print("Pilih : ");

            pilihan=sc.nextInt();



            switch(pilihan){


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


            }



        }while(pilihan!=0);



    }





    static void dataDummy(){


        Mahasiswa m1 =
        new Mahasiswa(
        "1124035",
        "Jolvin",
        true,
        2024,
        2028);


        Mahasiswa m2 =
        new Mahasiswa(
        "1124036",
        "Mychle",
        true,
        2024,
        2028);



        daftarmahasiswa.add(m1);
        daftarmahasiswa.add(m2);



        Matakuliah mk1 =
        new Matakuliah(
        "IF101",
        "Algoritma",
        true);



        Matakuliah mk2 =
        new Matakuliah(
        "IF102",
        "PBO",
        true);



        daftarmatakuliah.add(mk1);
        daftarmatakuliah.add(mk2);



        daftarkrs.add(
        new KRS(
        m1,
        mk1,
        90));



        daftarkaryawan.add(
        new DosenTetap(
        "dosen@gmail.com",
        "123",
        "D001",
        5000000,
        100000,
        10));



        daftarkaryawan.add(
        new Staff(
        "staff@gmail.com",
        "123",
        "S001",
        3000000));



        daftarkaryawan.add(
        new DosenHonorer(
        "honor@gmail.com",
        "123",
        "H001",
        150000,
        8));


    }






    static void menuMahasiswa(){


        System.out.println("\n1. Tambah");

        int pil=sc.nextInt();



        if(pil==1){


            sc.nextLine();


            System.out.print("NIM : ");
            String nim=sc.nextLine();



            System.out.print("Nama : ");
            String nama=sc.nextLine();



            System.out.print("Tahun Masuk : ");
            int masuk=sc.nextInt();



            System.out.print("Tahun Lulus : ");
            int lulus=sc.nextInt();



            daftarmahasiswa.add(

            new Mahasiswa(
            nim,
            nama,
            true,
            masuk,
            lulus)

            );


        }



    }






    static void menuMatakuliah(){


        sc.nextLine();


        System.out.print("Kode : ");
        String kode=sc.nextLine();


        System.out.print("Nama : ");
        String nama=sc.nextLine();



        daftarmatakuliah.add(
        new Matakuliah(
        kode,
        nama,
        true));



    }






    static void menuKRS(){


        tampilDataMahasiswa();

        System.out.print("Mahasiswa : ");

        int m=sc.nextInt()-1;



        tampilDataMatakuliah();

        System.out.print("Matkul : ");

        int mk=sc.nextInt()-1;



        System.out.print("Nilai : ");

        double nilai=sc.nextDouble();



        daftarkrs.add(

        new KRS(
        daftarmahasiswa.get(m),
        daftarmatakuliah.get(mk),
        nilai)

        );



    }







    static void menuKaryawan(){



        System.out.println("\n1. Tampilkan");

        System.out.println("2. Tambah");

        System.out.println("3. Hitung Gaji");


        int pil=sc.nextInt();



        if(pil==1){


            for(Karyawan k:daftarkaryawan){


                System.out.println(
                k.NIK+
                " | "+
                k.type);


            }



        }



        else if(pil==2){


            System.out.println(
            "1 Dosen Tetap");

            System.out.println(
            "2 Dosen Honorer");

            System.out.println(
            "3 Staff");


            int jenis=sc.nextInt();



            if(jenis==1){


                daftarkaryawan.add(
                new DosenTetap(
                "dt@gmail.com",
                "123",
                "DT01",
                5000000,
                100000,
                5));


            }



            else if(jenis==2){


                daftarkaryawan.add(
                new DosenHonorer(
                "dh@gmail.com",
                "123",
                "DH01",
                200000,
                6));


            }



            else{


                daftarkaryawan.add(
                new Staff(
                "st@gmail.com",
                "123",
                "ST01",
                3500000));


            }


        }



        else if(pil==3){



            for(Karyawan k:daftarkaryawan){


                System.out.println(

                k.NIK+
                " Gaji = "+
                k.hitungGaji()

                );


            }



        }




    }






    static void hitungIPMahasiswa(){



        tampilDataMahasiswa();



        System.out.print(
        "Pilih : ");



        int pilih=sc.nextInt()-1;



        Mahasiswa m =
        daftarmahasiswa.get(pilih);



        System.out.println(

        "IP "+m.nama+
        " = "+
        m.hitungIP()

        );


    }







    static void listBerdasarkanMahasiswa(){


        tampilDataMahasiswa();

        int pilih=sc.nextInt()-1;


        Mahasiswa m =
        daftarmahasiswa.get(pilih);



        for(KRS k:daftarkrs){


            if(k.mhs==m){


                System.out.println(
                k.matkul.nama+
                " Nilai : "+
                k.nilai);


            }


        }



    }






    static void listBerdasarkanMatakuliah(){



        tampilDataMatakuliah();


        int pilih=sc.nextInt()-1;


        Matakuliah mk =
        daftarmatakuliah.get(pilih);



        for(KRS k:daftarkrs){


            if(k.matkul==mk){


                System.out.println(
                k.mhs.nama);

            }

        }


    }







    static void tampilDataMahasiswa(){


        for(int i=0;i<daftarmahasiswa.size();i++){


            System.out.println(
            (i+1)+". "+
            daftarmahasiswa.get(i).nama);


        }


    }






    static void tampilDataMatakuliah(){


        for(int i=0;i<daftarmatakuliah.size();i++){


            System.out.println(
            (i+1)+". "+
            daftarmatakuliah.get(i).nama);


        }


    }



}