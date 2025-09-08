import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    private String courseId;
    private String courseName;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status; // ACTIVE, COMPLETED, DROPPED
    private Double grade; // Điểm cuối cùng (nếu có)
    private int credits; // số tín chỉ của môn học khi đăng ký

    // Constructor đầy đủ
    public Enrollment(String courseId, String courseName, LocalDate enrollmentDate, EnrollmentStatus status, Double grade) {
        setCourseId(courseId);
        setCourseName(courseName);
        setEnrollmentDate(enrollmentDate);
        setStatus(status);
        setGrade(grade);

        this.credits = 0; // mặc định nếu không truyền, có thể đặt sau bằng setCredits
    }

    // ... existing code ...

    // Constructor khi mới đăng ký (chưa có điểm)
    public Enrollment(String courseId, String courseName, LocalDate enrollmentDate) {
        this(courseId, courseName, enrollmentDate, EnrollmentStatus.ACTIVE, null);
    }

    // Constructor khi đăng ký kèm tín chỉ
    public Enrollment(String courseId, String courseName, LocalDate enrollmentDate, int credits) {
        this(courseId, courseName, enrollmentDate, EnrollmentStatus.ACTIVE, null);
        setCredits(credits);
    }

    // Getters & Setters (có validation)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be empty");
        }
        this.courseId = courseId.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        this.courseName = courseName.trim();
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        if (enrollmentDate == null) {
            throw new IllegalArgumentException("Enrollment date cannot be null");
        }
        this.enrollmentDate = enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        if (grade != null && (grade < 0.0 || grade > 4.0)) { // GPA 0.0 - 4.0
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0");
        }
        this.grade = grade;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }
        this.credits = credits;
    }

    // Check nếu đã hoàn thành khóa học
    public boolean isCompleted() {
        return status == EnrollmentStatus.COMPLETED;
    }

    // Override toString
    @Override
    public String toString() {
        return "Enrollment{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", status=" + status +
                ", grade=" + grade +
                ", credits=" + credits +
                '}';
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName);
    }
    enum EnrollmentStatus {
        ACTIVE, COMPLETED, DROPPED
    }
}