package com.SistemRentalKendaraan.application;

import com.SistemRentalKendaraan.domain.model.Kendaraan;
import com.SistemRentalKendaraan.domain.model.enums.StatusKendaraan;
import com.SistemRentalKendaraan.repository.KendaraanRepository;
import java.util.List;

public class KendaraanService {
    private KendaraanRepository kendaraanRepository;

    public KendaraanService(KendaraanRepository kendaraanRepository) {
        this.kendaraanRepository = kendaraanRepository;
    }

    public void tambahKendaraan(Kendaraan kendaraan) throws Exception {
        Kendaraan kendaraanExist = kendaraanRepository.cariBerdasarkanPlat(kendaraan.getPlatNomor());
        if (kendaraanExist != null) {
            throw new Exception("Plat Nomor sudah terdaftar!");
        }
        kendaraanRepository.simpan(kendaraan);
    }

    public List<Kendaraan> lihatSemuaKendaraan() {
        return kendaraanRepository.ambilSemua();
    }

    public void hapusKendaraan(String platNomor) throws Exception {
        Kendaraan kendaraan = kendaraanRepository.cariBerdasarkanPlat(platNomor);
        
        if (kendaraan == null) {
            throw new Exception("Kendaraan dengan plat nomor tersebut tidak ditemukan.");
        }
        
        if (kendaraan.getStatus() == StatusKendaraan.SEDANG_DISEWA) {
            throw new Exception("Kendaraan masih berstatus SEDANG DISEWA, data tidak dapat dihapus!");
        }
        
        kendaraanRepository.hapus(platNomor);
    }
}