package model;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final String semester;
    private String grade; // có thể thay đổi sau khi chấm

    private Enrollment(Builder builder) {
        this.student = builder.student;
        this.course = builder.course;
        this.semester = builder.semester;
        this.grade = builder.grade;
    }

    // --- Getters ---
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public String getGrade() {
        return grade;
    }

    // --- Grade Management ---
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isCompleted() {
        return grade != null;
    }

    // --- Builder Pattern ---
    public static class Builder {
        private Student student;
        private Course course;
        private String semester;
        private String grade;

        public Builder student(Student student) {
            this.student = student;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public Builder semester(String semester) {
            this.semester = semester;
            return this;
        }

        public Builder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public Enrollment build() {
            if (student == null || course == null || semester == null) {
                throw new IllegalStateException("Student, Course, and Semester are required!");
            }
            return new Enrollment(this);
        }
    }
}
