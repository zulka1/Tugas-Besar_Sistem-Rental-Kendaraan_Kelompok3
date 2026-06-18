package SistemRentalKendaraan.application;

<<<<<<< HEAD
import SistemRentalKendaraan.domain.model.User;
import SistemRentalKendaraan.domain.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Login gagal
    }
}
=======
public class AuthService {
}
>>>>>>> origin/main
