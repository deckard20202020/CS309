package _HB_2.Backend.trip;

import _HB_2.Backend.riderstop.RiderStop;
import _HB_2.Backend.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime scheduledStartDate;
    private LocalDateTime scheduledEndDate;

    private LocalDateTime actualStartDate;
    private LocalDateTime actualEndDate;

    private boolean hasARider;
    private boolean hasADriver;
    private boolean isConfirmed;
    private boolean hasStarted;
    private boolean isCompleted;

    private String originAddress;

    private String destAddress;

    /*
    Please Do Not remove these @JosonIgnore annotations without checking with Matt first.
    These might need to be relocated in the future
    Prevents infinite loop in JSON objects returned.
     */
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Driver_ID")
    private User tripDriver;

//    @OneToMany
//    @JoinColumn(name = "Trip_Id")
//    private Set<RiderStop> riderStops;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinTable(name = "Trip_Riders",
                joinColumns = @JoinColumn(name = "trip_id"),
                inverseJoinColumns = @JoinColumn(name = "rider_id"))
    private Set<User> riders;

    private int maxNumberOfRiders;

    private int numberOfRiders;

    //represent distances from driver start location
    //that the driver is willing to pick up/drop off a rider
    private int radius;

    private double ratePerMin;

    //Empty Constructor
    public Trip() {
    }

    public List<Integer> getRiderIds() {
        List<Integer> riderIds = new ArrayList<>();

        if (riders != null) {
            for (User user : riders) {
                riderIds.add(user.getId());
            }
        }

        return riderIds;
    }

    public void addRider(User rider) {
        //This might need to throw an error
            //check to see if the rider/user isARider
            //what if we don't have room for the rider?
        if (numberOfRiders < maxNumberOfRiders) {
            riders.add(rider);
            numberOfRiders++;
            hasARider = true;
        }
    }

    public void removeRiderById(User rider){
        //Probably need to add try/catch to avoid trying to remove rider from trip that doesn't have a rider
        riders.remove(rider);
        numberOfRiders--;

        if (numberOfRiders == 0) {
            hasARider = false;
        }

    }

    public int getId() {return id;}

    public LocalDateTime getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(LocalDateTime scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public LocalDateTime getScheduledEndDate() {
        return scheduledEndDate;
    }

    public void setScheduledEndDate(LocalDateTime scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public LocalDateTime getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(LocalDateTime actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public LocalDateTime getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public boolean isHasARider() {
        return hasARider;
    }

    public void setHasARider(boolean hasARider) {
        this.hasARider = hasARider;
    }

    public boolean isHasADriver() {
        return hasADriver;
    }

    public void setHasADriver(boolean hasADriver) {
        this.hasADriver = hasADriver;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public User getTripDriver() {
        return tripDriver;
    }

    public void setTripDriver(User tripDriver) {
        this.tripDriver = tripDriver;
    }

    @Transactional
    public Set<User> getRiders() {
        return riders;
    }

    public void setRiders(Set<User> riders) {
        this.riders = riders;
    }

    public int getMaxNumberOfRiders() {
        return maxNumberOfRiders;
    }

    public void setMaxNumberOfRiders(int maxNumberOfRiders) {
        this.maxNumberOfRiders = maxNumberOfRiders;
    }

    public int getNumberOfRiders() {
        return numberOfRiders;
    }

    public void setNumberOfRiders(int numberOfRiders) {
        this.numberOfRiders = numberOfRiders;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getRatePerMin() { return ratePerMin; }

    public void setRatePerMin(double ratePerMin) {
        this.ratePerMin = ratePerMin;
    }

//    public void addRiderStop(RiderStop riderStop) {
//        this.riderStops.add(riderStop);
//    }
//
//    public void removeRiderStop(int riderId) {
//        for(RiderStop riderStop: this.riderStops) {
//            if (riderStop.getRiderId() == riderId) {
//                this.riderStops.remove(riderStop);
//            }
//        }
//    }
}
