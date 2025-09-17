package com.studentmanagement.service;

import com.studentmanagement.dto.EnrollmentRequest;
import com.studentmanagement.dto.StatsResponse;
import com.studentmanagement.entity.Student;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.StudentCourse;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public boolean enrollStudentToCourse(EnrollmentRequest request) {
        // Check if student exists and is active
        Optional<Student> studentOpt = studentRepository.findById(request.getStudentId());
        if (studentOpt.isEmpty() || !studentOpt.get().getActive()) {
            return false;
        }

        // Check if course exists and is active
        Optional<Course> courseOpt = courseRepository.findById(request.getCourseId());
        if (courseOpt.isEmpty() || !courseOpt.get().getActive()) {
            return false;
        }

        // Check if enrollment already exists
        Optional<StudentCourse> existingEnrollment = studentCourseRepository
                .findActiveEnrollment(request.getStudentId(), request.getCourseId());
        if (existingEnrollment.isPresent()) {
            return false; // Already enrolled
        }

        // Get current authenticated admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminUsername = authentication.getName();

        // Create new enrollment
        StudentCourse studentCourse = new StudentCourse(
                studentOpt.get(), 
                courseOpt.get(), 
                adminUsername
        );
        studentCourse.setStatus(StudentCourse.EnrollmentStatus.ACTIVE);

        studentCourseRepository.save(studentCourse);
        return true;
    }

    public boolean unenrollStudentFromCourse(Long studentId, Long courseId) {
        Optional<StudentCourse> enrollment = studentCourseRepository
                .findActiveEnrollment(studentId, courseId);

        if (enrollment.isPresent()) {
            enrollment.get().setStatus(StudentCourse.EnrollmentStatus.DROPPED);
            studentCourseRepository.save(enrollment.get());
            return true;
        }
        return false;
    }

    public StatsResponse getEnrollmentStats() {
        StatsResponse stats = new StatsResponse();

        // Get total counts
        stats.setTotalActiveStudents((long) studentRepository.findByActiveTrue().size());
        stats.setTotalActiveCourses((long) courseRepository.findByActiveTrue().size());
        stats.setTotalActiveEnrollments((long) studentCourseRepository.findByStatus(StudentCourse.EnrollmentStatus.ACTIVE).size());

        // Get student with max enrollments
        Object[] studentWithMax = studentCourseRepository.findStudentWithMaxEnrollments();
        if (studentWithMax != null && studentWithMax.length >= 2) {
            Long studentId = ((Number) studentWithMax[0]).longValue();
            Long count = ((Number) studentWithMax[1]).longValue();

            Optional<Student> student = studentRepository.findById(studentId);
            if (student.isPresent()) {
                StatsResponse.StudentWithMaxCoursesDTO studentDto = 
                        new StatsResponse.StudentWithMaxCoursesDTO(
                                studentId, 
                                student.get().getName(), 
                                student.get().getEmail(), 
                                count
                        );
                stats.setStudentWithMaxCourses(studentDto);
            }
        }

        // Get course with max enrollments
        Object[] courseWithMax = studentCourseRepository.findCourseWithMaxEnrollments();
        if (courseWithMax != null && courseWithMax.length >= 2) {
            Long courseId = ((Number) courseWithMax[0]).longValue();
            Long count = ((Number) courseWithMax[1]).longValue();

            Optional<Course> course = courseRepository.findById(courseId);
            if (course.isPresent()) {
                StatsResponse.CourseWithMaxStudentsDTO courseDto = 
                        new StatsResponse.CourseWithMaxStudentsDTO(
                                courseId, 
                                course.get().getName(), 
                                course.get().getCourseCode(), 
                                count
                        );
                stats.setCourseWithMaxStudents(courseDto);
            }
        }

        return stats;
    }

    public boolean changeEnrollmentStatus(Long enrollmentId, StudentCourse.EnrollmentStatus newStatus) {
        Optional<StudentCourse> enrollment = studentCourseRepository.findById(enrollmentId);
        if (enrollment.isPresent()) {
            enrollment.get().setStatus(newStatus);
            studentCourseRepository.save(enrollment.get());
            return true;
        }
        return false;
    }
}