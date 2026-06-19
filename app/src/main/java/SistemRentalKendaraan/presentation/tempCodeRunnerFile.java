package SistemRentalKendaraan.presentation;

import com.SistemRentalKendaraan.application.KendaraanService;
import com.SistemRentalKendaraan.domain.model.Kendaraan;
import com.SistemRentalKendaraan.domain.model.Mobil;
import com.SistemRentalKendaraan.domain.model.Motor;
import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
    private KendaraanService kendaraanService;
    private Scanner scanner;

    public AdminDashboard(KendaraanService kendaraanService) {
        this.kendaraanService = kendaraanService;
        this.scanner = new Scanner(System.in);
    }
    
    public void showMenu() {
        ConsoleHelper.printHeader("DASHBOARD ADMIN");
        System.out.println("1. Kelola Sopir");
        System.out.println("2. Kelola Mobil");
        System.out.println("3. Kelola Motor");
        System.out.println("0. Logout");
        ConsoleHelper.getInput("Pilih menu: ");
    }
}

public void menuTambahKendaraan() {
        System.out.print("Pilih Jenis Kendaraan (1. Mobil, 2. Motor, 0. Kembali) > ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

int pilihan = scanner.nextInt();
        scanner.nextLine(); 

        if (pilihan == 0) return;

        System.out.print("Masukkan Plat Nomor : ");
        String plat = scanner.nextLine();
        
        System.out.print("Masukkan Harga Sewa/Hari : ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        try {
            if (pilihan == 1) {
                System.out.print("Masukkan Jumlah Pintu : ");
                int pintu = scanner.nextInt();
                scanner.nextLine();
                kendaraanService.tambahKendaraan(new Mobil(plat, harga, pintu));
            } else if (pilihan == 2) {
                System.out.print("Masukkan Jenis Transmisi (Manual/Matic) : ");
                String transmisi = scanner.nextLine();
                kendaraanService.tambahKendaraan(new Motor(plat, harga, transmisi));
            }
            System.out.println("[SUKSES] Kendaraan berhasil ditambahkan dengan status TERSEDIA.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }

    public void menuLihatKendaraan() {
        System.out.println("\n=========================================================================================");
        System.out.println("DAFTAR SELURUH KENDARAAN");
        System.out.println("=========================================================================================");
        
        List<Kendaraan> daftar = kendaraanService.lihatSemuaKendaraan();
        
        if (daftar == null || daftar.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            return;
        }

        System.out.printf("%-15s | %-10s | %-15s | %-15s | %-15s\n", 
            "Plat Nomor", "Jenis", "Harga/Hari", "Info Tambahan", "Status");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Kendaraan k : daftar) {
            System.out.printf("%-15s | %-10s | Rp%-13.0f | %-15s | %-15s\n", 
                k.getPlatNomor(), 
                k.getJenis(), 
                k.getHargaSewa(), 
                k.getAtributKhusus(), 
                k.getStatus().toString());
        }
    }

    public void menuHapusKendaraan() {
        System.out.println("\n==================================");
        System.out.println("MENU HAPUS KENDARAAN");
        System.out.println("==================================");
        System.out.print("Masukkan Plat Nomor yang ingin dihapus (ketik 0 untuk batal): ");
        
        String plat = scanner.nextLine();
        if (plat.equals("0")) return;

        try {
            kendaraanService.hapusKendaraan(plat);
            System.out.println("[SUKSES] Kendaraan " + plat + " berhasil dihapus dari sistem.");
        } catch (Exception e) {
            System.out.println("[GAGAL] " + e.getMessage());
        }
    }
}