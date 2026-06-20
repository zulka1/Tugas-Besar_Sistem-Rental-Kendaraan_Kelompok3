package SistemRentalKendaraan.domain.model;

import SistemRentalKendaraan.domain.model.enums.StatusSopir;

public class Sopir {
    private String idSopir;
    private String nama;
    private String noTelepon;
    private double biayaPerHari;
    private StatusSopir status;

    public Sopir() {
        // Constructor kosong diperlukan untuk serialization/deserialization oleh Gson.
    }

    public Sopir(String idSopir, String nama, String noTelepon, double biayaPerHari, StatusSopir status) {
        this.idSopir = idSopir;
        this.nama = nama;
        this.noTelepon = noTelepon;
        this.biayaPerHari = biayaPerHari;
        this.status = status;
    }

    public String getIdSopir() {
        return idSopir;
    }

    public void setIdSopir(String idSopir) {
        this.idSopir = idSopir;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public double getBiayaPerHari() {
        return biayaPerHari;
    }

    public void setBiayaPerHari(double biayaPerHari) {
        this.biayaPerHari = biayaPerHari;
    }

    public StatusSopir getStatus() {
        return status;
    }

    public void setStatus(StatusSopir status) {
        this.status = status;
    }
}
