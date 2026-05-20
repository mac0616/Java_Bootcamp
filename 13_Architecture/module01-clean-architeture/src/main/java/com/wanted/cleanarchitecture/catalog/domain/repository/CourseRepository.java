package com.wanted.cleanarchitecture.catalog.domain.repository;

import com.wanted.cleanarchitecture.catalog.domain.model.Course;
import com.wanted.cleanarchitecture.catalog.domain.model.CourseSection;

import java.util.Optional;

public interface CourseRepository {
    Course save(Course newCourse);

    // 강의 상세 조회
    Optional<Course> findById(Long courseId);

//    CourseSection saveSection(CourseSection newCourseSection);

}
