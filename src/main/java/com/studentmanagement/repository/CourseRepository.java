package com.studentmanagement.repository;

import com.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByActiveTrue();

    List<Course> findByActiveFalse();

    Optional<Course> findByCourseCode(String courseCode);

    List<Course> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM Course c WHERE c.active = :active")
    List<Course> findByActiveStatus(@Param("active") Boolean active);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.course.id = :courseId AND sc.status = 'ACTIVE'")
    Long countActiveEnrollmentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c JOIN c.studentCourses sc WHERE sc.student.id = :studentId AND sc.status = 'ACTIVE'")
    List<Course> findActiveCoursesByStudentId(@Param("studentId") Long studentId);
}