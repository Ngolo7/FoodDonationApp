package client;

import Implements.AuthServiceImpl;
import Implements.FoodImplements;
import Implements.UserImplement;
import Interface.UserInterface;
import models.Consumer;
import models.Donor;
import models.User;
import models.constants.Constants;

import java.util.Scanner;

public class UserAppInit {
    private static final UserImplement userImplement = new UserImplement();
    private static final UserInterface authService = new AuthServiceImpl();  // Handles user authentication/registration
    private static final FoodImplements foodService = new FoodImplements();  // Handles food-related operations

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printWelcomeMessage();

        User user = null;
        while (user == null) {
            System.out.println("1. Login\n2. Register\nChoose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                user = handleLogin();
            } else if (choice == 2) {
                authService.registerUser();
                System.out.println("Registration successful. Please log in.");
                user = handleLogin();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        // Use equalsIgnoreCase for better matching of roles
        if (user.getRole().equalsIgnoreCase(Constants.DONOR)) {
            handleDonor((Donor) user);
        } else if (user.getRole().equalsIgnoreCase(Constants.CONSUMER)) {
            handleConsumer((Consumer) user);
        } else {
            System.out.println("Error: User role not recognized.");
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to our Donation Management System!");
    }

    private static User handleLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        User user = authService.loginUser(email, password);

        if (user == null) {
            System.out.println("Login failed. Please try again.");
            return null;
        } else {
            System.out.println("Login successful. Welcome " + user.getFirstName() + "!");
        }
        return user;
    }

    // Handle Donor actions
    private static void handleDonor(Donor donor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(donor.getFirstName() +" is a Donor!!!!");

        boolean exit = false;
        while (!exit) {
            System.out.println("1. View Profile\n2. Donate Food\n3. View My Donations\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    userImplement.printDonorData(donor);
                    break;
                case 2:
                    foodService.registerDonation(donor);
                    break;
                case 3:
                    foodService.viewDonatedList(donor);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Handle Consumer actions
    private static void handleConsumer(Consumer consumer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(consumer.getFirstName() +" is a Consumer!!!!");

        boolean exit = false;
        while (!exit) {
            System.out.println("1. View Profile\n2. View Available Donations\n3. Claim Donation\n4. View Consumed Donations\n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    userImplement.printConsumerData(consumer);
                    break;
                case 2:
                    foodService.viewAvailableDonations();
                    break;
                case 3:
                    System.out.println("Enter the Counter ID of the donation you want to claim:");
                    int counterId = scanner.nextInt();
                    foodService.claimDonation(counterId, consumer);
                    break;
                case 4:
                    foodService.viewClaimedDonations(consumer);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}