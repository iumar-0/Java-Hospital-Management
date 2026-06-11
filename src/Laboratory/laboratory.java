package Laboratory;

import Hospital.Hospital;

public class laboratory extends Hospital {
    public laboratory(String patientName, int patientNumber, float patientPayment) {
        super(patientName, patientNumber, patientPayment);
    }


    public void print_details() {
        System.out.println();
        System.out.println("--- Patient details ---");
        System.out.println("Track Order : " + super.getOrder_track());
        System.out.println("Name : " + getPatient_name());
        System.out.println("Patient number : " + getPatient_number());
        System.out.println("Payment : " + getPatient_payment());

    }
}
