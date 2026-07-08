package model;

import java.time.LocalDate;

/**
 * Represents a gym member with enhanced validation.
 * VERSION B - AI-Assisted Approach
 * AI Prompt used: "Generate a Java Member class for a gym system with enums for
 * membership types, input validation, and builder pattern"
 */
public class Member {

    public enum MembershipType {
        BASIC, STANDARD, PREMIUM
    }

    private final String memberId;
    private String name;
    private String email;
    private String phone;
    private MembershipType membershipType;
    private final LocalDate joinDate;
    private boolean active;

    // Builder pattern (AI-assisted generation)
    public static class Builder {
        private String memberId;
        private String name;
        private String email;
        private String phone;
        private MembershipType membershipType;
        private LocalDate joinDate = LocalDate.now();

        public Builder(String memberId) { this.memberId = memberId; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder membershipType(MembershipType type) { this.membershipType = type; return this; }
        public Builder joinDate(LocalDate date) { this.joinDate = date; return this; }

        public Member build() {
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name is required.");
            if (email == null || !email.contains("@")) throw new IllegalArgumentException("Valid email required.");
            if (membershipType == null) throw new IllegalArgumentException("Membership type is required.");
            return new Member(this);
        }
    }

    private Member(Builder builder) {
        this.memberId = builder.memberId;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.membershipType = builder.membershipType;
        this.joinDate = builder.joinDate;
        this.active = true;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public MembershipType getMembershipType() { return membershipType; }
    public LocalDate getJoinDate() { return joinDate; }
    public boolean isActive() { return active; }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;
    }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email.");
        this.email = email;
    }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMembershipType(MembershipType type) { this.membershipType = type; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return memberId + "," + name + "," + email + "," + phone + ","
                + membershipType.name() + "," + joinDate.toString() + "," + active;
    }

    public static Member fromString(String line) {
        String[] p = line.split(",");
        return new Member.Builder(p[0])
                .name(p[1])
                .email(p[2])
                .phone(p[3])
                .membershipType(MembershipType.valueOf(p[4]))
                .joinDate(LocalDate.parse(p[5]))
                .build();
        // Note: active flag is set after build; workaround below:
    }

    public static Member fromStringFull(String line) {
        String[] p = line.split(",");
        Member m = new Member.Builder(p[0])
                .name(p[1]).email(p[2]).phone(p[3])
                .membershipType(MembershipType.valueOf(p[4]))
                .joinDate(LocalDate.parse(p[5]))
                .build();
        m.setActive(Boolean.parseBoolean(p[6]));
        return m;
    }
}
