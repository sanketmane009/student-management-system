import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    // JDBC connection details
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "sanketdev@2025"; // 

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
     initializeDatabase() ;

    while (true) {
        try {   
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please enter between 1-5.");
            }
        } catch (Exception e) {   // CATCH BLOCK
            System.out.println(" Invalid input! Please enter numbers only.");
            sc.nextLine(); // Clear invalid input
        }
    }
}


    // Get database connection
    private static Connection getConnection() throws SQLException {
        // Modern MySQL Connector auto-loads the driver
       

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // This method will check for the table and create it if not exists
private static void initializeDatabase() {
    String createTableQuery = "CREATE TABLE IF NOT EXISTS students (" +
            "id INT PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "age INT NOT NULL," +
            "grade VARCHAR(10) NOT NULL" +
            ")";

    try (Connection con = getConnection();
         Statement stmt = con.createStatement()) {
        stmt.executeUpdate(createTableQuery);
        System.out.println(" Database is ready. 'students' table is available.");
    } catch (SQLException e) {
        System.out.println(" Error during database initialization: " + e.getMessage());
    }
}

    // Add Student
    private static void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        String query = "INSERT INTO students (id, name, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, age);
            pst.setString(4, grade);

            int rows = pst.executeUpdate();
            if (rows > 0) System.out.println(" Student added successfully!");
            else System.out.println("❌ Failed to add student.");

        } catch (SQLException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    // View Students
    private static void viewStudents() {
        String query = "SELECT * FROM students";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n===== All Students =====");
            System.out.printf("%-5s %-20s %-5s %-10s%n", "ID", "Name", "Age", "Grade");
            System.out.println("------------------------------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String grade = rs.getString("grade");

                System.out.printf("%-5d %-20s %-5d %-10s%n", id, name, age, grade);
            }

            if (!hasRecords) {
                System.out.println("No student records found.");
            }

        } catch (SQLException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    // Update Student (placeholder)
    // Update Student
private static void updateStudent() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter Student ID to update: ");
    int id = sc.nextInt();
    sc.nextLine(); // consume newline

    System.out.print("Enter NEW Name: ");
    String name = sc.nextLine();
    System.out.print("Enter NEW Age: ");
    int age = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter NEW Grade: ");
    String grade = sc.nextLine();

    String query = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";

    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {

        pst.setString(1, name);
        pst.setInt(2, age);
        pst.setString(3, grade);
        pst.setInt(4, id);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println(" Student updated successfully!");
        } else {
            System.out.println("❌ No student found with ID: " + id);
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}


    // Delete Student (placeholder)
    public static void deleteStudent() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Student ID to delete: ");
    int id = sc.nextInt();

    String query = "DELETE FROM students WHERE id = ?";

    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {

        pst.setInt(1, id); // Set the ID in the query

        int rows = pst.executeUpdate(); // Execute delete

        if (rows > 0) {
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("❌ No student found with ID: " + id);
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

}
