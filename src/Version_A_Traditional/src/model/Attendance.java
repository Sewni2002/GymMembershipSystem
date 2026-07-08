package model;

/**
 * Represents an attendance record for a gym member.
 * VERSION A - Traditional (Manually Written)
 */
public class Attendance {
    private String attendanceId;
    private String memberId;
    private String checkInDate;
    private String checkInTime;

    public Attendance(String attendanceId, String memberId, String checkInDate, String checkInTime) {
        this.attendanceId = attendanceId;
        this.memberId = memberId;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }

    public String getAttendanceId() { return attendanceId; }
    public String getMemberId() { return memberId; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckInTime() { return checkInTime; }

    @Override
    public String toString() {
        return attendanceId + "," + memberId + "," + checkInDate + "," + checkInTime;
    }

    public static Attendance fromString(String line) {
        String[] parts = line.split(",");
        return new Attendance(parts[0], parts[1], parts[2], parts[3]);
    }
}
