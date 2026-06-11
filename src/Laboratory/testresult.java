package Laboratory;

public class testresult extends laboratory {
    private String disease;
    private String note;


    public testresult(String patientName, int patientNumber, float patientPayment, String disease, String note) {
        super(patientName, patientNumber, patientPayment);
        this.disease = disease;
        this.note = note;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void print_testdetials() {
        System.out.println();
        System.out.println("--- Patient details ---");
        System.out.println("Name : " + getPatient_name());
        System.out.println("Patient number : " + getPatient_number());
        System.out.println("Payment : " + getPatient_payment());
        System.out.println("Disease : " + getDisease());
        System.out.println("Note : " + getNote());

    }
}
