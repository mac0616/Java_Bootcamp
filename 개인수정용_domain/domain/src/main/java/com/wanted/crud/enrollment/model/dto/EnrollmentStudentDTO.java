package com.wanted.crud.enrollment.model.dto;

public class EnrollmentStudentDTO {

    private Long courseId;
    private String courseTitle;

    private Long userNo;
    private String userId;
    private String userName;
    private String userPhoneNumber;
    private Long progressRate;

    public EnrollmentStudentDTO(Long courseId, String courseTitle,
                                Long userNo, String userId,
                                String userName, String userPhoneNumber) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.userNo = userNo;
        this.userId = userId;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
    }

    public EnrollmentStudentDTO(Long courseId, String courseName, Long progressRate) {
        this.courseId = courseId;
        this.courseTitle = courseName;
        this.progressRate = progressRate;
    }

    public Long getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public Long getUserNo() { return userNo; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserPhoneNumber() { return userPhoneNumber; }
    public Long getProgress(){ return progressRate; }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";      // 생각풍선 테두리
        final String B_WHITE = "\u001B[97m";   // 사람, 책상, 기본 텍스트
        final String B_YELLOW = "\u001B[93m";  // 메인 타이틀, 이름 등 강조
        final String B_GREEN = "\u001B[92m";   // 연락처, 진도율 바
        final String B_MAGENTA = "\u001B[95m"; // 아이디, 번호 라벨
        final String B_BLACK = "\u001B[90m";   // 빈 진도율 바

        // 📝 1. Null 안전 포맷팅 (모든 변수 처리)
        String safeName = (userName != null) ? userName : "정보 없음";
        String safeUserId = (userId != null) ? userId : "정보 없음";
        String safeUserNo = (userNo != null) ? String.format("%04d", userNo) : "----";
        String safePhone = (userPhoneNumber != null) ? userPhoneNumber : "정보 없음";
        String safeCourseId = (courseId != null) ? String.format("%04d", courseId) : "----";
        String safeTitle = (courseTitle != null) ? courseTitle : "수강 강좌 없음";

        // 🚀 2. 진도율 (progressRate) 프로그레스 바 로직
        long actualProgress = (progressRate != null) ? progressRate : 0L;
        int filledBars = (int) (actualProgress / 10);
        StringBuilder pb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < filledBars) pb.append(B_GREEN).append("■").append(RESET);
            else pb.append(B_BLACK).append("□").append(RESET);
        }
        String progressBar = pb.toString();

        // 🎨 3. 오른쪽 생각풍선 UI 렌더링
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(CYAN).append("                        ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);
        sb.append(CYAN).append("                     o   ").append(B_YELLOW).append("👑 [ 관리자 뷰 - 결제 학생 상세 정보 ]\n").append(RESET);
        sb.append(B_WHITE).append("     ∧＿∧").append(CYAN).append("          O     ").append(B_MAGENTA).append("🧑‍🎓 학생 번호: ").append(B_WHITE).append("#").append(safeUserNo).append("   ").append(B_YELLOW).append("🧑‍🎓 이름: ").append(B_WHITE).append(safeName).append("\n").append(RESET);
        sb.append(B_WHITE).append("    (* ･ω･)").append(CYAN).append("       ◯      ").append(B_GREEN).append("📞 연 락 처 : ").append(B_WHITE).append(safePhone).append("   ").append(B_MAGENTA).append("🆔 아이디: ").append(B_WHITE).append(safeUserId).append("\n").append(RESET);
        sb.append(B_WHITE).append("  ＿(__つ/￣￣￣/_").append(CYAN).append("         ").append(B_YELLOW).append("📚 수강 강좌: ").append(B_WHITE).append("NO.").append(safeCourseId).append(" (").append(safeTitle).append(")\n").append(RESET);
        sb.append(B_WHITE).append("     ＼/　　　/").append(CYAN).append("            ").append(B_GREEN).append("📊 진 도 율 : ").append(progressBar).append(" ").append(B_WHITE).append(String.format("%-4s", actualProgress + "%")).append("\n").append(RESET);
        sb.append(CYAN).append("                        ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);

        return sb.toString();
    }
}