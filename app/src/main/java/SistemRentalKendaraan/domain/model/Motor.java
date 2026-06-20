package SistemRentalKendaraan.domain.model;

import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;

public class Motor extends Kendaraan {
    private String jenisTransmisi; // Contoh: "Manual" atau "Matic"

    public Motor() {
        super();
    }

    public Motor(String platNomor, String merk, double hargaSewaPerHari, StatusKendaraan status, String jenisTransmisi) {
        super(platNomor, merk, hargaSewaPerHari, status);
        this.jenisTransmisi = jenisTransmisi;
    }

    public String getJenisTransmisi() {
        return jenisTransmisi;
    }

    public void setJenisTransmisi(String jenisTransmisi) {
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public String getInfoTambahan() {
        return jenisTransmisi;
    }

    @Override
    public double hitungDendaPerHari() {
        return 20000.0;
    }
}
