package Implements;

import Interface.FoodInterface;
import fileStorage.FileStorage;
import models.Consumer;
import models.Donation;
import models.Donor;

import java.util.List;
import java.util.Scanner;

public class FoodImplements implements FoodInterface {

    private final FileStorage fileStorage = new FileStorage();  // File storage handler
    private List<Donation> donationList;  // CHANGE START: List for donations
    private List<Donation> claimedDonationsList;  // CHANGE START: List for claimed donations

    private static final Scanner scanner = new Scanner(System.in);

    // Constructor to load data
    public FoodImplements() {
        fileStorage.loadDonationsFromFile();
        fileStorage.loadClaimedDonationsFromFile();
        fileStorage.loadDonationIdCounter();  // Load donation counter from file
        donationList = fileStorage.getDonationList();
        claimedDonationsList = fileStorage.getClaimedDonationsList();
    }

    @Override
    public Donor registerDonation(Donor donor) {
        System.out.println("Enter type of food to donate:");
        String typeOfFood = scanner.nextLine();
        System.out.println("Enter quantity:");
        double quantity = scanner.nextDouble();
        System.out.println("Enter expiry date (YYYY-MM-DD):");
        scanner.nextLine();
        String expDate = scanner.nextLine();
        System.out.println("Enter unit:");
        double unit = scanner.nextDouble();
        scanner.nextLine();

        int counterId = fileStorage.getDonationIdCounter();  // Get the current counter ID
        fileStorage.setDonationIdCounter(counterId + 1);  // Increment the counter

        Donation newDonation = new Donation(donor.getId(), typeOfFood, quantity, unit, expDate, "available", counterId, -1);
        donationList.add(newDonation);

        fileStorage.saveDonationsToFile();
        fileStorage.saveDonationIdCounter();  // Save the updated counter
        System.out.println("Donation registered successfully!");

        return donor;
    }
    @Override
    public void claimDonation(int counterId, Consumer consumer) {
        for (Donation donation : donationList) {
            if (donation.getCounterId() == counterId && donation.getStatus().equals("available")) {
                donation.setStatus("claimed");
                donation.setClaimedBy(consumer.getId());
                claimedDonationsList.add(donation);  // Add to claimed donations
                fileStorage.saveDonationsToFile();  // Save updated donation list
                fileStorage.saveClaimedDonationsToFile();  // Save claimed donation list
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

        for (Donation donation : donationList) {
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

        for (Donation donation : claimedDonationsList) {
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

        for (Donation donation : donationList) {
            if (donation.getDonorId() == donor.getId()) {
                System.out.println(donation.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("You haven't donated anything yet.");
        }
    }
}
