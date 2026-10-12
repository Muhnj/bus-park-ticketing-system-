import java.util.Scanner;

/**
 * InputHelper.java
 * Reads keyboard input safely. Every method keeps asking until the user
 * types something valid, so no input can crash the program.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Reads one line. If input ends (e.g. Ctrl+D / Ctrl+Z), the program closes cleanly. */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input has ended. Closing the program. Goodbye!");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    /** Reads text that is not blank. */
    public String readNonBlank(String prompt) {
        while (true) {
            String text = readLine(prompt);
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("  Error: this field cannot be blank. Please try again.");
        }
    }

    /** Reads a whole number between min and max. */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            String text = readLine(prompt);
            try {
                int value = Integer.parseInt(text);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("  Error: enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Error: \"" + text + "\" is not a whole number. Please try again.");
            }
        }
    }

    /** Reads a decimal number greater than 0 and at most max. */
    public double readPositiveDouble(String prompt, double max) {
        while (true) {
            String text = readLine(prompt);
            try {
                double value = Double.parseDouble(text);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("  Error: please enter a real number.");
                } else if (value <= 0) {
                    System.out.println("  Error: the value must be greater than 0.");
                } else if (value > max) {
                    System.out.println("  Error: the value cannot be more than " + (int) max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Error: \"" + text + "\" is not a number. Please try again.");
            }
        }
    }

    /** Reads Y/YES or N/NO (any case). */
    public boolean readYesNo(String prompt) {
        while (true) {
            String text = readLine(prompt).toLowerCase();
            if (text.equals("y") || text.equals("yes")) {
                return true;
            }
            if (text.equals("n") || text.equals("no")) {
                return false;
            }
            System.out.println("  Error: please answer Y or N.");
        }
    }
}
