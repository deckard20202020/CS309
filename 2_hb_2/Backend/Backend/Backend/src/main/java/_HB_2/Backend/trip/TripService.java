package _HB_2.Backend.trip;

import _HB_2.Backend.riderstop.RiderStop;
import _HB_2.Backend.riderstop.RiderStopRepository;
import _HB_2.Backend.user.User;
import _HB_2.Backend.driver.DriverRepository;
import _HB_2.Backend.rider.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderStopRepository riderStopRepository;

    //there should be no riders in the trip initially
    public Trip createTripByDriver(int Id, Trip trip) {

        User d =  driverRepository.getById(Id);
                trip.setHasARider(false);;
                trip.setHasADriver(true);
                trip.setConfirmed(false);
                trip.setHasStarted(false);
                trip.setCompleted(false);
                trip.setTripDriver(d);
                trip.setNumberOfRiders(0);
                trip.setRiders(new HashSet<>());

        tripRepository.save(trip);
        return trip;
    }

    public Trip createTripByRider(int id, Trip trip){
        User r = riderRepository.getById(id);
        Set<User> riders = new HashSet<>();
        riders.add(r);
        trip.setHasARider(true);
        trip.setHasADriver(false);
        trip.setConfirmed(false);
        trip.setHasStarted(false);
        trip.setCompleted(false);
        trip.setRiders(riders);
        trip.setNumberOfRiders(1);
        trip.setRatePerMin(0);

        tripRepository.save(trip);
        return trip;
    }

    @Transactional
    public Trip getTripById(int id) {
        return tripRepository.findById(id);

    }

    public Trip addRiderToTripById(int tripId, int riderId, String riderOriginAddress, String riderDestAddress){
        //need to do a try/catch here
            //what if we try to add a rider but the trip is already full???

        //find the trip we are adding the rider to
        Trip addRiderToThis = tripRepository.findById(tripId);
        //find the rider we are adding to the trip
        User rider = riderRepository.findById(riderId);
        //add the rider to the trip
        addRiderToThis.addRider(rider);

        //create a riderStop
        RiderStop riderStop = new RiderStop();
        riderStop.setRiderId(riderId);
        riderStop.setRiderOriginAddress(riderOriginAddress);
        riderStop.setRiderDestAddress(riderDestAddress);
        riderStop.setTripId(tripId);
        riderStopRepository.save(riderStop);

        tripRepository.save(addRiderToThis);
        return tripRepository.findById(tripId);
    }

    public Trip removeRiderFromTripById(int tripId, int riderId){
        Trip removeRiderFromThis = tripRepository.findById(tripId);
        User rider = riderRepository.findById(riderId);
        removeRiderFromThis.removeRiderById(rider);

        riderStopRepository.deleteByTripIdAndRiderId(tripId, riderId);
        tripRepository.save(removeRiderFromThis);
        return tripRepository.findById(tripId);
    }

    public Trip editTripById(int tripId, Trip newTripInfo) {

        Trip newTrip = tripRepository.findById(tripId);

        newTrip.setScheduledStartDate(newTripInfo.getScheduledStartDate());
        newTrip.setScheduledEndDate(newTripInfo.getScheduledEndDate());

        newTrip.setActualStartDate(newTripInfo.getActualStartDate());
        newTrip.setActualEndDate(newTripInfo.getActualEndDate());

        //need to adjust maxNumberOfRiders before we adjust the riders
        newTrip.setMaxNumberOfRiders(newTripInfo.getMaxNumberOfRiders());

        //Set the numberOfRiders to 0
        //the addRiderById funtion in the trip class will increment as we add riders below
        newTrip.setNumberOfRiders(0);

        newTrip.setHasARider(newTripInfo.isHasARider());
        if (newTripInfo.isHasARider()) {
            Set<User> newRiders = newTripInfo.getRiders();
            for (User newRider : newRiders) {
                newTripInfo.addRider(newRider);
            }
        }

        newTrip.setHasADriver(newTripInfo.isHasADriver());
        if (newTripInfo.isHasADriver()) {
            newTrip.setTripDriver(newTripInfo.getTripDriver());
        }

        newTrip.setConfirmed(newTripInfo.isConfirmed());
        newTrip.setHasStarted(newTripInfo.isHasStarted());
        newTrip.setCompleted(newTripInfo.isCompleted());

        newTrip.setOriginAddress(newTripInfo.getOriginAddress());
        newTrip.setDestAddress(newTripInfo.getDestAddress());

        newTrip.setRadius(newTripInfo.getRadius());

        tripRepository.save(newTrip);

        return getTripById(tripId);
    }

    public List<Trip> getAllTrips() {
        List<Trip> list = new ArrayList<>();
        list = tripRepository.findAll();
        return list;
    }

    //returns a list of all trips that have not been completed
    public List<Trip> getAllActiveTrips() {
        List<Trip> list = new ArrayList<>();
        list = tripRepository.findAll();

        List<Trip> activeTrips = new ArrayList<>();
        for(Trip trip: list) {
            if (!trip.isCompleted()) {
                activeTrips.add(trip);
            }
        }

        return activeTrips;
    }

    //we should consider speeding this up by writing a sql query to only retrieve
    //the trips that we want from the database rather than filtering them here.
    public List<Trip> getTripsForRider(LocalDateTime startDate, LocalDateTime endDate) {

        List<Trip> allTrips = tripRepository.findAll();

        Map<String, LocalDateTime> timeWindows = getTimeWindows(startDate, endDate);

        List<Trip> validTrips = new ArrayList<>();
        for(Trip trip: allTrips) {
            //need this check for trips in database with null start and end times.
            //avoids null pointer exceptions- this check could be deleted if all rows in
            //database had scheduled start dates and scheduled end dates.
            if (trip.getScheduledStartDate() != null && trip.getScheduledEndDate() != null) {
                //check to see if the start date and end dates of the trip are the same as our request
                //currently we ignore the time of day.  If the day is the same the trip is valid.
                if (    !trip.isHasStarted() &&
                        trip.getScheduledStartDate().isAfter(timeWindows.get("earliestStartDate")) &&
                        trip.getScheduledStartDate().isBefore(timeWindows.get("latestStartDate")) &&
                        trip.getScheduledEndDate().isAfter(timeWindows.get("earliestEndDate")) &&
                        trip.getScheduledEndDate().isBefore(timeWindows.get("latestEndDate"))) {
                    //check to make sure the trip has room for another rider
                    if (trip.getNumberOfRiders() < trip.getMaxNumberOfRiders()) {
                        validTrips.add(trip);
                    }
                }
            }
        }

        return validTrips;

    }

    //returns a map of the windows for the trip we want
    private Map<String, LocalDateTime> getTimeWindows(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, LocalDateTime> timeWindows = new HashMap<>();

        LocalDateTime earliestStartDate = beginningOfDay(startDate);
        LocalDateTime latestStartDate = endOfDay(startDate);
        LocalDateTime earliestEndDate = beginningOfDay(endDate);
        LocalDateTime latestEndDate = endOfDay(endDate);

        timeWindows.put("earliestStartDate", earliestStartDate);
        timeWindows.put("latestStartDate", latestStartDate);
        timeWindows.put("earliestEndDate", earliestEndDate);
        timeWindows.put("latestEndDate", latestEndDate);

        return timeWindows;
    }

    //edits a LocalDateTime to the beginning of the day
    private LocalDateTime beginningOfDay(LocalDateTime startDate) {
        LocalDateTime answer = startDate.withHour(00).withMinute(00).withSecond(00);

        return answer;
    }

    //edits a LocalDateTime to the end of the day
    private LocalDateTime endOfDay(LocalDateTime startDate) {
        LocalDateTime answer = startDate.withHour(23).withMinute(59).withSecond(59);

        return answer;
    }

    public Trip completeTripById(int tripId) {
        Trip tripToBeCompleted = tripRepository.findById(tripId);
        tripToBeCompleted.setCompleted(true);
        tripRepository.save(tripToBeCompleted);
        return tripRepository.findById(tripId);
    }

    public Trip startTripById(int tripId) {
        Trip tripToBeStarted = tripRepository.findById(tripId);
        tripToBeStarted.setHasStarted(true);
        tripRepository.save(tripToBeStarted);
        return tripRepository.findById(tripId);
    }

    public void deleteTripById(int id) {
        tripRepository.deleteById(id);
    }

    public User getDriverInfoByTripId(int tripId) {
        Trip t = tripRepository.findById(tripId);
        User driver = t.getTripDriver();
        return driver;
    }
}
