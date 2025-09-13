package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final int capacity;
    private final String dayOfWeek;  // ví dụ: "Monday"
    private final int startHour;     // 24h format, ví dụ: 9
    private final int endHour;       // ví dụ: 11
    private final List<Course> prerequisites;

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.capacity = builder.capacity;
        this.dayOfWeek = builder.dayOfWeek;
        this.startHour = builder.startHour;
        this.endHour = builder.endHour;
        this.prerequisites = builder.prerequisites;
    }

    // --- Getters ---
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public String getDayOfWeek() { return dayOfWeek; }
    public int getStartHour() { return startHour; }
    public int getEndHour() { return endHour; }
    public List<Course> getPrerequisites() { return prerequisites; }

    // --- Business Logic ---
    public boolean hasPrerequisites() {
        return !prerequisites.isEmpty();
    }

    public boolean conflictsWith(Course other) {
        if (!this.dayOfWeek.equals(other.dayOfWeek)) return false;
        return this.startHour < other.endHour && other.startHour < this.endHour;
    }

    // --- Builder Pattern ---
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private int capacity = 50; // mặc định
        private String dayOfWeek;
        private int startHour;
        private int endHour;
        private List<Course> prerequisites = new ArrayList<>();


        public Builder prerequisites(List<Course> prerequisites) {
            this.prerequisites = prerequisites;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder schedule(String dayOfWeek, int startHour, int endHour) {
            this.dayOfWeek = dayOfWeek;
            this.startHour = startHour;
            this.endHour = endHour;
            return this;
        }

        public Builder addPrerequisite(Course course) {
            this.prerequisites.add(course);
            return this;
        }

        public Course build() {
            if (code == null || title == null || credits <= 0) {
                throw new IllegalStateException("Course must have code, title, and valid credits.");
            }
            return new Course(this);
        }
    }

    // --- equals/hashCode để so sánh course theo code ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
