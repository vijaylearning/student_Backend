package com.studentmanagement.service;

import com.studentmanagement.dto.CourseEnrollmentDetailsDTO;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.Student;
import com.studentmanagement.entity.StudentCourse;
import com.studentmanagement.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<CourseEnrollmentDetailsDTO> getCourseEnrollments() {
        List<StudentCourse> enrollments = studentCourseRepository.findAll();

        return enrollments.stream().map(enrollment -> {
            Course course = enrollment.getCourse();
            Student student = enrollment.getStudent();

            CourseEnrollmentDetailsDTO dto = new CourseEnrollmentDetailsDTO();
            dto.setCourseId(course.getId());
            dto.setCourseCode(course.getCourseCode());
            dto.setCourseName(course.getName());
            dto.setCredits(course.getCredits());
            dto.setActive(course.getActive());

            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            dto.setStudentEmail(student.getEmail());
            dto.setStudentPhone(student.getPhone());

            dto.setEnrollmentDate(enrollment.getEnrolledAt());
            dto.setEnrolledBy(enrollment.getEnrolledBy());
            dto.setStatus(enrollment.getStatus().name());

            return dto;
        }).toList();
    }
}
