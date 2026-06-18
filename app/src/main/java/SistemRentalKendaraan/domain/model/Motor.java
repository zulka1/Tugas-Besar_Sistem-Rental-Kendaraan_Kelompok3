package com.SistemRentalKendaraan.domain.model;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String platNomor, double hargaSewa, String jenisTransmisi) {
        super(platNomor, hargaSewa);
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public String getAtributKhusus() {
        return jenisTransmisi;
    }
}