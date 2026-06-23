package com.wanted.crud.payment.model.dto;

import java.util.Date;

public class PaymentDTO {
    private long paymentId;
    private long paymentAmount;
    private String paymentMethod;
    private boolean status;
    private Date paidAt;
    private long studentId;
    private long courseId;

    // 강좌별 총수익 계산을 위한 필드
    private String courseTitle;
    private long totalRevenue;

    // ✨ [1단계] 여백 계산기: Java 버전에 상관없이 돌아가도록 수정했습니다!
    private String padRight(String text) {
        int targetWidth = 46; // 테두리 안쪽 너비
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= '가' && c <= '힣') width += 2; // 한글은 2칸
            else width += 1; // 나머지는 1칸
        }

        StringBuilder padded = new StringBuilder(text);
        while (width < targetWidth) {
            padded.append(" ");
            width++;
        }
        return padded.toString();
    }

    @Override
    public String toString() {
        // 1. 결제 상태 및 금액 포맷팅
        boolean isPaid = "1".equals(String.valueOf(status));
        String formattedAmount = String.format("%,d원", paymentAmount);
        String statusText = isPaid ? "✅ COMPLETE!" : "⏳ WAITING..";
        String paymentTitle = isPaid ? " [ PAYMENT SUCCESS ]" : " [ PAYMENT PENDING ]";

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("  .--------------------------------------------------\n");
        sb.append("  |   ____   _  _   _  _   _  ____  _  _  ____ \n");
        sb.append("  |  |  _ \\ / _\\ \\_/ /| \\_/ ||  __|| \\| ||_  _|\n");
        sb.append("  |  |  __/| [ ] | | || \\_/ ||  __|| \\  |  ||  \n");
        sb.append("  |  |_|   |_| |_|_| ||_| |_||____||_|\\_|  |_|  \n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  | ").append(paymentTitle).append("\n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  |   💰 결제 번호  : # ").append(String.format("%06d", paymentId)).append("\n");
        sb.append("  |   💵 결제 금액  : ").append(formattedAmount).append("\n");
        sb.append("  |   💳 결제 수단  : ").append(paymentMethod != null ? paymentMethod : "미지정").append("\n");
        sb.append("  |   🧑‍🎓 학생 번호  : ").append(studentId).append("번 회원\n");
        sb.append("  |   📘 강좌 번호  : ").append(courseId).append("번 강좌\n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  |   [상태] ").append(statusText).append(" [일시] ").append(paidAt != null ? paidAt : "결제 대기 중").append("\n");
        sb.append("  '--------------------------------------------------\n");

        return sb.toString();
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    // 강좌별 수익 조회
    public String getCourseTitle() { return courseTitle; }
    public long getTotalRevenue() { return totalRevenue; }

    public PaymentDTO(long paymentId, long paymentAmount, String paymentMethod, boolean status, Date paidAt, long studentId, long courseId) {
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paidAt = paidAt;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // 업데이트 DTO
    public PaymentDTO(long paymentAmount, String paymentMethod, boolean status, long studentId, long courseId) {

        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // 강좌별 수익 조회
    public PaymentDTO(long courseId, String courseTitle, long totalRevenue) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.totalRevenue = totalRevenue;
    }

    public String toCourseDashboardString() {
        return String.format("  📘 강좌: %-15s (ID: %d) | 💰 누적 결제금액: %,d 원",
                courseTitle, courseId, totalRevenue);
    }
}




