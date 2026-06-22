package SistemRentalKendaraan.presentation;

import SistemRentalKendaraan.application.KendaraanService;
import SistemRentalKendaraan.application.SopirService;
import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.model.Mobil;
import SistemRentalKendaraan.domain.model.Motor;
import SistemRentalKendaraan.domain.model.Sopir;
import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;
import SistemRentalKendaraan.domain.repository.KendaraanRepository;
import SistemRentalKendaraan.domain.repository.SopirRepository;
import SistemRentalKendaraan.infrastructure.JsonKendaraanRepository;
import SistemRentalKendaraan.infrastructure.JsonSopirRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdminDashboard {
    private final KendaraanService kendaraanService;
    private final SopirService sopirService;

    public AdminDashboard() {
        // Instansiasi internal agar LoginView dan Main tidak perlu diubah
        KendaraanRepository kendaraanRepository = new JsonKendaraanRepository();
        this.kendaraanService = new KendaraanService(kendaraanRepository);
        SopirRepository sopirRepository = new JsonSopirRepository();
        this.sopirService = new SopirService(sopirRepository);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("           DASHBOARD - ADMIN            ");
            System.out.println("========================================");
            System.out.println("Selamat Datang, Admin!");
            System.out.println("Silahkan pilih menu:");
            System.out.println("1. Tambah Kendaraan Baru");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("4. Tambah Sopir Baru");
            System.out.println("5. Lihat Semua Sopir");
            System.out.println("0. Logout");
            System.out.println("----------------------------------------");
            
            String pilihan = ConsoleHelper.getInput("Pilihan Anda > ");
            if (pilihan.equals("1")) {
                menuTambahKendaraan();
            } else if (pilihan.equals("2")) {
                menuLihatKendaraan();
            } else if (pilihan.equals("3")) {
                menuHapusKendaraan();
            } else if (pilihan.equals("4")) {
                menuTambahSopir();
            } else if (pilihan.equals("5")) {
                menuLihatSopir();
            } else if (pilihan.equals("0")) {
                System.out.println("[SUKSES] Berhasil logout dari Admin.");
                break;
            } else {
                System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void menuTambahKendaraan() {
        ConsoleHelper.printHeader("MENU TAMBAH KENDARAAN BARU");
        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        
        String pilihan = ConsoleHelper.getInput("Pilihan Anda > ");
        if (pilihan.equals("0")) return;
        if (!pilihan.equals("1") && !pilihan.equals("2")) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        String plat = ConsoleHelper.getInput("Masukkan Plat Nomor      : ");
        if (plat.isEmpty()) {
            System.out.println("Plat nomor tidak boleh kosong!");
            return;
        }

        double harga;
        try {
            harga = Double.parseDouble(ConsoleHelper.getInput("Masukkan Harga Sewa/Hari : "));
        } catch (NumberFormatException e) {
            System.out.println("Harga sewa harus berupa angka!");
            return;
        }

        String merk = ConsoleHelper.getInput("Masukkan Merk Kendaraan  : ");
        String infoKhusus = ConsoleHelper.getInput(pilihan.equals("1") ? "Masukkan Info Tambahan   : " : "Masukkan Jenis Transmisi : ");

        try {
            Kendaraan k;
            if (pilihan.equals("1")) {
                k = new Mobil(plat, merk, harga, StatusKendaraan.TERSEDIA, infoKhusus);
            } else {
                k = new Motor(plat, merk, harga, StatusKendaraan.TERSEDIA, infoKhusus);
            }
            kendaraanService.tambahKendaraan(k);
            System.out.println("\n[SUKSES] " + k.getJenis() + " dengan plat " + plat + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
        } catch (Exception e) {
            System.out.println("\n[GAGAL] " + e.getMessage());
        }
        ConsoleHelper.getInput("\nTekan ENTER untuk kembali ke menu utama...");
    }

    private void menuLihatKendaraan() {
        ConsoleHelper.printHeader("DAFTAR SELURUH KENDARAAN");
        List<Kendaraan> daftar = kendaraanService.lihatSemuaKendaraan();
        if (daftar.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
        } else {
            System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-12s | %-8s | %-15s | %-12s | %-22s | %-13s |\n", 
                "Plat Nomor", "Jenis", "Harga/Hari", "Merek", "Info Tambahan", "Status");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            for (Kendaraan k : daftar) {
                System.out.printf("| %-12s | %-8s | %-15s | %-12s | %-22s | %-13s |\n",
                    k.getPlatNomor(),
                    k.getJenis(),
                    formatRupiah(k.getHargaSewaPerHari()),
                    k.getMerk(),
                    k.getInfoTambahan(),
                    k.getStatus()
                );
            }
            System.out.println("-------------------------------------------------------------------------------------------------------");
        }
        ConsoleHelper.getInput("\nTekan ENTER untuk kembali ke menu utama...");
    }

    private void menuHapusKendaraan() {
        ConsoleHelper.printHeader("MENU HAPUS KENDARAAN");
        System.out.println("(ketik 0 untuk kembali)");
        String plat = ConsoleHelper.getInput("Masukkan Plat Nomor yang ingin dihapus : ");
        if (plat.equals("0") || plat.isEmpty()) return;

        try {
            kendaraanService.hapusKendaraan(plat);
            System.out.println("\n[SUKSES] Kendaraan " + plat + " berhasil dihapus dari sistem.");
        } catch (Exception e) {
            System.out.println("\n[GAGAL] " + e.getMessage());
        }
        ConsoleHelper.getInput("\nTekan ENTER untuk kembali ke menu utama...");
    }

    private String formatRupiah(double amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        return nf.format(amount).replace(",00", "").replace("Rp", "Rp ");
    }

    private void menuTambahSopir() {
        ConsoleHelper.printHeader("MENU TAMBAH SOPIR BARU");
        String nama = ConsoleHelper.getInput("Masukkan Nama Sopir     : ");
        if (nama.isEmpty()) {
            System.out.println("Nama sopir tidak boleh kosong!");
            return;
        }
        String noTelp = ConsoleHelper.getInput("Masukkan No Telepon      : ");
        if (noTelp.isEmpty()) {
            System.out.println("Nomor telepon tidak boleh kosong!");
            return;
        }

        try {
            Sopir sopir = new Sopir();
            sopir.setNama(nama);
            sopir.setNoTelepon(noTelp);
            sopir.setBiayaPerHari(150000.0); // Biaya default sesuai peta file
            
            sopirService.tambahSopir(sopir);
            System.out.println("\n[SUKSES] Sopir " + nama + " berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("\n[GAGAL] " + e.getMessage());
        }
        ConsoleHelper.getInput("\nTekan ENTER untuk kembali ke menu utama...");
    }

    private void menuLihatSopir() {
        ConsoleHelper.printHeader("DAFTAR SELURUH SOPIR");
        List<Sopir> daftar = sopirService.lihatSemuaSopir();
        if (daftar.isEmpty()) {
            System.out.println("Data sopir masih kosong.");
        } else {
            System.out.println("----------------------------------------------------------------------");
            System.out.printf("| %-10s | %-20s | %-15s | %-12s | %-10s |\n", 
                "ID Sopir", "Nama", "No Telepon", "Biaya/Hari", "Status");
            System.out.println("----------------------------------------------------------------------");
            for (Sopir s : daftar) {
                System.out.printf("| %-10s | %-20s | %-15s | %-12s | %-10s |\n",
                    s.getIdSopir(),
                    s.getNama(),
                    s.getNoTelepon(),
                    formatRupiah(s.getBiayaPerHari()),
                    s.getStatus()
                );
            }
            System.out.println("----------------------------------------------------------------------");
        }
        ConsoleHelper.getInput("\nTekan ENTER untuk kembali ke menu utama...");
    }
}