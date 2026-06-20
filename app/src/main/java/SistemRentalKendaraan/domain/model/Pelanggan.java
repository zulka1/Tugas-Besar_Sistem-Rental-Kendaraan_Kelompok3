package SistemRentalKendaraan.domain.model;

public class Pelanggan {
    private String nomorKtp;
    private String namaLengkap;

    public Pelanggan(String nomorKtp, String namaLengkap) {
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void tampilkanInfo() {
        System.out.println("Nomor KTP : " + nomorKtp);
        System.out.println("Nama      : " + namaLengkap);
    }
}