package SistemRentalKendaraan.infrastructure;

import SistemRentalKendaraan.domain.model.Sopir;
import SistemRentalKendaraan.domain.repository.SopirRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonSopirRepository implements SopirRepository {
    private static final String FILE_PATH = "data/sopir.json";
    private final Gson gson = GsonFactory.createGson();

    public JsonSopirRepository() {
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeAll(new ArrayList<>());
            } catch (IOException e) {
                System.out.println("Gagal menginisialisasi file sopir: " + e.getMessage());
            }
        }
    }

    private List<Sopir> readAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            List<Sopir> list = gson.fromJson(reader, new TypeToken<List<Sopir>>(){}.getType());
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeAll(List<Sopir> list) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data sopir: " + e.getMessage());
        }
    }

    @Override
    public void save(Sopir sopir) {
        List<Sopir> list = readAll();
        list.add(sopir);
        writeAll(list);
    }

    @Override
    public List<Sopir> findAll() {
        return readAll();
    }

    @Override
    public Optional<Sopir> findById(String idSopir) {
        if (idSopir == null) {
            return Optional.empty();
        }
        String searchId = idSopir.trim();
        return readAll().stream()
            .filter(s -> s.getIdSopir() != null && s.getIdSopir().equalsIgnoreCase(searchId))
            .findFirst();
    }

    @Override
    public void update(Sopir sopir) {
        List<Sopir> list = readAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdSopir() != null && list.get(i).getIdSopir().equalsIgnoreCase(sopir.getIdSopir())) {
                list.set(i, sopir);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Sopir dengan ID " + sopir.getIdSopir() + " tidak ditemukan.");
        }
        writeAll(list);
    }

    @Override
    public String generateNextId() {
        int maxId = 0;
        for (Sopir s : readAll()) {
            String id = s.getIdSopir();
            if (id != null && id.matches("DRV-\\d+")) {
                try {
                    int num = Integer.parseInt(id.substring(4));
                    maxId = Math.max(maxId, num);
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("DRV-%03d", maxId + 1);
    }
}
