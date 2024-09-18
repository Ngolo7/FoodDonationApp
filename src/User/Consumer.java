package User;

import java.util.List;

public class Consumer extends User {

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
        System.out.println("Consumer Profile: " + this);
    }

    // Claim a donation by setting its status to "claimed"
    public void claimDonation(int donationId, List<Donors> donationsList) {
        for (Donors donation : donationsList) {
            if (donation.getDonationId() == donationId && donation.getStatus().equalsIgnoreCase("available")) {
                donation.setStatus("claimed");
                System.out.println("You have successfully claimed the donation: " + donation);
                return;
            }
        }
        System.out.println("The donation is either not available or has already been claimed.");
    }
}
