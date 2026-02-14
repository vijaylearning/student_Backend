package com.studentmanagement.service;

import com.studentmanagement.entity.Student;
import com.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentServiceTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void createStudent_increasesCount() {
        long before = studentRepository.count();
        Student s = new Student("New Student", "new.student@example.com", "1112223333");
        studentRepository.save(s);
        long after = studentRepository.count();
        assertThat(after).isEqualTo(before + 1);
    }
}
