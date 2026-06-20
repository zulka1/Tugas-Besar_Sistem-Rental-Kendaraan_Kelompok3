package SistemRentalKendaraan.presentation;

import SistemRentalKendaraan.application.LaporanService;
import SistemRentalKendaraan.domain.model.Transaksi;
import SistemRentalKendaraan.domain.model.enums.StatusTransaksi;
import SistemRentalKendaraan.domain.repository.TransaksiRepository;
import SistemRentalKendaraan.infrastructure.JsonTransaksiRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OwnerDashboard {
    private final LaporanService laporanService;

    public OwnerDashboard() {
        TransaksiRepository transaksiRepository =
            new JsonTransaksiRepository();

        this.laporanService =
            new LaporanService(transaksiRepository);
    }

    public void showMenu() {
        while (true) {
            ConsoleHelper.printHeader("DASHBOARD - OWNER");

            System.out.println(
                "1. Lihat Laporan Pendapatan & Riwayat"
            );
            System.out.println("0. Logout");

            String pilihan =
                ConsoleHelper.getInput("Pilihan Anda > ");

            if (pilihan.equals("1")) {
                tampilkanLaporan();

            } else if (pilihan.equals("0")) {
                System.out.println(
                    "[SUKSES] Logout berhasil."
                );
                break;

            } else {
                System.out.println(
                    "[GAGAL] Pilihan tidak valid."
                );

                ConsoleHelper.getInput(
                    "Tekan ENTER untuk kembali..."
                );
            }
        }
    }

    private void tampilkanLaporan() {
        ConsoleHelper.printHeader(
            "LAPORAN RIWAYAT & PENDAPATAN"
        );

        List<Transaksi> daftarTransaksi =
            laporanService.lihatSemuaRiwayat();

        if (daftarTransaksi.isEmpty()) {
            System.out.println(
                "Belum ada riwayat transaksi."
            );

            ConsoleHelper.getInput(
                "Tekan ENTER untuk kembali..."
            );

            return;
        }

        System.out.println(
            "---------------------------------------------------------------------------------------------------"
        );

        System.out.printf(
            "| %-13s | %-20s | %-15s | %-10s | %-19s |%n",
            "ID Transaksi",
            "Pelanggan",
            "Kendaraan",
            "Status",
            "Total Tagihan"
        );

        System.out.println(
            "---------------------------------------------------------------------------------------------------"
        );

        for (Transaksi transaksi : daftarTransaksi) {
            String totalTagihan;

            if (transaksi.getStatus()
                    == StatusTransaksi.SELESAI) {

                totalTagihan = formatRupiah(
                    transaksi.getTotalTagihan()
                );

            } else {
                totalTagihan = "-";
            }

            System.out.printf(
                "| %-13s | %-20s | %-15s | %-10s | %-19s |%n",
                aman(transaksi.getIdTransaksi()),
                potongTeks(
                    aman(transaksi.getNamaPelanggan()),
                    20
                ),
                potongTeks(
                    aman(transaksi.getPlatNomor()),
                    15
                ),
                transaksi.getStatus(),
                totalTagihan
            );
        }

        System.out.println(
            "---------------------------------------------------------------------------------------------------"
        );

        System.out.println(
            "Transaksi Berjalan : "
            + laporanService
                .hitungJumlahTransaksiBerjalan()
        );

        System.out.println(
            "Transaksi Selesai  : "
            + laporanService
                .hitungJumlahTransaksiSelesai()
        );

        System.out.println(
            "TOTAL PENDAPATAN "
            + "(Hanya dari Transaksi Selesai): "
            + formatRupiah(
                laporanService
                    .hitungTotalPendapatan()
            )
        );

        System.out.println(
            "==================================================================================================="
        );

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private String formatRupiah(double jumlah) {
        NumberFormat formatter =
            NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("id-ID")
            );

        return formatter
            .format(jumlah)
            .replace(",00", "")
            .replace("Rp", "Rp ");
    }

    private String aman(String teks) {
        return teks == null ? "-" : teks;
    }

    private String potongTeks(
            String teks,
            int panjangMaksimum) {

        if (teks.length() <= panjangMaksimum) {
            return teks;
        }

        return teks.substring(
            0,
            panjangMaksimum - 3
        ) + "...";
    }
}