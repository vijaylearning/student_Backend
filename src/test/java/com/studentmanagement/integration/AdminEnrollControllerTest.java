package com.studentmanagement.integration;

import com.studentmanagement.dto.EnrollmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminEnrollControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void duplicateEnrollment_returnsBadRequestOrConflict() {
        TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin123");
        EnrollmentRequest req = new EnrollmentRequest(1L, 1L); // already enrolled in data.sql

        ResponseEntity<String> response = admin.postForEntity("/api/admin/enroll", req, String.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void enrollNewStudent_successful() {
        TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin123");
        // Student 7 is currently enrolled only in course 9; enroll in course 1 should succeed
        EnrollmentRequest req = new EnrollmentRequest(7L, 1L);

        ResponseEntity<String> response = admin.postForEntity("/api/admin/enroll", req, String.class);

        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.CREATED);
    }
}
