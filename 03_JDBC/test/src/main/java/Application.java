import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        Date today = new Date();

        CourseMyStudentDTO student1 = new CourseMyStudentDTO(
                101L, "자바 스프링부트 마스터 클래스", 7701L, "김토끼",
                75L, today, null, true
        );

        CourseMyStudentDTO student2 = new CourseMyStudentDTO(
                102L, "리액트 프론트엔드 실무", 7702L, "이거북",
                100L, today, today, false
        );

        System.out.println("\n🐰 [테스트 1] 수강 중인 학생 정보");
        System.out.println(student1);

        System.out.println("\n🐰 [테스트 2] 수강 종료된 학생 정보");
        System.out.println(student2);
    }
}

class CourseMyStudentDTO {

    private Long courseId;
    private String title;
    private List sections;
    private Long studentId;
    private String userName;
    private Long progressRate;
    private Long price;
    private Date startDate;
    private Date createdAt;
    private Date endDate;
    private Boolean status;

    public CourseMyStudentDTO(Long courseId, String title, Long studentId, String userName, Long progressRate, Date startDate, Date endDate, Boolean status) {
        this.courseId = courseId;
        this.title = title;
        this.studentId = studentId;
        this.userName = userName;
        this.progressRate = progressRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

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


        // 🚀 2. 진도율 (progressRate) 프로그레스 바 로직
        long actualProgress = (progressRate != null) ? progressRate : 0L;
        int filledBars = (int) (actualProgress / 10);
        StringBuilder pb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < filledBars) pb.append(B_GREEN).append("■").append(RESET);
            else pb.append(B_BLACK).append("□").append(RESET);
        }
        String progressBar = pb.toString();

        @Override
        public String toString() {
            // 1. 환불 상태 분석 (null 방어 포함)
            boolean isDone = "1".equals(String.valueOf(refundStatus));
            String statusIcon = isDone ? "✅ 완료 (COMPLETED)" : "⏳ 진행 중 (PROCESSING)";
            String formattedAmount = String.format("%,d원", refundAmount);

            // 2. 상태 도장(STAMP) 아스키 아트
            String stampTop = " .----------. ";
            String stampMid = isDone ? " |  REFUND  | " : " |  PENDING | ";
            String stampBot = " '----------' ";

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("  .--------------------------------------------------\n");
            // 🚀 REFUND 타이포그래피 (오른쪽 테두리 제거)
            sb.append("  |   ____   _____  _____  _   _  _   _  ____  \n");
            sb.append("  |  |  _ \\ |  ___||  ___|| | | || \\ | ||  _ \\ \n");
            sb.append("  |  | |_) || |__  | |__  | | | ||  \\| || | | |\n");
            sb.append("  |  |  _ < |  __| |  __| | |_| || |\\  || |_| |\n");
            sb.append("  |  |_| \\_\\|_____||_|     \\___/ |_| \\_||____/ \n");
            sb.append("  ├--------------------------------------------------\n");
            sb.append("  |      ").append(stampTop).append("\n");
            sb.append("  |      ").append(stampMid).append("  ID : #").append(String.format("%05d", 123)).append("\n");
            sb.append("  |      ").append(stampBot).append("\n");
            sb.append("  ├--------------------------------------------------\n");
            sb.append("  |   💰 환불 금액  : ").append(formattedAmount).append("\n");
            sb.append("  |   💳 결제 번호  : # ").append(String.format("%06d", 12)).append("\n");
            sb.append("  |   ✨ 처리 상태  : ").append(statusIcon).append("\n");
            sb.append("  |   📅 처리 일시  : ").append(refundAt != null ? refundAt : "확인 중 (Pending)").append("\n");
            sb.append("  '--------------------------------------------------\n");

            return sb.toString();
        }
    }
}