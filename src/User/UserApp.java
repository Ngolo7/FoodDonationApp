package User;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserApp {

    private static final String FILE_PATH = "users.json";
    private static final String FOOD_FILE_PATH = "Foods.json";
    private static final String FOODClAIMED_FILE_PATH = "FooodClaimed.json";
    private static final String DONATION_ID_COUNTER_FILE = "donation_counter.json";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static List<User> usersList = new ArrayList<>();
    private static List<Donors> donorList = new ArrayList<>();
    public static List<Donors> claimedDonationsList = new ArrayList<>();

    // To store the current donation ID counter
    private static int donationIdCounter;



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        loadUsersFromFile();
        loadFoodFromFile();
        loadClaimedFoodFromFile();
        loadDonationIdCounter();



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
                    saveUsersToFile();
                    saveDonationIdCounter();
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
        saveUsersToFile();
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
        if (user.getRole().equalsIgnoreCase("consumer")) {
            // Convert the user object to Consumer
            Consumer consumer = new Consumer(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());

            System.out.println("Welcome to the Consumer Portal!");
            while (true) {
                System.out.println("1. View Profile");
                System.out.println("2. View Available Donations");
                System.out.println("3. Claim a Donation");
                System.out.println("4. View Claimed Donations");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        consumer.viewProfile();
                        break;
                    case 2:
                        consumer.viewAvailableDonations(donorList);  // Pass the list of donations
                        break;
                    case 3:
                        System.out.print("Enter the donation ID you want to claim: ");
                        int donationId = scanner.nextInt();
                        consumer.claimDonation(donationId, donorList);  // Pass the list of donations
                        FoodToFile();  // Save the changes after claiming the donation
                        saveClaimedFoodToFile();  // Save claimed donations
                        break;
                    case 4:
                        System.out.print("List of claimed Donations: ");
                        consumer.viewClaimedDonations();
                        break;

                    case 5:
                        System.out.println("Exiting the consumer portal...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } else {
            System.out.println("Invalid user role.");
        }
    }


    private static void donorPortal(Scanner scanner, User user) {
        System.out.println("Welcome to the Donor Portal!");

        Donors donor = new Donors(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), "", 0.0, "", 0.0, donationIdCounter++);

        while (true) {
            System.out.println("1. View Profile");
            System.out.println("2. Donate Food");
            System.out.println("3. View Donated Food List");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewProfile(user);  // View donor's profile
                    break;
                case 2:
                    donateFood(scanner, donor);  // Donor can donate food
                    break;
                case 3:
                    viewDonatedFoodList(donor);  // View all donated food by this donor
                    break;
                case 4:
                    System.out.println("Exiting the donor portal...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private static void viewProfile(User user) {
        System.out.println("Donor Profile:");
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Email: " + user.getPassword());
        System.out.println("Role: " + user.getRole());
    }

    private static void donateFood(Scanner scanner, Donors donor) {
        System.out.print("Enter type of food: ");
        String typeOfFood = scanner.nextLine();

        System.out.print("Enter quantity: ");
        double quantity = scanner.nextDouble();

        scanner.nextLine();  // Consume the newline

        System.out.print("Enter expiration date (YYYY-MM-DD): ");
        String expDate = scanner.nextLine();

        System.out.print("Enter unit (kg, grams, liters): ");
        double unit = scanner.nextDouble();

        scanner.nextLine();  // Consume the newline

        // Create a new donor donation and add it to the list
        Donors newDonation = new Donors(donor.getId(), donor.getFirstName(), donor.getLastName(), donor.getEmail(), donor.getPassword(), typeOfFood, quantity, expDate, unit, donationIdCounter++);
        donorList.add(newDonation);
        // Save the updated donation ID counter and food data
        saveDonationIdCounter();
        FoodToFile();  // Save the updated list to the file

        System.out.println("Donation registered successfully: " + newDonation);
    }

    private static void viewDonatedFoodList(Donors donor) {
        System.out.println("Your Donated Food List:");
        boolean hasDonations = false;

        // Loop through the donor list and show all donations from the current donor
        for (Donors donation : donorList) {
            if (donation.getDonationId() == donor.getId()) {
                System.out.println(donation);
                hasDonations = true;
            }
        }

        if (!hasDonations) {
            System.out.println("You have not donated any food yet.");
        }
    }


    private static void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing user data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                usersList = gson.fromJson(reader, listType);
                if (usersList == null) {
                    usersList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(gson.toJson(usersList)); // Write JSON with indentation
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFoodFromFile() {
        File file = new File(FOOD_FILE_PATH);
        System.out.println("Loading food donations from: " + file.getAbsolutePath());  // Print file path for debugging

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing food donation data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FOOD_FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<Donors>>() {}.getType();
                donorList = gson.fromJson(reader, listType);
                if (donorList == null) {
                    donorList = new ArrayList<>();
                }
                System.out.println("Loaded " + donorList.size() + " donations.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void FoodToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOOD_FILE_PATH))) {
            writer.write(gson.toJson(donorList));  // Write JSON with indentation
            System.out.println("Food donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving food data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadClaimedFoodFromFile() {
        File file = new File(FOODClAIMED_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing claimed food donation data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FOODClAIMED_FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<Donors>>() {}.getType();
                claimedDonationsList = gson.fromJson(reader, listType);
                if (claimedDonationsList == null) {
                    claimedDonationsList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void saveClaimedFoodToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOODClAIMED_FILE_PATH))) {
            writer.write(gson.toJson(claimedDonationsList));  // Save claimed donations
            System.out.println("Claimed food donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving claimed food data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Load donation ID counter from file
    private static void loadDonationIdCounter() {
        File file = new File(DONATION_ID_COUNTER_FILE);
        if (!file.exists()) {
            donationIdCounter = 1;  // Start counter from 1 if no file exists
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                donationIdCounter = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                donationIdCounter = 1;  // Reset to 1 if there is an issue reading the file
            }
        }
    }

    // Save donation ID counter to file
    private static void saveDonationIdCounter() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONATION_ID_COUNTER_FILE))) {
            writer.write(Integer.toString(donationIdCounter));
        } catch (IOException e) {
            System.out.println("Error saving donation ID counter: " + e.getMessage());
        }
    }

}


