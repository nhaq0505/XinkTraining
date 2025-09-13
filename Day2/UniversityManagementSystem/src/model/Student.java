package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person  {
    private final String major;
    private final int year;
    private final List<Enrollment> enrollment;

    private Student(Builder builder){
        super(builder);
        this.major = builder.major;
        this.year = builder.year;
        this.enrollment = builder.enrollment;
    }
    public void addEnrollment(Enrollment e){
        enrollment.add(e);
    }

    public List<Enrollment> getEnrollment() {
        return enrollment;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    //Builder Pattern===============================================
    public static class Builder extends Person.Builder<Builder>{
        private String major;
        private int year;
        private List<Enrollment> enrollment = new ArrayList<>();

        public Builder major(String major){
            this.major = major;
            return this;
        }

        public Builder year(int year){
            this.year = year;
            return this;
        }

        public Builder enrollment(List<Enrollment> enrollment){
            this.enrollment = enrollment;
            return this;
        }

        @Override
        public Builder self(){
            return this;
        }

        @Override
        public Student build(){
            return new Student(this);
        }
    }
}
