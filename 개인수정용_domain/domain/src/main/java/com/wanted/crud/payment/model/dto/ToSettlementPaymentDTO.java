package com.wanted.crud.payment.model.dto;

public class ToSettlementPaymentDTO {

    private long rawAmount; // 기존 paymentAmount에서 변경
    private long courseId;

    public ToSettlementPaymentDTO(long rawAmount, long courseId) {
        this.rawAmount = rawAmount;
        this.courseId = courseId;
    }

    // Getter & Setter
    public long getRawAmount() {
        return rawAmount;
    }

    public void setRawAmount(long rawAmount) {
        this.rawAmount = rawAmount;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        // 🎨 ANSI 색상 및 스타일 코드 정의
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";      // 테두리
        final String B_WHITE = "\u001B[97m";   // 토끼, 값 텍스트
        final String B_YELLOW = "\u001B[93m";  // 타이틀, 토끼 눈(돈)
        final String B_GREEN = "\u001B[92m";   // 결제 원금 라벨 (돈이니까 초록색!)
        final String B_MAGENTA = "\u001B[95m"; // 강좌 번호 라벨

        // 📝 데이터 포맷팅
        String formattedCourseId = String.format("%04d", courseId);
        String formattedAmount = String.format("%,d원", rawAmount);

        // 🚀 돈을 안고 있는 토끼 + 컴팩트 와이드 정산 패널 조합
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(CYAN).append("  ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);
        sb.append(CYAN).append("  ┃ ").append(B_YELLOW).append(" 💰 [ 정산 대기 데이터 전송 (To Settlement) ]\n").append(RESET);
        sb.append(B_WHITE).append("     (\\__/)  ").append(CYAN).append("      ┃ \n").append(RESET);
        sb.append(B_WHITE).append("     (").append(B_YELLOW).append("💲_💲").append(B_WHITE).append(") ").append(CYAN).append("      ┃ ").append(B_MAGENTA).append("🏷️ 대상 강좌 : ").append(B_WHITE).append("NO.").append(formattedCourseId).append("\n").append(RESET);
        sb.append(B_WHITE).append("    / つ 💰つ").append(CYAN).append("      ┃ ").append(B_GREEN).append("💸 결제 원금 : ").append(B_WHITE).append(formattedAmount).append("\n").append(RESET);
        sb.append(CYAN).append("  ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n").append(RESET);

        return sb.toString();
    }
}