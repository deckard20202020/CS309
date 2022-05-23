package _HB_2.Backend.review.driverReview;

import _HB_2.Backend.user.User;

import javax.persistence.*;

@Entity
public class DriverReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String review;

    //id of the reviewer
    private int reviewer;

    //this will be the driver that is reviewed
    @ManyToOne
    @JoinColumn(name = "Driver_ID")
    private User reviewDriver;

    public DriverReview() {
    }

    public int getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public User getReviewDriver() {
        return reviewDriver;
    }

    public void setReviewDriver(User reviewDriver) {
        this.reviewDriver = reviewDriver;
    }
}
