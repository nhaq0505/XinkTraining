import java.util.*;

public class Course {
    private final String courseCode;
    private String courseName;
    private int credits;
    private String instructor;
    private int maxCapacity;
    private List<Student> enrolledStudents;
    private Queue<Student> waitlist; // danh sách chờ
    private Map<String, Integer> schedule; // Day -> Hour

    // ===== Constructor =====
    public Course(String courseCode, String courseName, int credits,
                  String instructor, int maxCapacity,
                  List<Student> enrolledStudents, Map<String, Integer> schedule) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = (enrolledStudents != null) ? enrolledStudents : new ArrayList<>();
        this.waitlist = new LinkedList<>();
        this.schedule = (schedule != null) ? schedule : new HashMap<>();
    }

    // ===== Getter / Setter =====
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.isBlank()) {
            throw new IllegalArgumentException("Tên môn học không hợp lệ.");
        }
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Số tín chỉ phải lớn hơn 0.");
        }
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        if (instructor == null || instructor.isBlank()) {
            throw new IllegalArgumentException("Tên giảng viên không hợp lệ.");
        }
        this.instructor = instructor;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Sức chứa phải lớn hơn 0.");
        }
        this.maxCapacity = maxCapacity;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Map<String, Integer> getSchedule() {
        return schedule;
    }

    // ===== Business Logic =====

    // Đăng ký sinh viên
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.contains(student)) {
            System.out.println("⚠ Sinh viên đã đăng ký môn này rồi.");
            return false;
        }
        if (enrolledStudents.size() < maxCapacity) {
            enrolledStudents.add(student);
            System.out.println("✅ Sinh viên " + student.getFirstName() + " đăng ký thành công môn " + courseName);
            return true;
        } else {
            waitlist.add(student);
            System.out.println("📌 Lớp đã đầy. Sinh viên " + student.getFirstName() + " được đưa vào danh sách chờ.");
            return false;
        }
    }

    // Rút môn học
    public boolean dropStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            System.out.println("✅ Sinh viên " + student.getFirstName() + " đã rút khỏi môn " + courseName);
            // Thêm 1 người từ waitlist vào lớp
            if (!waitlist.isEmpty()) {
                Student next = waitlist.poll();
                enrolledStudents.add(next);
                System.out.println("📌 Sinh viên " + next.getFirstName() + " được chuyển từ danh sách chờ vào lớp.");
            }
            return true;
        }
        return false;
    }




}
