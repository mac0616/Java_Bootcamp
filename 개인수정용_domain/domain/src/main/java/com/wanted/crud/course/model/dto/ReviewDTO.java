package com.wanted.crud.course.model.dto;

import java.util.Date;

public class ReviewDTO {

    private Long reviewId;
    private Long rating;
    private Date createdAt;
    private Long courseId;
    private Long studentId;



    public ReviewDTO(Long reviewId, Long rating, Date createdAt, Long courseId, Long studentId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // ✨ 말풍선용 여백 계산기
    private String padRightBubble(String text) {
        int targetWidth = 42; // 말풍선 안쪽 너비
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= '가' && c <= '힣') width += 2;
            else width += 1;
        }
        int padding = targetWidth - width;
        if (padding > 0) return text + " ".repeat(padding);
        return text;
    }

    @Override
    public String toString() {
        // ✨ 별점(rating) 숫자에 맞춰 진짜 별(★)을 그려주는 로직
        int starCount = 0;
        try {
            // rating이 Double이든 String이든 안전하게 처리
            double r = Double.parseDouble(String.valueOf(rating));
            starCount = (int) Math.round(r);
            starCount = Math.max(0, Math.min(5, starCount)); // 0~5 사이로 고정
        } catch (Exception e) {
            starCount = 0;
        }
        String stars = "★".repeat(starCount) + "☆".repeat(5 - starCount);

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("   💬 [ REVIEW NO.").append(String.format("%04d", reviewId)).append(" ]\n");
        sb.append("  ╭──────────────────────────────────────────\n");
        sb.append("  │  📚 강좌 번호 : ").append(courseId).append("\n");
        sb.append("  │  ⭐ 강좌 별점 : ").append(stars).append(" (").append(rating).append("점)\n");
        sb.append("  │  📅 작 성 일  : ").append(createdAt).append("\n");
        sb.append("  ╰──────────────────────────────────────────\n");
        sb.append("      \\\n");
        sb.append("       \\   O \n");
        sb.append("          /|\\   <-- [ 작성자 : 수강생 NO.").append(studentId).append(" ]\n");
        sb.append("          / \\ \n");

        return sb.toString();
    }
}
