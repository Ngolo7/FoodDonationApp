package fileStorage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Consumer;
import models.Donor;
import models.User;
import models.Donation;
import models.constants.Constants;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private static final String USER_FILE_PATH = "users.json";
    private static final String DONATION_FILE_PATH = "donations.json";  // NEW: Path for donation data
    private static final String CLAIMED_DONATION_FILE_PATH = "claimedDonations.json";  // Renamed for clarity
    private static final String DONATION_ID_COUNTER_FILE = "donation_counter.json";

    private static Gson gson = new Gson();

    // Instance variables for holding lists in memory
    private List<User> usersList = new ArrayList<>();
    private List<Donation> donationList = new ArrayList<>();  // NEW: List for available donations
    private List<Donation> claimedDonationsList = new ArrayList<>();  // NEW: List for claimed donations
    private int donationIdCounter = 1;

    // Load users from JSON file and correctly initialize them as Consumer or Donor
    public void loadUsersFromFile() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No existing user data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, listType);

            if (users == null) {
                usersList = new ArrayList<>();  // Initialize as empty list if no users are found
                System.out.println("No users found in the file. Initialized empty user list.");
                return;
            }

            usersList = users;
            System.out.println("Loaded " + usersList.size() + " users.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save Users to File
    public void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gsonPretty.toJson(usersList)); // Write users list with pretty printing
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load donations from JSON file
    public void loadDonationsFromFile() {
        File file = new File(DONATION_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No existing donation data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<ArrayList<Donation>>() {}.getType();
            donationList = gson.fromJson(reader, listType);
            if (donationList == null) {
                donationList = new ArrayList<>();  // Initialize as empty list if no donations are found
            }
            System.out.println("Loaded " + donationList.size() + " donations.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save donations to File
    public void saveDonationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONATION_FILE_PATH))) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gsonPretty.toJson(donationList));  // Write the donation list with pretty printing
            System.out.println("Donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving donation data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load Claimed Donations from File
    public void loadClaimedDonationsFromFile() {
        File file = new File(CLAIMED_DONATION_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing claimed donation data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(CLAIMED_DONATION_FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<Donation>>() {}.getType();
                claimedDonationsList = gson.fromJson(reader, listType);
                if (claimedDonationsList == null) {
                    claimedDonationsList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Save Claimed Donations to File
    public void saveClaimedDonationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLAIMED_DONATION_FILE_PATH))) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gsonPretty.toJson(claimedDonationsList));  // Save claimed donations with pretty printing
            System.out.println("Claimed donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving claimed donation data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load donation ID counter from file
    public void loadDonationIdCounter() {
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

    // Save donation ID counter to file donation-_counter.json
    public void saveDonationIdCounter() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONATION_ID_COUNTER_FILE))) {
            writer.write(Integer.toString(donationIdCounter));
        } catch (IOException e) {
            System.out.println("Error saving donation ID counter: " + e.getMessage());
        }
    }

    // Getter and Setter Methods for Lists and Donation Counter

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<Donation> getDonationList() {  // Changed return type to Donation
        return donationList;
    }

    public void setDonationList(List<Donation> donationList) {  // Changed parameter to Donation list
        this.donationList = donationList;
    }

    public List<Donation> getClaimedDonationsList() {  // Changed return type to Donation
        return claimedDonationsList;
    }

    public void setClaimedDonationsList(List<Donation> claimedDonationsList) {  // Changed parameter to Donation list
        this.claimedDonationsList = claimedDonationsList;
    }

    public int getDonationIdCounter() {
        return donationIdCounter;
    }

    public void setDonationIdCounter(int donationIdCounter) {
        this.donationIdCounter = donationIdCounter;
    }
}
