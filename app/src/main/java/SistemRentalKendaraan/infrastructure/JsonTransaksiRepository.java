package SistemRentalKendaraan.infrastructure;

import SistemRentalKendaraan.domain.model.Transaksi;
import SistemRentalKendaraan.domain.model.enums.StatusTransaksi;
import SistemRentalKendaraan.domain.repository.TransaksiRepository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonTransaksiRepository implements TransaksiRepository {
    private static final String FILE_PATH = "data/transaksi.json";

    private final Gson gson = GsonFactory.createGson();

    public JsonTransaksiRepository() {
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        try {
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("Folder data tidak dapat dibuat.");
            }

            if (!file.exists() && file.createNewFile()) {
                writeAll(new ArrayList<>());
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                "Gagal menyiapkan file transaksi: " + e.getMessage(),
                e
            );
        }
    }

    private List<Transaksi> readAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            List<Transaksi> daftar = gson.fromJson(
                reader,
                new TypeToken<List<Transaksi>>() { }.getType()
            );

            return daftar != null ? daftar : new ArrayList<>();
        } catch (IOException e) {
            throw new IllegalStateException(
                "Gagal membaca data transaksi: " + e.getMessage(),
                e
            );
        }
    }

    private void writeAll(List<Transaksi> daftar) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(daftar, writer);
        } catch (IOException e) {
            throw new IllegalStateException(
                "Gagal menyimpan data transaksi: " + e.getMessage(),
                e
            );
        }
    }

    @Override
    public void save(Transaksi transaksi) {
        List<Transaksi> daftar = readAll();
        daftar.add(transaksi);
        writeAll(daftar);
    }

    @Override
    public List<Transaksi> findAll() {
        return readAll();
    }

    @Override
    public Optional<Transaksi> findById(String idTransaksi) {
        if (idTransaksi == null) {
            return Optional.empty();
        }

        String idDicari = idTransaksi.trim();

        return readAll().stream()
            .filter(transaksi -> transaksi.getIdTransaksi() != null)
            .filter(transaksi ->
                transaksi.getIdTransaksi().equalsIgnoreCase(idDicari)
            )
            .findFirst();
    }

    @Override
    public Optional<Transaksi> findBerjalanByPlatNomor(String platNomor) {
        if (platNomor == null) {
            return Optional.empty();
        }

        String platDicari = platNomor.trim();

        return readAll().stream()
            .filter(transaksi -> transaksi.getPlatNomor() != null)
            .filter(transaksi ->
                transaksi.getPlatNomor().equalsIgnoreCase(platDicari)
            )
            .filter(transaksi ->
                transaksi.getStatus() == StatusTransaksi.BERJALAN
            )
            .findFirst();
    }

    @Override
    public void update(Transaksi transaksi) {
        List<Transaksi> daftar = readAll();
        boolean ditemukan = false;

        for (int i = 0; i < daftar.size(); i++) {
            Transaksi transaksiLama = daftar.get(i);

            if (transaksiLama.getIdTransaksi() != null
                    && transaksiLama.getIdTransaksi()
                        .equalsIgnoreCase(transaksi.getIdTransaksi())) {

                daftar.set(i, transaksi);
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            throw new IllegalArgumentException(
                "Transaksi yang akan diperbarui tidak ditemukan."
            );
        }

        writeAll(daftar);
    }

    @Override
    public String generateNextId() {
        int nomorTerbesar = 0;

        for (Transaksi transaksi : readAll()) {
            String id = transaksi.getIdTransaksi();

            if (id == null || !id.matches("TRX-\\d+")) {
                continue;
            }

            try {
                int nomor = Integer.parseInt(id.substring(4));
                nomorTerbesar = Math.max(nomorTerbesar, nomor);
            } catch (NumberFormatException ignored) {
                // ID yang formatnya tidak benar dilewati.
            }
        }

        return String.format("TRX-%03d", nomorTerbesar + 1);
    }
}