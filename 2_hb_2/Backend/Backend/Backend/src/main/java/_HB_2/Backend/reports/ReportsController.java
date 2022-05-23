package _HB_2.Backend.reports;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ReportsController", description = "REST APIs related to Reports")
@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @ApiOperation(value = "Enter a new report", response = Reports.class)
    @PostMapping("/createReports")
    Reports createReports (@RequestParam int reporter_id,
                           @RequestParam int reported_id,
                           @RequestBody Reports reports){

        return reportsService.createReport(reporter_id, reported_id, reports);
    }

    @ApiOperation(value = "Retrieve a list of reports for a given user", response = Iterable.class)
    @GetMapping("/getUserReports")
    List<Reports> getUserReports(@RequestParam int userId){
        return reportsService.getUserReports(userId);
    }

}
