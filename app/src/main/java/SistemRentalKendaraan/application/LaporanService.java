package SistemRentalKendaraan.application;

import SistemRentalKendaraan.domain.model.Transaksi;
import SistemRentalKendaraan.domain.model.enums.StatusTransaksi;
import SistemRentalKendaraan.domain.repository.TransaksiRepository;

import java.util.List;

public class LaporanService {
    private final TransaksiRepository transaksiRepository;

    public LaporanService(TransaksiRepository transaksiRepository) {
        this.transaksiRepository = transaksiRepository;
    }

    /**
     * Mengambil seluruh transaksi, baik yang masih berjalan
     * maupun yang sudah selesai.
     */
    public List<Transaksi> lihatSemuaRiwayat() {
        return transaksiRepository.findAll();
    }

    /**
     * Menghitung total pendapatan hanya dari transaksi
     * yang sudah selesai.
     */
    public double hitungTotalPendapatan() {
        return transaksiRepository.findAll()
            .stream()
            .filter(transaksi ->
                transaksi.getStatus() == StatusTransaksi.SELESAI
            )
            .mapToDouble(Transaksi::getTotalTagihan)
            .sum();
    }

    public int hitungJumlahTransaksiBerjalan() {
        return (int) transaksiRepository.findAll()
            .stream()
            .filter(transaksi ->
                transaksi.getStatus() == StatusTransaksi.BERJALAN
            )
            .count();
    }

    public int hitungJumlahTransaksiSelesai() {
        return (int) transaksiRepository.findAll()
            .stream()
            .filter(transaksi ->
                transaksi.getStatus() == StatusTransaksi.SELESAI
            )
            .count();
    }
}