package incidentreport;

public class incident {
    public String incident_reporting;
    public String time_incident;

    public incident(String incident_reporting, String time_incident) {
        this.incident_reporting = incident_reporting;
        this.time_incident = time_incident;
    }

    public void print_incident() {
        System.out.println("Incident Report :" + this.incident_reporting);
        System.out.println("Time : " + this.time_incident);
    }
}
