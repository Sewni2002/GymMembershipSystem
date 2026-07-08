package main;

import service.GymService;

import java.util.Scanner;

/**
 * Entry point for Gym Membership System - Version A (Traditional).
 * VERSION A - Traditional (Manually Written)
 */
public class Main {

    private static GymService gymService = new GymService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   GYM MEMBERSHIP SYSTEM - VERSION A     ");
        System.out.println("   (Traditional Programming Approach)     ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": registerMember(); break;
                case "2": gymService.viewAllMembers(); break;
                case "3": searchMember(); break;
                case "4": updateMember(); break;
                case "5": deactivateMember(); break;
                case "6": recordAttendance(); break;
                case "7": viewAttendance(); break;
                case "8": viewByType(); break;
                case "0":
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
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
        var member = gymService.searchMemberById(id);
        if (member != null) {
            System.out.println("ID: " + member.getMemberId());
            System.out.println("Name: " + member.getName());
            System.out.println("Email: " + member.getEmail());
            System.out.println("Phone: " + member.getPhone());
            System.out.println("Type: " + member.getMembershipType());
            System.out.println("Joined: " + member.getJoinDate());
            System.out.println("Active: " + member.isActive());
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void updateMember() {
        System.out.print("Enter Member ID to update: ");
        String id = scanner.nextLine().trim();
        System.out.print("New Name (press Enter to skip): ");
        String name = scanner.nextLine().trim();
        System.out.print("New Phone (press Enter to skip): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New Type (press Enter to skip): ");
        String type = scanner.nextLine().trim();
        gymService.updateMember(id, name, phone, type);
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
        System.out.print("Enter Membership Type (BASIC/STANDARD/PREMIUM): ");
        String type = scanner.nextLine().trim();
        gymService.viewMembersByType(type);
    }
}
