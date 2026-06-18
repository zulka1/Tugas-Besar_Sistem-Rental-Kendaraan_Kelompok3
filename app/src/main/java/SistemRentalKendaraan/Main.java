package SistemRentalKendaraan;

<<<<<<< HEAD
import SistemRentalKendaraan.application.AuthService;
import SistemRentalKendaraan.domain.repository.UserRepository;
import SistemRentalKendaraan.infrastructure.JsonUserRepository;
import SistemRentalKendaraan.presentation.LoginView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistem Rental Kendaraan - Kelompok 3");
        UserRepository userRepository = new JsonUserRepository();
        AuthService authService = new AuthService(userRepository);
        LoginView loginView = new LoginView(authService);

        loginView.start();
    }
}
=======
public class Main {
    public static void main(String[] args) {
        System.out.println("Sistem Rental Kendaraan - Kelompok 3");
    }
}
>>>>>>> origin/main
