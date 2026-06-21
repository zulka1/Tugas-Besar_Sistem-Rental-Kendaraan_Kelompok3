package SistemRentalKendaraan.application;

import SistemRentalKendaraan.domain.model.Sopir;
import SistemRentalKendaraan.domain.model.enums.StatusSopir;
import SistemRentalKendaraan.domain.repository.SopirRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SopirService {
    private final SopirRepository sopirRepository;

    public SopirService(SopirRepository sopirRepository) {
        this.sopirRepository = sopirRepository;
    }

    public void tambahSopir(Sopir sopir) throws Exception {
        if (sopir.getNama() == null || sopir.getNama().trim().isEmpty()) {
            throw new Exception("Nama sopir tidak boleh kosong.");
        }
        if (sopir.getNoTelepon() == null || sopir.getNoTelepon().trim().isEmpty()) {
            throw new Exception("Nomor telepon tidak boleh kosong.");
        }
        sopir.setIdSopir(sopirRepository.generateNextId());
        sopir.setStatus(StatusSopir.TERSEDIA);
        sopirRepository.save(sopir);
    }

    public List<Sopir> lihatSemuaSopir() {
        return sopirRepository.findAll();
    }

    public List<Sopir> lihatSopirTersedia() {
        return sopirRepository.findAll().stream()
            .filter(s -> s.getStatus() == StatusSopir.TERSEDIA)
            .collect(Collectors.toList());
    }

    public Optional<Sopir> getSopirById(String idSopir) {
        return sopirRepository.findById(idSopir);
    }
}
