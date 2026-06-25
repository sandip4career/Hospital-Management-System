package HospitalManagementSystem;

// JDBC imports for database connectivity and SQL operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

    // Stores the database connection object
    private Connection connection;

    // =====================================================
    // CONSTRUCTOR
    // Initializes the database connection
    // =====================================================
    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // =====================================================
    // METHOD: DISPLAY ALL DOCTORS
    // Retrieves and displays all doctor records
    // =====================================================
    public void viewDoctors() {

        // SQL query to fetch all doctors
        String query = "SELECT * FROM doctors";

        try (
                // Create PreparedStatement object
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query);

                // Execute query and store result
                ResultSet resultSet =
                        preparedStatement.executeQuery()
        ) {

            // Print table header
            System.out.println("--------------------------------------------------------------");
            System.out.printf("| %-10s | %-20s | %-20s |%n",
                    "Doctor ID", "NAME", "SPECIALIZATION");
            System.out.println("--------------------------------------------------------------");

            // Iterate through all rows in the ResultSet
            while (resultSet.next()) {

                // Retrieve values from current row
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization =
                        resultSet.getString("specialization");

                // Display doctor details in tabular format
                System.out.printf("| %-10d | %-20s | %-20s |%n",
                        id, name, specialization);
            }

            // Print footer line
            System.out.println("--------------------------------------------------------------");

        } catch (SQLException e) {

            // Display SQL error details
            e.printStackTrace();
        }
    }

    // =====================================================
    // METHOD: CHECK WHETHER A DOCTOR EXISTS BY ID
    // Returns true if doctor is found, otherwise false
    // =====================================================
    public boolean getDoctorsById(int id) {

        // SQL query with parameter placeholder
        String query = "SELECT * FROM doctors WHERE id = ?";

        try (
                // Create PreparedStatement
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)
        ) {

            // Set doctor ID in query
            preparedStatement.setInt(1, id);

            // Execute query
            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                // If a record exists, return true
                return resultSet.next();
            }

        } catch (SQLException e) {

            // Display SQL error details
            e.printStackTrace();
        }

        // Return false if doctor not found or error occurs
        return false;
    }
}