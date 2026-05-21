package com.wanted.cleanarchitecture.catalog.domain.model;

import com.wanted.cleanarchitecture.catalog.domain.event.CoursePublishedEvent;
import com.wanted.cleanarchitecture.global.domain.common.event.DomainEvent;
import com.wanted.cleanarchitecture.global.domain.common.exception.DomainRuleViolationException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseTest {

    @Test
    void publishChangesStatusAndRecordsDomainEvent() {
        CourseSection section = CourseSection.create("Introduction", 1);
        section.addModule("Why Boundaries Matter", ContentType.TEXT, 1);
        Course course = Course.restore(
                1L,
                100L,
                "Clean Architecture",
                "DDD sample",
                CourseStatus.DRAFT,
                List.of(section)
        );
        Instant occurredAt = Instant.parse("2026-05-19T00:00:00Z");

        course.publish(occurredAt);

        assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
        assertThat(course.pullDomainEvents())
                .containsExactly(new CoursePublishedEvent(1L, occurredAt));
    }

    @Test
    void publishFailsWhenCourseHasNoModule() {
        Course course = Course.create(100L, "Clean Architecture", "DDD sample");
        course.addSection("Introduction", 1);

        assertThatThrownBy(() -> course.publish(Instant.parse("2026-05-19T00:00:00Z")))
                .isInstanceOf(DomainRuleViolationException.class)
                .hasMessage("Course requires at least one module.");
    }

    @Test
    void pulledDomainEventsAreCleared() {
        CourseSection section = CourseSection.create("Introduction", 1);
        section.addModule("Why Boundaries Matter", ContentType.TEXT, 1);
        Course course = Course.restore(
                1L,
                100L,
                "Clean Architecture",
                "DDD sample",
                CourseStatus.DRAFT,
                List.of(section)
        );

        course.publish(Instant.parse("2026-05-19T00:00:00Z"));
        List<DomainEvent> firstPull = course.pullDomainEvents();
        List<DomainEvent> secondPull = course.pullDomainEvents();

        assertThat(firstPull).hasSize(1);
        assertThat(secondPull).isEmpty();
    }
}
