package com.wanted.crud.user.model.dto;

import java.util.Date;

public class UserDTO {
    private Long userNo;
    private String userId;
    private String userPassword;
    private String userName;
    private String userPhoneNumber;
    private Long userPrice;
    private String userRole;
    private Date createdAt;
    private boolean status;


    @Override
    public String toString() {
        // 항목 정렬 폭 설정
        String noStr    = String.format("%-22s", userNo);
        String idStr    = String.format("%-22s", userId);
        String nameStr  = String.format("%-22s", userName);
        String phoneStr = String.format("%-22s", userPhoneNumber);
        String priceStr = String.format("%-22s", userPrice + "원");
        String roleStr  = String.format("%-22s", userRole);
        String dateStr  = String.format("%-22s", createdAt);
        String statStr  = String.format("%-22s", status);

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        // 🚀 [왕귀 포인트] 볼에 해바라기씨를 가득 넣은 햄찌
//        sb.append("       ╲  |  /           \n");
//        sb.append("        \\_|_/            \n");
//        sb.append("       (  -  )           \n");
//        sb.append("      (  ◕ ω ◕ )  <-- 꾸꾹.. 유저 리포트 배달왔찌! \n");
//        sb.append("     o(  :   :  )o       \n");
//        sb.append("       (  m m  )____     \n");


        sb.append("         (\\_/)  🎓           \n");
        sb.append("         ( •ᴥ•)  * ﾟ + . * \n");
        sb.append("         / > 📜c < -- 유저 리포트 배달왔끼! \n");
        sb.append("        (____|____)          \n");
        sb.append("  .----/       \\--------------------------------------\n");
        sb.append("  |        [ USER INFORMATION REPORT ]\n");
        sb.append("  ├--------------------------------------------------\n");
        sb.append("  |  [기본 정보]\n");
        sb.append("  |  👤 사용자 번호 : ").append(noStr).append("\n");
        sb.append("  |  🆔 아이디     : ").append(idStr).append("\n");
        sb.append("  |  📛 이름       : ").append(nameStr).append("\n");
        sb.append("  |  📞 연락처     : ").append(phoneStr).append("\n");
        sb.append("  | \n");
        sb.append("  |  [계정 상태]\n");
        sb.append("  |  💰 보유 금액  : ").append(priceStr).append("\n");
        sb.append("  |  🎖️ 권한 등급  : ").append(roleStr).append("\n");
        sb.append("  |  📅 가입 일자  : ").append(dateStr).append("\n");
        sb.append("  |  ✅ 현재 상태  : ").append(statStr).append("\n");
        sb.append("  '----------------------------------------- 🐾 🐾 --\n");

        return sb.toString();
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Long getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(Long userPrice) {
        this.userPrice = userPrice;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public UserDTO(Long userNo, String userId, String userPassword, String userName, String userPhoneNumber, Long userPrice, String userRole, Date createdAt, boolean status) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userPrice = userPrice;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.status = status;
    }

    // 로그인 전용 생성자입니다.
    public UserDTO(Long userNo, String userId, String password, String role) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPassword = password;
        this.userRole = role;
    }


    public UserDTO(Long userNo, String userId, String password, String role, String userPhoneNumber) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPassword = password;
        this.userRole = role;
    }

    // 아이디, 비번 찾기 생성자
    public UserDTO(String userId, String password) {
        this.userId = userId;
        this.userPassword = password;
        //this.userRole = role;
    }



}
