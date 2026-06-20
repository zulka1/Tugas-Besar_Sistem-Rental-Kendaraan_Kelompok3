package SistemRentalKendaraan.domain.repository;

import SistemRentalKendaraan.domain.model.Kendaraan;
import java.util.List;
import java.util.Optional;

public interface KendaraanRepository {
    void save(Kendaraan kendaraan);
    List<Kendaraan> findAll();
    Optional<Kendaraan> findByPlatNomor(String platNomor);
    void delete(String platNomor);
    void update(Kendaraan kendaraan);
}
