package shoppinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class DemoShoppingListSpringBootApplication implements CommandLineRunner
{
    @Autowired
    private DaftarBelanjaRepo repo;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoShoppingListSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("Membaca Semua Record DaftarBelanja:");
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db : all) {
            System.out.println("[" + db.getId() + "] " + db.getJudul());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t" + barang.getNamaBarang() + " " + barang.getByk() + " " + barang.getSatuan());
            }
        }
        
        Scanner keyb = new Scanner(System.in);

        // Pilih menu
        System.out.println("Masukan Menu : \n1.Baca objek berdasarkan ID \n2.DaftarBelanja berdasarkan Judul \n3.Menyimpan DaftarBelanja ke database \n4.Update DaftarBelanja ke databse \n5.Menghapus berdasarkan ID");
        int menu = Integer.parseInt(keyb.nextLine());
        switch(menu) {
            case 1 :
                // Baca berdasarkan ID
                System.out.print("Masukkan ID dari objek DaftarBelanja yg ingin ditampilkan : ");
                long id = Long.parseLong(keyb.nextLine());
                System.out.println("Hasil pencarian: ");

                Optional<DaftarBelanja> optDB = repo.findById(id);
                if (optDB.isPresent()) {
                    DaftarBelanja db = optDB.get();
                    System.out.println("\tJudul: " + db.getJudul());
                } else {
                    System.out.println("\tTIDAK DITEMUKAN.");
                }
            case 2 :
                // DaftarBelanja berdasarkan Judul
                System.out.println("Masukan Judul dari objek DaftarBelanja yg ingin ditampilkan : ");
                String judul = keyb.nextLine();
                List<DaftarBelanja> listDB = repo.findByJudulLike(judul + "%");
                System.out.println("Hasil pencarian: ");
                if (listDB.size() > 0) {
                    for (DaftarBelanja db : listDB) {
                        System.out.println(db.toString());
                    }
                } else {
                    System.out.println("Tidak ditemukan");
                }
            case 3 :
                // Menyimpan DaftarBelanja ke database
                DaftarBelanja db = new DaftarBelanja();
                System.out.println("Masukan Judul dari objek DaftarBelanja yg ingin ditampilkan : ");
                judul = keyb.nextLine();
                db.setJudul(judul);
                db.setTanggal(LocalDateTime.now());

                System.out.println("Masukan jumlah barang yang ditambah kedatabase : ");
                int jmllist = keyb.nextInt();
                keyb.nextLine();
                DaftarBelanjaDetil detil = new DaftarBelanjaDetil();
                int noUrut = detil.getNoUrut();
                for (int i=0; i<jmllist; i++){
                    System.out.println("Nama Barang : ");
                    String namaBarang = keyb.nextLine();
                    System.out.println("Jumlah Barang : ");
                    int jumlahBarang = keyb.nextInt();
                    System.out.println("Satuan Barang : ");
                    String satuan = keyb.nextLine();
                    keyb.nextLine();
                    System.out.println("Memo : ");
                    String memo = keyb.nextLine();

                    detil.setNoUrut(noUrut);
                    detil.setNamaBarang(namaBarang);
                    detil.setByk(jumlahBarang);
                    detil.setSatuan(satuan);
                    detil.setMemo(memo);
                    noUrut++;
                }
            case 4 :
                // Update DaftarBelanja le database
                System.out.println("Masukan ID : ");
                id = Long.parseLong(keyb.nextLine());
            case 5 :
                System.out.println("Delete DaftarBelanja dari database");
                System.out.println("Masukan ID : ");
                id = Long.parseLong(keyb.nextLine());
                optDB = repo.findById(id);
                if (optDB.isPresent()) {
                    DaftarBelanja listShopping = optDB.get();
                   List<DaftarBelanjaDetil> daftarBarangDetil = listShopping.getDaftarBarang();
                   for(DaftarBelanjaDetil belanjaDetil : daftarBarangDetil){

                   }
                }
        }
    }
}
