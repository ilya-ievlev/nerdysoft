package org.example.model;

public class User {
    private final String name;
    private final int age;

    private User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static User createUser(String name, int age) {
        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("age can't be less than 18 or bigger than 100");
        }
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        return new User(name, age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
