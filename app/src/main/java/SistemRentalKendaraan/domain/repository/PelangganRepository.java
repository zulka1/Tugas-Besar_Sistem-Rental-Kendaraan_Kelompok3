package SistemRentalKendaraan.domain.repository;

import SistemRentalKendaraan.domain.model.Pelanggan;
import java.util.List;
public interface PelangganRepository {
    void saveAll(List<Pelanggan> daftarPelanggan);
    List<Pelanggan> findAll();
    Pelanggan findByKtp(String nomorKtp);
}   