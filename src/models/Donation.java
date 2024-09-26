package models;

public class Donation {
    private int donorId;  // Reference to the donor
    private String typeOfFood;
    private double quantity;
    private double unit;
    private String expDate;
    private String status; // available or claimed
    private int counterId;
    private int claimedBy; // Consumer ID who claimed it, -1 if not claimed

    public Donation(int donorId, String typeOfFood, double quantity, double unit, String expDate, String status, int counterId, int claimedBy) {
        this.donorId = donorId;
        this.typeOfFood = typeOfFood;
        this.quantity = quantity;
        this.unit = unit;
        this.expDate = expDate;
        this.status = status;
        this.counterId = counterId;
        this.claimedBy = claimedBy;
    }

    // Getters and setters

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public int getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(int claimedBy) {
        this.claimedBy = claimedBy;
    }

    @Override
    public String toString() {
        return "Donation [Donor ID= " + donorId + ", Counter ID= " + counterId + ", Type of Food=" + typeOfFood + ", Quantity=" + quantity + ", Expiry Date=" + expDate + ", Status= " + status + ", Claimed By= " + claimedBy + "]";
    }
}
