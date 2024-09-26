// File: Implements/AuthServiceImpl.java
package Implements;

import Interface.UserInterface;
import fileStorage.FileStorage;
import models.constants.Constants;
import models.Consumer;
import models.Donor;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthServiceImpl implements UserInterface {

    private List<User> userList = new ArrayList<>();
    private List<Consumer> consumerList = new ArrayList<>();
    private List<Donor> donorList = new ArrayList<>();

    private final FileStorage fileStorage = new FileStorage();  // File storage handler
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor - load data from JSON files when service is initialized
    public AuthServiceImpl() {
        loadData();
    }

    @Override
    public User loginUser(String username, String password) {
        for (User u : userList) {
            if (u.getEmail().equals(username)) {
                if (u.getPassword().equals(password)) {
                    // Check if the user is a Consumer or Donor and return the appropriate type
                    if (u.getRole().equalsIgnoreCase(Constants.CONSUMER)) {
                        return new Consumer(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.getRole());
                    } else if (u.getRole().equalsIgnoreCase(Constants.DONOR)) {
                        return new Donor(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), null, 0, null, 0, 0);
                    }
                    return u;  // Fallback to User if no specific role is found
                } else {
                    System.out.println("Password did not match.");
                }
            }
        }
        return null;  // Return null if no matching user is found
    }


    @Override
    public User registerUser() {
        User user = getInputAndRegister();
        if (user.getRole().equals(Constants.CONSUMER)) {
            Consumer consumer = createConsumerData(user);
            fileStorage.saveUsersToFile();
            return consumer;
        } else if (user.getRole().equals(Constants.DONOR)) {
            Donor donor = createDonorData(user);
            fileStorage.saveUsersToFile();  // Save to JSON
            return donor;
        }
        return user;
    }

    // Helper method to gather user registration data
    private User getInputAndRegister() {
        String fullName;
        do {
            System.out.println("Enter Full Name (No numbers allowed):");
            fullName = scanner.nextLine();
            if (!isValidName(fullName)) {
                System.out.println("Invalid name. Names should not contain numbers. Please try again.");
            }
        } while (!isValidName(fullName));  // Loop until valid name is entered
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = (nameParts.length > 1) ? nameParts[1] : "";

        // Email check loop to ensure valid and unique email
        String email;
        do {
            System.out.println("Enter Email (Must be a valid @gmail.com address):");
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid @gmail.com email.");
            } else if (emailExists(email)) {
                System.out.println("Email already exists. Please try another email.");
            }
        } while (!isValidEmail(email) || emailExists(email));  // Loop until valid and unique email is entered

        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        String role = null;
        boolean validRole = false;
        do {
            System.out.printf("Enter Role: [C] for Consumer or [D] for Donor: ");
            char roleInput = scanner.next().charAt(0);
            if (Character.toLowerCase(roleInput) == 'd') {
                role = Constants.DONOR;
                validRole = true;
            } else if (Character.toLowerCase(roleInput) == 'c') {
                role = Constants.CONSUMER;
                validRole = true;
            } else {
                System.out.println("Invalid role entered! Please re-enter.");
            }
        } while (!validRole);

        scanner.nextLine();  // Consume the leftover newline

        User user = new User(userList.size() + 1, firstName, lastName, email, password, role);
        userList.add(user);
        return user;
    }
    // method to check if an email already exists
    private boolean emailExists(String email) {
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;  // Email already exists
            }
        }
        return false;  // Email does not exist
    }
    // Helper method to validate that the email ends with @gmail.com
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@gmail\\.com$");
    }

    // Helper method to validate that the name does not contain numbers
    private boolean isValidName(String name) {
        return !name.matches(".*\\d.*");  // Returns true if name does not contain any digits
    }
    // Method to create consumer-specific data
    private Consumer createConsumerData(User user) {
        Consumer consumer = new Consumer(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
        consumerList.add(consumer);
        return consumer;
    }

    // Method to create donor-specific data
    private Donor createDonorData(User user) {
        // Create the donor object without any donation information
        Donor donor = new Donor(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), null, 0, null, 0, 0);

        // Add the donor to the user list but do not add it to the donor list for donations
        userList.add(donor);
        return donor;
    }


    // Load data for users
    private void loadData() {
        fileStorage.loadUsersFromFile();
        userList = fileStorage.getUsersList();
    }
}
