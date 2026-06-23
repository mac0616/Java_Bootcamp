package com.wanted.crud.course.model.dao;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.dto.CourseMyStudentDTO;
import com.wanted.crud.course.model.dto.CourseReviewDTO;
import com.wanted.crud.course.model.dto.SectionDTO;
import com.wanted.crud.global.utils.CourseQueryUtil;
import com.wanted.crud.global.utils.UserQueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    // 전체 강좌 조회
    public List<CourseDTO> selectAllCourses() throws SQLException {
        String query = CourseQueryUtil.getQuery("course.findAllCourse");
        List<CourseDTO> courseList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                CourseDTO course = new CourseDTO(
                        rset.getLong("course_id"),
                        rset.getString("title"),
                        rset.getString("description"),
                        rset.getLong("price"),
                        rset.getString("status"),
                        rset.getDate("created_at"),
                        rset.getLong("instructor_id")
                );
                courseList.add(course);
            }
        }
        return courseList;
    }


    // courseId 사용
    public List<Long> findcourseid(Long instructorId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.findId");
        List<Long> courseIdList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, instructorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    courseIdList.add(rset.getLong("course_id"));
                }
            }
        }
        return courseIdList;
    }




    // 내 강좌 조회
    public List<CourseDTO> selectCourse(long id) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.selectMyCourse");
        List<CourseDTO> courseMyList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO(
                        rset.getLong("course_id"),
                        rset.getString("title"),
                        rset.getString("description"),
                        rset.getLong("price"),
                        rset.getString("Status"),
                        rset.getDate("created_at"),
                        rset.getLong("instructor_id")
                );

                courseMyList.add(course);
            }

        }
        return courseMyList;
    }


    // 내 강좌 등록
    public Long save(CourseDTO newCourse) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.insertCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newCourse.getTitle());
            pstmt.setString(2, newCourse.getDescription());
            pstmt.setLong(3, newCourse.getPrice());
            pstmt.setLong(4, newCourse.getInstructorId());

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


    // 내 강좌 삭제
    // CourseDAO.java 내부
    public boolean deleteCourse(Long courseId, Long instructorId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.deleteCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, instructorId);

            int result = pstmt.executeUpdate();

            return result > 0;
        }
    }

    // 내 강좌 수정
    public int updateCourse(CourseDTO course) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.updateCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setLong(3, course.getPrice());
            pstmt.setLong(4, course.getCourseId());
            pstmt.setLong(5, course.getInstructorId());

            return pstmt.executeUpdate();
        }
    }

    public Long getPrice(Long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.getPrice");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getLong("price");
            }
        }
        return null;
    }

    // 관리자가 강사 강좌 조회
    public List<CourseDTO> selectCoursesByInstructorId(long instructorId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.selectMyCourse"); // WHERE instructor_id = ? 쿼리 사용
        List<CourseDTO> courses = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, instructorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    courses.add(new CourseDTO(
                            rset.getLong("course_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getLong("price"),
                            rset.getString("status"),
                            rset.getDate("created_at"),
                            rset.getLong("instructor_id")
                    ));
                }
            }
        }
        return courses;
    }

    // 관리자가 강좌 삭제
    public boolean adminDeleteCourse(Long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.adminDeleteCourse");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // 관리자가 강좌 수정
    public int updateCourseByAdmin(CourseDTO course) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.adminUpdateCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setLong(3, course.getPrice());
            pstmt.setString(4, course.getStatus());
            pstmt.setLong(5, course.getCourseId());

            return pstmt.executeUpdate();
        }
    }

    // 내 강좌를 수강 중인 전체 학생 현황 조회
    public List<CourseMyStudentDTO> selectMyStudent(long instructorId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.myStudent");
        List<CourseMyStudentDTO> mystudentList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, instructorId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseMyStudentDTO course = new CourseMyStudentDTO(
                        rset.getLong("course_id"),
                        rset.getString("title"),
                        rset.getLong("student_id"),
                        rset.getString("user_name"),
                        rset.getLong("progress_rate"),
                        rset.getDate("start_date"),
                        rset.getDate("end_date"),
                        rset.getBoolean("status")
                );

                mystudentList.add(course);
            }

        }
        return mystudentList;
    }

    // 강좌 아이디로 해당 강좌 가격 가져오기
    public Long coursePrice(Long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.findPriceById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            // ResultSet도 안전하게 닫히도록 try에 추가하는 것을 권장합니다.
            try (ResultSet rset = pstmt.executeQuery()) {

                // 1. rset.next()로 커서를 한 칸 내리면서 데이터가 있는지 확인합니다.
                if (rset.next()) {
                    return rset.getLong("price"); // 데이터가 있으면 가격 반환
                }
            }
        }

        return null;
    }

    public List<CourseReviewDTO> selectCourseReview() throws SQLException {
        String query = CourseQueryUtil.getQuery("review.selectByCourseId");
        List<CourseReviewDTO> courseReviewList = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                courseReviewList.add(new CourseReviewDTO(
                   rset.getLong("course_id"),
                   rset.getString("title"),
                   rset.getString("Description"),
                   rset.getLong("price"),
                   rset.getDouble("avg_rating")
                ));
            }
        }
        return courseReviewList;
    }


    public boolean isCourseExists(Long courseId) throws SQLException {
        String query = CourseQueryUtil.getQuery("course.isCourseExists");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // 1. ? 에 courseId 값 매핑
            pstmt.setLong(1, courseId);

            try (ResultSet rset = pstmt.executeQuery()) {
                // 2. rset.next()로 결과 행이 있는지 확인 후 값 가져오기
                if (rset.next()) {
                    // 참고: 메서드명이나 별칭에 오타(Exixts -> Exists)가 있지만,
                    // 쿼리 별칭과 맞추기 위해 그대로 사용합니다.
                    return rset.getBoolean("isCourseExists");
                }
            }
        }
        return false; // 만약의 경우를 대비한 기본 반환값
    }
}





