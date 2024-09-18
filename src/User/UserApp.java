package User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserApp {


    private static List<User> usersList = new ArrayList<>();



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Welcome to User Management System");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    signup(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system...");

                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void signup(Scanner scanner) {
        System.out.println("Signup Form");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (consumer/donor): ");
        String role = scanner.nextLine().toLowerCase();

        // ID generation based on size of the current user list
        int id = usersList.size() + 1;

        // Create User object and add it to the list
        User newUser = new User(id, firstName, lastName, email, password, role);
        usersList.add(newUser);


        System.out.println("Signup successful! Please login to continue.");
    }
    private static void login(Scanner scanner) {
        System.out.println("Login");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : usersList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                String role = user.getRole();
                // Print the role for debugging purposes
                System.out.println("Role: " + role);
                if (role.equals("consumer")) {
                    customerPortal(scanner,user);
                } else if (role.equals("donor")) {
                    donorPortal(scanner,user);
                } else {
                    System.out.println("Invalid role.");
                }
                return;
            }
        }
        System.out.println("Invalid credentials! Please try again.");
    }

    private static void customerPortal(Scanner scanner, User user) {

    }
    private static void donorPortal(Scanner scanner, User user) {}
}
