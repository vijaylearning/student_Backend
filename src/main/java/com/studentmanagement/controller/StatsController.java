package com.studentmanagement.controller;

import com.studentmanagement.dto.StatsResponse;
import com.studentmanagement.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Statistics", description = "APIs for retrieving system statistics")
public class StatsController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @Operation(summary = "Get enrollment statistics", 
               description = "Get comprehensive statistics including student with max courses, course with max students, and overall counts")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatsResponse> getEnrollmentStats() {
        StatsResponse stats = adminService.getEnrollmentStats();
        return ResponseEntity.ok(stats);
    }
}