package _HB_2.Backend.review.riderReview;

import _HB_2.Backend.user.User;

import javax.persistence.*;

@Entity
public class RiderReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String review;

    //id of the reviewer
    private int reviewer;

    //this will be the rider that is reviewed
    @ManyToOne
    @JoinColumn(name = "Rider_ID")
    private User reviewRider;

    public RiderReview() {
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

    public User getReviewRider() {
        return reviewRider;
    }

    public void setReviewRider(User reviewRider) {
        this.reviewRider = reviewRider;
    }
}
