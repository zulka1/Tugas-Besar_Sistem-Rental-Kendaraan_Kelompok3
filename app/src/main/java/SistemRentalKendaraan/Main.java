package SistemRentalKendaraan;

import SistemRentalKendaraan.application.AuthService;
import SistemRentalKendaraan.domain.repository.UserRepository;
import SistemRentalKendaraan.infrastructure.JsonUserRepository;
import SistemRentalKendaraan.presentation.LoginView;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new JsonUserRepository();
        AuthService authService = new AuthService(userRepository);
        LoginView loginView = new LoginView(authService);

        loginView.start();
    }
}