package _HB_2.Backend.riderstop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RiderStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int riderId;

    private int tripId;

    private String riderOriginAddress;

    private String riderDestAddress;

    public RiderStop() {}

    public int getRiderId() {return riderId;}

    public void setRiderId(int riderId) {this.riderId = riderId;}

    public int getTripId() {return tripId;}

    public void setTripId(int tripId) {this.tripId = tripId;}

    public String getRiderOriginAddress() {return riderOriginAddress;}

    public void setRiderOriginAddress(String riderOriginAddress) {this.riderOriginAddress = riderOriginAddress;}

    public String getRiderDestAddress() {return riderDestAddress;}

    public void setRiderDestAddress(String riderDestAddress) {this.riderDestAddress = riderDestAddress;}
}
