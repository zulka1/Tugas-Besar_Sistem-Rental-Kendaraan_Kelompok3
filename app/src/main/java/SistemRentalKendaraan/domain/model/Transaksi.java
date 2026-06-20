package SistemRentalKendaraan.domain.model;

import SistemRentalKendaraan.domain.model.enums.StatusTransaksi;

public class Transaksi {
    private String idTransaksi;
    private String nomorKtp;
    private String namaPelanggan;
    private String platNomor;
    private String jenisKendaraan;
    private int durasiSewa;
    private double hargaSewaPerHari;
    private double biayaDasar;
    private int hariKeterlambatan;
    private double dendaKeterlambatan;

    // Disiapkan untuk integrasi Epic 6 Layanan Sopir.
    private String idSopir;
    private double biayaSopir;

    private double totalTagihan;
    private StatusTransaksi status;

    public Transaksi() {
        // Constructor kosong diperlukan Gson saat membaca JSON.
    }

    public Transaksi(
            String idTransaksi,
            String nomorKtp,
            String namaPelanggan,
            String platNomor,
            String jenisKendaraan,
            int durasiSewa,
            double hargaSewaPerHari,
            double biayaDasar,
            StatusTransaksi status) {

        this.idTransaksi = idTransaksi;
        this.nomorKtp = nomorKtp;
        this.namaPelanggan = namaPelanggan;
        this.platNomor = platNomor;
        this.jenisKendaraan = jenisKendaraan;
        this.durasiSewa = durasiSewa;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.biayaDasar = biayaDasar;

        this.hariKeterlambatan = 0;
        this.dendaKeterlambatan = 0;

        this.idSopir = null;
        this.biayaSopir = 0;

        this.totalTagihan = biayaDasar;
        this.status = status;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public void setNomorKtp(String nomorKtp) {
        this.nomorKtp = nomorKtp;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public int getDurasiSewa() {
        return durasiSewa;
    }

    public void setDurasiSewa(int durasiSewa) {
        this.durasiSewa = durasiSewa;
    }

    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public void setHargaSewaPerHari(double hargaSewaPerHari) {
        this.hargaSewaPerHari = hargaSewaPerHari;
    }

    public double getBiayaDasar() {
        return biayaDasar;
    }

    public void setBiayaDasar(double biayaDasar) {
        this.biayaDasar = biayaDasar;
    }

    public int getHariKeterlambatan() {
        return hariKeterlambatan;
    }

    public void setHariKeterlambatan(int hariKeterlambatan) {
        this.hariKeterlambatan = hariKeterlambatan;
    }

    public double getDendaKeterlambatan() {
        return dendaKeterlambatan;
    }

    public void setDendaKeterlambatan(double dendaKeterlambatan) {
        this.dendaKeterlambatan = dendaKeterlambatan;
    }

    public String getIdSopir() {
        return idSopir;
    }

    public void setIdSopir(String idSopir) {
        this.idSopir = idSopir;
    }

    public double getBiayaSopir() {
        return biayaSopir;
    }

    public void setBiayaSopir(double biayaSopir) {
        this.biayaSopir = biayaSopir;
    }

    public double getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(double totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public StatusTransaksi getStatus() {
        return status;
    }

    public void setStatus(StatusTransaksi status) {
        this.status = status;
    }
}