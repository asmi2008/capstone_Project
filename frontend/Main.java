import java.io.*;
import java.util.*;

public class Main {

    static final String FILE_NAME = "users.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== ONLINE FASHION SHOPPING SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println("Thank you!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Register
    static void register() {

        System.out.println("\n===== REGISTER =====");

        System.out.print("Enter Email ID: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        // Check whether email already exists
        if (userExists(email)) {
            System.out.println("Email already registered!");
            return;
        }

        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(email + "," + password);
            bw.newLine();

            bw.close();

            System.out.println("Registration successful!");

        } catch (IOException e) {
            System.out.println("Error while saving user details.");
        }
    }

    // Login
    static void login() {

        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter Email ID: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(FILE_NAME)
            );

            String line;
            boolean loginSuccess = false;

            while ((line = br.readLine()) != null) {

                String[] user = line.split(",");

                if (user.length == 2) {

                    String savedEmail = user[0];
                    String savedPassword = user[1];

                    if (savedEmail.equals(email)
                            && savedPassword.equals(password)) {

                        loginSuccess = true;
                        break;
                    }
                }
            }

            br.close();

            if (loginSuccess) {
                System.out.println("Login successful!");
                System.out.println("Welcome to Online Fashion Shopping System!");

            } else {
                System.out.println("Invalid Email ID or Password.");
                System.out.println("Please register first or check your details.");
            }

        } catch (FileNotFoundException e) {

            System.out.println("No users registered yet.");
            System.out.println("Please register first.");

        } catch (IOException e) {

            System.out.println("Error while reading user details.");
        }
    }

    // Check if email already exists
    static boolean userExists(String email) {

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(FILE_NAME)
            );

            String line;

            while ((line = br.readLine()) != null) {

                String[] user = line.split(",");

                if (user.length == 2 && user[0].equals(email)) {
                    br.close();
                    return true;
                }
            }

            br.close();

        } catch (FileNotFoundException e) {

            return false;

        } catch (IOException e) {

            System.out.println("Error checking user details.");
        }

        return false;
    }
}