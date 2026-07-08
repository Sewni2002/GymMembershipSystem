package service;

import model.Attendance;
import model.Member;
import util.FileManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for the Gym Membership System.
 * VERSION A - Traditional (Manually Written)
 */
public class GymService {

    private static final String MEMBERS_FILE = "members.txt";
    private static final String ATTENDANCE_FILE = "attendance.txt";

    private List<Member> members;
    private List<Attendance> attendanceList;

    public GymService() {
        members = new ArrayList<>();
        attendanceList = new ArrayList<>();
        loadData();
    }

    // ---- Load from files ----
    private void loadData() {
        for (String line : FileManager.readLines(MEMBERS_FILE)) {
            members.add(Member.fromString(line));
        }
        for (String line : FileManager.readLines(ATTENDANCE_FILE)) {
            attendanceList.add(Attendance.fromString(line));
        }
    }

    // ---- Save members to file ----
    private void saveMembers() {
        List<String> lines = new ArrayList<>();
        for (Member m : members) lines.add(m.toString());
        FileManager.writeLines(MEMBERS_FILE, lines);
    }

    // ---- Register new member ----
    public boolean registerMember(String name, String email, String phone, String membershipType) {
        // Validate membership type
        if (!membershipType.equalsIgnoreCase("BASIC")
                && !membershipType.equalsIgnoreCase("STANDARD")
                && !membershipType.equalsIgnoreCase("PREMIUM")) {
            System.out.println("Invalid membership type. Choose BASIC, STANDARD, or PREMIUM.");
            return false;
        }
        // Check duplicate email
        for (Member m : members) {
            if (m.getEmail().equalsIgnoreCase(email)) {
                System.out.println("A member with this email already exists.");
                return false;
            }
        }
        String id = "M" + String.format("%03d", members.size() + 1);
        String today = LocalDate.now().toString();
        Member newMember = new Member(id, name, email, phone, membershipType.toUpperCase(), today);
        members.add(newMember);
        saveMembers();
        System.out.println("Member registered successfully! ID: " + id);
        return true;
    }

    // ---- View all members ----
    public void viewAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        System.out.printf("%-8s %-20s %-25s %-15s %-10s %-12s %-6s%n",
                "ID", "Name", "Email", "Phone", "Type", "Join Date", "Active");
        System.out.println("-".repeat(100));
        for (Member m : members) {
            System.out.printf("%-8s %-20s %-25s %-15s %-10s %-12s %-6s%n",
                    m.getMemberId(), m.getName(), m.getEmail(),
                    m.getPhone(), m.getMembershipType(), m.getJoinDate(), m.isActive());
        }
    }

    // ---- Search member by ID ----
    public Member searchMemberById(String id) {
        for (Member m : members) {
            if (m.getMemberId().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    // ---- Update member ----
    public boolean updateMember(String id, String newName, String newPhone, String newType) {
        Member m = searchMemberById(id);
        if (m == null) {
            System.out.println("Member not found.");
            return false;
        }
        if (newName != null && !newName.isEmpty()) m.setName(newName);
        if (newPhone != null && !newPhone.isEmpty()) m.setPhone(newPhone);
        if (newType != null && !newType.isEmpty()) m.setMembershipType(newType.toUpperCase());
        saveMembers();
        System.out.println("Member updated successfully.");
        return true;
    }

    // ---- Deactivate member ----
    public boolean deactivateMember(String id) {
        Member m = searchMemberById(id);
        if (m == null) {
            System.out.println("Member not found.");
            return false;
        }
        m.setActive(false);
        saveMembers();
        System.out.println("Member deactivated.");
        return true;
    }

    // ---- Record attendance ----
    public boolean recordAttendance(String memberId) {
        Member m = searchMemberById(memberId);
        if (m == null) {
            System.out.println("Member not found.");
            return false;
        }
        if (!m.isActive()) {
            System.out.println("Member is inactive. Cannot record attendance.");
            return false;
        }
        String attId = "A" + String.format("%04d", attendanceList.size() + 1);
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Attendance att = new Attendance(attId, memberId, date, time);
        attendanceList.add(att);
        FileManager.appendLine(ATTENDANCE_FILE, att.toString());
        System.out.println("Attendance recorded for " + m.getName() + " at " + time);
        return true;
    }

    // ---- View attendance by member ----
    public void viewAttendance(String memberId) {
        boolean found = false;
        System.out.printf("%-10s %-12s %-10s%n", "Att ID", "Date", "Time");
        System.out.println("-".repeat(40));
        for (Attendance a : attendanceList) {
            if (a.getMemberId().equalsIgnoreCase(memberId)) {
                System.out.printf("%-10s %-12s %-10s%n",
                        a.getAttendanceId(), a.getCheckInDate(), a.getCheckInTime());
                found = true;
            }
        }
        if (!found) System.out.println("No attendance records found for member: " + memberId);
    }

    // ---- View members by type ----
    public void viewMembersByType(String type) {
        boolean found = false;
        for (Member m : members) {
            if (m.getMembershipType().equalsIgnoreCase(type) && m.isActive()) {
                System.out.printf("%-8s %-20s %-25s%n", m.getMemberId(), m.getName(), m.getEmail());
                found = true;
            }
        }
        if (!found) System.out.println("No active members found for type: " + type);
    }
}
