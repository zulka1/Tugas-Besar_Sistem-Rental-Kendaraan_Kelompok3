package SistemRentalKendaraan.application;

import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;
import SistemRentalKendaraan.domain.repository.KendaraanRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KendaraanService {
    private final KendaraanRepository kendaraanRepository;

    public KendaraanService(KendaraanRepository kendaraanRepository) {
        this.kendaraanRepository = kendaraanRepository;
    }

    public void tambahKendaraan(Kendaraan kendaraan) throws Exception {
        Optional<Kendaraan> exist = kendaraanRepository.findByPlatNomor(kendaraan.getPlatNomor());
        if (exist.isPresent()) {
            throw new Exception("Plat Nomor sudah terdaftar!");
        }
        kendaraan.setStatus(StatusKendaraan.TERSEDIA);
        kendaraanRepository.save(kendaraan);
    }

    public List<Kendaraan> lihatSemuaKendaraan() {
        return kendaraanRepository.findAll();
    }

    public List<Kendaraan> lihatKendaraanTersedia() {
        return kendaraanRepository.findAll().stream()
            .filter(k -> k.getStatus() == StatusKendaraan.TERSEDIA)
            .collect(Collectors.toList());
    }

    public void hapusKendaraan(String platNomor) throws Exception {
        Optional<Kendaraan> exist = kendaraanRepository.findByPlatNomor(platNomor);
        if (exist.isEmpty()) {
            throw new Exception("Kendaraan dengan plat nomor tersebut tidak ditemukan.");
        }
        
        Kendaraan k = exist.get();
        if (k.getStatus() == StatusKendaraan.SEDANG_DISEWA) {
            throw new Exception("Kendaraan masih berstatus SEDANG DISEWA, data tidak dapat dihapus!");
        }
        
        kendaraanRepository.delete(platNomor);
    }
}
