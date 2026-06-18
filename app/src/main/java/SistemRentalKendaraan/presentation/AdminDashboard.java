package SistemRentalKendaraan.presentation;

public class AdminDashboard {
    public void showMenu() {
        ConsoleHelper.printHeader("DASHBOARD ADMIN");
        System.out.println("1. Kelola Sopir");
        System.out.println("2. Kelola Mobil");
        System.out.println("3. Kelola Motor");
        System.out.println("0. Logout");
        ConsoleHelper.getInput("Pilih menu: ");
    }
}