package com.wanted.crud.course.model.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseSectionDTO {

    private Long courseId;
    private String title;
    private String description;
    private Long price;
    private String status;
    private Date createdAt;
    private Long instructorId;

    // 강좌에 포함된 섹션 목록을 저장하는 리스트
    private List<SectionDTO> sections = new ArrayList<>();

    public CourseSectionDTO() {}

    public CourseSectionDTO(Long courseId, String title, String description, Long price, String status, Date createdAt, Long instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.instructorId = instructorId;
    }

    // Getter & Setter
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    public List<SectionDTO> getSections() { return sections; }
    public void setSections(List<SectionDTO> sections) { this.sections = sections; }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String B_WHITE = "\u001B[97m";   // 텍스트/강아지
        final String CYAN = "\u001B[38;2;78;108;105m";      // 테두리
        final String B_YELLOW = "\u001B[93m";  // 라벨/강아지 볼터치
        final String B_GREEN = "\u001B[92m";   // 제목/상태
        final String B_MAGENTA = "\u001B[38;2;255;182;193m"; // 번호/섹션
        final String B_CYAN = "\u001B[96m";    // 설명
        final String RED = "\u001B[31m";       // 비공개

        // 📝 1. 데이터 포맷팅
        String formattedCourseId = courseId != null ? String.format("%04d", courseId) : "----";
        String statusText = "1".equals(status) ? B_GREEN + "✅ 공개" + RESET : RED + "❌ 비공개" + RESET;
        String formattedPrice = price != null ? String.format("%,d원", price) : "0원";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = (createdAt != null) ? sdf.format(createdAt) : "----";

        // 좌우 간격을 맞추기 위한 수동 패딩 (가격 부분을 일정 너비로 고정)
        String pricePad = String.format("%-15s", formattedPrice);

        // 🚀 2. 와이드 UI & 하단 섹션 목록 조합
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(CYAN).append("  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("    /^ ^\\    ").append(CYAN).append("  ").append(B_YELLOW).append(" COURSE ").append(CYAN).append("    ").append(B_MAGENTA).append("🏷️ [ 강좌 번호: ").append(formattedCourseId).append(" ]    ").append(B_WHITE).append("📚 강좌 제목 : ").append(RESET).append(title).append("\n");
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("   ( ◕ᴥ◕ )   ").append(CYAN).append("  ").append(B_YELLOW).append(" DETAILS").append(CYAN).append("    ").append(B_CYAN).append("💬 강좌 설명 : ").append(RESET).append(description).append("\n");
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("   /  ~  \\   ").append(CYAN).append("              ").append(B_YELLOW).append("💰 강좌 가격 : ").append(RESET).append(pricePad).append(B_GREEN).append("📌 개방 상태 : ").append(RESET).append(statusText).append("\n");
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("   \\____ /   ").append(CYAN).append("              ").append(B_YELLOW).append("📅 등 록 일  : ").append(RESET).append(dateStr).append("     ").append(B_MAGENTA).append("👨‍🏫 강사 번호 : ").append(RESET).append(instructorId).append("\n");
        sb.append(CYAN).append("  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("📺 [ 포함된 세부 강의 목록 ]\n");

        if (sections == null || sections.isEmpty()) {
            sb.append(CYAN).append("  ┃ ").append(RED).append("  ❌ 텅~ (아직 등록된 강의가 없습니다.)\n").append(RESET);
        } else {
            int count = 1;
            for (Object section : sections) {
                // section.toString() 결과를 예쁘게 가져옵니다.
                String secStr = String.valueOf(section);
                if (secStr.contains("{")) {
                    secStr = secStr.substring(secStr.indexOf("{") + 1, secStr.lastIndexOf("}"));
                }
                sb.append(CYAN).append("  ┃ ").append(B_MAGENTA).append("  🎬 ").append(count).append("강 : ").append(RESET).append(secStr).append("\n");
                count++;
            }
        }
        sb.append(CYAN).append("  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);

        return sb.toString();
    }
}