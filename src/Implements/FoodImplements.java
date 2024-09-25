// File: Implements/FoodImplements.java
package Implements;

import Interface.FoodInterface;
import fileStoprage.FileStorage;
import models.Consumer;
import models.Donor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodImplements implements FoodInterface {
    private List<Donor> donorList = new ArrayList<>();
    private List<Donor> claimedDonorList = new ArrayList<>();
    private final FileStorage fileStorage = new FileStorage();  // File storage handler

    private static final Scanner scanner = new Scanner(System.in);

    // Constructor to load data
    public FoodImplements() {
        loadData();
        fileStorage.loadDonationIdCounter();
    }

    @Override
    public Donor registerDonation(Donor donor) {
        System.out.println("Enter type of food to donate:");
        String typeOfFood = scanner.nextLine();
        System.out.println("Enter quantity:");
        double quantity = scanner.nextDouble();
        System.out.println("Enter expiry date (YYYY-MM-DD):");
        scanner.nextLine();  // Consume the leftover newline
        String expDate = scanner.nextLine();
        System.out.println("Enter unit:");
        double unit = scanner.nextDouble();
        scanner.nextLine();  // Consume the leftover newline

      //  int counterId = donorList.size() + 1;
        int counterId = fileStorage.getDonationIdCounter();
        fileStorage.setDonationIdCounter(counterId + 1);

        Donor newDonation = new Donor(donor.getId(), donor.getFirstName(), donor.getLastName(), donor.getEmail(), donor.getPassword(), typeOfFood, quantity, expDate, unit, counterId);
        donorList.add(newDonation);

        fileStorage.saveFoodToFile();
        fileStorage.saveDonationIdCounter();
        System.out.println("Donation registered successfully!");

        return newDonation;
    }

    @Override
    public void claimDonation(int counterId, Consumer consumer) {
        for (Donor donor : donorList) {
            if (donor.getCounterId() == counterId && donor.getStatus().equals("available")) {
                donor.setStatus("claimed");
                donor.setClaimedBy(consumer.getId());
                claimedDonorList.add(donor);
                fileStorage.saveFoodToFile();  // Save updated donor list
                fileStorage.saveClaimedFoodToFile();  // Save claimed list
                System.out.println("Donation claimed successfully!");
                return;
            }
        }
        System.out.println("Donation not found or already claimed.");
    }

    @Override
    public void viewAvailableDonations() {
        System.out.println("Available Donations:");
        boolean found = false;
        for (Donor donation : donorList) {
            if (donation.getStatus().equalsIgnoreCase("available")) {
                System.out.println(donation.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available donations at the moment.");
        }
    }

    @Override
    public void viewClaimedDonations(Consumer consumer) {
        System.out.println("Claimed Donations:");
        boolean found = false;
        for (Donor donation : claimedDonorList) {
            if (donation.getClaimedBy() == consumer.getId()) {
                System.out.println(donation.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("You haven't claimed any donations yet.");
        }
    }

    @Override
    public void viewDonatedList(Donor donor) {
        System.out.println("Your Donations:");
        boolean found = false;

        for (Donor d : donorList) {
            // Only display donations that belong to the logged-in donor and have valid data
            if (d.getId() == donor.getId() && d.getTypeOfFood() != null && d.getQuantity() > 0 && d.getExpDate() != null && !d.getTypeOfFood().equalsIgnoreCase("Unknown food")) {
                System.out.println(d.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("You haven't donated anything yet.");
        }
    }



    // Load data from files
    private void loadData() {
        fileStorage.loadFoodFromFile();
        fileStorage.loadClaimedFoodFromFile();
        donorList = fileStorage.getDonorList();
        claimedDonorList = fileStorage.getClaimedDonationsList();
    }
}
