import java.time.LocalDate;
import java.util.*;

public class Main {
    // Danh sách sinh viên & môn học
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Thêm môn học");
            System.out.println("3. Đăng ký môn học cho sinh viên");
            System.out.println("4. Xem danh sách sinh viên");
            System.out.println("5. Xem danh sách môn học");
            System.out.println("6. Nhập điểm cho sinh viên");
            System.out.println("7. Kiểm tra điều kiện tốt nghiệp");
            System.out.println("8. Rút môn học cho sinh viên");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> addCourse(sc);
                case 3 -> registerCourse(sc);
                case 4 -> listStudents();
                case 5 -> listCourses();
                case 6 -> addGrade(sc);
                case 7 -> checkGraduation(sc);
                case 8 -> dropCourse(sc);
                case 0 -> {
                    System.out.println("Thoát chương trình...");
                    return;
                }
                default -> System.out.println(" Lựa chọn không hợp lệ.");
            }
        }
    }

    // ================== Chức năng ==================

    private static void addStudent(Scanner sc) {
        System.out.print("Nhập ID sinh viên: ");
        String id = sc.nextLine();
        System.out.print("Nhập tên: ");
        String first = sc.nextLine();
        System.out.print("Nhập họ: ");
        String last = sc.nextLine();

        Student s = new Student(
                id, first, last,
                LocalDate.now(),
                "email@example.com",
                "0123456789",
                // Address yêu cầu (street, city, state, postalCode, country) hoặc (street, city)
                new Address("123 Street", "City", "", "", "Country")
        );
        students.add(s);
        System.out.println(" Thêm sinh viên thành công!");
    }

    private static void dropCourse(Scanner sc) {
        System.out.print("Nhập id sinh viên :");
        String sid = sc.nextLine();
        System.out.println("Nhập mã môn học :");
        String mid = sc.nextLine();
        Student student = findStudentById(sid);
        Course course = findCourseByCode(mid);
        if (student == null) {
            System.out.println(" Không tìm thấy sinh viên.");
            return;
        }
        if (course == null) {
            System.out.println(" Không tìm thấy môn học.");
            return;
        }
        boolean dropped = course.dropStudent(student);
        if (!dropped) {
            System.out.println("⚠ Sinh viên chưa đăng ký môn này.");
        }
    }

    private static void addCourse(Scanner sc) {
        System.out.print("Nhập mã môn học: ");
        String cid = sc.nextLine();
        System.out.print("Nhập tên môn học: ");
        String cname = sc.nextLine();


        Course c = new Course(cid, cname, 3, "Giảng viên A", 50, new ArrayList<>(), new HashMap<>());
        courses.add(c);
        System.out.println(" Thêm môn học thành công!");
    }

    private static void registerCourse(Scanner sc) {
        System.out.print("Nhập ID sinh viên: ");
        String sid = sc.nextLine();
        System.out.print("Nhập mã môn học: ");
        String cid = sc.nextLine();

        Student student = findStudentById(sid);
        Course course = findCourseByCode(cid);

        if (student == null) {
            System.out.println(" Không tìm thấy sinh viên.");
            return;
        }
        if (course == null) {
            System.out.println(" Không tìm thấy môn học.");
            return;
        }

        // Tạo Enrollment kèm tín chỉ để phục vụ tính tổng tín chỉ tốt nghiệp
        Enrollment e = new Enrollment(course.getCourseCode(), course.getCourseName(), LocalDate.now(), course.getCredits());
        student.addEnrollment(e);

        // Đăng ký vào lớp
        course.enrollStudent(student);

        System.out.println(" Đăng ký môn học thành công!");
    }

    private static void addGrade(Scanner sc) {
        System.out.print("Nhập ID sinh viên: ");
        String sid = sc.nextLine();
        System.out.print("Nhập mã môn học: ");
        String cid = sc.nextLine();
        System.out.print("Nhập điểm (0.0 - 4.0): ");
        double grade;
        try {
            grade = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println(" Điểm không hợp lệ.");
            return;
        }

        Student student = findStudentById(sid);
        if (student == null) {
            System.out.println(" Không tìm thấy sinh viên.");
            return;
        }

        // Lưu điểm vào Student để tính GPA
        try {
            student.addGrade(cid, grade);
        } catch (IllegalArgumentException ex) {
            System.out.println( ex.getMessage());
            return;
        }

        // Đồng bộ điểm vào Enrollment nếu có enrollment tương ứng
        for (Enrollment en : student.getEnrollments()) {
            if (en.getCourseId().equals(cid)) {
                try {
                    en.setGrade(grade);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Không thể đặt điểm cho Enrollment: " + ex.getMessage());
                }
                break;
            }
        }

        System.out.println(" Đã nhập điểm cho sinh viên " + sid + " môn " + cid);
    }

    private static void checkGraduation(Scanner sc) {
        System.out.print("Nhập ID sinh viên: ");
        String sid = sc.nextLine();

        Student student = findStudentById(sid);
        if (student == null) {
            System.out.println(" Không tìm thấy sinh viên.");
            return;
        }

        double gpa = student.calculateGPA();
        // Tính tổng tín chỉ từ Enrollment (đã lưu credits khi đăng ký)
        int totalCredits = student.getEnrollments().stream()
                .mapToInt(Enrollment::getCredits)
                .sum();

        boolean eligible = student.checkGraduationRequirements();
        System.out.println("Kết quả tốt nghiệp cho " + student.getFirstName() + " " + student.getLastName() + " (ID: " + student.getStudentId() + "):");
        System.out.println("- Tổng tín chỉ: " + totalCredits);
        System.out.println("- GPA: " + String.format(Locale.US, "%.2f", gpa));
        System.out.println("- Đủ điều kiện: " + (eligible ? " Có" : " Chưa"));
    }

    private static void listStudents() {
        System.out.println("\n--- Danh sách sinh viên ---");
        for (Student s : students) {
            System.out.println(s.getStudentId() + " - " + s.getFirstName() + " " + s.getLastName());
        }
    }

    private static void listCourses() {
        System.out.println("\n--- Danh sách môn học ---");
        for (Course c : courses) {
            System.out.println(c.getCourseCode() + " - " + c.getCourseName());
        }
    }

    // ================== Helper ==================
    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) return s;
        }
        return null;
    }

    private static Course findCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCourseCode().equals(code)) return c;
        }
        return null;
    }
}
