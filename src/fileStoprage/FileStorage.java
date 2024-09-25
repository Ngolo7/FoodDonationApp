package fileStoprage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Consumer;
import models.Donor;
import models.User;
import models.constants.Constants;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private static final String USER_FILE_PATH = "users.json";
    private static final String FOOD_FILE_PATH = "foods.json";
    private static final String FOOD_CLAIMED_FILE_PATH = "foodClaimed.json";
    private static final String DONATION_ID_COUNTER_FILE = "donation_counter.json";

    private static Gson gson = new Gson();

    // Instance variables for holding lists in memory
    private List<User> usersList = new ArrayList<>();
    private List<Donor> donorList = new ArrayList<>();
    private List<Donor> claimedDonationsList = new ArrayList<>();
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

            for (User user : users) {
                if (user.getRole().equals(Constants.CONSUMER)) {
                    Consumer consumer = new Consumer(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
                    usersList.add(consumer);  // Add to user list
                } else if (user.getRole().equals(Constants.DONOR)) {
                    // Adjust this to match the constructor of the Donor class
                    Donor donor = new Donor(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getPassword(),
                            "Unknown food",  // You might want to load this from JSON
                            0,               // Default quantity
                            "0000-00-00",    // Default expiration date
                            0,               // Default unit
                            0);              // Default counterId
                    usersList.add(donor);  // Add to user list
                    donorList.add(donor);  // Add to donor list
                }
            }

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


    public void loadFoodFromFile() {
        File file = new File(FOOD_FILE_PATH);
        System.out.println("Loading food donations from: " + file.getAbsolutePath());

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing food donation data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FOOD_FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<Donor>>() {}.getType();
                donorList = gson.fromJson(reader, listType);
                if (donorList == null) {
                    donorList = new ArrayList<>();
                } else {
                    // Filter out invalid donations
                    donorList.removeIf(d -> d.getTypeOfFood() == null || d.getTypeOfFood().equalsIgnoreCase("Unknown food") || d.getQuantity() <= 0);
                }
                System.out.println("Loaded " + donorList.size() + " donations.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFoodToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOOD_FILE_PATH))) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();  // Enable pretty printing
            writer.write(gsonPretty.toJson(donorList));  // Write the donor list with pretty printing
            System.out.println("Food donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving food data: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Load Claimed Food from File
    public void loadClaimedFoodFromFile() {
        File file = new File(FOOD_CLAIMED_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No existing claimed food donation data found. A new file will be created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FOOD_CLAIMED_FILE_PATH))) {
                Type listType = new TypeToken<ArrayList<Donor>>() {}.getType();
                claimedDonationsList = gson.fromJson(reader, listType);
                if (claimedDonationsList == null) {
                    claimedDonationsList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Save Claimed Food to File
    public void saveClaimedFoodToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOOD_CLAIMED_FILE_PATH))) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gsonPretty.toJson(claimedDonationsList));  // Save claimed donations with pretty printing
            System.out.println("Claimed food donation data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving claimed food data: " + e.getMessage());
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

    // Save donation ID counter to file
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

    public List<Donor> getDonorList() {
        return donorList;
    }

    public void setDonorList(List<Donor> donorList) {
        this.donorList = donorList;
    }

    public List<Donor> getClaimedDonationsList() {
        return claimedDonationsList;
    }

    public void setClaimedDonationsList(List<Donor> claimedDonationsList) {
        this.claimedDonationsList = claimedDonationsList;
    }

    public int getDonationIdCounter() {
        return donationIdCounter;
    }

    public void setDonationIdCounter(int donationIdCounter) {
        this.donationIdCounter = donationIdCounter;
    }
}
