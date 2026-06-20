package SistemRentalKendaraan.presentation;

import SistemRentalKendaraan.application.PelangganService;
import SistemRentalKendaraan.domain.model.Pelanggan;
import SistemRentalKendaraan.domain.repository.PelangganRepository;
import SistemRentalKendaraan.infrastructure.JsonPelangganRepository;

public class StaffDashboard {
    private PelangganService pelangganService;
    public StaffDashboard() {
        PelangganRepository pelangganRepository = new JsonPelangganRepository();
        this.pelangganService = new PelangganService(pelangganRepository);
    }

    public void showMenu() {
        while (true) {
            ConsoleHelper.printHeader("DASHBOARD STAFF");
            System.out.println("1. Transaksi Peminjaman");
            System.out.println("2. Transaksi Pengembalian");
            System.out.println("3. Kelola Pelanggan");
            System.out.println("0. Logout");
            String pilihan = ConsoleHelper.getInput("Pilih menu: ");

            if (pilihan.equals("1")) {

            } else if (pilihan.equals("2")) {

            } else if (pilihan.equals("3")) {
                menuKelolaPelanggan();
            } else if (pilihan.equals("0")) {
                System.out.println("Logout berhasil");
                break;
            } else {
                System.out.println("Pilihan tidak valid");
                ConsoleHelper.getInput("Tekan ENTER untuk kembali...");
            }
        }
    }

    private void menuKelolaPelanggan() {
        while (true) {
            ConsoleHelper.printHeader("KELOLA PELANGGAN");
            System.out.println("1. Daftar Pelanggan Baru");
            System.out.println("2. Cari Pelanggan Berdasarkan KTP");
            System.out.println("3. Hapus Pelanggan");
            System.out.println("0. Kembali");
            String pilihan = ConsoleHelper.getInput("Pilih menu: ");

            if (pilihan.equals("1")) {
                menuDaftarPelanggan();
            } else if (pilihan.equals("2")) {
                menuCariPelanggan();
            } else if (pilihan.equals("3")) {
                menuHapusPelanggan();
            } else if (pilihan.equals("0")) {
                break;
            } else {
                System.out.println("Pilihan tidak valid");
                ConsoleHelper.getInput("Tekan ENTER untuk kembali...");
            }
        }
    }

    private void menuDaftarPelanggan() {
        ConsoleHelper.printHeader("DAFTAR PELANGGAN BARU");
        String namaLengkap = ConsoleHelper.getInput("Nama      : ");
        String nomorKtp = ConsoleHelper.getInput("Nomor KTP : ");
        pelangganService.daftarPelanggan(nomorKtp, namaLengkap);
        ConsoleHelper.getInput("Tekan ENTER untuk kembali...");
    }

    private void menuCariPelanggan() {
        ConsoleHelper.printHeader("CARI PELANGGAN");
        String nomorKtp = ConsoleHelper.getInput("Masukkan Nomor KTP: ");
        Pelanggan pelanggan = pelangganService.cariPelanggan(nomorKtp);
        if (pelanggan != null) {
            System.out.println();
            pelanggan.tampilkanInfo();
        }
        ConsoleHelper.getInput("Tekan ENTER untuk kembali...");
    }

    private void menuHapusPelanggan() {
        ConsoleHelper.printHeader("HAPUS PELANGGAN");
        String nomorKtp = ConsoleHelper.getInput("Masukkan Nomor KTP: ");
        pelangganService.hapusPelanggan(nomorKtp);
        ConsoleHelper.getInput("Tekan ENTER untuk kembali...");
    }
}