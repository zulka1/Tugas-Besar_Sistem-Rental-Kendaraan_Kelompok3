package SistemRentalKendaraan.application;

import SistemRentalKendaraan.domain.model.Pelanggan;
import SistemRentalKendaraan.domain.repository.PelangganRepository;
import java.util.List;

public class PelangganService {
    private PelangganRepository pelangganRepository;
    public PelangganService(PelangganRepository pelangganRepository) {
        this.pelangganRepository = pelangganRepository;
    }
    public void daftarPelanggan(String nomorKtp, String namaLengkap) {
        if (!ktpValid(nomorKtp)) {
            System.out.println("Nomor KTP harus 16 digit angka");
            return;
        }
        if (namaLengkap == null || namaLengkap.trim().isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong");
            return;
        }
        if (pelangganRepository.findByKtp(nomorKtp) != null) {
            System.out.println("Pelanggan dengan nomor KTP " + nomorKtp + " sudah terdaftar");
            return;
        }
        List<Pelanggan> daftarPelanggan = pelangganRepository.findAll();
        Pelanggan pelanggan = new Pelanggan(nomorKtp, namaLengkap);
        daftarPelanggan.add(pelanggan);
        pelangganRepository.saveAll(daftarPelanggan);
        System.out.println("Pelanggan berhasil didaftarkan");
    }

    public Pelanggan cariPelanggan(String nomorKtp) {
        Pelanggan pelanggan = pelangganRepository.findByKtp(nomorKtp);
        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan");
        }
        return pelanggan;
    }

    public void hapusPelanggan(String nomorKtp) {
        if (!ktpValid(nomorKtp)) {
            System.out.println("Nomor KTP harus 16 digit angka");
            return;
        }
        List<Pelanggan> daftarPelanggan = pelangganRepository.findAll();
        Pelanggan pelangganDihapus = null;
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNomorKtp().equals(nomorKtp)) {
                pelangganDihapus = p;
                break;
            }
        }
        if (pelangganDihapus == null) {
            System.out.println("Pelanggan tidak ditemukan");
            return;
        }
        daftarPelanggan.remove(pelangganDihapus);
        pelangganRepository.saveAll(daftarPelanggan);
        System.out.println("Pelanggan berhasil dihapus");
    }

    private boolean ktpValid(String nomorKtp) {
        if (nomorKtp == null || nomorKtp.length() != 16) {
            return false;
        }

        for (int i = 0; i < nomorKtp.length(); i++) {
            if (!Character.isDigit(nomorKtp.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}