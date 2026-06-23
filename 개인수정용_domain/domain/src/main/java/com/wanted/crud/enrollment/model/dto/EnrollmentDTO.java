package com.wanted.crud.enrollment.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnrollmentDTO {
    private Long enrollmentId;
    private Long progressRate;
    private Date startDate;
    private Date endDate;
    private String status;
    private Long studentId;
    private Long courseId;
    private String courseTitle;

    public EnrollmentDTO() {}

    public EnrollmentDTO(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public EnrollmentDTO(Long studentId, Long courseId, String CourseTitle) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }

    public EnrollmentDTO(Long courseId, Long progressRate, String title, Long studentId) {
        this.courseId = courseId;
        this.progressRate = progressRate;
        this.courseTitle = title; // (💡 만약 필드명이 courseTitle로 되어있다면 this.courseTitle = title; 로 변경하세요!)
        this.studentId = studentId;
    }

    public EnrollmentDTO(Long enrollmentId, Long progressRate, java.sql.Date startDate,
                         java.sql.Date endDate, String status, Long studentId,
                         Long courseId, String title) {
        this.enrollmentId = enrollmentId;
        this.progressRate = progressRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseTitle = title; // 만약 필드명이 courseTitle이면 this.courseTitle = title;
    }

    public EnrollmentDTO(Long enrollmentId, Long progressRate, Date startDate, Date endDate, String status, Long studentId, Long courseId) {
        this.enrollmentId = enrollmentId;
        this.progressRate = progressRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getter & Setter
    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public Long getProgressRate() { return progressRate; }
    public void setProgressRate(Long progressRate) { this.progressRate = progressRate; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle){this.courseTitle = courseTitle;}

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String B_WHITE = "\u001B[97m";   // 토끼
        final String B_BLACK = "\u001B[90m";   // 빈 프로그레스 바
        final String CYAN = "\u001B[36m";      // 테두리
        final String B_YELLOW = "\u001B[93m";  // 별/라벨
        final String B_GREEN = "\u001B[92m";   // 강좌/상태
        final String B_MAGENTA = "\u001B[95m"; // 등록번호/진도율
        final String B_CYAN = "\u001B[96m";    // 학생번호
        final String RED = "\u001B[31m";       // 종료/비활성 상태

        // 📝 1. 데이터 포맷팅
        String formattedEnrollmentId = enrollmentId != null ? String.format("%04d", enrollmentId) : "----";
        String formattedStudentId = studentId != null ? String.format("%04d", studentId) : "----";
        String formattedCourseId = courseId != null ? String.format("%04d", courseId) : "----";

        boolean isActive = "1".equals(status);
        String statusIcon = isActive ? B_GREEN + "✅ 수강 중 (ACTIVE)" + RESET : RED + "❌ 종료됨 (CLOSED)" + RESET;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startStr = (startDate != null) ? sdf.format(startDate) : "미정";
        String endStr = (endDate != null) ? sdf.format(endDate) : "진행 중";

        // 🚀 2. 진도율 프로그레스 바 (Progress Bar) 로직
        long actualProgress = (progressRate != null) ? progressRate : 0L;
        int filledBars = (int) (actualProgress / 10);
        StringBuilder pb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < filledBars) pb.append(B_MAGENTA).append("■").append(RESET);
            else pb.append(B_BLACK).append("□").append(RESET);
        }
        String progressBar = pb.toString();
        String paddedProgressNum = String.format("%-4s", actualProgress + "%");

        // 🎨 3. 학사모 토끼 + 와이드 레이아웃 조합
        return "\n" +
                CYAN + "  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                CYAN + "  ┃ " + B_YELLOW + "      * ﾟ  +  .  * " + CYAN + "           " + B_MAGENTA + "    🆔 [ 등록 번호: EN-" + formattedEnrollmentId + " ]    " + B_CYAN + " 🧑‍🎓 [ 학생 번호: " + formattedStudentId + " ]\n" +
                CYAN + "  ┃ " + B_WHITE + "        (\\_/)      " + CYAN + " " + B_YELLOW + " OFFICIAL" + B_GREEN + "     📘 수강 강좌 : " + RESET + courseTitle + "번 강좌\n" +
                CYAN + "  ┃ " + B_WHITE + "       ( •_•)      " + CYAN + " " + B_YELLOW + " ENROLL  " + B_MAGENTA + "     📊 진 도 율 : " + progressBar + " " + B_WHITE + paddedProgressNum + "   " + B_GREEN + "          📌 상 태 : " + RESET + statusIcon + "\n" +
                CYAN + "  ┃ " + B_WHITE + "       / >🎓      " + CYAN + " " + B_YELLOW + " RECORD  " + B_GREEN + "      📅 수강 기간 : " + RESET + startStr + " ~ " + endStr + "\n" +
                CYAN + "  ┃ " + B_YELLOW + "      * .  +  ﾟ * " + CYAN + "           \n" +
                CYAN + "  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + RESET;
    }
}