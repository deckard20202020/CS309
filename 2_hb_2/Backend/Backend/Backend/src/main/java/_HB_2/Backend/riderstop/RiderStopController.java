package _HB_2.Backend.riderstop;

import _HB_2.Backend.trip.Trip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "RiderStopController", description = "REST APIs related to RiderStops")
@RestController
@RequestMapping( "/riderStop")
public class RiderStopController {

    @Autowired
    RiderStopService riderStopService;

    @ApiOperation(value = "Get RiderStops for a Trip", response = Trip.class)
    @GetMapping("/getRiderStopsByTripId")
    List<RiderStop> getRiderStopsByTripId(@RequestParam int tripId) {

        List<RiderStop> riderStops = riderStopService.getRiderStopsByTripId(tripId);

        return riderStops;
    }
}
