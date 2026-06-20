package SistemRentalKendaraan.domain.repository;

import SistemRentalKendaraan.domain.model.Transaksi;

import java.util.List;
import java.util.Optional;

public interface TransaksiRepository {
    void save(Transaksi transaksi);

    List<Transaksi> findAll();

    Optional<Transaksi> findById(String idTransaksi);

    Optional<Transaksi> findBerjalanByPlatNomor(String platNomor);

    void update(Transaksi transaksi);

    String generateNextId();
}