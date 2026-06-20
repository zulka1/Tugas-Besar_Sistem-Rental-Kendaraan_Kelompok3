package SistemRentalKendaraan.presentation;

import SistemRentalKendaraan.application.AuthService;
import SistemRentalKendaraan.domain.model.User;

public class LoginView {
    private final AuthService authService;
    private final AdminDashboard adminDashboard = new AdminDashboard();
    private final StaffDashboard staffDashboard = new StaffDashboard();
    private final OwnerDashboard ownerDashboard = new OwnerDashboard();

    public LoginView(AuthService authService) {
        this.authService = authService;
    }

    public void start() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            ConsoleHelper.printHeader("SISTEM RENTAL KELOMPOK 3");
            String username = ConsoleHelper.getInput("Username: ");
            String password = ConsoleHelper.getInput("Password: ");

            User loggedInUser = authService.login(username, password);

            if (loggedInUser != null) {
                System.out.println("\n[SUKSES] Selamat datang, " + loggedInUser.getUsername() + "!");
                routeToDashboard(loggedInUser);
                return; // Keluar dari loop login
            } else {
                attempts++;
                System.out.println("[GAGAL] Username atau password salah.");
                System.out.println("Sisa percobaan: " + (MAX_ATTEMPTS - attempts) + "\n");
            }
        }
        
        System.out.println("Anda telah gagal login 3 kali. Sistem ditutup untuk keamanan.");
        System.exit(0);
    }

    private void routeToDashboard(User user) {
        switch (user.getRole()) {
            case ADMIN:
                adminDashboard.showMenu();
                break;
            case STAFF:
                staffDashboard.showMenu();
                break;
            case OWNER:
                ownerDashboard.showMenu();
                break;
        }
    }
}