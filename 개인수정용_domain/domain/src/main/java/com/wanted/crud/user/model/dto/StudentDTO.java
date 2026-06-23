package com.wanted.crud.user.model.dto;

public class StudentDTO {

    private Long studentId;
    private Long userNo;   // FK

    public StudentDTO() {}

    public StudentDTO(Long studentId, Long userNo) {
        this.studentId = studentId;
        this.userNo = userNo;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        // 한글과 이모지가 포함된 경우 길이를 맞추기 위해 여유 있게 포맷팅 (20칸)
        String idStr = String.format("%-20s", studentId);
        String userNoStr = String.format("%-20s", userNo);

        return "\n" +
                "      (\\__/)   ____ _____ _   _ ____  _____ _   _ _____ \n" +
                "      ( •ㅅ•)  / ___|_   _| | | |  _ \\| ____| \\ | |_   _|\n" +
                "      / 　 ▷ |___ \\  | | | | | | | | |  _| |  \\| | | |  \n" +
                "     |          ___) | | | | |_| | |_| | |___| |\\  | | |  \n" +
                "     |         |____/  |_|  \\___/|____/|_____|_| \\_| |_|  \n" +
                "      \\__________________________________________________________\n" +
                "       |                                                          \n" +
                "       |  🆔 학생 고유 ID : " + idStr + "        |\n" +
                "       |  👤 사용자 번호  : " + userNoStr + "        |\n" +
                "       |__________________________________________________________";
    }
}