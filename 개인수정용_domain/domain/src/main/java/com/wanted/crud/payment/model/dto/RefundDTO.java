package com.wanted.crud.payment.model.dto;

import java.util.Date;

public class RefundDTO {
    private long refundId;
    private long refundAmount;
    private boolean refundStatus;
    private Date refundAt;
    private long paymentId;

    // ✨ 환불 승인서 전용 정밀 여백 계산기 (너비 46)
    private String padRight(String text) {
        int targetWidth = 46;
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= '가' && c <= '힣') width += 2;
            else width += 1;
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
        sb.append("  |   ____   _____  _____  _   _  _   _  ____  \n");
        sb.append("  |  |  _ \\ |  ___||  ___|| | | || \\ | ||  _ \\ \n");
        sb.append("  |  | |_) || |__  | |__  | | | ||  \\| || | | |\n");
        sb.append("  |  |  _ < |  __| |  __| | |_| || |\\  || |_| |\n");
        sb.append("  |  |_| \\_\\|_____||_|     \\___/ |_| \\_||____/ \n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  |      ").append(stampTop).append("\n");
        sb.append("  |      ").append(stampMid).append("  ID : #").append(String.format("%05d", refundId)).append("\n");
        sb.append("  |      ").append(stampBot).append("\n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  |   💰 환불 금액  : ").append(formattedAmount).append("\n");
        sb.append("  |   💳 결제 번호  : # ").append(String.format("%06d", paymentId)).append("\n");
        sb.append("  |   ✨ 처리 상태  : ").append(statusIcon).append("\n");
        sb.append("  |   📅 처리 일시  : ").append(refundAt != null ? refundAt : "확인 중 (Pending)").append("\n");
        sb.append("  '--------------------------------------------------\n");

        return sb.toString();
    }

    public long getRefundId() {
        return refundId;
    }

    public void setRefundId(long refundId) {
        this.refundId = refundId;
    }

    public long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public boolean isRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(boolean refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getRefundAt() {
        return refundAt;
    }

    public void setRefundAt(Date refundAt) {
        this.refundAt = refundAt;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public RefundDTO(long refundId, long refundAmount, boolean refundStatus, Date refundAt, long paymentId) {
        this.refundId = refundId;
        this.refundAmount = refundAmount;
        this.refundStatus = refundStatus;
        this.refundAt = refundAt;
        this.paymentId = paymentId;
    }
}

