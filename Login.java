public class Login {
    // Private data fields (encapsulation)
    private String username;
    private String password;
    private boolean isAdmin;
    // Constructors
    public Login(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    //set and get methods
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}