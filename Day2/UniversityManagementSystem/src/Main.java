

import model.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 1. Tạo Department
        Department csDept = new Department("CS", "Computer Science");

        // 2. Tạo Professors
        Professor profA = new Professor.Builder()
                .id("P001")
                .name("Dr. Nguyen Van A")
                .email("nguyena@university.edu")
                .department("Computer Science")
                .build();

        Professor profB = new Professor.Builder()
                .id("P002")
                .name("Dr. Tran Van B")
                .email("tranb@university.edu")
                .department("Computer Science")
                .build();

        csDept.addProfessor(profA);
        csDept.addProfessor(profB);

        // 3. Tạo Staff
        Staff staff1 = new Staff.Builder()
                .id("ST001")
                .name("Le Thi Staff")
                .email("staff@university.edu")
                .department("Computer Science")
                .role("Academic Advisor")
                .build();

        csDept.addStaff(staff1);

        // 4. Tạo Courses
        Course course1 = new Course.Builder()
                .code("CS101")
                .title("Intro to Programming")
                .credits(3)
                .capacity(40)
                .schedule("Monday",7,10)
                .build();

        Course course2 = new Course.Builder()
                .code("CS201")
                .title("Data Structures")
                .credits(4)
                .capacity(35)
                .schedule("MonDay",9,12)
                .prerequisites(Arrays.asList(course1)) // yêu cầu phải học CS101
                .build();

        csDept.addCourse(course1);
        csDept.addCourse(course2);

        // Assign professor dạy course
        profA.assignCourse(course1);
        profB.assignCourse(course2);

        // 5. Tạo Student
        Student student1 = new Student.Builder()
                .id("S001")
                .name("Nguyen Van Student")
                .email("student@university.edu")
                .major("Computer Science")
                .year(2)
                .build();

        csDept.addStudent(student1);

        // 6. Enroll student vào course
        Enrollment enrollment1 = new Enrollment.Builder()
                .student(student1)
                .course(course1)
                .semester("Fall 2025")
                .build();

        // Gán grade
        enrollment1.setGrade("A");

        // 7. In kết quả test
        System.out.println("=== Department Info ===");
        System.out.println("Department: " + csDept.getName());
        System.out.println("Professors: " + csDept.getProfessors().size());
        System.out.println("Staff Members: " + csDept.getStaffMembers().size());
        System.out.println("Courses: " + csDept.getCourses().size());
        System.out.println("Students: " + csDept.getStudents().size());

        System.out.println("\n=== Enrollment Info ===");
        System.out.println(student1.getName() + " enrolled in " +
                enrollment1.getCourse().getTitle() +
                " (" + enrollment1.getSemester() + ")" +
                " - Grade: " + enrollment1.getGrade());
    }
}
