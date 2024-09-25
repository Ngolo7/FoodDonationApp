package User;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends User {
    private static List<Donors> claimedDonationsList = new ArrayList<>();

    // Constructor for Consumer class, calling User class constructor with super()
    public Consumer(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, "consumer");
    }

    // View available donations
    public void viewAvailableDonations(List<Donors> donationsList) {
        System.out.println("Available Donations:");
        boolean hasAvailableDonations = false;

        // Loop through donations and show only the available ones
        for (Donors donation : donationsList) {
            if (donation.getStatus() != null && donation.getStatus().equalsIgnoreCase("available")) {
                System.out.println(donation);
                hasAvailableDonations = true;
            }
        }

        if (!hasAvailableDonations) {
            System.out.println("No available donations at the moment.");
        }
    }


    // View consumer profile
    public void viewProfile() {

        //System.out.println("Consumer Profile: " + this);
        System.out.println("Consumer Profile:");
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Email: " + getPassword());
        System.out.println("Role: " + getRole());
    }

    // Claim a donation by setting its status to "claimed"
    public void claimDonation(int donationId, List<Donors> donationsList) {
        for (Donors donation : donationsList) {
            if (donation.getDonationId() == donationId && donation.getStatus().equalsIgnoreCase("available")) {


                donation.setStatus("claimed");
                UserApp.claimedDonationsList.add(donation);
                // viewClaimedDonations(claimedDonationList);
                System.out.println("You have successfully claimed the donation: " + donation);
                return;
            }
        }
        System.out.println("The donation is either not available or has already been claimed.");
    }

    // Method to view claimed donations
    public void viewClaimedDonations() {
        if (UserApp.claimedDonationsList.isEmpty()) {
            System.out.println("You have not claimed any donations yet.");
        } else {
            System.out.println("Your claimed donations:");
            for (Donors donation : UserApp.claimedDonationsList) {
                if (donation.getStatus().equalsIgnoreCase("claimed")) {
                    System.out.println(donation);
                }
            }
        }
    }
}
