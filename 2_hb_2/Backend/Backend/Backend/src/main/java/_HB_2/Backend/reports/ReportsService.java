package _HB_2.Backend.reports;

import _HB_2.Backend.user.User;
import _HB_2.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportsRepository reportsRepository;

    public Reports createReport(int reporter_id, int reported_id, Reports reports){
        User reporter = userRepository.findById(reporter_id);
        User reported = userRepository.findById(reported_id);

        reports.setReporter(reporter);
        reports.setReported(reported);

        reportsRepository.save(reports);
        return reports;
    }

    public List<Reports> getUserReports(int userId){
        User reported = userRepository.findById(userId);
        return reportsRepository.getAllReportsOfUser(reported);
    }
}
