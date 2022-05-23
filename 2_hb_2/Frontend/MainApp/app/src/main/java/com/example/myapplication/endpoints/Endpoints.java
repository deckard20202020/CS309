package com.example.myapplication.endpoints;

/**
 * commonly used request Endpoints in the app
 */
public final class Endpoints {

    /**
     * where drivers register an account
     */
    public static final String DriverRegUrl = "http://coms-309-030.class.las.iastate.edu:8080/driver/registerDriver/";

    /**
     * where riders register an account
     */
    public static final String RiderRegUrl = "http://coms-309-030.class.las.iastate.edu:8080/rider/registerRider/";

    /**
     * where users sign in
     */
    public static final String LoginUrl = "http://coms-309-030.class.las.iastate.edu:8080/user/getUserSignIn?email=";

    /**
     * where users edit their account
     */
    public static final String EditUserUrl = "http://coms-309-030.class.las.iastate.edu:8080/user/editUser?id=";

    /**
     * where admins can delete a user
     */
    public static final String DeleteUserUrl = "http://coms-309-030.class.las.iastate.edu:8080/admin/deleteUser?id=";

    /**
     * where trips can be deleted
     */
    public static final String DeleteTripUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/deleteTripById";

    /**
     * where a driver can create a trip
     */
    public static final String DriverCreateTripUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/createTripByDriver?driverId=";

    /**
     * where a trip can be edited
     */
    public static final String EditTripUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/editTrip";

    /**
     * where all active trips from a rider a shown
     */
    public static final String AllDriverTripsUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/getAllActiveTripsFromDriverId?driverId=";

    /**
     * where a rider searches for a trip based on start date
     */
    public static final String RiderSearchTripUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/getPossibleTripsForRider?scheduledStartDate=";

    /**
     * where a rider is added to a trip
     */
    public static final String AddRiderToTripUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/addRiderToTrip?tripId=";

    /**
     * where directions from place A to place B are generated
     */
    public static final String GoogleMapsDirectionUrl = "https://maps.googleapis.com/maps/api/directions/json?";

    /**
     * where the distance from place A to place B is calculated
     */
    public static final String GoogleMapsDistanceUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?";

    /**
     * where users change their profile picture
     */
    public static final String SetProfilePictureUrl = "http://coms-309-030.class.las.iastate.edu:8080/user/setProfilePicture?userId=";

    /**
     * gets user details
     */
    public static final String GetUserUrl = "http://coms-309-030.class.las.iastate.edu:8080/user/getUser?id=";

    /**
     * gets trips of a specific rider
     */
    public static final String GetRiderTrips = "http://coms-309-030.class.las.iastate.edu:8080/trip/getAllActiveTripsFromRiderId?riderId=";

    /**
     * removes a rider from trip
     */
    public static final String RemoveRiderFromTrip = "http://coms-309-030.class.las.iastate.edu:8080/trip/removeRiderFromTrip?tripId=";

    /**
     * where a rating is mader for a user
     */
    public static final String CreateRatingUrl = "http://coms-309-030.class.las.iastate.edu:8080/rating/createRating?raterId=";

    /**
     * where a driver reviews a rider
     */
    public static final String PostRiderReview = "http://coms-309-030.class.las.iastate.edu:8080/riderReview/postRiderReview?driverId=";

    /**
     * where a driver reviews a rider
     */
    public static final String PostDriverReview = "http://coms-309-030.class.las.iastate.edu:8080/driverReview/postDriverReview?driverId=";

    /**
     * gets the rating for a user
     */
    public static final String GetUserRating = "http://coms-309-030.class.las.iastate.edu:8080/rating/getUserRating?userId=";

    /**
     * gets the reviews of a rider
     */
    public static final String GetRiderReviews = "http://coms-309-030.class.las.iastate.edu:8080/riderReview/getAllRiderReviewsByRiderId?riderId=";

    /**
     * gets the reviews of a rider
     */
    public static final String GetDriverReviews = "http://coms-309-030.class.las.iastate.edu:8080/driverReview/getAllDriverReviewsByDriverId?driverId=";

    /**
     * gets the driver of a trip
     */
    public static final String GetTripDriverUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/getDriverInfoByTripId?tripId=";

    /**
     * url for chat websocket
     */
    public static final String ChatUrl = "ws://coms-309-030.class.las.iastate.edu:8080/chat/%7B";

    /*
     * gets origin and destinations of riders in a trip
     */
    public static final String GetRiderStops = "http://coms-309-030.class.las.iastate.edu:8080/riderStop/getRiderStopsByTripId?tripId=";

    /**
     * starts a trip
     */
    public static final String SetTripStartedUrl = "http://coms-309-030.class.las.iastate.edu:8080/trip/setTripStarted?tripId=";

    /**
     * get messages for a pair
     */
    public static final String GetMessages = "http://coms-309-030.class.las.iastate.edu:8080/message/getMessagesForPairOfUsers?user1Id=";
}
