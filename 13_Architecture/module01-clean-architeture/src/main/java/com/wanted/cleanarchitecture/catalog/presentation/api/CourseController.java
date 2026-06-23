package com.wanted.cleanarchitecture.catalog.presentation.api;

import com.wanted.cleanarchitecture.catalog.application.command.AddSectionCommand;
import com.wanted.cleanarchitecture.catalog.application.command.CreateCourseCommand;
import com.wanted.cleanarchitecture.catalog.application.usecase.CourseCommandUseCase;
import com.wanted.cleanarchitecture.catalog.application.usecase.CourseQueryUseCase;
import com.wanted.cleanarchitecture.catalog.presentation.api.request.AddSectionRequest;
import com.wanted.cleanarchitecture.catalog.presentation.api.request.CreateCourseRequest;
import com.wanted.cleanarchitecture.catalog.presentation.api.response.CreateCourseResponse;
import com.wanted.cleanarchitecture.global.presentation.api.common.ApiResponse;
import com.wanted.cleanarchitecture.global.presentation.api.common.ApiResponseCode;
import com.wanted.cleanarchitecture.global.presentation.api.common.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    // command (dml)
    private final CourseCommandUseCase courseCommandUseCase;
    // query (read)
    private final CourseQueryUseCase courseQueryUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCourseResponse>> createCourse(@RequestBody CreateCourseRequest request) {
        // 요청 (Request) 받아서 Service에서 처리할 수 있도록 command로 바꿈.
        Long courseId = courseCommandUseCase.handle(new CreateCourseCommand(
                request.authorId(),
                request.title(),
                request.description()
        ));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(
                        ApiResponseCode.COURSE_CREATED,
                        ApiResponseMessage.COURSE_CREATED,
                        new CreateCourseResponse(courseId)
//                        "COURSE_CREATED" // 이미 공통으로 쓰는거 만들어놔서 하드코딩 안 해도 갖다 쓰면 됨
                ));
    }

    @GetMapping("/{courseId}")
    ResponseEntity<ApiResponse<CourseQueryUseCase.CourseView>> getCourse(@PathVariable Long courseId) {

        return ResponseEntity.ok(ApiResponse.success(
                ApiResponseCode.SUCCESS,
                ApiResponseMessage.SUCCESS,
                courseQueryUseCase.handle(courseId)
        ));
    }

    @PostMapping("/{courseId}/sections")
    public ResponseEntity<ApiResponse<Void>> addSection(
            @PathVariable Long courseId,
            @RequestBody AddSectionRequest request
    ) {
        courseCommandUseCase.addSectionHandle(
                new AddSectionCommand(courseId, request.title(), request.sectionOrder())
        );
        return ResponseEntity.ok(
                ApiResponse.success(
                        ApiResponseCode.COURSE_SECTION_CREATED,
                        ApiResponseMessage.COURSE_SECTION_CREATED,
                        null
                )
        );
    }

}