package models;

public class Consumer extends User {

    public Consumer(int id, String FirstName, String LastName, String Email, String Password, String Role) {
        super(id, FirstName, LastName, Email, Password, Role);
    }

   // @Override
  // public void showProfile() {
    //    System.out.println("Consumer ID: " + getId() + ", Name: " + getFirstName() + " " + getLastName() + ", Email: " + getEmail());
   // }
}
