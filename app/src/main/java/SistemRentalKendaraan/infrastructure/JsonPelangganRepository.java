package SistemRentalKendaraan.infrastructure;

import SistemRentalKendaraan.domain.model.Pelanggan;
import SistemRentalKendaraan.domain.repository.PelangganRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class JsonPelangganRepository implements PelangganRepository {
    private String filePath = "data/pelanggan.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveAll(List<Pelanggan> daftarPelanggan) {
        try {
            FileWriter writer = new FileWriter(filePath);
            gson.toJson(daftarPelanggan, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Gagal menyimpan data pelanggan");
        }
    }

    @Override
    public List<Pelanggan> findAll() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                saveAll(new ArrayList<Pelanggan>());
            }
            FileReader reader = new FileReader(file);
            List<Pelanggan> daftarPelanggan = gson.fromJson(reader, new TypeToken<ArrayList<Pelanggan>>(){}.getType());
            reader.close();
            if (daftarPelanggan == null) {
                return new ArrayList<Pelanggan>();
            }
            return daftarPelanggan;
        } catch (Exception e) {
            System.out.println("Gagal membaca data pelanggan");
            return new ArrayList<Pelanggan>();
        }
    }

    @Override
    public Pelanggan findByKtp(String nomorKtp) {
        List<Pelanggan> daftarPelanggan = findAll();
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNomorKtp().equals(nomorKtp)) {
                return p;
            }
        }
        return null;
    }
}