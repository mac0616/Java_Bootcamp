package com.wanted.crud.course.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseMyStudentDTO {

    private Long courseId;
    private String title;
    private Long studentId;
    private String userName;
    private Long progressRate;
    private Date startDate;
    private Date endDate;
    private Boolean status;

    public CourseMyStudentDTO() {}

    public CourseMyStudentDTO(Long courseId, String title, Long studentId, String userName, Long progressRate, Date startDate, Date endDate, Boolean status){
        this.courseId = courseId;
        this.title = title;
        this.studentId = studentId;
        this.userName =  userName;
        this.progressRate = progressRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getter & Setter
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getProgressRate() { return progressRate; }
    public void setProgressRate(Long progressRate) { this.progressRate = progressRate; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String B_WHITE = "\u001B[97m";
        final String B_BLACK = "\u001B[90m";
        final String CYAN = "\u001B[38;2;78;108;105m";
        final String B_YELLOW = "\u001B[93m";
        final String B_GREEN = "\u001B[92m";
        final String B_MAGENTA = "\u001B[38;2;255;182;193m";;
        final String B_CYAN = "\u001B[96m";
        final String B_BLUE = "\u001B[94m";
        final String RED = "\u001B[31m";

        // 📝 1. 상태 및 텍스트 포맷팅
        String statusText = (status != null && status) ? B_BLUE + "✅ 활성 (Active)" + RESET : RED + "❌ 비활성 (Inactive)" + RESET;
        String formattedCourseId = courseId != null ? String.format("%04d", courseId) : "----";
        String formattedStudentId = studentId != null ? String.format("%04d", studentId) : "----";

        // 📝 2. 날짜 포맷팅 (시작일 ~ 마감일)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startStr = (startDate != null) ? sdf.format(startDate) : "----";
        String endStr = (endDate != null) ? sdf.format(endDate) : "진행 중 (Ongoing)";
        String periodText = startStr + " ~ " + endStr;

        // 🚀 3. 예쁜 프로그레스 바 (Progress Bar) 생성 로직
        long actualProgress = (progressRate != null) ? progressRate : 0L;
        int filledBars = (int) (actualProgress / 10);
        StringBuilder pb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < filledBars) pb.append(B_GREEN + "■" + RESET);
            else pb.append(B_BLACK + "□" + RESET);
        }
        String progressBar = pb.toString();
        String paddedProgressNum = String.format("%-4s", actualProgress + "%");

        // 🎨 4. 요청하신 순서대로 재배치한 UI 조합
        return "\n" +
                CYAN + "  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                CYAN + "  ┃ " + B_MAGENTA + "    * ﾟ  +  .  * " + CYAN + "                " + B_GREEN + "📚 제  목 : " + RESET + title + "   " + B_MAGENTA + "              🏷️ [ 강좌 번호: " + formattedCourseId + " ]\n" +
                CYAN + "  ┃ " + B_WHITE + "     (\\ _ /)      " + CYAN + "  " + B_YELLOW + " STUDENT " + CYAN + "    " + B_YELLOW + "🧑‍🎓 이  름 : " + RESET + userName + "   " + B_CYAN + "               🆔 [ 학생 번호: " + formattedStudentId + " ]\n" +
                CYAN + "  ┃ " + B_WHITE + "     ( 'x' )      " + CYAN + "  " + B_YELLOW + " PROFILE " + CYAN + "    " + B_MAGENTA + "📊 진도율 : " + progressBar + " " + B_WHITE + paddedProgressNum + "    " + B_GREEN + "     📌 상 태 : " + RESET + statusText + "\n" +
                CYAN + "  ┃ " + B_WHITE + "     c(\")(\")      " + CYAN + "               " + B_GREEN + "📅 기  간 : " + RESET + periodText + "\n" +
                CYAN + "  ┃ " + B_MAGENTA + "    * .  +  ﾟ * " + CYAN + "                 \n" +
                CYAN + "  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET;
    }
}