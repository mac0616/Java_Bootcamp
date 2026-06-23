package com.wanted.crud.course.model.dao;

import com.wanted.crud.enrollment.model.dto.EnrollmentStudentDTO;
import com.wanted.crud.global.utils.CourseQueryUtil;
import com.wanted.crud.global.utils.UserQueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private final Connection connection;

    public ReviewDAO(Connection connection){
        this.connection = connection;
    }

    // 수강 완료 강좌 목록
    public List<EnrollmentStudentDTO> completeCourses(Long studentId) throws SQLException {
        String query = CourseQueryUtil.getQuery("review.completedCourses");
        List<EnrollmentStudentDTO> completeList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()){
                completeList.add(new EnrollmentStudentDTO(
                        rset.getLong("course_id"),
                        rset.getString("title"),
                        rset.getLong("progress_rate")
                ));
            }
        }
        return completeList;
    }

    // 리뷰 중복 작성 검사
    public int checkReviewExists(Long studentId, Long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("review.checkExists");
        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, studentId);
            pstmt.setLong(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // 리뷰 저장
    public int saveReview(Long studentId, Long courseId, Long rating) throws SQLException {
        String query = CourseQueryUtil.getQuery("review.save");
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, studentId);
            pstmt.setLong(2, courseId);
            pstmt.setLong(3, rating);
            return pstmt.executeUpdate();
        }
    }

}
