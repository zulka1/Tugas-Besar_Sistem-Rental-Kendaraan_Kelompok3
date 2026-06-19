package com.SistemRentalKendaraan.domain.model;

import com.SistemRentalKendaraan.domain.model.enums.StatusKendaraan;

public abstract class Kendaraan {
    private String platNomor;
    private double hargaSewa;
    private StatusKendaraan status;

    public Kendaraan(String platNomor, double hargaSewa) {
        this.platNomor = platNomor;
        this.hargaSewa = hargaSewa;
        this.status = StatusKendaraan.TERSEDIA;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public StatusKendaraan getStatus() {
        return status;
    }

    public void setStatus(StatusKendaraan status) {
        this.status = status;
    }

    public abstract String getJenis();
    public abstract String getAtributKhusus();
}