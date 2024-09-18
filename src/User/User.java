package User;

public class User {
    private int id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String Role;


    public User(int id, String FirstName, String LastName, String Email, String Password, String Role) {
        this.id = id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
        this.Role = Role;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return Role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        if(firstName == null || FirstName.isEmpty())
        {
            System.out.println("FirstName is null or empty");
        }
        else{
        this.FirstName = firstName;}
    }

    public void setLastName(String lastName) {
        if(lastName == null || LastName.isEmpty())
        {
            System.out.println("LastName is null or empty");
        }
        else{

        this.LastName = lastName;}
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        if(password == null || Password.isEmpty())
        {
            System.out.println("Password is null or empty");
        }
        else{
            this.Password = password;
        }
    }

    public void setRole(String role) {
        this.Role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Email=" + Email + ", Password=" + Password + ", Role=" + Role + "]";
    }
}
