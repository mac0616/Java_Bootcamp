package com.wanted.fileupload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {

    // 파일 관력 로직 처리 시 필수 4가지.
    private String originFileName;      // 원본 파일명
    private String savedName;           // 저장시 이름
    private String filePath;            // 파일 걍로
    private String description;         // 설명

}
