package com.wanted.crud.settlement.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettlementDTO {

    private Long settlementId;
    private Long courseId;
    private Date settlementDate;
    private Long instructorId;
    private Long rawAmount; //원금
    private Long commssion; //강사의 돈 (오타 그대로 유지)
    private Long finalAmount; //관리자의 돈
    private String status;

    public SettlementDTO(Long settlementId, Long courseId, Date settlementDate, Long instructorId, Long rawAmount, Long commssion, Long finalAmount, String status) {
        this.settlementId = settlementId;
        this.courseId = courseId;
        this.settlementDate = settlementDate;
        this.instructorId = instructorId;
        this.rawAmount = rawAmount;
        this.commssion = commssion;
        this.finalAmount = finalAmount;
        this.status = status;
    }

    public SettlementDTO(Long settlementId, String status, Date settlementDate, Long instructorId, Long courseId) {
        this.settlementId = settlementId;
        this.courseId = courseId;
        this.settlementDate = settlementDate;
        this.instructorId = instructorId;
        this.status = status;
    }

    public Long getSettlementId() { return settlementId; }
    public void setSettlementId(Long settlementId) { this.settlementId = settlementId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Date getSettlementDate() { return settlementDate; }
    public void setSettlementDate(Date settlementDate) { this.settlementDate = settlementDate; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    public Long getRawAmount() { return rawAmount; }
    public void setRawAmount(Long rawAmount) { this.rawAmount = rawAmount; }
    public Long getCommssion() { return commssion; }
    public void setCommssion(Long commssion) { this.commssion = commssion; }
    public Long getFinalAmount() { return finalAmount; }
    public void setFinalAmount(Long finalAmount) { this.finalAmount = finalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";      // 테두리
        final String B_WHITE = "\u001B[97m";   // 고양이, 금액 숫자
        final String B_YELLOW = "\u001B[93m";  // 타이틀, 강사/강좌 정보
        final String B_GREEN = "\u001B[92m";   // 강사 수익 (초록)
        final String RED = "\u001B[31m";       // 플랫폼 수익 (빨강 포인트)
        final String B_MAGENTA = "\u001B[95m"; // 정산 번호, 총 매출
        final String B_BLUE = "\u001B[94m";    // 완료 상태

        // 📝 1. Null 방어 및 금액 포맷팅 (콤마 추가)
        String formattedId = (settlementId != null) ? String.format("%04d", settlementId) : "----";
        String formattedRaw = (rawAmount != null) ? String.format("%,d원", rawAmount) : "0원";
        String formattedCommission = (commssion != null) ? String.format("%,d원", commssion) : "0원";
        String formattedFinal = (finalAmount != null) ? String.format("%,d원", finalAmount) : "0원";

        String instructorStr = (instructorId != null) ? String.format("%04d", instructorId) : "----";
        String courseStr = (courseId != null) ? String.format("%04d", courseId) : "----";

        // 📝 2. 상태 및 날짜 포맷팅
        boolean isDone = "DONE".equals(status);
        String statusIcon = isDone ? B_BLUE + "✅ 정산 완료" + RESET : RED + "⏳ 정산 대기" + RESET;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String safeDate = (settlementDate != null) ? sdf.format(settlementDate) : "미정";

        // 🚀 3. 똑똑한 회계사 고양이 + 영수증 레이아웃 조합
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(CYAN).append("  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_YELLOW).append("  [ 📊 SETTLEMENT REPORT ]  ").append(CYAN).append("       ").append(B_MAGENTA).append("🏷️ 정산 번호 : S-").append(formattedId).append("       ").append(statusIcon).append("\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("     /\\___/\\                ").append(CYAN).append("       ").append(B_YELLOW).append("👨‍🏫 강사 번호 : NO.").append(instructorStr).append("    ").append(CYAN).append("  📘 강좌 번호 : NO.").append(courseStr).append("\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("    (  • _ •)   ").append(B_YELLOW).append(" ACCOUNTING ").append(CYAN).append("       ").append(B_GREEN).append("📅 정산 일자 : ").append(B_WHITE).append(safeDate).append("\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("    / > 🧮 < \\  ").append(B_YELLOW).append("   CLEARED! ").append(CYAN).append("      ────────────────────────────────────────────────────────────────\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("   |__________|             ").append(CYAN).append("       ").append(B_MAGENTA).append("💰 총 매출액 : ").append(B_WHITE).append(formattedRaw).append("\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_WHITE).append("                            ").append(CYAN).append("       ").append(B_GREEN).append("💸 강사 정산금 : ").append(B_WHITE).append(formattedFinal).append("   |   ").append(RED).append("🏢 플랫폼 수수료 : ").append(B_WHITE).append(formattedCommission).append("\n").append(RESET);
        sb.append(CYAN).append("  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);

        return sb.toString();
    }
}