package _HB_2.Backend.reports;
import _HB_2.Backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Reporter_id")
    private User reporter;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Reported_id")
    private User reported;

    private String reportMessage;

    public Reports() {
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String message){
        this.reportMessage = message;
    }

    public User getReported() {
        return reported;
    }

    public void setReported(User reported){
        this.reported=reported;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public int getId() {
        return id;
    }
}
