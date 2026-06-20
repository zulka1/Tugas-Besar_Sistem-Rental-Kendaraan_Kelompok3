package SistemRentalKendaraan.infrastructure;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import SistemRentalKendaraan.domain.model.Kendaraan;
import SistemRentalKendaraan.domain.repository.KendaraanRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonKendaraanRepository implements KendaraanRepository {
    private final String filePath = "data/kendaraan.json";
    private final Gson gson = GsonFactory.createGson();

    public JsonKendaraanRepository() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeAll(new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Kendaraan> readAll() {
        try (Reader reader = new FileReader(filePath)) {
            List<Kendaraan> list = gson.fromJson(reader, new TypeToken<List<Kendaraan>>(){}.getType());
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeAll(List<Kendaraan> list) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Kendaraan kendaraan) {
        List<Kendaraan> list = readAll();
        list.add(kendaraan);
        writeAll(list);
    }

    @Override
    public List<Kendaraan> findAll() {
        return readAll();
    }

    @Override
    public Optional<Kendaraan> findByPlatNomor(String platNomor) {
        return readAll().stream()
            .filter(k -> k.getPlatNomor().equalsIgnoreCase(platNomor))
            .findFirst();
    }

    @Override
    public void delete(String platNomor) {
        List<Kendaraan> list = readAll();
        list.removeIf(k -> k.getPlatNomor().equalsIgnoreCase(platNomor));
        writeAll(list);
    }

    @Override
    public void update(Kendaraan kendaraan) {
        List<Kendaraan> list = readAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPlatNomor().equalsIgnoreCase(kendaraan.getPlatNomor())) {
                list.set(i, kendaraan);
                break;
            }
        }
        writeAll(list);
    }
}
