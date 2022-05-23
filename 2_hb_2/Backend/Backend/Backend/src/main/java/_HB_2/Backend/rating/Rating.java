package _HB_2.Backend.rating;

import _HB_2.Backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Rater_id")
    private User rater;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Rated_id")
    private User rated;

    private int rating;

    public Rating(){

    }

    public int getId() {
        return id;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public User getRater() {
        return rater;
    }

    public void setRated(User rated) {
        this.rated = rated;
    }

    public User getRated() {
        return rated;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
