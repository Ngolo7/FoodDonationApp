package User;

public class Donors extends User {
    private String typeOfFood;
    private double quantity;
    private String expDate;
    private double unit;
    private int donorCounter = 1;
    private String status;

    // Constructor for Donors class, calling User class constructor with super()
    public Donors(int id, String firstName, String lastName, String email, String password, String typeOfFood, double quantity, String expDate, double unit) {
        super(id, firstName, lastName, email, password, "donor");
        this.typeOfFood = typeOfFood;
        this.quantity = quantity;
        this.expDate = expDate;
        this.unit = unit;
        this.status = "available";
        donorCounter++;
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
        return getId();  // Use the inherited getId() method from the User class
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Donors  [ Donor ID=" + getDonationId() + ", typeOfFood=" + typeOfFood + ", quantity=" + quantity + ", expDate=" + expDate +  ", Status: " + status + "]";
    }
}
