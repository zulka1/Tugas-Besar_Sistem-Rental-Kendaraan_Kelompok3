package SistemRentalKendaraan.presentation;

import SistemRentalKendaraan.application.PelangganService;
import SistemRentalKendaraan.application.SopirService;
import SistemRentalKendaraan.application.TransaksiService;
import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.model.Pelanggan;
import SistemRentalKendaraan.domain.model.Sopir;
import SistemRentalKendaraan.domain.model.Transaksi;
import SistemRentalKendaraan.domain.repository.KendaraanRepository;
import SistemRentalKendaraan.domain.repository.PelangganRepository;
import SistemRentalKendaraan.domain.repository.SopirRepository;
import SistemRentalKendaraan.domain.repository.TransaksiRepository;
import SistemRentalKendaraan.infrastructure.JsonKendaraanRepository;
import SistemRentalKendaraan.infrastructure.JsonPelangganRepository;
import SistemRentalKendaraan.infrastructure.JsonSopirRepository;
import SistemRentalKendaraan.infrastructure.JsonTransaksiRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class StaffDashboard {
    private final PelangganService pelangganService;
    private final TransaksiService transaksiService;
    private final SopirService sopirService;

    public StaffDashboard() {
        PelangganRepository pelangganRepository =
            new JsonPelangganRepository();

        KendaraanRepository kendaraanRepository =
            new JsonKendaraanRepository();

        TransaksiRepository transaksiRepository =
            new JsonTransaksiRepository();

        SopirRepository sopirRepository =
            new JsonSopirRepository();

        this.pelangganService =
            new PelangganService(pelangganRepository);

        this.transaksiService = new TransaksiService(
            transaksiRepository,
            kendaraanRepository,
            pelangganRepository,
            sopirRepository
        );

        this.sopirService = new SopirService(sopirRepository);
    }

    public void showMenu() {
        while (true) {
            ConsoleHelper.printHeader("DASHBOARD - STAFF");

            System.out.println("1. Daftar Pelanggan Baru");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Cek Kendaraan Tersedia");
            System.out.println("4. Proses Peminjaman (Sewa)");
            System.out.println("5. Proses Pengembalian");
            System.out.println("6. Hapus Pelanggan");
            System.out.println("0. Logout");

            String pilihan =
                ConsoleHelper.getInput("Pilihan Anda > ");

            if (pilihan.equals("1")) {
                menuDaftarPelanggan();

            } else if (pilihan.equals("2")) {
                menuCariPelanggan();

            } else if (pilihan.equals("3")) {
                menuKendaraanTersedia();

            } else if (pilihan.equals("4")) {
                menuPeminjaman();

            } else if (pilihan.equals("5")) {
                menuPengembalian();

            } else if (pilihan.equals("6")) {
                menuHapusPelanggan();

            } else if (pilihan.equals("0")) {
                System.out.println("[SUKSES] Logout berhasil.");
                break;

            } else {
                System.out.println("Pilihan tidak valid.");
                ConsoleHelper.getInput(
                    "Tekan ENTER untuk kembali..."
                );
            }
        }
    }

    private void menuDaftarPelanggan() {
        ConsoleHelper.printHeader(
            "MENU PENDAFTARAN PELANGGAN"
        );

        String nomorKtp = ConsoleHelper.getInput(
            "Masukkan Nomor KTP   : "
        );

        String namaLengkap = ConsoleHelper.getInput(
            "Masukkan Nama Lengkap: "
        );

        pelangganService.daftarPelanggan(
            nomorKtp,
            namaLengkap
        );

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void menuCariPelanggan() {
        ConsoleHelper.printHeader(
            "MENU PENCARIAN PELANGGAN"
        );

        String nomorKtp = ConsoleHelper.getInput(
            "Masukkan Nomor KTP: "
        );

        Pelanggan pelanggan =
            pelangganService.cariPelanggan(nomorKtp);

        if (pelanggan != null) {
            System.out.println("\n[DATA DITEMUKAN]");
            pelanggan.tampilkanInfo();
        }

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void menuHapusPelanggan() {
        ConsoleHelper.printHeader("HAPUS PELANGGAN");

        String nomorKtp = ConsoleHelper.getInput(
            "Masukkan Nomor KTP: "
        );

        pelangganService.hapusPelanggan(nomorKtp);

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void menuKendaraanTersedia() {
        ConsoleHelper.printHeader(
            "DAFTAR KENDARAAN TERSEDIA"
        );

        List<Kendaraan> daftar =
            transaksiService.lihatKendaraanTersedia();

        if (daftar.isEmpty()) {
            System.out.println(
                "Tidak ada kendaraan yang tersedia."
            );

        } else {
            System.out.println(
                "-------------------------------------------------------------------------------------------------------"
            );

            System.out.printf(
                "| %-12s | %-8s | %-15s | %-12s | %-22s | %-13s |%n",
                "Plat Nomor",
                "Jenis",
                "Harga/Hari",
                "Merek",
                "Info Tambahan",
                "Status"
            );

            System.out.println(
                "-------------------------------------------------------------------------------------------------------"
            );

            for (Kendaraan kendaraan : daftar) {
                System.out.printf(
                    "| %-12s | %-8s | %-15s | %-12s | %-22s | %-13s |%n",
                    kendaraan.getPlatNomor(),
                    kendaraan.getJenis(),
                    formatRupiah(
                        kendaraan.getHargaSewaPerHari()
                    ),
                    kendaraan.getMerk(),
                    kendaraan.getInfoTambahan(),
                    kendaraan.getStatus()
                );
            }

            System.out.println(
                "-------------------------------------------------------------------------------------------------------"
            );
        }

        System.out.println(
            "*Kendaraan yang sedang disewa tidak ditampilkan."
        );

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void menuPeminjaman() {
        ConsoleHelper.printHeader(
            "MENU PEMINJAMAN KENDARAAN"
        );

        System.out.println(
            "(ketik 0 pada Nomor KTP untuk kembali)"
        );

        String nomorKtp = ConsoleHelper.getInput(
            "Masukkan Nomor KTP Pelanggan : "
        );

        if (nomorKtp.equals("0")) {
            return;
        }

        String platNomor = ConsoleHelper.getInput(
            "Masukkan Plat Nomor Kendaraan: "
        );

        int durasiSewa;

        try {
            durasiSewa = Integer.parseInt(
                ConsoleHelper.getInput(
                    "Rencana Durasi Sewa (Hari)  : "
                )
            );

        } catch (NumberFormatException e) {
            System.out.println(
                "[GAGAL] Durasi sewa harus berupa angka."
            );

            ConsoleHelper.getInput(
                "Tekan ENTER untuk kembali..."
            );

            return;
        }

        String opsiSopir = ConsoleHelper.getInput(
            "Apakah ingin menggunakan layanan Sopir? (y/n): "
        );

        String idSopir = null;
        if (opsiSopir.equalsIgnoreCase("y")) {
            List<Sopir> sopirTersedia = sopirService.lihatSopirTersedia();
            if (sopirTersedia.isEmpty()) {
                System.out.println("Maaf, tidak ada sopir yang tersedia saat ini.");
                System.out.println("Peminjaman akan dilanjutkan tanpa sopir (lepas kunci).");
                ConsoleHelper.getInput("Tekan ENTER untuk melanjutkan...");
            } else {
                System.out.println("\n--- DAFTAR SOPIR TERSEDIA ---");
                System.out.println("----------------------------------------------");
                System.out.printf("| %-10s | %-20s | %-12s |\n", "ID Sopir", "Nama", "Biaya/Hari");
                System.out.println("----------------------------------------------");
                for (Sopir s : sopirTersedia) {
                    System.out.printf("| %-10s | %-20s | %-12s |\n",
                        s.getIdSopir(), s.getNama(), formatRupiah(s.getBiayaPerHari()));
                }
                System.out.println("----------------------------------------------");
                idSopir = ConsoleHelper.getInput("Pilih ID Sopir: ");
            }
        }

        try {
            System.out.println(
                "\nMemproses transaksi..."
            );

            Transaksi transaksi =
                transaksiService.prosesPeminjaman(
                    nomorKtp,
                    platNomor,
                    durasiSewa,
                    idSopir
                );

            tampilkanStrukPeminjaman(transaksi);

            System.out.println(
                "[SUKSES] Transaksi berhasil dicatat. "
                + "Status kendaraan berubah menjadi "
                + "SEDANG_DISEWA."
            );

        } catch (Exception e) {
            System.out.println(
                "[GAGAL] " + e.getMessage()
            );
        }

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void menuPengembalian() {
        ConsoleHelper.printHeader(
            "MENU PENGEMBALIAN KENDARAAN"
        );

        System.out.println(
            "(ketik 0 untuk kembali)"
        );

        String idAtauPlat = ConsoleHelper.getInput(
            "Masukkan ID Transaksi/Plat Nomor: "
        );

        if (idAtauPlat.equals("0")) {
            return;
        }

        int hariKeterlambatan;

        try {
            hariKeterlambatan = Integer.parseInt(
                ConsoleHelper.getInput(
                    "Durasi Keterlambatan "
                    + "(Hari, 0 jika tepat waktu): "
                )
            );

        } catch (NumberFormatException e) {
            System.out.println(
                "[GAGAL] Hari keterlambatan "
                + "harus berupa angka."
            );

            ConsoleHelper.getInput(
                "Tekan ENTER untuk kembali..."
            );

            return;
        }

        try {
            System.out.println(
                "\nMenghitung tagihan..."
            );

            Transaksi transaksi =
                transaksiService.prosesPengembalian(
                    idAtauPlat,
                    hariKeterlambatan
                );

            tampilkanStrukPengembalian(transaksi);

            System.out.println(
                "[SUKSES] Pengembalian berhasil. "
                + "Status kendaraan kembali menjadi "
                + "TERSEDIA."
            );

        } catch (Exception e) {
            System.out.println(
                "[GAGAL] " + e.getMessage()
            );
        }

        ConsoleHelper.getInput(
            "Tekan ENTER untuk kembali..."
        );
    }

    private void tampilkanStrukPeminjaman(
            Transaksi transaksi) {

        System.out.println(
            "\n--- STRUK PEMINJAMAN SEMENTARA ---"
        );

        System.out.println(
            "ID Transaksi   : "
            + transaksi.getIdTransaksi()
        );

        System.out.println(
            "Nama Pelanggan : "
            + transaksi.getNamaPelanggan()
        );

        System.out.println(
            "Kendaraan      : "
            + transaksi.getJenisKendaraan()
            + " ("
            + transaksi.getPlatNomor()
            + ")"
        );

        System.out.println(
            "Durasi Sewa    : "
            + transaksi.getDurasiSewa()
            + " Hari"
        );

        System.out.println(
            "Estimasi Biaya : "
            + formatRupiah(
                transaksi.getBiayaDasar()
            )
        );

        System.out.println(
            "----------------------------------"
        );
    }

    private void tampilkanStrukPengembalian(
            Transaksi transaksi) {

        System.out.println(
            "\n--- STRUK TAGIHAN AKHIR ---"
        );

        System.out.println(
            "ID Transaksi : "
            + transaksi.getIdTransaksi()
        );

        System.out.println(
            "Pelanggan    : "
            + transaksi.getNamaPelanggan()
        );

        System.out.println(
            "Kendaraan    : "
            + transaksi.getJenisKendaraan()
            + " ("
            + transaksi.getPlatNomor()
            + ")"
        );

        System.out.println(
            "Biaya Dasar  : "
            + formatRupiah(
                transaksi.getBiayaDasar()
            )
            + " ("
            + transaksi.getDurasiSewa()
            + " Hari)"
        );

        System.out.println(
            "Denda Telat  : "
            + formatRupiah(
                transaksi.getDendaKeterlambatan()
            )
            + " ("
            + transaksi.getHariKeterlambatan()
            + " Hari)"
        );

        if (transaksi.getBiayaSopir() > 0) {
            System.out.println(
                "Biaya Sopir  : "
                + formatRupiah(
                    transaksi.getBiayaSopir()
                )
            );
        }

        System.out.println(
            "---------------------------------- +"
        );

        System.out.println(
            "TOTAL BAYAR  : "
            + formatRupiah(
                transaksi.getTotalTagihan()
            )
        );

        System.out.println(
            "----------------------------------"
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
}