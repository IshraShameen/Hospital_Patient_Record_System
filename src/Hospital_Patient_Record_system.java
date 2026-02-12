import java.sql.*;
import java.util.Scanner;

public class HospitalSystem {

    static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    static final String USER = "root";
    static final String PASSWORD = "root"; // change if needed

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n---- Hospital Patient Record System ----");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Medical History");
            System.out.println("4. Delete Patient");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    updateHistory();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static void addPatient() {
        try (Connection con = getConnection()) {

            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Disease: ");
            String disease = sc.nextLine();

            System.out.print("Enter Medical History: ");
            String history = sc.nextLine();

            String query = "INSERT INTO patients(name, age, disease, medical_history) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, disease);
            ps.setString(4, history);

            ps.executeUpdate();
            System.out.println("Patient Added Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void viewPatients() {
        try (Connection con = getConnection()) {

            String query = "SELECT * FROM patients";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Disease: " + rs.getString("disease"));
                System.out.println("History: " + rs.getString("medical_history"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void updateHistory() {
        try (Connection con = getConnection()) {

            System.out.print("Enter Patient ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Medical History: ");
            String history = sc.nextLine();

            String query = "UPDATE patients SET medical_history=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, history);
            ps.setInt(2, id);

            ps.executeUpdate();
            System.out.println("Record Updated!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deletePatient() {
        try (Connection con = getConnection()) {

            System.out.print("Enter Patient ID: ");
            int id = sc.nextInt();

            String query = "DELETE FROM patients WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Patient Deleted!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
