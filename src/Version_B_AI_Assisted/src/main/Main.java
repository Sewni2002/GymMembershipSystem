package main;

import model.Member;
import service.GymService;

import java.util.Optional;
import java.util.Scanner;

/**
 * Entry point for Gym Membership System - Version B (AI-Assisted).
 * VERSION B - AI-Assisted Approach
 * AI Prompt: "Generate a console menu-driven Java main class for a gym system
 * using try-catch for user-friendly error messages"
 */
public class Main {

    private static final GymService gymService = new GymService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   GYM MEMBERSHIP SYSTEM - VERSION B     ");
        System.out.println("     (AI-Assisted Programming Approach)   ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> registerMember();
                    case "2" -> gymService.viewAllMembers();
                    case "3" -> searchMember();
                    case "4" -> updateMember();
                    case "5" -> deactivateMember();
                    case "6" -> recordAttendance();
                    case "7" -> viewAttendance();
                    case "8" -> viewByType();
                    case "0" -> {
                        System.out.println("Exiting system. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose 0-8.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // User-friendly error handling (AI-assisted pattern)
                System.out.println("[Error] " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Register Member");
        System.out.println("2. View All Members");
        System.out.println("3. Search Member by ID");
        System.out.println("4. Update Member");
        System.out.println("5. Deactivate Member");
        System.out.println("6. Record Attendance");
        System.out.println("7. View Member Attendance");
        System.out.println("8. View Members by Type");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void registerMember() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Membership Type (BASIC/STANDARD/PREMIUM): ");
        String type = scanner.nextLine().trim();
        gymService.registerMember(name, email, phone, type);
    }

    private static void searchMember() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine().trim();
        Optional<Member> result = gymService.getMemberById(id);
        result.ifPresentOrElse(
                m -> {
                    System.out.println("ID      : " + m.getMemberId());
                    System.out.println("Name    : " + m.getName());
                    System.out.println("Email   : " + m.getEmail());
                    System.out.println("Phone   : " + m.getPhone());
                    System.out.println("Type    : " + m.getMembershipType());
                    System.out.println("Joined  : " + m.getJoinDate());
                    System.out.println("Active  : " + m.isActive());
                },
                () -> System.out.println("Member not found.")
        );
    }

    private static void updateMember() {
        System.out.print("Enter Member ID to update: ");
        String id = scanner.nextLine().trim();
        System.out.print("New Name (Enter to skip): ");
        String name = scanner.nextLine().trim();
        System.out.print("New Phone (Enter to skip): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New Type (Enter to skip): ");
        String type = scanner.nextLine().trim();
        gymService.updateMember(id,
                name.isEmpty() ? null : name,
                phone.isEmpty() ? null : phone,
                type.isEmpty() ? null : type);
    }

    private static void deactivateMember() {
        System.out.print("Enter Member ID to deactivate: ");
        String id = scanner.nextLine().trim();
        gymService.deactivateMember(id);
    }

    private static void recordAttendance() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine().trim();
        gymService.recordAttendance(id);
    }

    private static void viewAttendance() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine().trim();
        gymService.viewAttendance(id);
    }

    private static void viewByType() {
        System.out.print("Membership Type (BASIC/STANDARD/PREMIUM): ");
        String type = scanner.nextLine().trim();
        gymService.viewMembersByType(type);
    }
}
