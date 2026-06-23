package com.wanted.crud.course.model.dto;

import java.util.Date;

public class SectionDTO {

    private Long sectionId;
    private String title;
    private String videoUrl;
    private String materialUrl;
    private Date createdAt;
    private Long courseId;

    public SectionDTO() {}

    public SectionDTO(Long sectionId, String title, String videoUrl, String materialUrl, Date createdAt, Long courseId) {
        this.sectionId = sectionId;
        this.title = title;
        this.videoUrl = videoUrl;
        this.materialUrl = materialUrl;
        this.createdAt = createdAt;
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
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

    // ✨ SectionDTO 전용 여백 계산기 (테두리 철통 방어!)
    private String padRight(String text) {
        int targetWidth = 58; // 플레이어 안쪽 너비
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= '가' && c <= '힣') width += 2; // 한글은 2칸
            else width += 1; // 나머지는 1칸
        }
        int padding = targetWidth - width;
        if (padding > 0) return text + " ".repeat(padding);
        return text;
    }

    @Override
    public String toString() {
        // 테두리가 없으므로 길이를 좀 더 넉넉하게 보여줍니다.
        String safeTitle = (title != null) ? title : "제목 없음";
        String safeVideo = (videoUrl != null) ? videoUrl : "링크 없음";
        String safeMaterial = (materialUrl != null) ? materialUrl : "자료 없음";

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("  ╭────────────────────────────────────────────────────────────────────────\n");
        sb.append("  │  ▶  NOW PLAYING...  [ LEC NO.").append(String.format("%04d", sectionId)).append(" ]\n");
        sb.append("  ├────────────────────────────────────────────────────────────────────────\n");
        sb.append("  │  🎬 강의 제목 : ").append(safeTitle).append("\n");
        sb.append("  │  🔗 강의 영상 : ").append(safeVideo).append("\n").append("     📂 강의 자료 : ").append(safeMaterial).append("\n");
        sb.append("  │  📅 등 록 일  : ").append(createdAt).append("\n").append("     📁 소속 강좌 : ").append(courseId).append("번 강좌\n");
        sb.append("  ├────────────────────────────────────────────────────────────────────────\n");
        sb.append("  │  04:22  ━━━━━━━●────────────────────────────────────────  22:02  🔉\n");
        sb.append("  ╰────────────────────────────────────────────────────────────────────────\n");

        return sb.toString();
    }
}
