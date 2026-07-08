package service;

import model.Attendance;
import model.Member;
import util.FileManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic layer using repository pattern and streams.
 * VERSION B - AI-Assisted Approach
 * AI Prompt: "Generate a GymService class in Java using repository pattern,
 * Optional for null safety, and Java streams for filtering members"
 */
public class GymService {

    private static final String ATTENDANCE_FILE = "attendance_b.txt";

    private final MemberRepository memberRepo;
    private final List<Attendance> attendanceList = new ArrayList<>();

    public GymService() {
        this.memberRepo = new MemberRepository();
        loadAttendance();
    }

    private void loadAttendance() {
        for (String line : FileManager.readLines(ATTENDANCE_FILE)) {
            try {
                attendanceList.add(Attendance.fromString(line));
            } catch (Exception e) {
                System.err.println("[GymService] Skipping corrupt attendance: " + line);
            }
        }
    }

    /**
     * Register a new gym member with validation.
     */
    public void registerMember(String name, String email, String phone, String type) {
        // Check duplicate email
        if (memberRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("A member with email '" + email + "' already exists.");
        }

        Member.MembershipType membershipType;
        try {
            membershipType = Member.MembershipType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type. Use BASIC, STANDARD, or PREMIUM.");
        }

        String id = String.format("M%03d", memberRepo.count() + 1);
        Member member = new Member.Builder(id)
                .name(name)
                .email(email)
                .phone(phone)
                .membershipType(membershipType)
                .build();

        memberRepo.save(member);
        System.out.println("Member registered successfully! ID: " + id);
    }

    /**
     * Display all members in a formatted table.
     */
    public void viewAllMembers() {
        List<Member> all = memberRepo.findAll();
        if (all.isEmpty()) {
            System.out.println("No members registered yet.");
            return;
        }
        System.out.printf("%-8s %-20s %-25s %-15s %-10s %-12s %-6s%n",
                "ID", "Name", "Email", "Phone", "Type", "Join Date", "Active");
        System.out.println("=".repeat(100));
        all.forEach(m -> System.out.printf("%-8s %-20s %-25s %-15s %-10s %-12s %-6s%n",
                m.getMemberId(), m.getName(), m.getEmail(),
                m.getPhone(), m.getMembershipType(), m.getJoinDate(), m.isActive()));
    }

    /**
     * Retrieve member by ID wrapped in Optional.
     */
    public Optional<Member> getMemberById(String id) {
        return memberRepo.findById(id);
    }

    /**
     * Update member details.
     */
    public void updateMember(String id, String newName, String newPhone, String newType) {
        Member m = memberRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        if (newName != null && !newName.isBlank()) m.setName(newName);
        if (newPhone != null && !newPhone.isBlank()) m.setPhone(newPhone);
        if (newType != null && !newType.isBlank()) {
            m.setMembershipType(Member.MembershipType.valueOf(newType.toUpperCase()));
        }
        memberRepo.update(m);
        System.out.println("Member updated successfully.");
    }

    /**
     * Deactivate a member.
     */
    public void deactivateMember(String id) {
        Member m = memberRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));
        m.setActive(false);
        memberRepo.update(m);
        System.out.println("Member '" + m.getName() + "' deactivated.");
    }

    /**
     * Record attendance for an active member.
     */
    public void recordAttendance(String memberId) {
        Member m = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        if (!m.isActive()) {
            throw new IllegalStateException("Member is inactive. Cannot record attendance.");
        }

        String attId = String.format("A%04d", attendanceList.size() + 1);
        Attendance att = new Attendance(attId, memberId, LocalDate.now(), LocalTime.now());
        attendanceList.add(att);
        FileManager.appendLine(ATTENDANCE_FILE, att.toString());
        System.out.printf("Attendance recorded for %s [%s at %s]%n",
                m.getName(), att.getCheckInDate(), att.getCheckInTime());
    }

    /**
     * View attendance records for a specific member.
     */
    public void viewAttendance(String memberId) {
        List<Attendance> records = attendanceList.stream()
                .filter(a -> a.getMemberId().equalsIgnoreCase(memberId))
                .toList();

        if (records.isEmpty()) {
            System.out.println("No attendance records found for: " + memberId);
            return;
        }
        System.out.printf("%-10s %-12s %-12s%n", "Att ID", "Date", "Time");
        System.out.println("-".repeat(38));
        records.forEach(a -> System.out.printf("%-10s %-12s %-12s%n",
                a.getAttendanceId(), a.getCheckInDate(), a.getCheckInTime()));
    }

    /**
     * View active members by membership type.
     */
    public void viewMembersByType(String type) {
        Member.MembershipType mType;
        try {
            mType = Member.MembershipType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type: " + type);
            return;
        }

        List<Member> filtered = memberRepo.findByType(mType);
        if (filtered.isEmpty()) {
            System.out.println("No active " + type + " members found.");
            return;
        }
        filtered.forEach(m -> System.out.printf("%-8s %-20s %-25s%n",
                m.getMemberId(), m.getName(), m.getEmail()));
    }
}
