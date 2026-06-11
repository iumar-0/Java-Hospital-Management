package Hospital;

public class Hospital {

    private String Patient_name;
    private int Patient_number;
    private float Patient_payment;
    private String order_track;



    public Hospital(String patient_name, int patient_number, float patient_payment) {
        this.Patient_name = patient_name;
        this.Patient_number = patient_number;

        this.Patient_payment = patient_payment;
        this.order_track = "Still Working";

    }

//    public void print_incident() {
//        System.out.println("Incident Report :" + this.incident_reporting);
//        System.out.println("Time : " + time_incident);
//    }
    public String getOrder_track() {
        return order_track;
    }

    public void setOrder_track(String order_track) {
        this.order_track = order_track;
    }

    public String getPatient_name() {
        return Patient_name;
    }

    public void setPatient_name(String patient_name) {
        Patient_name = patient_name;
    }

    public int getPatient_number() {
        return Patient_number;
    }

    public void setPatient_number(int patient_number) {
        Patient_number = patient_number;
    }

    public float getPatient_payment() {
        return Patient_payment;
    }

    public void setPatient_payment(float patient_payment) {
        Patient_payment = patient_payment;
    }

}

