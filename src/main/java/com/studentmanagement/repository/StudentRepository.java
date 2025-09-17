package com.studentmanagement.repository;

import com.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByActiveTrue();

    List<Student> findByActiveFalse();

    Optional<Student> findByEmail(String email);

    List<Student> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Student s WHERE s.active = :active")
    List<Student> findByActiveStatus(@Param("active") Boolean active);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.status = 'ACTIVE'")
    Long countActiveEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT s FROM Student s JOIN s.studentCourses sc WHERE sc.course.id = :courseId AND sc.status = 'ACTIVE'")
    List<Student> findActiveStudentsByCourseId(@Param("courseId") Long courseId);
}