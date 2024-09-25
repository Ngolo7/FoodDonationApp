// File: Interface/FoodInterface.java
package Interface;

import models.Consumer;
import models.Donor;

public interface FoodInterface {
    // Methods related to food donation management
    Donor registerDonation(Donor donor);
    void claimDonation(int counterId, Consumer consumer);
    void viewAvailableDonations();
    void viewClaimedDonations(Consumer consumer);
    void viewDonatedList(Donor donor);
}
