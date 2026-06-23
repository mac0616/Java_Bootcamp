package com.wanted.crud.course.model.dto;

import java.util.Date;

public class CourseDTO {

    private Long courseId;
    private String title;
    private String description;
    private Long price;
    private String status;
    private Date createdAt;
    private Long instructorId;

    public CourseDTO(Long courseId, String title, String description, Long price, String status, Date createdAt, Long instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.instructorId = instructorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }


    // 한글 길이를 계산해 오른쪽 여백을 채워주는 메서드
    private String padRight(String text) {
        int targetWidth = 57; // 박스 안쪽의 고정 너비
        int width = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // 한글은 콘솔에서 2칸을 차지하므로 +2
            if (c >= '가' && c <= '힣') {
                width += 2;
            } else {
                width += 1; // 영어, 숫자, 공백, 기호 등은 1칸
            }
        }

        int padding = targetWidth - width;
        if (padding > 0) {
            return text + " ".repeat(padding); // 모자란 길이만큼 공백을 반복해서 붙임
        }
        return text;
    }


    @Override
    public String toString() {
        // 🎨 ANSI 색상 코드 설정
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[38;2;78;108;105m";   // 외곽선 (시안)
        final String YELLOW = "\u001B[33m"; // 아트워크 (노랑)
        final String GREEN = "\u001B[32m";  // 항목 라벨 (초록)
        final String MAGENTA = "\u001B[38;2;255;182;193m";// 강조 번호 (마젠타)
        final String BLUE = "\u001B[34m";   // 상태-공개 (파랑)
        final String RED = "\u001B[31m";    // 상태-비공개 (빨강)

        // 📝 데이터 포맷팅
        String statusText = "1".equals(status) ? BLUE + "🔹 공개" + RESET : RED + "❌ 비공개" + RESET;
        String formattedId = String.format("%04d", courseId);

        // 간격을 예쁘게 맞추기 위한 좌측 정렬 패딩 (약 12칸 할당)
        String paddedPrice = String.format("%-12s", String.format("%,d원", price));
        String paddedInstructor = String.format("%-12s", instructorId);

        // 🚀 좌우 분할 가로형 UI 조합
        return "\n" +
                CYAN + "  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                CYAN + "  ┃" + YELLOW + "  /\\_/\\      ____ ___  _   _ ____  ____  _____  " + CYAN + "" + MAGENTA + " 📌 [ NO." + formattedId + " ]\n" +
                CYAN + "  ┃" + YELLOW + " ( o.o )    / ___/ _ \\| | | |  _ \\/ ___|| ____| " + CYAN + "" + GREEN + " 📚 강좌 제목 : " + RESET + title + "\n" +
                CYAN + "  ┃" + YELLOW + "  > ^ <     | |  | | | | | | | |_) \\___ \\|  _|  " + CYAN + "" + GREEN + " 📃 강좌 설명 : " + RESET + description + "\n" +
                CYAN + "  ┃" + YELLOW + " /  _  \\    | |__| |_| | |_| |  _ < ___) | |___ " + CYAN + "" + GREEN + " 💰 강좌 가격 : " + RESET + paddedPrice + GREEN + " ✅ 개방 상태 : " + RESET + statusText + "\n" +
                CYAN + "  ┃" + YELLOW + " ( ( | |     \\____\\___/ \\___/|_| \\_\\____/|_____|" + CYAN + "" + GREEN + " 👨‍🏫 강사 번호 : " + RESET + paddedInstructor + GREEN + "  📅 등 록 일 : " + RESET + createdAt + "\n" +
                CYAN + "  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET;
    }
}
