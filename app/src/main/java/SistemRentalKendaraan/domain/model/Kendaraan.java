package SistemRentalKendaraan.domain.model;

import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;

public abstract class Kendaraan {
    private String platNomor;
    private String merk;
    private double hargaSewaPerHari;
    private StatusKendaraan status;

    public Kendaraan() {}

    public Kendaraan(String platNomor, String merk, double hargaSewaPerHari, StatusKendaraan status) {
        this.platNomor = platNomor;
        this.merk = merk;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.status = status;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public void setHargaSewaPerHari(double hargaSewaPerHari) {
        this.hargaSewaPerHari = hargaSewaPerHari;
    }

    public StatusKendaraan getStatus() {
        return status;
    }

    public void setStatus(StatusKendaraan status) {
        this.status = status;
    }

    public abstract String getJenis();
    public abstract String getInfoTambahan();
    public abstract double hitungDendaPerHari();
}
