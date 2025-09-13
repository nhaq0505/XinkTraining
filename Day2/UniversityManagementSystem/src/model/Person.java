package model;

public abstract class Person {
    protected String name;
    protected String id;
    protected String email;

    protected Person(Builder<?> builder){
        this.name=builder.name;
        this.id = builder.id;
        this.email= builder.email;
    }

    public static abstract class Builder<T extends Builder<T>>{
        protected String name;
        protected String id;
        protected String email;

        public T id(String id){
            this.id=id;
            return self();
        }

        public T name(String name){
            this.name=name;
            return self();
        }

        public T email(String email){
            this.email =email;
            return self();
        }
        public abstract T self();
        public abstract Person build();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "Name: "+name+" ID: "+id+" Email: "+email;
    }





}
