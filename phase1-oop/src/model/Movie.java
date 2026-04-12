package model;

public class Movie {
    private int id; // encapsulation(s)
    private String name;
    private String genre;
    private double rating;

    public Movie(int id, String name, String genre, double rating) {
        this.id = id; // this keyword --> refers the current object(s)
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if(rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Enter values between 0 to 5");
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ". rating=" + rating +
                '}';
    }
}
