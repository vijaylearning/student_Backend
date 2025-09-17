package com.studentmanagement.dto;

public class StatsResponse {

    private StudentWithMaxCoursesDTO studentWithMaxCourses;
    private CourseWithMaxStudentsDTO courseWithMaxStudents;
    private Long totalActiveStudents;
    private Long totalActiveCourses;
    private Long totalActiveEnrollments;

    public static class StudentWithMaxCoursesDTO {
        private Long studentId;
        private String studentName;
        private String studentEmail;
        private Long coursesCount;

        // Constructors
        public StudentWithMaxCoursesDTO() {}

        public StudentWithMaxCoursesDTO(Long studentId, String studentName, String studentEmail, Long coursesCount) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentEmail = studentEmail;
            this.coursesCount = coursesCount;
        }

        // Getters and Setters
        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public Long getCoursesCount() {
            return coursesCount;
        }

        public void setCoursesCount(Long coursesCount) {
            this.coursesCount = coursesCount;
        }
    }

    public static class CourseWithMaxStudentsDTO {
        private Long courseId;
        private String courseName;
        private String courseCode;
        private Long studentsCount;

        // Constructors
        public CourseWithMaxStudentsDTO() {}

        public CourseWithMaxStudentsDTO(Long courseId, String courseName, String courseCode, Long studentsCount) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseCode = courseCode;
            this.studentsCount = studentsCount;
        }

        // Getters and Setters
        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public Long getStudentsCount() {
            return studentsCount;
        }

        public void setStudentsCount(Long studentsCount) {
            this.studentsCount = studentsCount;
        }
    }

    // Getters and Setters
    public StudentWithMaxCoursesDTO getStudentWithMaxCourses() {
        return studentWithMaxCourses;
    }

    public void setStudentWithMaxCourses(StudentWithMaxCoursesDTO studentWithMaxCourses) {
        this.studentWithMaxCourses = studentWithMaxCourses;
    }

    public CourseWithMaxStudentsDTO getCourseWithMaxStudents() {
        return courseWithMaxStudents;
    }

    public void setCourseWithMaxStudents(CourseWithMaxStudentsDTO courseWithMaxStudents) {
        this.courseWithMaxStudents = courseWithMaxStudents;
    }

    public Long getTotalActiveStudents() {
        return totalActiveStudents;
    }

    public void setTotalActiveStudents(Long totalActiveStudents) {
        this.totalActiveStudents = totalActiveStudents;
    }

    public Long getTotalActiveCourses() {
        return totalActiveCourses;
    }

    public void setTotalActiveCourses(Long totalActiveCourses) {
        this.totalActiveCourses = totalActiveCourses;
    }

    public Long getTotalActiveEnrollments() {
        return totalActiveEnrollments;
    }

    public void setTotalActiveEnrollments(Long totalActiveEnrollments) {
        this.totalActiveEnrollments = totalActiveEnrollments;
    }
}