package SistemRentalKendaraan.application;

import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.model.Pelanggan;
import SistemRentalKendaraan.domain.model.Sopir;
import SistemRentalKendaraan.domain.model.Transaksi;
import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;
import SistemRentalKendaraan.domain.model.enums.StatusSopir;
import SistemRentalKendaraan.domain.model.enums.StatusTransaksi;
import SistemRentalKendaraan.domain.repository.KendaraanRepository;
import SistemRentalKendaraan.domain.repository.PelangganRepository;
import SistemRentalKendaraan.domain.repository.SopirRepository;
import SistemRentalKendaraan.domain.repository.TransaksiRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransaksiService {
    private final TransaksiRepository transaksiRepository;
    private final KendaraanRepository kendaraanRepository;
    private final PelangganRepository pelangganRepository;
    private final SopirRepository sopirRepository;

    public TransaksiService(
            TransaksiRepository transaksiRepository,
            KendaraanRepository kendaraanRepository,
            PelangganRepository pelangganRepository,
            SopirRepository sopirRepository) {

        this.transaksiRepository = transaksiRepository;
        this.kendaraanRepository = kendaraanRepository;
        this.pelangganRepository = pelangganRepository;
        this.sopirRepository = sopirRepository;
    }

    public List<Kendaraan> lihatKendaraanTersedia() {
        return kendaraanRepository.findAll()
            .stream()
            .filter(kendaraan ->
                kendaraan.getStatus() == StatusKendaraan.TERSEDIA
            )
            .collect(Collectors.toList());
    }

    public Transaksi prosesPeminjaman(
            String nomorKtp,
            String platNomor,
            int durasiSewa,
            String idSopir) throws Exception {

        if (nomorKtp == null || nomorKtp.trim().isEmpty()) {
            throw new Exception("Nomor KTP tidak boleh kosong.");
        }

        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new Exception("Plat nomor tidak boleh kosong.");
        }

        if (durasiSewa <= 0) {
            throw new Exception(
                "Durasi sewa harus lebih dari 0 hari."
            );
        }

        Pelanggan pelanggan =
            pelangganRepository.findByKtp(nomorKtp.trim());

        if (pelanggan == null) {
            throw new Exception(
                "Data pelanggan tidak ditemukan. "
                + "Daftarkan pelanggan terlebih dahulu."
            );
        }

        Optional<Kendaraan> kendaraanOptional =
            kendaraanRepository.findByPlatNomor(platNomor.trim());

        if (kendaraanOptional.isEmpty()) {
            throw new Exception(
                "Kendaraan dengan plat nomor tersebut tidak ditemukan."
            );
        }

        Kendaraan kendaraan = kendaraanOptional.get();

        if (kendaraan.getStatus() != StatusKendaraan.TERSEDIA) {
            throw new Exception(
                "Kendaraan sedang disewa dan tidak dapat dipinjam."
            );
        }

        double biayaSopir = 0;
        Sopir sopir = null;
        if (idSopir != null && !idSopir.trim().isEmpty()) {
            Optional<Sopir> sopirOpt = sopirRepository.findById(idSopir.trim());
            if (sopirOpt.isEmpty()) {
                throw new Exception("Sopir dengan ID tersebut tidak ditemukan.");
            }
            sopir = sopirOpt.get();
            if (sopir.getStatus() != StatusSopir.TERSEDIA) {
                throw new Exception("Sopir sedang bertugas dan tidak tersedia.");
            }
            biayaSopir = sopir.getBiayaPerHari() * durasiSewa;
        }

        double biayaDasar =
            kendaraan.getHargaSewaPerHari() * durasiSewa;

        Transaksi transaksi = new Transaksi(
            transaksiRepository.generateNextId(),
            pelanggan.getNomorKtp(),
            pelanggan.getNamaLengkap(),
            kendaraan.getPlatNomor(),
            kendaraan.getJenis(),
            durasiSewa,
            kendaraan.getHargaSewaPerHari(),
            biayaDasar,
            StatusTransaksi.BERJALAN
        );

        if (sopir != null) {
            transaksi.setIdSopir(sopir.getIdSopir());
            transaksi.setBiayaSopir(biayaSopir);
            transaksi.setTotalTagihan(biayaDasar + biayaSopir);
        }

        kendaraan.setStatus(StatusKendaraan.SEDANG_DISEWA);
        kendaraanRepository.update(kendaraan);

        if (sopir != null) {
            sopir.setStatus(StatusSopir.BERTUGAS);
            sopirRepository.update(sopir);
        }

        try {
            transaksiRepository.save(transaksi);
        } catch (RuntimeException e) {
            // Jika transaksi gagal disimpan, kembalikan status kendaraan dan sopir.
            kendaraan.setStatus(StatusKendaraan.TERSEDIA);
            kendaraanRepository.update(kendaraan);

            if (sopir != null) {
                sopir.setStatus(StatusSopir.TERSEDIA);
                sopirRepository.update(sopir);
            }

            throw new Exception(
                "Transaksi gagal disimpan: " + e.getMessage(),
                e
            );
        }

        return transaksi;
    }

    public Transaksi prosesPengembalian(
            String idAtauPlat,
            int hariKeterlambatan) throws Exception {

        if (idAtauPlat == null || idAtauPlat.trim().isEmpty()) {
            throw new Exception(
                "ID transaksi atau plat nomor tidak boleh kosong."
            );
        }

        if (hariKeterlambatan < 0) {
            throw new Exception(
                "Hari keterlambatan tidak boleh negatif."
            );
        }

        String kataKunci = idAtauPlat.trim();

        Optional<Transaksi> transaksiOptional =
            transaksiRepository.findById(kataKunci);

        if (transaksiOptional.isEmpty()) {
            transaksiOptional =
                transaksiRepository.findBerjalanByPlatNomor(kataKunci);
        }

        if (transaksiOptional.isEmpty()) {
            throw new Exception(
                "Transaksi berjalan tidak ditemukan."
            );
        }

        Transaksi transaksi = transaksiOptional.get();

        if (transaksi.getStatus() == StatusTransaksi.SELESAI) {
            throw new Exception(
                "Transaksi tersebut sudah selesai."
            );
        }

        Optional<Kendaraan> kendaraanOptional =
            kendaraanRepository.findByPlatNomor(
                transaksi.getPlatNomor()
            );

        if (kendaraanOptional.isEmpty()) {
            throw new Exception(
                "Data kendaraan pada transaksi tidak ditemukan."
            );
        }

        Kendaraan kendaraan = kendaraanOptional.get();

        /*
         * Polymorphism:
         * Mobil menghasilkan denda Rp50.000 per hari.
         * Motor menghasilkan denda Rp20.000 per hari.
         */
        double dendaKeterlambatan =
            kendaraan.hitungDendaPerHari() * hariKeterlambatan;

        double totalTagihan =
            transaksi.getBiayaDasar()
            + transaksi.getBiayaSopir()
            + dendaKeterlambatan;

        transaksi.setHariKeterlambatan(hariKeterlambatan);
        transaksi.setDendaKeterlambatan(dendaKeterlambatan);
        transaksi.setTotalTagihan(totalTagihan);
        transaksi.setStatus(StatusTransaksi.SELESAI);

        kendaraan.setStatus(StatusKendaraan.TERSEDIA);
        kendaraanRepository.update(kendaraan);

        Sopir sopir = null;
        if (transaksi.getIdSopir() != null) {
            Optional<Sopir> sopirOpt = sopirRepository.findById(transaksi.getIdSopir());
            if (sopirOpt.isPresent()) {
                sopir = sopirOpt.get();
                sopir.setStatus(StatusSopir.TERSEDIA);
                sopirRepository.update(sopir);
            }
        }

        try {
            transaksiRepository.update(transaksi);
        } catch (RuntimeException e) {
            // Jika transaksi gagal diperbarui, kembalikan status kendaraan dan sopir.
            kendaraan.setStatus(StatusKendaraan.SEDANG_DISEWA);
            kendaraanRepository.update(kendaraan);

            if (sopir != null) {
                sopir.setStatus(StatusSopir.BERTUGAS);
                sopirRepository.update(sopir);
            }

            throw new Exception(
                "Pengembalian gagal disimpan: " + e.getMessage(),
                e
            );
        }

        return transaksi;
    }

    public List<Transaksi> lihatSemuaTransaksi() {
        return transaksiRepository.findAll();
    }
}