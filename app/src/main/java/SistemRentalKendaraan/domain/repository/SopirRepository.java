package SistemRentalKendaraan.domain.repository;

import SistemRentalKendaraan.domain.model.Sopir;
import java.util.List;
import java.util.Optional;

public interface SopirRepository {
    void save(Sopir sopir);
    List<Sopir> findAll();
    Optional<Sopir> findById(String idSopir);
    void update(Sopir sopir);
    String generateNextId();
}
