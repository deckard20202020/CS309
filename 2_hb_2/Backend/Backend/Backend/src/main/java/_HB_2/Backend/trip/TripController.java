package _HB_2.Backend.trip;

import _HB_2.Backend.reports.Reports;
import _HB_2.Backend.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Api(value = "TripController", description = "REST APIs related to Trips")
@RestController
@RequestMapping( "/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepository tripRepository;

    //there will be no riders in the initial creation of the trip
    @ApiOperation(value = "Enter a new trip, created by a driver", response = Trip.class)
    @PostMapping("/createTripByDriver")
    Trip createTripByDriver(
            @RequestParam int driverId,
            @RequestBody Trip trip) {
        Trip t = tripService.createTripByDriver(driverId, trip);

        return t;
    }

//    @ApiOperation(value = "Enter a new trip, created by a rider", response = Trip.class)
//    @PostMapping("/createTripByRider")
//    Trip createTripByRider(
//            @RequestParam int riderId,
//            @RequestBody Trip trip){
//        Trip t = tripService.createTripByRider(riderId, trip);
//
//        return t;
//    }

    @ApiOperation(value = "Retrieve a trip given its id", response = Trip.class)
    @GetMapping("/getTrip")
    Trip getListOfTripsByDriverId(
            @RequestParam int id) {

        Trip trip = tripService.getTripById(id);
        return trip;
    }

    @ApiOperation(value = "Edit a trip given its id", response = Trip.class)
    @PutMapping("/editTrip")
    Trip getTripById(
            @RequestParam int tripId,
            @RequestBody Trip t) {
        return tripService.editTripById(tripId, t);
    }

    @ApiOperation(value = "Add a rider to an existing trip", response = Trip.class)
    @PutMapping("/addRiderToTrip")
    Trip addRiderToTrip(@RequestParam int tripId,
                          @RequestParam int riderId,
                        @RequestParam String riderOriginAddress,
                        @RequestParam String riderDestAddress) {
        return tripService.addRiderToTripById(tripId, riderId, riderOriginAddress, riderDestAddress);
    }

    @ApiOperation(value = "Remove a rider from a trip", response = Trip.class)
    @PutMapping("/removeRiderFromTrip")
    Trip removeRiderFromTripById(@RequestParam int tripId,
                               @RequestParam int riderId) {
        return tripService.removeRiderFromTripById(tripId, riderId);
    }

    @ApiOperation(value = "Set the trip as completed", response = Trip.class)
    @PutMapping("/completeTrip")
    Trip completeTripById(
            @RequestParam int id) {
        return tripService.completeTripById(id);
    }

    @ApiOperation(value = "Set the trip as being started", response = String.class)
    @PutMapping("/setTripStarted")
    String startTripById(
            @RequestParam int tripId) {
        tripService.startTripById(tripId);
        return "Started trip with id " + tripId;
    }

    //returns a list of all trips that have not been completed
    @ApiOperation(value = "Retrieve a list of uncompleted trips of a driver", response = Iterable.class)
    @GetMapping("/getAllActiveTripsFromDriverId")
    List<Trip> getAllActiveTrips(
            @RequestParam int driverId) {
        return tripRepository.getAllUncompletedTripsByDriverId(driverId);
    }

    @ApiOperation(value = "Retrieve a list of uncompleted trips of a rider", response = Iterable.class)
    @GetMapping("/getAllActiveTripsFromRiderId")
    List<Trip> getAllActiveTrips2(
            @RequestParam int riderId) {
        return tripRepository.getAllUncompletedTripsByRiderId(riderId);

    }

    @ApiOperation(value = "Retrieve a list of all trips", response = Iterable.class)
    @GetMapping("/getAllTrips")
    List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    //returns a list of all trips that have not been completed
    @ApiOperation(value = "Retrieve a list of all active trips ", response = Iterable.class)
    @GetMapping("/getAllActiveTrips")
    List<Trip> getAllActiveTrips() {
        List<Trip> list = new ArrayList<>();
        return tripService.getAllActiveTrips();
    }

    @ApiOperation(value = "Retrieve a list of trips for a rider given their input", response = Iterable.class)
    @GetMapping("/getPossibleTripsForRider")
    List<Trip> getTripsForRider(
            @RequestParam("scheduledStartDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledStartDate,

            @RequestParam("scheduledEndDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledEndDate) {

        return tripService.getTripsForRider(scheduledStartDate,scheduledEndDate);
    }

    @ApiOperation(value = "Delete a trip by its id", response = String.class)
    @DeleteMapping("/deleteTripById")
    String deleteTripById(
            @RequestParam int id) {
        tripService.deleteTripById(id);
        return "You have deleted trip " + id;
    }

    @ApiOperation(value = "Get driver info by Trip Id", response = User.class)
    @GetMapping("/getDriverInfoByTripId")
    User getDriverInfoByTripId(@RequestParam int tripId) {

        return tripService.getDriverInfoByTripId(tripId);
    }



}
