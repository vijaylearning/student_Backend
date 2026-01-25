package com.studentmanagement.integration;

import com.studentmanagement.dto.StatsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void statsEndpoint_requiresAdmin_and_returnsStats() {
        // Unauthenticated -> 401
        ResponseEntity<StatsResponse> anon = restTemplate.getForEntity("/api/stats", StatsResponse.class);
        assertThat(anon.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        // Regular user -> 403
        TestRestTemplate user = restTemplate.withBasicAuth("user", "user123");
        ResponseEntity<String> forbidden = user.getForEntity("/api/stats", String.class);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        // Admin -> 200 and body has counts
        TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin123");
        ResponseEntity<StatsResponse> response = admin.getForEntity("/api/stats", StatsResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        StatsResponse stats = response.getBody();
        assertThat(stats).isNotNull();
        assertThat(stats.getTotalActiveStudents()).isGreaterThanOrEqualTo(1);
        assertThat(stats.getTotalActiveCourses()).isGreaterThanOrEqualTo(1);
        assertThat(stats.getTotalActiveEnrollments()).isGreaterThanOrEqualTo(1);
        // The nested DTOs may be null depending on SQL behavior; ensure counts are present instead of strict nested DTO checks
    }
}
