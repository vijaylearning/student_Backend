package com.studentmanagement.repository;

import com.studentmanagement.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findByCourseCode_returnsCourse() {
        Course c = courseRepository.findByCourseCode("CS101").orElse(null);
        assertThat(c).isNotNull();
        assertThat(c.getName()).containsIgnoringCase("Introduction to Programming");
    }

    @Test
    void countActiveCourses_expectedTen() {
        long count = courseRepository.count();
        assertThat(count).isEqualTo(10);
    }
}
