package com.wanted.crud.user.model.dto;

import java.util.Date;

public class InstructorDTO {

    private Long instructorId;
    private Date createdAt;
    private Long userNo;  // FK

    public InstructorDTO() {}

    public InstructorDTO(Long instructorId, Date createdAt, Long userNo) {
        this.instructorId = instructorId;
        this.createdAt = createdAt;
        this.userNo = userNo;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        // 데이터 포맷팅 (정렬을 위해 20칸 확보)
        String idStr = String.format("%-20s", instructorId);
        String dateStr = String.format("%-20s", createdAt);
        String userNoStr = String.format("%-20s", userNo);

        return "\n" +
                "   .--.              .--.     ___ _   _ ____ _____ ____  _   _  ____ _____ ___  ____  \n" +
                "  : (\\ \\____________/ /) :   |_ _| \\ | / ___|_   _|  _ \\| | | |/ ___|_   _/ _ \\|  _ \\ \n" +
                "  '.    /          \\    .'    | ||  \\| \\___ \\ | | | |_) | | | | |     | || | | | |_) |\n" +
                "    :_ |            | _:      | || |\\  |___) || | |  _ <| |_| | |___  | || |_| |  _ < \n" +
                "     '.'\\  ●    ●  /'鞠      |___|_| \\_|____/ |_| |_| \\_\\\\___/ \\____| |_| \\___/|_| \\_\\\n" +
                "        ]    ▽    [          ==========================================================\n" +
                "        '.__ _ __.'           🆔 강사 고유 ID : " + idStr + "\n" +
                "           /  _  \\            📅 최초 등록일  : " + dateStr + "\n" +
                "          /  / \\  \\           👤 사용자 번호  : " + userNoStr + "\n" +
                "         (__/   \\__)         ==========================================================";
    }
}