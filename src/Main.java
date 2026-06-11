
//Importing Class

import Hospital.FileUtility;
import Hospital.Hospital;
import Laboratory.*;
import Staff.*;
import Pharmacy.*;
import History_of_Patient.*;
import incidentreport.incident;

//Importing Lib
import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

//important imports
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;

//save file to -- Json --
class filesaving implements Runnable {
    public void run() {
        try {
            boolean running = true;
            while (running) {
                Thread.sleep(10000);
                FileUtility.saveToFile(Hospital_Management.LABORATORY_FILE, Hospital_Management.Laboratory_placingname);
                FileUtility.saveToFile(Hospital_Management.TEST_RESULTS_FILE, Hospital_Management.Test_Result_Management);
                FileUtility.saveToFile(Hospital_Management.STAFF_FILE, Hospital_Management.Laboratory_staff);
                FileUtility.saveToFile(Hospital_Management.PHARMACY_MEDICINE, Hospital_Management.Pharmacy_Medicine);
                FileUtility.saveToFile(Hospital_Management.PATIENT_HISTORY, Hospital_Management.Patient_History);
                FileUtility.saveToFile(Hospital_Management.HOSPITAL_STAFF, Hospital_Management.Hospital_staff);
                FileUtility.saveToFile(Hospital_Management.INCIDENT_REPORT, Hospital_Management.Incident_reporting);
            }
            System.out.println("Data loaded successfully" + System.currentTimeMillis());

        } catch (Exception e) {
            System.out.println("File cannot be saved");
        }
    }
}


class Hospital_Management {
    //All files
    static String LABORATORY_FILE = "LaboratoryPatients.json";
    static String TEST_RESULTS_FILE = "TestResults.json";
    static String STAFF_FILE = "LaboratoryStaff.json";
    static String PHARMACY_MEDICINE = "PharmacyMedicine.json";
    static String PATIENT_HISTORY = "PatientHistory.json";
    static String HOSPITAL_STAFF = "HospitalStaff.json";
    static String INCIDENT_REPORT = "IncidentReprot.json";

    //---------- MY SQL connection create  ---------
    static final String url = "jdbc:mysql://127.0.0.1:3306/Hospital_Managment";
    static final String username = "root";
    static final String password = "Umar7515.";
    static Connection connection;


    //Typing the genrics
    static Type laboratoryType = new TypeToken<HashMap<Integer, laboratory>>() {
    }.getType();
    static Type testResultType = new TypeToken<HashMap<Integer, testresult>>() {
    }.getType();
    static Type staffType = new TypeToken<HashMap<Integer, Staff>>() {
    }.getType();
    static Type pharmacyType = new TypeToken<HashMap<Integer, Pharmacy>>() {
    }.getType();
    static Type patientType = new TypeToken<HashMap<Integer, History>>() {
    }.getType();
    static Type hospitalStaffType = new TypeToken<HashMap<Integer, Staff>>() {
    }.getType();
    static Type incidentreprotType = new TypeToken<HashMap<Integer, incident>>() {
    }.getType();

    //methods
    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    //===========================Creating Hashmaps===================================================================================
    static HashMap<Integer, laboratory> Laboratory_placingname = FileUtility.loadFromFile(LABORATORY_FILE, laboratoryType);
    static HashMap<Integer, testresult> Test_Result_Management = FileUtility.loadFromFile(TEST_RESULTS_FILE, testResultType);
    static HashMap<Integer, Staff> Laboratory_staff = FileUtility.loadFromFile(STAFF_FILE, staffType);
    static HashMap<Integer, Pharmacy> Pharmacy_Medicine = FileUtility.loadFromFile(PHARMACY_MEDICINE, pharmacyType);
    static HashMap<Integer, History> Patient_History = FileUtility.loadFromFile(PATIENT_HISTORY, patientType);
    static HashMap<Integer, Staff> Hospital_staff = FileUtility.loadFromFile(HOSPITAL_STAFF, hospitalStaffType);
    static HashMap<Integer, incident> Incident_reporting = FileUtility.loadFromFile(INCIDENT_REPORT, incidentreprotType);


    // ----------------------     this is mian control point( menu )      ----------------
    public static void main(String[] args) throws SQLException {
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("There is an Exception " + e);
        }

