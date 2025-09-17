package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    List<StudentCourse> findByStatus(StudentCourse.EnrollmentStatus status);

    List<StudentCourse> findByStudentId(Long studentId);

    List<StudentCourse> findByCourseId(Long courseId);

    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.status = 'ACTIVE'")
    Long countActiveEnrollmentsByStudent(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.course.id = :courseId AND sc.status = 'ACTIVE'")
    Long countActiveEnrollmentsByCourse(@Param("courseId") Long courseId);

    // Query to find student with maximum enrollments
    @Query(value = "SELECT sc.student_id, COUNT(*) as enrollment_count " +
                   "FROM student_courses sc " +
                   "WHERE sc.status = 'ACTIVE' " +
                   "GROUP BY sc.student_id " +
                   "ORDER BY enrollment_count DESC " +
                   "LIMIT 1", nativeQuery = true)
    Object[] findStudentWithMaxEnrollments();

    // Query to find course with maximum enrollments
    @Query(value = "SELECT sc.course_id, COUNT(*) as enrollment_count " +
                   "FROM student_courses sc " +
                   "WHERE sc.status = 'ACTIVE' " +
                   "GROUP BY sc.course_id " +
                   "ORDER BY enrollment_count DESC " +
                   "LIMIT 1", nativeQuery = true)
    Object[] findCourseWithMaxEnrollments();

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId AND sc.status = 'ACTIVE'")
    Optional<StudentCourse> findActiveEnrollment(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}