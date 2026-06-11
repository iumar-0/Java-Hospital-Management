package HospitalManagementGUI;//package HospitalManagementGUI;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.HashMap;
//
//public class HospitalManagementGUI extends JFrame {
//    private JTabbedPane tabbedPane;
//    private Connection conn;
//
//    // Database credentials
//    private static final String DB_URL = "jdbc:sqlite:hospital.db";
//
//    public HospitalManagementGUI() {
//        initializeDatabase();
//        createUI();
//        startAutoSaveThread();
//    }
//
//    private void initializeDatabase() {
//        try {
//            conn = DriverManager.getConnection(DB_URL);
//            createTables();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
//        }
//    }
//
//    private void createTables() {
//        String[] tables = {
//                "CREATE TABLE IF NOT EXISTS LaboratoryPatients (id INTEGER PRIMARY KEY, name TEXT, number INTEGER, payment REAL)",
//                // Add other table creation statements
//        };
//
//        try (Statement stmt = conn.createStatement()) {
//            for (String table : tables) {
//                stmt.execute(table);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void createUI() {
//        setTitle("Hospital Management System");
//        setSize(800, 600);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        tabbedPane = new JTabbedPane();
//
//        // Add Tabs
//        tabbedPane.addTab("Dashboard", new DashboardPanel());
//        tabbedPane.addTab("Pharmacy", new PharmacyPanel());
//        tabbedPane.addTab("Laboratory", new LaboratoryPanel());
//        tabbedPane.addTab("Admin", new AdminLoginPanel(() -> showAdminFeatures()));
//
//        add(tabbedPane);
//        setVisible(true);
//    }
//
//    // Admin Login Panel
//    class AdminLoginPanel extends JPanel {
//        public AdminLoginPanel(Runnable onSuccess) {
//            JPasswordField passwordField = new JPasswordField(15);
//            JButton loginBtn = new JButton("Login");
//
//            setLayout(new GridLayout(3, 1));
//            add(new JLabel("Admin Password:"));
//            add(passwordField);
//            add(loginBtn);
//
//            loginBtn.addActionListener(e -> {
//                if (new String(passwordField.getPassword()).equals("1234")) {
//                    onSuccess.run();
//                } else {
//                    JOptionPane.showMessageDialog(this, "Invalid password!");
//                }
//            });
//        }
//    }
//
//    // Pharmacy Panel with Table
//    class PharmacyPanel extends JPanel {
//        private JTable medicineTable;
//        private DefaultTableModel tableModel;
//
//        public PharmacyPanel() {
//            setLayout(new BorderLayout());
//
//            // Table
//            tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price"}, 0);
//            medicineTable = new JTable(tableModel);
//            loadPharmacyData();
//
//            // Buttons
//            JPanel buttonPanel = new JPanel();
//            JButton addBtn = new JButton("Add Medicine");
//            addBtn.addActionListener(e -> showAddMedicineDialog());
//
//            buttonPanel.add(addBtn);
//            add(new JScrollPane(medicineTable), BorderLayout.CENTER);
//            add(buttonPanel, BorderLayout.SOUTH);
//        }
//
//        private void loadPharmacyData() {
//            // Load from database
//            try (Statement stmt = conn.createStatement()) {
//                ResultSet rs = stmt.executeQuery("SELECT * FROM Pharmacy");
//                while (rs.next()) {
//                    tableModel.addRow(new Object[]{
//                            rs.getInt("id"),
//                            rs.getString("name"),
//                            rs.getInt("quantity"),
//                            rs.getFloat("price")
//                    });
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void showAddMedicineDialog() {
//            // Implementation for adding medicine
//        }
//    }
//
//    // Switch Case Alternative in Swing
//    private void handleMenuSelection(String actionCommand) {
//        switch (actionCommand) {
//            case "ADD_MEDICINE":
//                showAddMedicineDialog();
//                break;
//            case "EDIT_MEDICINE":
//                showEditMedicineDialog();
//                break;
//            // Other cases
//        }
//    }
//
//    private void startAutoSaveThread() {
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(10000);
//                    saveAllData();
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//        }).start();
//    }
//
//    private void saveAllData() {
//        // Save all data to database
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new HospitalManagementGUI());
//    }
//}
//
