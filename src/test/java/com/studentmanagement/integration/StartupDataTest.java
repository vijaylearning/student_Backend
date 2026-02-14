package com.studentmanagement.integration;

import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StartupDataTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void dataIsSeededWithoutDuplicates() {
        long studentCount = studentRepository.count();
        long courseCount = courseRepository.count();

        // Expect 10 students and 10 courses from data.sql
        assertThat(studentCount).isEqualTo(10);
        assertThat(courseCount).isEqualTo(10);
    }
}
