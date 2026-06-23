package com.wanted.cleanarchitecture.catalog.application.command;

public record AddSectionCommand(
        Long courseId,
        String title,
        int sectionOrder
) {
}
