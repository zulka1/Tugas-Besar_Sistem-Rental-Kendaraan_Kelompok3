package SistemRentalKendaraan.presentation;

import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void printHeader(String title) {
        System.out.println("\n=================================");
        System.out.println("      " + title);
        System.out.println("=================================");
    }
}