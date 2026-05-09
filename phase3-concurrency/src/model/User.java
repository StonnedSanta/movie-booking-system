package model;

public class User {

    private int userId; // Encapsulation(s)
    private String name;

    public User(int userId, String name) { // A Constructor
        if (name == null || name.isEmpty()) { // Validation
            throw new IllegalArgumentException("Name must not be blank");
        }

        this.userId = userId; // this keyword(s)
        this.name = name;
    }

    public int getUserId() { // Getter(s)
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
