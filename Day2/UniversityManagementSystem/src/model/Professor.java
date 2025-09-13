package model;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Person {
    private final String department;
    private final List<Course> coursesTaught;

    private Professor(Builder builder) {
        super(builder);
        this.department = builder.department;
        this.coursesTaught = builder.coursesTaught;
    }

    public String getDepartment() {
        return department;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void assignCourse(Course course) {
        coursesTaught.add(course);
    }

    public static class Builder extends Person.Builder<Builder> {
        private String department;
        private List<Course> coursesTaught = new ArrayList<>();

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder addCourse(Course course) {
            this.coursesTaught.add(course);
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public Professor build() {
            return new Professor(this);
        }
    }
}
