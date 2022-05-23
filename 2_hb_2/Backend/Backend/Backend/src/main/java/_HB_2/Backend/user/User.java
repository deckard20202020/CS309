package _HB_2.Backend.user;

import _HB_2.Backend.rating.Rating;
import _HB_2.Backend.reports.Reports;
import _HB_2.Backend.review.driverReview.DriverReview;
import _HB_2.Backend.review.riderReview.RiderReview;
import _HB_2.Backend.trip.Trip;
import _HB_2.Backend.websockets.chat.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Admin.class, name = "Admin"),
//
//        @JsonSubTypes.Type(value = Driver.class, name = "Driver") ,
//
//        @JsonSubTypes.Type(value = Rider.class, name = "Rider") }
//)

@Entity
//we will need to define the name of the table where we store the users
//@Table(name = "users")
public class User {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    @Column(unique = true, nullable = false)
    private String email;
    private String phoneNumber;
    private String password;
    private Boolean isADriver;
    private Boolean isARider;
    private Boolean isAnAdmin;

    @ManyToMany(mappedBy = "riders", cascade = CascadeType.REMOVE)
    private Set<Trip> trips;

    //We need the orphanRemoval = true here so we can delete whatever the user is related to when deleting the user
    @JsonIgnore
    @OneToMany(mappedBy = "rater", orphanRemoval = true)
    private Set<Rating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy = "rated", orphanRemoval = true)
    private Set<Rating> rating;

    @JsonIgnore
    @OneToMany(mappedBy = "userSent", orphanRemoval = true)
    private Set<Message> userSent;

    @JsonIgnore
    @OneToMany(mappedBy = "userReceived", orphanRemoval = true)
    private Set<Message> userReceived;

    @JsonIgnore
    @OneToMany(mappedBy = "reporter", orphanRemoval = true)
    private Set<Reports> reports;

    @JsonIgnore
    @OneToMany(mappedBy = "reported", orphanRemoval = true)
    private Set<Reports> report;

    @JsonIgnore
    @OneToMany(mappedBy = "tripDriver", orphanRemoval = true)
    private Set<Trip> trip;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewDriver", orphanRemoval = true)
    private Set<DriverReview> driverReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewRider", orphanRemoval = true)
    private Set<RiderReview> riderReviews;

    private String profilePicture;

    private boolean isBanned;

    //Can this constructor be deleted?  Where is it used?
    //no Boolean Values for User-These should be set in the subclass constructors
    public User(String firstName,
                String lastName,
                String address,
                String city,
                String state,
                String zip,
                String email,
                String phoneNumber,
                String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    //https://stackoverflow.com/questions/18099127/java-entity-why-do-i-need-an-empty-constructor
    public User() {}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getADriver() {
        return isADriver;
    }

    public void setADriver(Boolean ADriver) {
        isADriver = ADriver;
    }

    public Boolean getARider() {
        return isARider;
    }

    public void setARider(Boolean ARider) {
        isARider = ARider;
    }

    public Boolean getAnAdmin() {
        return isAnAdmin;
    }

    public void setAnAdmin(Boolean anAdmin) {
        isAnAdmin = anAdmin;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public String getProfilePicture() {return profilePicture;}

    public void setProfilePicture(String profilePicture) {this.profilePicture = profilePicture;}

    public boolean isBanned() {return isBanned;}

    public void setBanned(boolean banned) {isBanned = banned;}
}
