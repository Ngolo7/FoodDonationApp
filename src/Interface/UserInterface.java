package Interface;
import models.Consumer;
import models.Donor;
import models.User;

public interface UserInterface {
    User loginUser(String username, String password);
    User registerUser();
}
