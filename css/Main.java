import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input, password;
        Date date;
        FileWriter writer;

        // Load password from file
        try {
            Scanner fileScanner = new Scanner(new File("password.txt"));
            password = decrypt(fileScanner.nextLine());
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Password file not found. Using default password.");
            password = "mysecretpassword";
        }

        // Loop until user enters "q"
        while (true) {
            System.out.print("Enter password: ");
            input = scanner.nextLine();

            // Check if user wants to reset password
            if (input.equals("55")) {
                System.out.print("Enter new password: ");
                password = scanner.nextLine();

                try {
                    // Save new password to file
                    FileWriter passwordWriter = new FileWriter("password.txt");
                    passwordWriter.write(encrypt(password));
                    passwordWriter.close();
                } catch (IOException e) {
                    System.err.println("Error writing new password to file: " + e.getMessage());
                }

                continue;
            }

            // Check if password is correct
            if (!input.equals(password)) {
                System.out.println("Failed.");
                continue;
            }

            try {
                // Open the file for appending
                writer = new FileWriter("output.txt", true);

                // Loop until user enters "q" or "log"
                while (true) {
                    // Get current date and time
                    date = new Date();

                    // Prompt user for input
                    System.out.print("Enter some text (press 'q' to quit, 'log' to view decrypted file): ");
                    input = scanner.nextLine();

                    // Check if user wants to quit
                    if (input.equals("q")) {
                        break;
                    }

                    // Check if user wants to view decrypted file
                    if (input.equals("log")) {
                        BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(decrypt(line)); // display decrypted line
                        }
                        reader.close();
                        continue;
                    }

                    // Write to file with timestamp on a new line
                    writer.write(String.format("%n%s", encrypt(String.format("[%tF %tl:%tM:%tS %Tp] %s",
                            date, date, date, date, date, input))));
                    writer.flush(); // flush buffer to ensure immediate write
                }

                // Close the file and scanner
                writer.close();
                scanner.close();
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }

            // Exit the program
            break;
        }
    }

    public static String encrypt(String input) {
        // Implement your encryption algorithm here
        // For example, you could use a simple substitution cipher like ROT13:
        int shift = 13;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char)(((c - 'a') + shift) % 26 + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                c = (char)(((c - 'A') + shift) % 26 + 'A');
            }
            output.append(c);
        }
        return output.toString();
    }

    public static String decrypt(String input) {
        // Implement your decryption algorithm here
        int shift = 13;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char)(((c - 'a') + 26 - shift) % 26 + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                c = (char)(((c - 'A') + 26 - shift) % 26 + 'A');
            }
            output.append(c);
        }
        return output.toString();
    }
}