        Thread filesaving = new Thread(new filesaving());
        filesaving.start();
        System.out.println("Welcome to the Hospital.Hospital management");
        boolean exit = true;
        while (exit) {
            System.out.println("Menu ");
            System.out.println("1) Admin ");
            System.out.println("2) Staff ");
            System.out.println("3) Patient Information");
            System.out.println("4) Pharmacy Management ");
            System.out.println("5) Laboratory Management");
            System.out.println("6) Exit");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    admincontrol();
                    break;
                case 2:
                    staff();
                case 3:
                    patient_information();
                    break;
                case 4:
                    pharmacy_management();
                    break;
                case 5:
                    System.out.println("--- Laboratory management ---");
                    laboratory_management();
                    break;

                case 6:
                    System.out.println("Exiting ...");
                    exit = false;
                    filesaving.interrupt();
                    break;
                default:
                    System.out.println("--- Invalid input ---");
                    break;
            }
        }

    }

    //admin password check and menu
    public static void admincontrol() {
        System.out.println("Enter the password :");


        int pass = sc.nextInt();
        if (pass == 1234) {
            menu();
        } else {
            return;
        }
    }

    //admin menu options
    public static void menu() {
        System.out.println("--- Admin Menu ---");
        System.out.println("1) Pharmacy ");
        System.out.println("2) Laboratory");
        System.out.println("3) Add Hospital Staff");
        System.out.println("4) Add Laboratory Staff ");
        System.out.println("5) Patient Information ");
        System.out.println();
        System.out.print("Enter : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                pharmacy_management();
                break;
            case 2:
                laboratory_management();
                break;
            case 3:
                staffOverview(Hospital_staff, "Admin Hospital ");
                break;
            case 4:
                staffOverview(Laboratory_staff, "Admin Laboratory");
            case 5:
                patient_information();
                break;
            case 6:
                System.out.println("Exiting ....");
//file
                return;
            default:
                System.out.println("*** Invalid input ***");
                break;
        }
    }

    //Staff Hospital menu
    public static void staff() throws SQLException {
        boolean exit = true;
        while (exit) {
            System.out.println();
            System.out.println("--- Hospital Staff ---");
            System.out.println("1) Add Staff ");
            System.out.println("2) Shift Change");
            System.out.println("3) Write Incident Reporting ");
            System.out.println("4) View all Emergency Reports");
            System.out.println("5) Exit");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    staffOverview(Hospital_staff, "Hospital");

                    break;
                case 2:
                    shiftchange(Hospital_staff, "Hospital");

                case 3:
                    incident_reporting();
                    break;
                case 4:
                    view_all_incident_reports();
                    break;
                case 5:
                    System.out.println("Exiting ...");
                    return;
                default:
                    System.out.println("Invalid input");
                    System.out.println();
                    break;

            }
        }
    }

    //incident reporting
    public static void incident_reporting() throws SQLException {
        System.out.println("--- Incident Reporting ---");
        System.out.print("Enter the Incident ID : ");
        int id = sc.nextInt();

        // ------ creating the table of the incident reporting ------
        String query = "CREATE TABLE IF NOT EXISTS Incident_Reporting ("
                + "ID INT PRIMARY KEY, "
                + "Patient varchar(50),"
                + "Doctor varchar(50),"
                + "Incident_Description TEXT, "
                + "TIME DATETIME)";
        try (PreparedStatement create_table_statement = connection.prepareStatement(query)) {
            create_table_statement.executeUpdate();
        }

        String query1 = "SELECT 1 FROM Incident_Reporting WHERE ID =? LIMIT 1";
        try (PreparedStatement Id_exists = connection.prepareStatement(query1)) {
            Id_exists.setInt(1, id);

            try (ResultSet resultToCheck = Id_exists.executeQuery()) {

                if (!resultToCheck.next()) {

                    boolean exit = true;
                    while (exit) {
                        //------- Entry Data of Incident ------
                        System.out.print("Enter the patient name :");
                        String patient_name = br.readLine();
                        System.out.print("Enter the doctor name :");
                        String doctor_name = br.readLine();
                        System.out.print("Enter the Incident Details :");
                        String disease = br.readLine();

                        if (!patient_name.isEmpty() && !doctor_name.isEmpty() && !disease.isEmpty()) {

                            String query2 = "INSERT INTO Incident_Reporting (ID, Patient, Doctor, Incident_Description, TIME) VALUES (?,?,?,?,NOW())";
                            try (PreparedStatement save_incident_file = connection.prepareStatement(query2)) {
                                save_incident_file.setInt(1, id);
                                save_incident_file.setString(2, patient_name);
                                save_incident_file.setString(3, doctor_name);
                                save_incident_file.setString(4, disease);

                                save_incident_file.executeUpdate();

                                System.out.println("The data is saved successfully");
                            }
                            System.out.println();
                            System.out.println("1) Create Report " + '\n' + ("2) Exit"));
                            int option = sc.nextInt();
                            if (option == 2) {
                                exit = false;
                            }
                        } else {
                            System.out.println("Something Missing");
                        }
                    }
                } else {
                    System.out.println("--- The ID Already Exits ---");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //incident reporting View all
    public static void view_all_incident_reports() throws SQLException {
        String query = "SELECT * FROM Incident_Reporting ";
        System.out.printf("%-5s %-20s %-20s %-30s %-20s%n",
                "ID", "Patient", "Doctor", "Time", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        try (PreparedStatement print_all_reports = connection.prepareStatement(query)) {

            ResultSet resultSet = print_all_reports.executeQuery();
            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String patient_name = resultSet.getString("Patient");
                    String doctor_name = resultSet.getString("Doctor");
                    String incident_detail = resultSet.getString("Incident_Description");
                    Time time = resultSet.getTime("Time");
                    System.out.printf("%-5d %-20s %-30s %-40s  %-20s%n",
                            id, patient_name, doctor_name, time, incident_detail);

                }
                System.out.println();
                System.out.println("                                                       === End ===");
            } catch (Exception e) {
                System.out.println("There is an Error" + e);
            }
        }
    }

    //Patient information Menu
    public static void patient_information() {
        boolean exit = true;
        while (exit) {
            System.out.println("--- Patient Information ---");
            System.out.println("1) Treatment Plan ");
            System.out.println("2) Store Patient Data ");
            System.out.println("3) Print Appointment Recipt ");
            System.out.println("4) Delete patient Appointment ");
            System.out.println("5) Exit ");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("--- Treatment Plan --- ");
                    if (Patient_History != null && !Patient_History.isEmpty()) {
                        System.out.print("Enter the Appointment ID : ");
                        int id = sc.nextInt();
                        if (Patient_History.containsKey(id)) {
                            System.out.println("Patient disease : " + Patient_History.get(id).getDisease());

                        } else {
                            System.out.println("*** No Order Available ***");
                        }
                    } else {
                        System.out.println("No patient data available ");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("--- Store Patient Data ---");
                    store_patient_information();
                    System.out.println();
                    break;

                case 3:
                    System.out.println("--- Print Appointment Recipt ---");
                    if (Patient_History != null && !Patient_History.isEmpty()) {
                        System.out.print("Enter the Appointment ID : ");
                        int id = sc.nextInt();
                        if (Patient_History.containsKey(id)) {
                            Patient_History.get(id).print_appointment();
                        } else {
                            System.out.println("*** No Order Available ***");
                        }
                    } else {
                        System.out.println("No patient data available ");
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Delete patient Appointment");
                    if (Patient_History != null && !Patient_History.isEmpty()) {
                        System.out.print("Enter the Appointment ID : ");
                        int id = sc.nextInt();
                        if (Patient_History.containsKey(id)) {
                            Patient_History.remove(id);
                            System.out.println("--- Successfully deleted ---");
                        } else {
                            System.out.println("*** No Order Available ***");
                        }
                    } else {
                        System.out.println("No patient data available ");
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    //
                    return;
                default:
                    System.out.println("Invalid number...");
                    break;

            }
        }
    }

    public static void store_patient_information() {
        boolean exit = true;
        while (exit) {

            System.out.println();
            System.out.println("--- Appointments ID ---");
            System.out.print("ID : ");
            int id = sc.nextInt();
            if (!Patient_History.containsKey(id)) {

                System.out.println("--- Patient Profile ---");
                System.out.println();
                System.out.print("Name :");
                String name;
                try {
                    name = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("Age :");
                int age = sc.nextInt();
                //medical history
                System.out.println();
                System.out.println("--- Medical History --- ");
                System.out.print("Disease : ");
                String disease;
                try {
                    disease = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //////visit date
                System.out.println();
                System.out.println("--- Visit Date ---");
                LocalDateTime d_t = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy\nE-HH: MM a");
                String Date_time = d_t.format(format);
                System.out.println(Date_time);
                //------------
                System.out.println();
                LocalDateTime dt = null; // Storing date and time in this
                while (exit) {
                    System.out.print("Date for Appointment : ");
                    String input_date = sc.next();
                    System.out.print("Time : ");
                    String time = sc.next();
                    sc.nextLine();

                    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    DateTimeFormatter date1 = DateTimeFormatter.ofPattern("HH:mm");
                    try {
                        LocalDate date2 = LocalDate.parse(input_date, date);
                        LocalTime date3 = LocalTime.parse(time, date1);
                        dt = LocalDateTime.of(date2, date3);
                        System.out.println(dt);
                        exit = false;
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Date and Time\nFollow the method of date and time \nDD/MM/YYYY----HH: MM");
                    }
                }
                //----------------
                System.out.println();
                System.out.println("--- Billing ---");
                System.out.print("Enter the amount : ");
                float price = sc.nextFloat();
                try {
                    Patient_History.put(id, new History(age, id, Date_time, disease, name, price, dt));
                    Patient_History.get(id).print_appointment();
                    return;
                } catch (Exception e) {
                    System.out.println("*** Error in saving data ***\nEnter the details again ");
                }
            } else {
                System.out.println("*** Already Registered ID ***");
            }
        }
    }

    public static void pharmacy_management() {
        boolean exit = true;
        while (exit) {
            System.out.println("--- Pharmacy Management ---");
            System.out.println("1) Add New Medicine");
            System.out.println("2) Edit Medicine Details");
            System.out.println("3) Delete Medicine ");
            System.out.println("4) Search Medicine");
            System.out.println("5) View All Medicines");
            System.out.println("6) Sell Medicine");//Process a sale by reducing the stock and generating a receipt.
            System.out.println("7) Restock Medicine");
            System.out.println("8) View Low Stock Medicines ");
            System.out.println("9) Exit ");
            //------------------------------
            System.out.println();
            System.out.print("Enter :");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("--- Add New Medicine ---");
                    add_new_medicine();
                    break;
                case 2:
                    Edit_medicine();
                    break;
                case 3:
                    delete_medicine();
                    break;
                case 4:
                    search_medicine();
                    break;
                case 5:
                    view_all_medicine();
                    break;
                case 6:
                    sell_medicine();
                    break;
                case 7:
                    System.out.println("--- Restocking the Quantity ---");
                    edit_medince_quantity();
                    break;
                case 8:
                    low_Stock_Medicines();
                    break;
                case 9:
                    System.out.println("Returning to Main Menu ");
                    //
                    return;
                default:
                    System.out.println("Invalid input ");

            }
        }

    }

    //Pharmacy Add new medicine
    public static void add_new_medicine() {
        System.out.println("Enter the Product ID : ");
        int id = sc.nextInt();
        if (!Pharmacy_Medicine.containsKey(id)) {
            System.out.print("Enter the name of medicine :  ");
            String name;
            try {
                name = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Enter the batch number : ");
            int batch = sc.nextInt();
            System.out.println("Enter the quantity : ");
            int quantity = sc.nextInt();
            System.out.println("Enter the Price : ");
            float price = sc.nextFloat();
            System.out.println("Enter the Expiry date\n ** Format should be YYYY-MM-DD **");
            String date;
            try {
                date = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Pharmacy_Medicine.put(id, new Pharmacy(batch, date, price, name, quantity));
            Pharmacy_Medicine.get(id).print_medicine();
        } else {
            System.out.println("Product Already Registered");
        }
    }

    //Pharmacy Edit a medicine
    public static void Edit_medicine() {
        System.out.println("--- Edit Medicine ---");
        System.out.println("* All medicine *");
        Pharmacy_Medicine.forEach((key, value) -> {
            System.out.println("Product ID : " + Pharmacy_Medicine.get(key));
            Pharmacy_Medicine.get(key).print_medicine();
        });
        System.out.println("Enter the product ID : ");
        int id = sc.nextInt();
        if (Pharmacy_Medicine.containsKey(id)) {
            edit_menu_medicine(id);
        } else {
            System.out.println("No product found");
        }
    }

    //Pharmacy Edit a medicine Menu
    public static void edit_menu_medicine(int id) {
        boolean exit = true;
        while (exit) {
            System.out.println("--- Edit Product Menu ---");
            System.out.println("1) Edit Product name ");
            System.out.println("2) edit Product Batch ID ");
            System.out.println("3) Edit Product Quantity  ");
            System.out.println("4) Edit Product Price  ");
            System.out.println("5) Product Expiry Date ");
            System.out.println("6) Exit ");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("--- Editing Product Name ---");
                    if (Pharmacy_Medicine.containsKey(id)) {
                        System.out.print("Enter the name : ");
                        try {
                            String name = br.readLine();
                            Pharmacy pharmacy = Pharmacy_Medicine.get(id);
                            pharmacy.setProduct_name(name);
                            System.out.println("--- Product Successfully Edited ---");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("No product found");
                    }
                    break;
                case 2:
                    System.out.println("--- Editing Batch ID ---");
                    if (Pharmacy_Medicine.containsKey(id)) {
                        System.out.print("Enter the New Batch ID : ");
                        int newbatch = sc.nextInt();
                        Pharmacy pharmacy = Pharmacy_Medicine.get(id);
                        pharmacy.setBatch_id(newbatch);
                        System.out.println("--- Product Successfully Edited ---");
                    } else {
                        System.out.println("No Product Found");
                    }
                    break;
                case 3:
                    edit_medince_quantity();
                    System.out.println("--- Editing Product Quantity ---");
                    break;
                case 4:
                    System.out.println("--- Editing Product Price ---");

                    if (Pharmacy_Medicine.containsKey(id)) {
                        System.out.println("Old Price : " + Pharmacy_Medicine.get(id).getPrice());
                        System.out.print("Enter the New Price : ");
                        float price = sc.nextInt();
                        Pharmacy pharmacy = Pharmacy_Medicine.get(id);
                        pharmacy.setPrice(price);
                        System.out.println("--- Product Successfully Edited ---");
                    } else {
                        System.out.println("No Product found");
                    }
                    break;
                case 5:
                    System.out.println("--- Editing Product Date ---");
                    if (Pharmacy_Medicine.containsKey(id)) {
                        System.out.println("Old Date : " + Pharmacy_Medicine.get(id).getExpiry_date());
                        System.out.print("Enter the New Expire Date : ");

                        String price = null;
                        try {
                            price = br.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Pharmacy pharmacy = Pharmacy_Medicine.get(id);
                        pharmacy.setExpiry_date(price);
                        System.out.println("--- Product Successfully Edited ---");
                    } else {
                        System.out.println("No Product found");
                    }
                    break;
                case 6:
                    System.out.println("Exiting.....");
                    //
                    return;
                default:
                    System.out.println("Invalid input ");
            }
        }
    }

    //editing the medicine Quantity
    public static void edit_medince_quantity() {

        System.out.println("Enter the Product ID : ");
        int id2 = sc.nextInt();
        if (Pharmacy_Medicine.containsKey(id2)) {
            System.out.println("Old Quantity : " + Pharmacy_Medicine.get(id2).getQuantity());
            System.out.print("Enter the New Quantity : ");
            int quantity = sc.nextInt();
            Pharmacy pharmacy = Pharmacy_Medicine.get(id2);
            pharmacy.setQuantity(quantity);
            System.out.println("--- Product Successfully Edited ---");
        } else {
            System.out.println("No Product found");
        }
    }

    //Pharmacy Delete Medicine
    public static void delete_medicine() {
        System.out.println("--- Delete Medicine ---");
        System.out.print("Enter the Medicine or Product ID : ");
        int id = sc.nextInt();
        if (Pharmacy_Medicine.containsKey(id)) {
            Pharmacy_Medicine.remove(id);
            System.out.println("--- Item Deleted Successfully ---");

        } else {
            System.out.println("No product found");
        }
    }

    //Pharmacy search medicine
    public static void search_medicine() {
        System.out.println("Enter the Product ID :");
        int id = sc.nextInt();
        if (Pharmacy_Medicine.containsKey(id)) {
            Pharmacy_Medicine.get(id).print_medicine();
            System.out.println("Returning....");
        } else {
            System.out.println("--- No product found ---");
        }
    }

    //Pharmacy View ALl medicine
    public static void view_all_medicine() {
        Pharmacy_Medicine.forEach((key, value) -> {
            System.out.println("Product key " + Pharmacy_Medicine.get(key));
            Pharmacy_Medicine.get(key).print_medicine();
        });
    }

    //Pharmacy sell medicine
    public static void sell_medicine() {
        System.out.println("--- Sell Medicine ---");
        System.out.print("Enter the product ID :");
        int id = sc.nextInt();
        if (Pharmacy_Medicine.containsKey(id)) {
            System.out.println("Enter the Quantity Want : ");
            int quantity = sc.nextInt();
            Pharmacy_Medicine.get(id).print_receipt(quantity);
            System.out.println();
        } else {
            System.out.println("*** NO Product Found ***");
        }
    }

    //View low stock medicine
    public static void low_Stock_Medicines() {
        boolean[] No_Product = {false};
        if (Pharmacy_Medicine != null && !Pharmacy_Medicine.isEmpty()) {


            Pharmacy_Medicine.forEach((key, value) -> {
                Pharmacy pharmacy = Pharmacy_Medicine.get(key);
                if (pharmacy.getQuantity() <= 10) {
                    Pharmacy_Medicine.get(key).print_medicine();
                    No_Product[0] = true;
                }

            });
            //Loop if No medicine is at low stock
            if (!No_Product[0]) {
                System.out.println("*** No Product at Low Stock ***");
            } else {
                return;
            }
        } else {
            System.out.println("*** No product Listed ***");
        }
    }

    //Laboratory Menu
    public static void laboratory_management() {
        boolean exit = true;
        while (exit) {
            System.out.println("--- Laboratory Menu ---");
            System.out.println("1) Laboratory Front Desk ");
            System.out.println("2) Laboratory Staff ");
            System.out.println("3) Exit ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    laboratory_frontdesk();
                    break;
                case 2:
                    laboratory_staff();
                    break;
                case 3:
                    System.out.println("--- Exit ---");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }
    }

    //Laboratory Front Desk Menu
    public static void laboratory_frontdesk() {
        boolean exit = true;
        while (exit) {
            System.out.println("- Front Desk -");
            System.out.println("1) Track order ");
            System.out.println("2) Place Order ");
            System.out.println("3) Print Report");
            System.out.println("4) Exit ");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("--- Track order ---");
                    System.out.println("Enter the order number : ");
                    int i = sc.nextInt();
                    if (Laboratory_placingname.containsKey(i)) {
                        laboratory laboratory = Laboratory_placingname.get(i);
                        laboratory.print_details();
                    } else {
                        System.out.println("No Order found ");
                    }
                    System.out.println();
                    break;

                case 2:
                    System.out.println("--- Place order ---");
                    placeorder();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("--- Print Report ---");
                    System.out.println("Enter the order number : ");
                    int i2 = sc.nextInt();
                    sc.nextLine();
                    if (Laboratory_placingname.containsKey(i2)) {
                        try {
                            testresult Testresult = Test_Result_Management.get(i2);
                            Testresult.print_testdetials();
                        } catch (Exception e) {
                            System.out.println("--No Result Found--");
                        }
                    } else {
                        System.out.println("No Order found ");
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("--- Exit ---");
                    //
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    //Laboratory Staff Menu
    public static void laboratory_staff() {
        boolean exit = true;
        while (exit) {
            System.out.println("- Staff -");
            System.out.println("1) Tracking order Management ");
            System.out.println("2) Test Result Management ");
            System.out.println("3) Staff Management");
            System.out.println("4) Exit");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    track_order();
                    break;
                case 2:
                    test_result_managment();
                    break;
                case 3:
                    staffOverview(Laboratory_staff, "Laboratory");
                    break;
                case 4:
                    System.out.println("--- Exiting ---");
                    //
                    return;
                default:
                    System.out.println("Invalid input");
                    break;

            }
        }
    }

    //Lab order Status Update
    public static void track_order() {
        System.out.println("Enter the order number : ");
        int number = sc.nextInt();
        laboratory lab = Laboratory_placingname.get(number);
        //creating this as a method

        if (lab != null) {
            while (true) {
                System.out.println("--- Tracking order Management ---");
                System.out.println("1) Still working ");
                System.out.println("2) Completed ");
                int choice1 = sc.nextInt();
                if (choice1 == 1) {
                    String status_change = "Still Working";
                    lab.setOrder_track(status_change);
                    System.out.println("Status Updated");
                    return;
                } else if (choice1 == 2) {
                    String status_change_Completed = "Completed";
                    lab.setOrder_track(status_change_Completed);
                    System.out.println("Status Updated");
                    return;
                } else {
                    System.out.println("Invalid input ");
                }
            }
        } else {
            System.out.println("No order found");
            return;
        }
    }

    //Lab test result Edit
    public static void update_test_result(int key2) {
        testresult result_edit = Test_Result_Management.get(key2);
        boolean exit = true;
        while (exit) {

            System.out.println("--- Edit Test Result ---");
            System.out.println("1) Edit Name ");
            System.out.println("2) Edit Contact number ");
            System.out.println("3) Edit Disease ");
            System.out.println("4) Edit Note ");
            System.out.println("5) Back to Menu");
            System.out.println();
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Edit Name : ");
                    String name = sc.nextLine();
                    sc.nextLine();
                    result_edit.setPatient_name(name);
                    System.out.println("Successfully modified");
                    break;
                case 2:
                    System.out.println("Edit Contact number : ");
                    int number = sc.nextInt();
                    result_edit.setPatient_number(number);
                    System.out.println("Successfully modified");
                    break;
                case 3:
                    System.out.println("Edit Disease : ");
                    String disease;
                    try {
                        disease = br.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    result_edit.setDisease(disease);
                    System.out.println("Successfully modified");
                    break;
                case 4:
                    System.out.println("Edit Note : ");
                    String note;
                    try {
                        note = br.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    result_edit.setNote(note);
                    System.out.println("Successfully modified");
                    break;
                case 5:
                    System.out.println("--- Returning ---");
                    return;
                default:
                    System.out.println("invalid input ...");
                    break;
            }

        }
    }

    //Lab Staff Menu Options
    public static void staffOverview(HashMap<Integer, Staff> managing_Staff, String Type) {

        boolean exit = true;
        while (exit) {
            System.out.println("--- " + Type + " Staff Overview ---");
            System.out.println("1) View Staff List");
            System.out.println("2) Add New Staff");
            System.out.println("3) Update Staff Details");
            System.out.println("4) Remove Staff");
            System.out.println("5) Shift change");
            System.out.println("6) Back to Main Menu");
            System.out.print("Enter: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Viewing Staff List...");
                    if (managing_Staff != null && !managing_Staff.isEmpty()) {
                        managing_Staff.forEach((key, value) -> {
                            managing_Staff.get(key).print();
                        });
                        System.out.println();
                    } else {
                        System.out.println("No Staff Hired ");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Adding New Staff...");
                    System.out.println("Enter the ID number :");
                    int id_integer = sc.nextInt();
                    assert managing_Staff != null;
                    if (!managing_Staff.containsKey(id_integer)) {
                        System.out.println("Enter the name : ");
                        String name = null;
                        try {
                            name = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Error ");
                            break;
                        }
                        System.out.println("Role in " + Type + " : ");
                        String role = null;
                        try {
                            role = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Error ");
                            break;
                        }
                        String shift = "Morning (8:00 AM - 4:00 PM)";
                        managing_Staff.put(id_integer, new Staff(name, id_integer, role, shift));
                        System.out.println("New Staff added Successfully ");
                    } else {
                        System.out.println("Employ already exit");
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Updating Staff Details...");
                    System.out.print("Enter the ID to Edit : ");
                    int id = sc.nextInt();
                    assert managing_Staff != null;
                    if (managing_Staff.containsKey(id)) {
                        System.out.println("Edit the name : ");
                        String name = null;
                        try {
                            name = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Error ");
                            break;
                        }
                        System.out.println("Enter new ID : ");
                        int id1 = sc.nextInt();
                        System.out.println("Role in " + Type + " : ");
                        String role = null;
                        try {
                            role = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Error ");
                            break;
                        }
                        Staff allstaff = managing_Staff.get(id);
                        allstaff.setName(name);
                        allstaff.setId(id1);
                        allstaff.setStaff(role);


                        managing_Staff.remove(id);
                        managing_Staff.put(id1, allstaff);

                        System.out.println("Successfully Edited ");
                    } else {
                        System.out.println("Employ does not exit");
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Removing Staff...");
                    System.out.print("Enter the ID number : ");
                    int id1 = sc.nextInt();
                    assert managing_Staff != null;
                    if (managing_Staff.containsKey(id1)) {
                        managing_Staff.remove(id1);
                    } else {
                        System.out.println("Employ does not exit");
                    }
                    System.out.println();
                    break;
                case 5:
                    shiftchange(managing_Staff, Type);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    //
                    return;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    //Shift change for all over the code
    public static void shiftchange(HashMap<Integer, Staff> Shift_change, String name) {
        System.out.println("--- " + name + " Shift change ---");
        System.out.println("Enter the ID : ");
        int id = sc.nextInt();
        if (Shift_change.containsKey(id)) {
            boolean exit = true;
            while (exit) {
                System.out.println("1) Morning (8:00 AM - 4:00 PM) \n 2) Evening (4:00 PM - 12:00 AM)");
                System.out.print("Enter :");
                int choice = sc.nextInt();
                if (choice == 1) {
                    String shift = "Morning (8:00 AM - 4:00 PM)";
                    Shift_change.get(id).setShift(shift);
                    return;
                } else if (choice == 2) {

                    String shift = "Evening (4:00 PM - 12:00 AM)";
                    Shift_change.get(id).setShift(shift);
                    return;
                } else {
                    System.out.println("*** Invalid input ***");
                }
            }
        } else {
            System.out.println("*** Invalid ID ***");
        }
    }

    //Lab patient Order Place
    public static void placeorder() {

        System.out.print("Patient name : ");
        sc.nextLine();
        String patient_name = sc.nextLine();
        System.out.print("Patient Contact number : ");
        int patient_number = sc.nextInt();
        sc.nextLine();
        System.out.print("Payment : ");
        float patient_payment = sc.nextFloat();
        sc.nextLine();
        System.out.print("Patient Order number : ");
        int patientorder_number = sc.nextInt();
        sc.nextLine();
        boolean exit = true;

        Laboratory_placingname.put(patientorder_number, new laboratory(patient_name, patient_number, patient_payment));
        Laboratory_placingname.get(patientorder_number).print_details();


    }

    //Lab test result Edit
    public static void test_result_managment() {
        boolean exit = true;
        while (exit) {

            System.out.println("--- Test Result Management ---");
            System.out.println("1) Add Test Result");
            System.out.println("2) View Test Results");
            System.out.println("3) Update Test Result");
            System.out.println("4) Delete Test Result");
            System.out.println("5) Back to Menu");
            System.out.print("Enter: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("--- Add Test Result ---");
                    System.out.println("Enter the order number : ");
                    int key = sc.nextInt();
                    laboratory lab = Laboratory_placingname.get(key);
                    if (!Laboratory_placingname.isEmpty()) {
                        if (Laboratory_placingname.containsKey(key) && !Test_Result_Management.containsKey(key)) {
                            System.out.println("Enter the disease name : ");
                            String disease;
                            try {
                                disease = br.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Note : ");
                            String note;
                            try {
                                note = br.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            testresult testresult = new testresult(lab.getPatient_name(), lab.getPatient_number(), lab.getPatient_payment(), disease, note);
                            testresult.setDisease(disease);
                            testresult.setNote(note);
                            Test_Result_Management.put(key, testresult);
                            Test_Result_Management.get(key).print_testdetials();
                            break;
                        } else {
                            System.out.println(" Already Registered ");
                        }
                    } else {
                        System.out.println("No order number found ");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("--- View Test Result ---");
                    System.out.println("Enter the order number : ");
                    int key1 = sc.nextInt();
                    testresult result = Test_Result_Management.get(key1);
                    if (result != null) {
                        Test_Result_Management.get(key1).print_testdetials();
                    } else {
                        System.out.println("No Test Found");
                    }
                    break;
                case 3:
                    System.out.println("--- Update the test Result ---");
                    System.out.println("Enter the order number : ");
                    int key2 = sc.nextInt();
                    testresult result_edit = Test_Result_Management.get(key2);
                    if (result_edit != null) {
                        update_test_result(key2);
                        break;
                    } else {
                        System.out.println("No Order or result Found");
                    }
                    break;
                case 4:
                    System.out.println("-- Update Test Result --");
                    System.out.print("Enter the order number : ");
                    int key3 = sc.nextInt();
                    if (Test_Result_Management.containsKey(key3)) {
                        Test_Result_Management.remove(key3);
                        System.out.println("Successfully deleted");
                    } else {
                        System.out.println("No Result found ");
                    }
                    break;
                case 5:
                    System.out.println("Exiting....");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
