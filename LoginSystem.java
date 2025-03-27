import java.util.Scanner;

// Login class inside the same file
class Login {
    private String username;
    private String password;
    private boolean isAdmin;

    public Login(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

public class LoginSystem {
    // ANSI color codes for styling
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Admin and Customer users
        Login admin = new Login("adminUser", "adminPass", true);
        Login customer = new Login("customerUser", "customerPass", false);

        while (true) {
            printHeader(" LOGIN SYSTEM ");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleLogin(scanner, admin, "Admin");
                    break;
                case 2:
                    handleLogin(scanner, customer, "Customer");
                    break;
                case 3:
                    System.out.println(GREEN + "\n🚪 Exiting... Goodbye!" + RESET);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }

            Thread.sleep(1000); // Pause for better user experience
        }
    }

    private static void handleLogin(Scanner scanner, Login user, String userType) throws InterruptedException {
        System.out.print("\n🔑 Enter username: ");
        String username = scanner.nextLine();
        System.out.print("🔒 Enter password: ");
        String password = scanner.nextLine();

        System.out.print("🔄 Logging in");
        loadingEffect();

        if (user.login(username, password)) {
            System.out.println(GREEN + "\n✅ " + userType + " login successful!" + RESET);
        } else {
            System.out.println(RED + "\n❌ Invalid username or password." + RESET);
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
