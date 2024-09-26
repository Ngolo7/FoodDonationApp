package models;

public class Donor extends User {
    private String typeOfFood;
    private double quantity;
    private String expDate;
    private double unit;
    private String status;
    private int counterId;  // Unique ID for each donation made by the donor
    private int claimedBy;

    // Constructor for Donors class, calling User class constructor with super()
    public Donor(int id, String firstName, String lastName, String email, String password, String typeOfFood, double quantity, String expDate, double unit, int counterId) {
        super(id, firstName, lastName, email, password, "donor");
        this.typeOfFood = typeOfFood;
        this.quantity = quantity;
        this.expDate = expDate;
        this.unit = unit;
        this.status = "available";
        this.counterId = counterId;  // Assign and increment the counterId for each new donation
        this.claimedBy = -1;

    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getExpDate() {
        return expDate;
    }

    public double getUnit() {
        return unit;
    }

    public int getCounterId() {
        return counterId;
    }

    public int getClaimedBy() {
        return claimedBy;
    }


    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    // Getter for Donation ID (this uses the inherited id field from the User class)
    public int getDonationId() {
        return getId();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClaimedBy(int claimedBy) {
        this.claimedBy = claimedBy;
    }

    @Override
    public String toString() {
        return "Donors  [ Donor ID= " + getId() + ", Counter ID= " + counterId + ", typeOfFood=" + typeOfFood + ", quantity=" + quantity + ", expDate=" + expDate + ", Status: "
                + status + ", Claimed By: " + claimedBy + "]";
    }
}
