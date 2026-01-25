package com.studentmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void unauthenticatedEndpoints_and_protectedEndpoints_behaveCorrectly() {
        // Public search endpoints should be accessible without auth
        ResponseEntity<String> studentsSearch = restTemplate.getForEntity("/api/students/search?name=John", String.class);
        assertThat(studentsSearch.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> coursesSearch = restTemplate.getForEntity("/api/courses/search?name=Programming", String.class);
        assertThat(coursesSearch.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Protected endpoint requires authentication
        ResponseEntity<String> protectedReq = restTemplate.getForEntity("/api/courses", String.class);
        assertThat(protectedReq.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        // Authenticated user can access /api/courses
        TestRestTemplate user = restTemplate.withBasicAuth("user", "user123");
        ResponseEntity<String> ok = user.getForEntity("/api/courses", String.class);
        assertThat(ok.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
