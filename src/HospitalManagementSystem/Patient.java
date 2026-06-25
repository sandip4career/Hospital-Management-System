package HospitalManagementSystem;

// JDBC imports for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Scanner for taking user input
import java.util.Scanner;

public class Patient {

    // Stores the database connection object
    private Connection connection;

    // Scanner object used for reading user input
    private Scanner scanner;

    // Constructor: initializes connection and scanner
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // =====================================================
    // METHOD: ADD A NEW PATIENT TO THE DATABASE
    // =====================================================
    public void addPatient() {

        // Read patient details from user
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();

        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try {

            // SQL INSERT query with parameter placeholders (?)
            String query =
                    "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";

            // Create PreparedStatement object
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            // Set values for the placeholders
            preparedStatement.setString(1, name);   // Patient name
            preparedStatement.setInt(2, age);       // Patient age
            preparedStatement.setString(3, gender); // Patient gender

            // Execute INSERT query
            int affectedRows = preparedStatement.executeUpdate();

            // Check whether data was inserted successfully
            if (affectedRows > 0) {
                System.out.println("Patient added successfully!");
            } else {
                System.out.println("Failed to add patient!");
            }

            // Close PreparedStatement
            preparedStatement.close();

        } catch (SQLException e) {

            // Display SQL error details
            e.printStackTrace();
        }
    }

    // =====================================================
    // METHOD: DISPLAY ALL PATIENTS
    // =====================================================
    public void viewPatients() {

        // SQL query to retrieve all patient records
        String query = "SELECT * FROM patients";

        try {

            // Create PreparedStatement
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            // Execute SELECT query
            ResultSet resultSet =
                    preparedStatement.executeQuery();

            // Print table header
            System.out.println("-------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-5s | %-10s |%n",
                    "ID", "NAME", "AGE", "GENDER");
            System.out.println("-------------------------------------------------------------");

            // Iterate through each record in the ResultSet
            while (resultSet.next()) {

                // Fetch values from current row
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                // Print patient data in tabular format
                System.out.printf("| %-5d | %-20s | %-5d | %-10s |%n",
                        id, name, age, gender);
            }

            // Print footer line
            System.out.println("-------------------------------------------------------------");

            // Close resources
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {

            // Display SQL error details
            e.printStackTrace();
        }
    }

    // =====================================================
    // METHOD: CHECK WHETHER A PATIENT EXISTS BY ID
    // =====================================================
    public boolean getPatientById(int id) {

        // SQL query to find patient by ID
        String query = "SELECT * FROM patients WHERE id = ?";

        try {

            // Create PreparedStatement
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            // Set ID value in query
            preparedStatement.setInt(1, id);

            // Execute query
            ResultSet resultSet =
                    preparedStatement.executeQuery();

            // If a record exists, return true
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {

            // Display SQL error details
            e.printStackTrace();
        }

        // Return false if any error occurs
        return false;
    }
}