package com.studentmanagement.integration;

import com.studentmanagement.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentsApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllStudents_withUserAuth_returnsList() {
        // Use basic auth with user credentials defined in SecurityConfig
        TestRestTemplate authRest = restTemplate.withBasicAuth("user", "user123");

        ResponseEntity<StudentDTO[]> response = authRest.getForEntity("/api/students", StudentDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        StudentDTO[] students = response.getBody();
        assertThat(students).isNotNull();
        assertThat(students.length).isEqualTo(10);
    }
}
