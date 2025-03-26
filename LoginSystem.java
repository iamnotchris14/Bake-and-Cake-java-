import java.util.Scanner;

public class LoginSystem {
    // ANSI color codes for styling
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Sample user (Username: admin, Password: 1234, isAdmin: true)
        Login user = new Login("admin", "1234", true);

        while (true) {
            printHeader(" LOGIN SYSTEM ");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Check Login Status");
            System.out.println("4. Check Admin Status");
            System.out.println("5. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("\n🔑 Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("🔒 Enter password: ");
                    String password = scanner.nextLine();

                    System.out.print("🔄 Logging in");
                    loadingEffect();

                    if (user.login(username, password)) {
                        System.out.println(GREEN + "\n✅ Login successful!" + RESET);
                    } else {
                        System.out.println(RED + "\n❌ Invalid username or password." + RESET);
                    }
                    break;

                case 2:
                    user.logout();
                    System.out.println(GREEN + "\n✅ You have been logged out." + RESET);
                    break;

                case 3:
                    System.out.println("\n🔍 Logged in: " + (user.isLoggedIn() ? GREEN + "Yes ✅" + RESET : RED + "No ❌" + RESET));
                    break;

                case 4:
                    System.out.println("\n🔑 Admin access: " + (user.isAdmin() ? GREEN + "Yes 👑" + RESET : RED + "No 🔓" + RESET));
                    break;

                case 5:
                    System.out.println(GREEN + "\n🚪 Exiting... Goodbye!" + RESET);
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }

            Thread.sleep(1000); // Pause for better user experience
        }
    }

    // Simulate loading effect
    private static void loadingEffect() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(500);
        }
    }

    // Method to print fancy headers
    private static void printHeader(String title) {
        String border = "==============================";
        System.out.println("\n" + border);
        System.out.println("   " + title);
        System.out.println(border);
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
