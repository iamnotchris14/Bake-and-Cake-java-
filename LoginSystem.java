import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Creating a sample user (Username: admin, Password: 1234, isAdmin: true)
        Login user = new Login("admin", "1234", true);

        while (true) {
            System.out.println("\n===== Login System =====");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Check Login Status");
            System.out.println("4. Check Admin Status");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (user.login(username, password)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 2:
                    user.logout();
                    System.out.println("You have been logged out.");
                    break;

                case 3:
                    System.out.println("Logged in: " + user.isLoggedIn());
                    break;

                case 4:
                    System.out.println("Admin access: " + user.isAdmin());
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

// Login class inside the same file
class Login {
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isLoggedIn;

    public Login(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isLoggedIn = false;
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
