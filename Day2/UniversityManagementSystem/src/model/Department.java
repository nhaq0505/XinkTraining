package model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final String id;
    private final String name;
    private final List<Course> courses;
    private final List<Professor> professors;
    private final List<Staff> staffMembers;
    private final List<Student> students;

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.staffMembers = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getName() { return name; }
    public List<Course> getCourses() { return courses; }
    public List<Professor> getProfessors() { return professors; }
    public List<Staff> getStaffMembers() { return staffMembers; }
    public List<Student> getStudents() { return students; }

    // --- Management methods ---
    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void addStaff(Staff staff) {
        staffMembers.add(staff);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Course findCourseById(String courseId) {
        return courses.stream()
                .filter(c -> c.getCode().equals(courseId))
                .findFirst()
                .orElse(null);
    }
}
