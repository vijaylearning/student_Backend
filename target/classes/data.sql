-- Insert 10 sample students
INSERT INTO students (id, name, email, phone, active, created_at) VALUES
(1, 'John Doe', 'john.doe@email.com', '1234567890', true, '2024-01-01 10:00:00'),
(2, 'Jane Smith', 'jane.smith@email.com', '1234567891', true, '2024-01-02 10:00:00'),
(3, 'Mike Johnson', 'mike.johnson@email.com', '1234567892', true, '2024-01-03 10:00:00'),
(4, 'Sarah Wilson', 'sarah.wilson@email.com', '1234567893', true, '2024-01-04 10:00:00'),
(5, 'David Brown', 'david.brown@email.com', '1234567894', true, '2024-01-05 10:00:00'),
(6, 'Emily Davis', 'emily.davis@email.com', '1234567895', true, '2024-01-06 10:00:00'),
(7, 'Chris Miller', 'chris.miller@email.com', '1234567896', true, '2024-01-07 10:00:00'),
(8, 'Lisa Anderson', 'lisa.anderson@email.com', '1234567897', false, '2024-01-08 10:00:00'),
(9, 'Robert Taylor', 'robert.taylor@email.com', '1234567898', true, '2024-01-09 10:00:00'),
(10, 'Amanda Martinez', 'amanda.martinez@email.com', '1234567899', true, '2024-01-10 10:00:00');

-- Insert 10 sample courses
INSERT INTO courses (id, name, description, course_code, credits, fee, active, created_at) VALUES
(1, 'Introduction to Programming', 'Learn the basics of programming with Python', 'CS101', 3, 500.00, true, '2024-01-01 09:00:00'),
(2, 'Data Structures and Algorithms', 'Advanced programming concepts and problem solving', 'CS201', 4, 750.00, true, '2024-01-01 09:00:00'),
(3, 'Database Management Systems', 'Learn SQL and database design principles', 'CS301', 3, 600.00, true, '2024-01-01 09:00:00'),
(4, 'Web Development', 'Build modern web applications with HTML, CSS, and JavaScript', 'CS401', 4, 800.00, true, '2024-01-01 09:00:00'),
(5, 'Machine Learning', 'Introduction to artificial intelligence and ML algorithms', 'CS501', 5, 1000.00, true, '2024-01-01 09:00:00'),
(6, 'Software Engineering', 'Software development lifecycle and project management', 'CS601', 4, 900.00, true, '2024-01-01 09:00:00'),
(7, 'Computer Networks', 'Networking protocols and distributed systems', 'CS701', 3, 650.00, true, '2024-01-01 09:00:00'),
(8, 'Cybersecurity', 'Information security and ethical hacking', 'CS801', 4, 1100.00, false, '2024-01-01 09:00:00'),
(9, 'Mobile App Development', 'Build native and cross-platform mobile applications', 'CS901', 4, 850.00, true, '2024-01-01 09:00:00'),
(10, 'Cloud Computing', 'Learn AWS, Docker, and containerization technologies', 'CS1001', 3, 700.00, true, '2024-01-01 09:00:00');

-- Insert sample enrollments (student-course relationships)
-- Student 1 (John Doe) - enrolled in 5 courses (maximum enrollments)
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(1, 1, 1, '2024-01-15 10:00:00', 'admin', 'ACTIVE'),
(2, 1, 2, '2024-01-16 10:00:00', 'admin', 'ACTIVE'),
(3, 1, 3, '2024-01-17 10:00:00', 'admin', 'ACTIVE'),
(4, 1, 4, '2024-01-18 10:00:00', 'admin', 'ACTIVE'),
(5, 1, 5, '2024-01-19 10:00:00', 'admin', 'ACTIVE');

-- Student 2 (Jane Smith) - enrolled in 3 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(6, 2, 1, '2024-01-15 11:00:00', 'admin', 'ACTIVE'),
(7, 2, 6, '2024-01-16 11:00:00', 'admin', 'ACTIVE'),
(8, 2, 7, '2024-01-17 11:00:00', 'admin', 'ACTIVE');

-- Student 3 (Mike Johnson) - enrolled in 4 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(9, 3, 2, '2024-01-15 12:00:00', 'admin', 'ACTIVE'),
(10, 3, 3, '2024-01-16 12:00:00', 'admin', 'ACTIVE'),
(11, 3, 9, '2024-01-17 12:00:00', 'admin', 'ACTIVE'),
(12, 3, 10, '2024-01-18 12:00:00', 'admin', 'ACTIVE');

-- Student 4 (Sarah Wilson) - enrolled in 2 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(13, 4, 4, '2024-01-15 13:00:00', 'admin', 'ACTIVE'),
(14, 4, 5, '2024-01-16 13:00:00', 'admin', 'ACTIVE');

-- Student 5 (David Brown) - enrolled in 3 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(15, 5, 1, '2024-01-15 14:00:00', 'admin', 'ACTIVE'),
(16, 5, 6, '2024-01-16 14:00:00', 'admin', 'ACTIVE'),
(17, 5, 7, '2024-01-17 14:00:00', 'admin', 'ACTIVE');

-- Student 6 (Emily Davis) - enrolled in 2 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(18, 6, 2, '2024-01-15 15:00:00', 'admin', 'ACTIVE'),
(19, 6, 8, '2024-01-16 15:00:00', 'admin', 'DROPPED');

-- Student 7 (Chris Miller) - enrolled in 1 course
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(20, 7, 9, '2024-01-15 16:00:00', 'admin', 'ACTIVE');

-- Student 9 (Robert Taylor) - enrolled in 2 courses
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(21, 9, 3, '2024-01-15 17:00:00', 'admin', 'ACTIVE'),
(22, 9, 10, '2024-01-16 17:00:00', 'admin', 'ACTIVE');

-- Student 10 (Amanda Martinez) - enrolled in 1 course
INSERT INTO student_courses (id, student_id, course_id, enrolled_at, enrolled_by, status) VALUES
(23, 10, 4, '2024-01-15 18:00:00', 'admin', 'COMPLETED');

-- Additional enrollments to make Course 1 (Introduction to Programming) have maximum students (4 students)
-- Course 1: Students 1, 2, 5 are already enrolled
-- Course 2: Students 1, 3, 6 (3 students)
-- Course 3: Students 1, 3, 9 (3 students)
-- Course 4: Students 1, 4, 10 (3 students)