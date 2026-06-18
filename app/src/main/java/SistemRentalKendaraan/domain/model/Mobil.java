package com.SistemRentalKendaraan.domain.model;

public class Mobil extends Kendaraan {
    private int jumlahPintu;

    public Mobil(String platNomor, double hargaSewa, int jumlahPintu) {
        super(platNomor, hargaSewa);
        this.jumlahPintu = jumlahPintu;
    }

    @Override
    public String getJenis() {
        return "Mobil";
    }

    @Override
    public String getAtributKhusus() {
        return jumlahPintu + " Pintu";
    }
}