package com.wanted.cleanarchitecture.catalog.presentation.api.request;

public record AddSectionRequest(
        String title,
        int sectionOrder
) {
}
