package SistemRentalKendaraan.domain.model;

import SistemRentalKendaraan.domain.model.enums.StatusKendaraan;

public class Mobil extends Kendaraan {
    private String infoTambahan; // Contoh: "Manual, 7 seater" atau "Jumlah Pintu: 4"

    public Mobil() {
        super();
    }

    public Mobil(String platNomor, String merk, double hargaSewaPerHari, StatusKendaraan status, String infoTambahan) {
        super(platNomor, merk, hargaSewaPerHari, status);
        this.infoTambahan = infoTambahan;
    }

    public String getInfoTambahanField() {
        return infoTambahan;
    }

    public void setInfoTambahanField(String infoTambahan) {
        this.infoTambahan = infoTambahan;
    }

    @Override
    public String getJenis() {
        return "Mobil";
    }

    @Override
    public String getInfoTambahan() {
        return infoTambahan;
    }

    @Override
    public double hitungDendaPerHari() {
        return 50000.0;
    }
}
