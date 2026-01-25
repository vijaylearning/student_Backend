package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentCourse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentCourseRepositoryTests {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Test
    void findActiveEnrollment_exists() {
        // Student 1 enrolled in Course 1 in data.sql
        StudentCourse sc = studentCourseRepository.findByStudentIdAndCourseId(1L, 1L).orElse(null);
        assertThat(sc).isNotNull();
        assertThat(sc.getStatus()).isEqualTo(StudentCourse.EnrollmentStatus.ACTIVE);
    }

    @Test
    void totalEnrollments_expectedAtLeast() {
        long count = studentCourseRepository.count();
        assertThat(count).isGreaterThanOrEqualTo(10);
    }
}
