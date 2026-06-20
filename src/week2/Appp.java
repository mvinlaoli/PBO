package week2;


import java.util.ArrayList;
import java.util.Scanner;



public class Appp {


static Scanner sc = new Scanner(System.in);



static ArrayList<Mahasiswa> mahasiswaList
        = new ArrayList<>();


static ArrayList<Matakuliah> matkulList
        = new ArrayList<>();


static ArrayList<KRS> krsList
        = new ArrayList<>();





public static void main(String[] args){



dataDummy();



int pilih;



do{


System.out.println("\n===== MENU =====");

System.out.println("1. Mata Kuliah");

System.out.println("2. Mahasiswa");

System.out.println("3. Mahasiswa - Mata Kuliah");

System.out.println("4. List Berdasarkan Mata Kuliah");

System.out.println("5. List Berdasarkan Mahasiswa");

System.out.println("0. Keluar");



System.out.print("Pilih : ");

pilih=sc.nextInt();




switch(pilih){


case 1:
    menuMatkul();
    break;



case 2:
    menuMahasiswa();
    break;



case 3:
    menuRelasi();
    break;



case 4:
    listMatkul();
    break;



case 5:
    listMahasiswa();
    break;


}



}while(pilih!=0);



}









static void dataDummy(){



Mahasiswa m1 =
new Mahasiswa(
"1124035",
"Jolvin",
true);



Mahasiswa m2 =
new Mahasiswa(
"1124036",
"Mychle",
true);



Mahasiswa m3 =
new Mahasiswa(
"1124037",
"Laoli",
false);



mahasiswaList.add(m1);
mahasiswaList.add(m2);
mahasiswaList.add(m3);





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



Matakuliah mk3 =
new Matakuliah(
"IF103",
"Basis Data",
false);



matkulList.add(mk1);
matkulList.add(mk2);
matkulList.add(mk3);





krsList.add(
new KRS(
m1,
mk1,
90));



krsList.add(
new KRS(
m1,
mk2,
85));



krsList.add(
new KRS(
m2,
mk1,
80));



}









static void menuMahasiswa(){



System.out.println("1. Tambah");

System.out.println("2. Edit");



int p=sc.nextInt();




if(p==1){


sc.nextLine();


System.out.print("NIM : ");

String nim=sc.nextLine();



System.out.print("Nama : ");

String nama=sc.nextLine();



mahasiswaList.add(

new Mahasiswa(
nim,
nama,
true)

);



}





else if(p==2){



tampilMahasiswa();


System.out.print("Pilih : ");

int i=sc.nextInt()-1;



sc.nextLine();


System.out.print("Nama baru : ");

mahasiswaList.get(i).nama=sc.nextLine();



}




}










static void menuMatkul(){



System.out.println("1. Tambah");

System.out.println("2. Edit");



int p=sc.nextInt();





if(p==1){


sc.nextLine();



System.out.print("Kode : ");

String kode=sc.nextLine();



System.out.print("Nama : ");

String nama=sc.nextLine();



matkulList.add(

new Matakuliah(
kode,
nama,
true)

);


}




else if(p==2){



tampilMatkul();


System.out.print("Pilih : ");

int i=sc.nextInt()-1;



sc.nextLine();



System.out.print("Nama baru : ");

matkulList.get(i).nama=sc.nextLine();



}




}










static void menuRelasi(){



System.out.println("1. Tambah");

System.out.println("2. Edit Nilai");



int p=sc.nextInt();





if(p==1){



tampilMahasiswa();



System.out.print("Pilih Mahasiswa : ");

int m=sc.nextInt()-1;





tampilMatkul();



System.out.print("Pilih Matkul : ");

int mk=sc.nextInt()-1;





Mahasiswa mahasiswa =
mahasiswaList.get(m);



Matakuliah matkul =
matkulList.get(mk);





if(!mahasiswa.status){


System.out.println(
"Mahasiswa tidak aktif");

return;


}



if(!matkul.status){


System.out.println(
"Matakuliah tidak aktif");

return;


}





System.out.print("Nilai : ");

double nilai=sc.nextDouble();





krsList.add(

new KRS(
mahasiswa,
matkul,
nilai)

);





System.out.println(
"Relasi berhasil");





}






else if(p==2){



for(int i=0;i<krsList.size();i++){


KRS k=krsList.get(i);



System.out.println(

(i+1)+". "
+k.mahasiswa.nama+
" - "+
k.matakuliah.nama+
" nilai "
+k.nilai);


}



System.out.print("Pilih : ");

int i=sc.nextInt()-1;



System.out.print("Nilai baru : ");

krsList.get(i).nilai=sc.nextDouble();



}





}









static void listMatkul(){



tampilMatkul();



System.out.print("Pilih : ");

int pilih=sc.nextInt()-1;



Matakuliah mk =
matkulList.get(pilih);





for(KRS k:krsList){



if(k.matakuliah==mk){



Utility.printNamaMatkul(k);



}



}





}










static void listMahasiswa(){



tampilMahasiswa();



System.out.print("Pilih : ");

int pilih=sc.nextInt()-1;



Mahasiswa m =
mahasiswaList.get(pilih);





for(KRS k:krsList){



if(k.mahasiswa==m){


Utility.printNimKode(k);


}



}





}









static void tampilMahasiswa(){



for(int i=0;i<mahasiswaList.size();i++){



System.out.println(

(i+1)+". "
+mahasiswaList.get(i).nama);


}



}







static void tampilMatkul(){



for(int i=0;i<matkulList.size();i++){



System.out.println(

(i+1)+". "
+matkulList.get(i).nama);


}



}



}