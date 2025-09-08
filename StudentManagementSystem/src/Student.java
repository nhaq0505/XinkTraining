import java.time.LocalDate;
import java.util.*;

public class Student {
    private final String studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private Address address;
    private List<Enrollment> enrollments;
    private Map<String, Double> grades;

    // Constructor
    public Student(String studentId, String firstName, String lastName) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        this.studentId = studentId;
        setFirstName(firstName);
        setLastName(lastName);
        this.enrollments = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    // Constructor Full
    public Student(String studentId, String firstName, String lastName,
                   LocalDate birthDate, String email, String phoneNumber, Address address) {
        this(studentId, firstName, lastName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Address getAddress() {
        return address;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    public Map<String, Double> getGrades() {
        return grades;
    }

    // Setters với validation
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }
        this.lastName = lastName.trim();
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future.");
        }
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        if (email != null && !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("\\d{9,15}")) {
            throw new IllegalArgumentException("Phone number must be 9-15 digits.");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Thêm enrollment
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            enrollments.add(enrollment);
        }
    }

    // Thêm grade
    public void addGrade(String courseCode, double grade) {
        if (grade < 0 || grade > 4.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0.");
        }
        grades.put(courseCode, grade);
    }

    // Tính GPA
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        double total = 0;
        for (double g : grades.values()) {
            total += g;
        }
        return total / grades.size();
    }



    // Kiểm tra đủ điều kiện tốt nghiệp
    public boolean checkGraduationRequirements() {
        int totalCredits = enrollments.stream()
                .mapToInt(Enrollment::getCredits)
                .sum();
        return totalCredits >= 120 && calculateGPA() >= 2.0;
    }

}