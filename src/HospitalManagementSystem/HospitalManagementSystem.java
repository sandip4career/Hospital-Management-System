package HospitalManagementSystem;

// JDBC imports for database connection and SQL handling
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    // Database URL (MySQL connection string)
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    // Database credentials
    private static final String username = "root";
    private static final String password = "Sandiipyadav87@gmail.com";

    public static void main(String[] args) {

        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish connection to database
            Connection connection =
                    DriverManager.getConnection(url, username, password);

            // Create Patient and Doctor objects
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            // Infinite loop for menu-driven system
            while (true) {

                // Display menu options
                System.out.println("\n==============================");
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("==============================");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                // Read user choice
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        // Add new patient to database
                        patient.addPatient();
                        System.out.println();
                        break;

                    case 2:
                        // Display all patients
                        patient.viewPatients();
                        System.out.println();
                        break;

                    case 3:
                        // Display all doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        // Book appointment between patient and doctor
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;

                    case 5:
                        // Exit system safely
                        System.out.println("----- THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM -----");

                        scanner.close();
                        connection.close();
                        return;

                    default:
                        // Invalid menu choice
                        System.out.println("Enter valid choice!!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =====================================================
    // METHOD: BOOK APPOINTMENT
    // Handles appointment creation between patient and doctor
    // =====================================================
    public static void bookAppointment(
            Patient patient,
            Doctor doctor,
            Connection connection,
            Scanner scanner) {

        // Input patient ID
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();

        // Input doctor ID
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();

        // Input appointment date
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        // Check if patient and doctor exist in database
        if (patient.getPatientById(patientId)
                && doctor.getDoctorsById(doctorId)) {

            // Check if doctor is available on that date
            if (checkDoctorAvailablity(doctorId, appointmentDate, connection)) {

                // SQL query to insert appointment
                String appointmentQuery =
                        "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";

                try {
                    PreparedStatement preparedStatement =
                            connection.prepareStatement(appointmentQuery);

                    // Set query parameters
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    // Execute insert query
                    int rowsAffected = preparedStatement.executeUpdate();

                    // Check insertion result
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked Successfully!");
                    } else {
                        System.out.println("Failed to Book Appointment!");
                    }

                    preparedStatement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor is not available on this date!");
            }

        } else {
            System.out.println("Either Patient ID or Doctor ID does not exist!");
        }
    }

    // =====================================================
    // METHOD: CHECK DOCTOR AVAILABILITY
    // Returns true if doctor has no appointment on that date
    // =====================================================
    public static boolean checkDoctorAvailablity(
            int doctorId,
            String appointmentDate,
            Connection connection) {

        // Query to check existing appointments for doctor
        String query =
                "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            // Set parameters
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);

            // Execute query
            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                int count = resultSet.getInt(1);

                // If no appointments exist, doctor is available
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Default: not available if error occurs
        return false;
    }
}