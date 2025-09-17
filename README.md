# Student Course Management System

A comprehensive Spring Boot 3 application for managing students, courses, and enrollments with role-based access control and Swagger documentation.

## Features

- **Student Management**: CRUD operations for students with active/inactive status
- **Course Management**: CRUD operations for courses with active/inactive status  
- **Enrollment Management**: Admin-only enrollment operations
- **Role-based Security**: Different access levels for users and admins
- **Statistics API**: Track enrollment metrics and popular courses/students
- **H2 Database**: In-memory database with pre-populated sample data
- **Swagger Documentation**: Interactive API documentation
- **Comprehensive Validation**: Input validation with meaningful error messages

## Technology Stack

- **Spring Boot 3.2.0**
- **Java 17**
- **Spring Security 6**
- **Spring Data JPA**
- **H2 Database**
- **OpenAPI 3 (Swagger)**
- **Maven**

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd student-course-management
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

### Default Credentials

The application comes with two pre-configured users:

- **Admin User**
  - Username: `admin`
  - Password: `admin123`
  - Roles: ADMIN, USER

- **Regular User**
  - Username: `user`
  - Password: `user123`
  - Roles: USER

### H2 Database Configuration

- **JDBC URL**: `jdbc:h2:mem:studentdb`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Student Management (`/api/students`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/students` | Get all students | USER |
| GET | `/api/students/active` | Get active students | USER |
| GET | `/api/students/inactive` | Get inactive students | ADMIN |
| GET | `/api/students/{id}` | Get student by ID | USER |
| GET | `/api/students/email/{email}` | Get student by email | USER |
| GET | `/api/students/search?name=` | Search students by name | PUBLIC |
| POST | `/api/students` | Create new student | ADMIN |
| PUT | `/api/students/{id}` | Update student | ADMIN |
| DELETE | `/api/students/{id}` | Delete student | ADMIN |
| PATCH | `/api/students/{id}/activate` | Activate student | ADMIN |
| PATCH | `/api/students/{id}/deactivate` | Deactivate student | ADMIN |
| GET | `/api/students/{id}/courses/count` | Get enrolled courses count | USER |

### Course Management (`/api/courses`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/courses` | Get all courses | USER |
| GET | `/api/courses/active` | Get active courses | USER |
| GET | `/api/courses/inactive` | Get inactive courses | ADMIN |
| GET | `/api/courses/{id}` | Get course by ID | USER |
| GET | `/api/courses/code/{courseCode}` | Get course by code | USER |
| GET | `/api/courses/search?name=` | Search courses by name | PUBLIC |
| POST | `/api/courses` | Create new course | ADMIN |
| PUT | `/api/courses/{id}` | Update course | ADMIN |
| DELETE | `/api/courses/{id}` | Delete course | ADMIN |
| PATCH | `/api/courses/{id}/activate` | Activate course | ADMIN |
| PATCH | `/api/courses/{id}/deactivate` | Deactivate course | ADMIN |
| GET | `/api/courses/{id}/students/count` | Get enrolled students count | USER |

### Admin Management (`/api/admin`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/admin/enroll` | Enroll student to course | ADMIN |
| DELETE | `/api/admin/enroll?studentId=&courseId=` | Unenroll student from course | ADMIN |
| PATCH | `/api/admin/enrollment/{enrollmentId}/status?status=` | Change enrollment status | ADMIN |

### Statistics (`/api/stats`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/stats` | Get enrollment statistics | ADMIN |

## Sample Data

The application comes pre-loaded with:

- **10 Students**: Mix of active and inactive students
- **10 Courses**: Various subjects with different credit hours and fees
- **Sample Enrollments**: Realistic enrollment patterns

### Key Statistics from Sample Data

- **Student with Most Enrollments**: John Doe (5 courses)
- **Most Popular Course**: Introduction to Programming (4 students)
- **Total Active Students**: 9
- **Total Active Courses**: 9
- **Total Active Enrollments**: 22

## Entity Relationships

### Student Entity
- ID (Primary Key)
- Name, Email, Phone
- Active Status
- Created/Updated timestamps
- One-to-Many relationship with StudentCourse

### Course Entity
- ID (Primary Key)  
- Name, Description, Course Code
- Credits, Fee
- Active Status
- Created/Updated timestamps
- One-to-Many relationship with StudentCourse

### StudentCourse Entity (Join Table)
- ID (Primary Key)
- Student ID (Foreign Key)
- Course ID (Foreign Key)
- Enrollment Date
- Enrolled By (Admin username)
- Status (ACTIVE, COMPLETED, DROPPED, SUSPENDED)

## Security Configuration

- **Basic Authentication**: Username/password authentication
- **Role-based Access Control**: Different endpoints for different roles
- **Method-level Security**: `@PreAuthorize` annotations
- **CSRF Disabled**: For API-only usage
- **H2 Console Access**: Enabled for development

## Swagger Documentation

Access the interactive API documentation at: http://localhost:8080/swagger-ui.html

Features:
- Interactive API testing
- Request/response examples
- Authentication support
- Comprehensive endpoint documentation

## Testing the Application

### Using Swagger UI

1. Navigate to http://localhost:8080/swagger-ui.html
2. Click "Authorize" and enter credentials (admin/admin123)
3. Test various endpoints interactively

### Using curl

```bash
# Get all active students (requires authentication)
curl -u admin:admin123 http://localhost:8080/api/students/active

# Create a new student (admin only)
curl -u admin:admin123 -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"New Student","email":"new@email.com","phone":"1234567890"}'

# Enroll student to course (admin only)
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/enroll \
  -H "Content-Type: application/json" \
  -d '{"studentId":1,"courseId":6}'

# Get enrollment statistics
curl -u admin:admin123 http://localhost:8080/api/stats
```

## Configuration

### Application Properties

```properties
# Server Configuration
server.port=8080

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:studentdb
spring.datasource.username=sa
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# OpenAPI Configuration
springdoc.swagger-ui.path=/swagger-ui.html
```

## Development

### Project Structure

```
src/main/java/com/studentmanagement/
├── entity/           # JPA entities
├── dto/             # Data Transfer Objects
├── repository/      # Spring Data repositories
├── service/         # Business logic services
├── controller/      # REST controllers
└── config/          # Configuration classes
```

### Key Design Patterns

- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **DTO Pattern**: Data transfer between layers
- **Builder Pattern**: Entity construction
- **Strategy Pattern**: Different access levels

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue on GitHub
- Check the Swagger documentation
- Review the application logs