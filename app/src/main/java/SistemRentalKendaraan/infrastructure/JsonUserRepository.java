package SistemRentalKendaraan.infrastructure;

<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import SistemRentalKendaraan.domain.model.User;
import SistemRentalKendaraan.domain.repository.UserRepository;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUserRepository implements UserRepository {
    private final String FILE_PATH = "data/users.json";
    private List<User> users;

    public JsonUserRepository() {
        loadUsers();
    }

    private void loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            users = gson.fromJson(reader, userListType);
            if (users == null) users = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Gagal memuat data user: " + e.getMessage());
            users = new ArrayList<>();
        }
    }

    @Override
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
=======
import SistemRentalKendaraan.domain.repository.UserRepository;

public class JsonUserRepository implements UserRepository {
}
>>>>>>> origin/main
