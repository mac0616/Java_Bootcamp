package com.wanted.crud.course.model.dto;

public class CourseReviewDTO {

    private Long courseId;
    private String title;
    private String description;
    private Long price;
    private Double avgRating;

    public CourseReviewDTO() {}

    public CourseReviewDTO (Long courseId, String title, String description, Long price, Double avgRating){
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.avgRating = avgRating;
    }

    // Getter
    public Long getCourseId(){ return courseId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public Long getPrice(){ return price; }
    public Double getAvgRating(){ return avgRating; }

    // Setter
    public void setCourseId(Long courseId){ this.courseId = courseId; }
    public void setTitle(String title){ this.title = title; }
    public void setDescription(String description){ this.description = description; }
    public void setPrice(Long price){ this.price = price; }
    public void setAvgRating(Double avgRating){ this.avgRating = avgRating; }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String B_WHITE = "\u001B[97m";   // 곰돌이/텍스트
        final String CYAN = "\u001B[38;2;78;108;105m";      // 테두리
        final String B_YELLOW = "\u001B[93m";  // 별/라벨
        final String B_GREEN = "\u001B[92m";   // 설명/제목
        final String B_MAGENTA = "\u001B[38;2;255;182;193m";; // 번호/가격
        final String B_CYAN = "\u001B[96m";    // 테마 텍스트
        final String RED = "\u001B[31m";       // 리뷰 없음 상태

        // 📝 1. 데이터 포맷팅
        String formattedCourseId = courseId != null ? String.format("%04d", courseId) : "----";
        String formattedPrice = price != null ? String.format("%,d원", price) : "0원";

        // 🚀 2. 예쁜 시각적 별점(Star Rating) 로직
        String ratingBar;
        if (avgRating != null && avgRating > 0) {
            int fullStars = (int) Math.round(avgRating); // 반올림하여 꽉 찬 별 개수 계산
            StringBuilder stars = new StringBuilder(B_YELLOW);
            for (int i = 0; i < 5; i++) {
                if (i < fullStars) stars.append("★"); // 채워진 별
                else stars.append("☆");             // 빈 별
            }
            stars.append(RESET).append(" (").append(String.format("%.1f", avgRating)).append(")");
            ratingBar = stars.toString();
        } else {
            ratingBar = RED + "❌ 등록된 강좌 별점이 없습니다." + RESET;
        }

        // 🎨 3. 리뷰 곰돌이 + 프리미엄 UI 레이아웃 조합
        return "\n" +
                CYAN + "  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                CYAN + "  ┃ " + B_YELLOW + "    * . 🌟 . * " + CYAN + "                 " + B_MAGENTA + "🏷️ [ 강좌 번호 : " + formattedCourseId + " ]   " + B_WHITE + "📚 강좌 제목 : " + RESET + title + "\n" +
                CYAN + "  ┃ " + B_WHITE + "     ʕ • ᴥ • ʔ    " + CYAN + "  " + B_YELLOW + " COURSE " + CYAN + "    " + B_CYAN + "💬 강좌 설명 : " + RESET + description + "\n" +
                CYAN + "  ┃ " + B_WHITE + "     /   ★   \\    " + CYAN + "  " + B_YELLOW + " REVIEW " + CYAN + "   " + B_GREEN + "💰 강좌 가격 : " + RESET + formattedPrice + "\n" +
                CYAN + "  ┃ " + B_WHITE + "    (____/____)   " + CYAN + "              " + B_MAGENTA + "⭐ 강좌 별점 : " + ratingBar + "\n" +
                CYAN + "  ┃ " + B_YELLOW + "      * ﾟ + . * " + CYAN + "                   \n" +
                CYAN + "  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET;
    }
}