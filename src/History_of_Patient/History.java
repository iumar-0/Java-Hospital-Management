package History_of_Patient;

import java.time.LocalDateTime;

public class History {
    private String name;
    private int aga;
    private String disease;
    private String date;
    public int appointment_id;
    private float price;
    public LocalDateTime dateTime;

    public History(int aga, int appointment_id, String date, String disease, String name, float price, LocalDateTime dateTime) {
        this.aga = aga;
        this.appointment_id = appointment_id;
        this.date = date;
        this.disease = disease;
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
    }

    public int getAga() {
        return aga;
    }

    public void setAga(int aga) {
        this.aga = aga;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void print_appointment() {
        System.out.println("--- Appointment Details ---");
        System.out.println("Patient name : " + getName());
        System.out.println("Patient age : " + getAga());
        System.out.println("Patient disease : " + getDisease());
        System.out.println("Patient visit date : " + getDate());
        System.out.println("Appointment ID : " + getAppointment_id());
        System.out.println("Hospital Price : " + getPrice());
        System.out.println("Doctor Appointment Date : " + this.dateTime);
    }
}
