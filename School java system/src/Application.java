import java.time.LocalDate;

public class Application {
    private String applicationId;
    private String name;
    private String course;
    private String yearLevel;
    private LocalDate dateSubmitted;
    private String status;

    public Application(String applicationId, String name, String course, String yearLevel, LocalDate dateSubmitted, String status) {
        this.applicationId = applicationId;
        this.name = name;
        this.course = course;
        this.yearLevel = yearLevel;
        this.dateSubmitted = dateSubmitted;
        this.status = status;
    }

    public String getApplicationId() { return applicationId; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public String getYearLevel() { return yearLevel; }
    public LocalDate getDateSubmitted() { return dateSubmitted; }
    public String getStatus() { return status; }
}
