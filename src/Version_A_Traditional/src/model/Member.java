package model;

/**
 * Represents a gym member.
 * VERSION A - Traditional (Manually Written)
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String membershipType; // BASIC, STANDARD, PREMIUM
    private String joinDate;
    private boolean active;

    public Member(String memberId, String name, String email, String phone,
                  String membershipType, String joinDate) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.joinDate = joinDate;
        this.active = true;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getMembershipType() { return membershipType; }
    public String getJoinDate() { return joinDate; }
    public boolean isActive() { return active; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return memberId + "," + name + "," + email + "," + phone + ","
                + membershipType + "," + joinDate + "," + active;
    }

    public static Member fromString(String line) {
        String[] parts = line.split(",");
        Member m = new Member(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        m.setActive(Boolean.parseBoolean(parts[6]));
        return m;
    }
}
