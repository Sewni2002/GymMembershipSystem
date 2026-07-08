package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a gym attendance record using LocalDate/LocalTime.
 * VERSION B - AI-Assisted Approach
 * AI Prompt: "Generate an Attendance class using Java LocalDate and LocalTime with serialization"
 */
public class Attendance {

    private final String attendanceId;
    private final String memberId;
    private final LocalDate checkInDate;
    private final LocalTime checkInTime;

    public Attendance(String attendanceId, String memberId, LocalDate checkInDate, LocalTime checkInTime) {
        this.attendanceId = attendanceId;
        this.memberId = memberId;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }

    public String getAttendanceId() { return attendanceId; }
    public String getMemberId() { return memberId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalTime getCheckInTime() { return checkInTime; }

    @Override
    public String toString() {
        return attendanceId + "," + memberId + "," + checkInDate + "," + checkInTime;
    }

    public static Attendance fromString(String line) {
        String[] p = line.split(",");
        return new Attendance(p[0], p[1], LocalDate.parse(p[2]), LocalTime.parse(p[3]));
    }
}
