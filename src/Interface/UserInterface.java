package Interface;
import models.Consumer;
import models.Donor;
import models.User;

public interface UserInterface {
    // Method for logging in a user. Accepts username and password as parameters
    // and returns a User object if login is successful, null otherwise.
    User loginUser(String username, String password);

    // Method for registering a new user. Gathers necessary user details and returns
    // a User object representing the newly registered user.
    User registerUser();
}
