package model;

public class Staff extends Person {
    private final String department;
    private final String role;

    private Staff(Builder builder) {
        super(builder);
        this.department = builder.department;
        this.role = builder.role;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public static class Builder extends Person.Builder<Builder> {
        private String department;
        private String role;

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public Staff build() {
            return new Staff(this);
        }
    }
}
