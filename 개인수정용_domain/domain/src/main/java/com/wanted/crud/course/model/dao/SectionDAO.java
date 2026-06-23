package com.wanted.crud.course.model.dao;


import com.wanted.crud.course.model.dto.SectionDTO;
import com.wanted.crud.global.utils.CourseQueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {


    private final Connection connection;

    public SectionDAO (Connection connection) {
        this.connection = connection;
    }

    // 내 강의 등록
    // title, video_url, material_url, created_at, course_id
    public Long sectionSave(SectionDTO newSection) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.insertSection");

        try (PreparedStatement pstmt = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newSection.getTitle());
            pstmt.setString(2, newSection.getVideoUrl());
            pstmt.setString(3, newSection.getMaterialUrl());
            pstmt.setLong(4, newSection.getCourseId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return null;
    }


    // 강의 수정
    public List<SectionDTO> selectSectionsByCourseId(long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("section.selectSectionsByCourseId");
        List<SectionDTO> sections = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                sections.add(new SectionDTO(
                        rset.getLong("section_id"),
                        rset.getString("title"),
                        rset.getString("video_url"),
                        rset.getString("material_url"),
                        rset.getDate("created_at"),
                        rset.getLong("course_id")
                ));
            }
        }
        return sections;
    }

    // 강의 정보 수정
    public int updateSection(SectionDTO section, Long instructorId) throws SQLException {
        String query = CourseQueryUtil.getQuery("section.updateSection");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, section.getTitle());
            pstmt.setString(2, section.getVideoUrl());
            pstmt.setString(3, section.getMaterialUrl());
            pstmt.setLong(4, section.getSectionId());
            pstmt.setLong(5, instructorId);

            return pstmt.executeUpdate();
        }
    }


}
