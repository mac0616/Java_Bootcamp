package com.wanted.cleanarchitecture.catalog.application.service;

import com.wanted.cleanarchitecture.catalog.application.command.CreateCourseCommand;
import com.wanted.cleanarchitecture.catalog.application.command.AddSectionCommand;
import com.wanted.cleanarchitecture.catalog.application.usecase.CourseCommandUseCase;
import com.wanted.cleanarchitecture.catalog.domain.model.Course;
import com.wanted.cleanarchitecture.catalog.domain.repository.CourseRepository;
import com.wanted.cleanarchitecture.global.domain.common.exception.DomainRuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
// command 는 명령이기에 조회는 제외하고 삽입, 수정, 삭제만 진행할 것임.
public class CourseCommandService implements CourseCommandUseCase {

    private final CourseRepository repository;

    @Override
    public Long handle(CreateCourseCommand command) {

        /* 내부 코드는 UseCase 를 직접 수행하며,
        *  이벤트스토밍 단계에서 DomainEvent 를 (주황색) 수행한다.
        * */

        // Course 기존에는 엔티티 -> 이제는 순수 Java 클래스
        // service 계층은 usecase 를 조립하고, transactional 경계만을 담당한다.
        // 객체 생성은 해당 도메인 클래스 내부에서 진행하며, 메소드로만 호출한다.

        // 서비스 클래스에서 직접 new 로 Course 를 만들면 domain 계층을 침범하는 것이다.
        // domain에 강의 객체 생성하라고 지시
        Course newCourse = Course.create(command.authorId(), command.title(), command.description());

        // Repository에게 위에서 만든 객체 DB에 저장하도록 지시
        Course savedCourse = repository.save(newCourse);

        return savedCourse.getId();
    }

    @Override
    public void addSectionHandle(AddSectionCommand command) {
        Course course = repository.findById(command.courseId())
                .orElseThrow(() -> new DomainRuleViolationException("강의를 찾을 수 없습니다."));

        course.addSection(command.title(), command.sectionOrder());
        repository.save(course);
    }
}
