package com.SistemRentalKendaraan.repository;

import com.SistemRentalKendaraan.domain.model.Kendaraan;
import java.util.List;

public interface KendaraanRepository {
    void simpan(Kendaraan kendaraan);
    List<Kendaraan> ambilSemua();
    Kendaraan cariBerdasarkanPlat(String platNomor);
    void hapus(String platNomor);
}