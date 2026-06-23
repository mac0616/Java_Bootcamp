package com.wanted.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileController {

    /* comment.
     *   ResourceLoader 는 classpath: static/.. 경로의 정적 리소스(image, css, js 등)
     *   를 읽어오거나, 실제 파일 시스템 경로로 변환할 때 사용된다.
     *  */
    @Autowired
    // 파일 경로를 찾아주는 스프링의 기본 도구(ResourceLoader)를 컨트롤러에서 사용하기 위해 부품을 자동 조립
    private ResourceLoader resourceLoader;

    @GetMapping(value={"/", "/main"})
    public String mainPage(){
        return "main";
    }

    /* comment.
     *   1. 업로드 된 파일과 설명값 확인
     *   2. 저장 경로 확인 및 없으면 생성
     *   3. 원본 파일명을 UUID 기반의 새 이름으로 변경
     *   4. 서버 경로에 실제 파일 저장
     *   5. 결과 메세지와 이미지 경로를 Model에 담아 result 페이지에서 응답
     *  */

    // UUID : 식별자라고 생각하면 된다.
    // ex) A가 가진 점심메뉴.png, B가 가진 점심메뉴.png
    // 이 경우에는 이미지 파일 이름이 동일하기 떄문에 A의 이미지 파일 이름을 수정하면
    // B가 가진 이미지 파일 이름도 수정이 된다.
    // 임의의 식별자를 만드는 것에 사용되는 것이 UUID 이다. 암호, 식별가능하게 해주는 것.
    @PostMapping("single-file")
    public String singleUpload(@RequestParam MultipartFile singleFile, @RequestParam String description, Model model) throws IOException {

        // 단순 화면에 값이 넘어오는 지 확인용
        System.out.println("singleFile = " + singleFile);
        System.out.println("description = " + description);

        // 입력한 파일을 저장할 경로를 지정
        // C:/Loader/module06
        // 사람들마다 절대경로가 다 다르므로 classpath:static/img/single 하위에 저장할거다
        Resource resource = resourceLoader.getResource("classpath:static/img/single");
        String filePath = null;

        if(!resource.exists()){
            // 위에 작성한 파일 저장 경로가 없으면 만들어주기
            String root = "src/main/resources/static/img/single";
            File file = new File(root); // 위 경로를 대상으로 파일객체 생성
            file.mkdirs(); // make directory 의 약자

            // 위에서 만든 경로를 filePath 변수에 담아주기
            filePath = file.getAbsolutePath();
        } else{
            filePath = resourceLoader
                    .getResource("classpath:static/img/single")
                    .getFile()
                    .getAbsolutePath();

        }

        // 파일의 원본 이름 추출 ex) test.png
        String originFileName = singleFile.getOriginalFilename();

        // 파일의 확장자 추출 ex) .png (. 기준 뒷쪽 추출)
        String ext = originFileName.substring(originFileName.lastIndexOf("."));

        // UUID 를 활용해서 중복되지 않는 파일명 생성
        // - UUID 는 하이픈(-) 이 들어있다. 하이픈 제거할 것.
        // - UUID 로 원본 파일 이름을 랜덤하게 바꾼 후 위 쪽에서 추출한 확장자 붙이기
        // ex) 스크린샷.png -> 23123as313sasd.png
        String savedName = UUID
                            .randomUUID()
                            .toString()
                            .replace("-", "")    // 하이픈 제거
                            + ext;                               // 확장자 붙여주기

        // 업로드한 파일을 filePath 디렉토리 하위에 savedName 이라는 이름으로 변호나(저장)
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));
            model.addAttribute("message", "파일 업로드 성공!");
            // 파일 저장 경로  view로 전달
            model.addAttribute("img", "static/img/single/" + savedName);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage() + "이유로 업로드 실패");
        }

        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam("multiFile") List<MultipartFile> multipartFile, String description, Model model) throws IOException {
                                    // MultipartFile = 1개만 넣을 수 있어 리스트 사용.

        System.out.println("multipartFile = " + multipartFile);
        System.out.println("description = " + description);

        // 파일 저장 경로 설정
        Resource resource = resourceLoader.getResource("classpath:static/img/multi");
        String filePath = null;

        if (!resource.exists()) {
            String root = "src/main/resources/static/img/multi";
            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            filePath = resourceLoader.getResource("classpath:static/img/multi")
                                     .getFile().getAbsolutePath();
        }

        // 여러 개의 파일을 처리하기 위한 DTO 객체 활용
        List<FileDTO> files = new ArrayList<>();
        // UUID 변환한 문자열 저장용
        List<String> savedFiles = new ArrayList<>();

        try {
            // 전달 받은 여러 개의 파일을 반복
            for (MultipartFile file : multipartFile) {

                String originName = file.getOriginalFilename();
                String ext = originName.substring(originName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

                files.add(new FileDTO(originName, savedName, filePath, description));

                file.transferTo(new File(filePath + "/" + savedName));
                savedFiles.add("static/img/multi/" + savedName);
                // img th:src = static/img/multi3123123313123.png
                // img th:src = static/img/multi/3123123313123.png
            }

            model.addAttribute("message", "멀티 파일 업로드 성공!");
            // savedFiles => 여러 개의 파일이 저장되어 있는 경로
            model.addAttribute("imgs", savedFiles);
        } catch (Exception e) {

            // 예를 들어 5개의 파일을 넣었는데
            // 4개는 정상적으로 저장되고, 1개만 안되면?
            // ex) Transaction 의 rollback 처럼 동작하게 만들 것이다.
            // 실패 시에 이전에 저장된 파일을 삭제(정상적으로 저장된 4개 파일)

            for (FileDTO file : files){
                // 경로에 위치한 차일을 찾아 delete();
                new File(filePath + "/" + file.getSavedName()).delete();
            }
            model.addAttribute("message" , "멀티 파일 업로드 실패!");
        }

        return "result";
    }

}
