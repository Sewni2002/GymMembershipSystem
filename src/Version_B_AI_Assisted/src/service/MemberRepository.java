package service;

import model.Member;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository pattern for member data access.
 * VERSION B - AI-Assisted Approach
 * AI Prompt: "Generate a repository pattern class for managing Member objects in Java
 * with file persistence using Optional for null safety"
 */
public class MemberRepository {

    private static final String FILE = "members_b.txt";
    private final List<Member> members = new ArrayList<>();

    public MemberRepository() {
        load();
    }

    private void load() {
        for (String line : FileManager.readLines(FILE)) {
            try {
                members.add(Member.fromStringFull(line));
            } catch (Exception e) {
                System.err.println("[MemberRepository] Skipping corrupt record: " + line);
            }
        }
    }

    private void persist() {
        List<String> lines = new ArrayList<>();
        for (Member m : members) lines.add(m.toString());
        FileManager.writeLines(FILE, lines);
    }

    public void save(Member member) {
        members.add(member);
        persist();
    }

    public void update(Member member) {
        persist();
    }

    public Optional<Member> findById(String id) {
        return members.stream()
                .filter(m -> m.getMemberId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Member> findByEmail(String email) {
        return members.stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    public List<Member> findByType(Member.MembershipType type) {
        return members.stream()
                .filter(m -> m.getMembershipType() == type && m.isActive())
                .toList();
    }

    public int count() {
        return members.size();
    }
}
