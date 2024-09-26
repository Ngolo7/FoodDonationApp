package client;

import Implements.UserImplement;
import Interface.FoodInterface;
import Interface.UserInterface;
import Implements.AuthServiceImpl;
import Implements.FoodImplements;
import models.Consumer;
import models.Donor;
import models.User;

import java.util.Scanner;

public class UserAppInit {
    private static final UserImplement userImplement = new UserImplement();
    private static final FoodImplements foodService = new FoodImplements();
    private static final UserInterface authService = new AuthServiceImpl();  // Handles user authentication/registration


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Food Donation System");
        boolean exit = false;

        while (!exit) {
            System.out.println("Please select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Login flow
                    login(authService, foodService);
                    break;

                case 2:
                    // Register flow and direct to login
                    User newUser = authService.registerUser();
                    System.out.println("Registration successful. Please log in.");
                    login(authService, foodService);
                    break;

                case 3:
                    System.out.println("Exiting the system. Thank you!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void login(UserInterface authService, FoodInterface foodService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        User loggedInUser = authService.loginUser(email, password);

        if (loggedInUser != null) {
            System.out.println("Login successful!");

            // Check role and provide relevant options
            if (loggedInUser instanceof Consumer) {
                Consumer consumer = (Consumer) loggedInUser;
                handleConsumerActions(foodService, consumer);
            } else if (loggedInUser instanceof Donor) {
                Donor donor = (Donor) loggedInUser;
                handleDonorActions(foodService, donor);
            }
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void handleConsumerActions(FoodInterface foodService, Consumer consumer) {
        Scanner scanner = new Scanner(System.in);
        boolean consumerExit = false;

        while (!consumerExit) {
            System.out.println("Consumer Actions:");
            System.out.println("1. View Profile");
            System.out.println("2. View Available Donations");
            System.out.println("3. Claim Donation");
            System.out.println("4. View Claimed Donations");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // View Consumer Profile
                    userImplement.printConsumerData(consumer);
                    break;

                case 2:
                    foodService.viewAvailableDonations();
                    break;

                case 3:
                    System.out.println("Enter the donation Counter ID to claim:");
                    int counterId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    foodService.claimDonation(counterId, consumer);
                    break;

                case 4:
                    foodService.viewClaimedDonations(consumer);
                    break;

                case 5:
                    System.out.println("Logging out.");
                    consumerExit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleDonorActions(FoodInterface foodService, Donor donor) {
        Scanner scanner = new Scanner(System.in);
        boolean donorExit = false;

        while (!donorExit) {
            System.out.println("Donor Actions:");
            System.out.println("1. View Profile");
            System.out.println("2. Register a Donation");
            System.out.println("3. View Donated List");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // View Donor Profile
                    userImplement.printDonorData(donor);
                    break;

                case 2:
                    foodService.registerDonation(donor);
                    break;

                case 3:
                    foodService.viewDonatedList(donor);
                    break;

                case 4:
                    System.out.println("Logging out.");
                    donorExit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}