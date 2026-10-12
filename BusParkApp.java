import java.util.List;
import java.util.Scanner;

/**
 * BusParkApp.java
 * Menu-driven command-line program for a bus park ticket office.
 *
 * Personal scenario from VU-BIT-2607-1823-E (A = 8, B = 2, C = 3):
 *   C = 3  -> Bus park: Ticket, StandardTicket (150/km), VipTicket (250/km)
 *   B = 2  -> P = 2 + 5 = 7% discount on return journeys
 *   A = 8  -> report sorted by ticket type, then passenger name (thenComparing)
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class BusParkApp {

    private static final String STUDENT_NAME = "MUNIRU Mujuzi";
    private static final String REG_NUMBER = "VU-BIT-2607-1823-E";

    private final TicketManager manager = new TicketManager();
    private final InputHelper input = new InputHelper(new Scanner(System.in));

    public static void main(String[] args) {
        new BusParkApp().run();
    }

    private void run() {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.readInt("Choose an option (0-6): ", 0, 6);
            System.out.println();
            switch (choice) {
                case 1 -> addStandardTicket();
                case 2 -> addVipTicket();
                case 3 -> showReport();
                case 4 -> findTicket();
                case 5 -> removeTicket();
                case 6 -> showSummary();
                case 0 -> running = false;
                default -> System.out.println("Unknown option.");
            }
        }
        System.out.println("Thank you for using the Bus Park Ticketing System. Goodbye!");
    }

    private void printBanner() {
        System.out.println("==============================================================");
        System.out.println("              BUS PARK TICKETING SYSTEM");
        System.out.println("   Student : " + STUDENT_NAME);
        System.out.println("   Reg. No : " + REG_NUMBER);
        System.out.println("   Standard UGX 150/km | VIP UGX 250/km | 7% off return trips");
        System.out.println("==============================================================");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("-------------------- MAIN MENU --------------------");
        System.out.println(" 1. Add a Standard ticket");
        System.out.println(" 2. Add a VIP ticket");
        System.out.println(" 3. View sorted ticket report");
        System.out.println(" 4. Find a ticket by ID");
        System.out.println(" 5. Remove a ticket");
        System.out.println(" 6. Show summary of charges and discounts");
        System.out.println(" 0. Exit");
        System.out.println("---------------------------------------------------");
    }

    // ------------------------------------------------------------------
    // Adding tickets
    // ------------------------------------------------------------------

    private void addStandardTicket() {
        System.out.println("--- Add a Standard ticket (UGX 150 per km) ---");
        String id = manager.generateNextId();
        String name = readValidName();
        String destination = input.readNonBlank("Destination: ");
        double km = input.readPositiveDouble("Distance in km (one way): ", Ticket.MAX_DISTANCE_KM);
        boolean isReturn = input.readYesNo("Return journey? (Y/N): ");
        try {
            Ticket ticket = new StandardTicket(id, name, destination, km, isReturn);
            manager.addTicket(ticket);
            printSaved(ticket);
        } catch (InvalidTicketException e) {
            System.out.println("Ticket not saved: " + e.getMessage());
        }
    }

    private void addVipTicket() {
        System.out.println("--- Add a VIP ticket (UGX 250 per km) ---");
        String id = manager.generateNextId();
        String name = readValidName();
        String destination = input.readNonBlank("Destination: ");
        double km = input.readPositiveDouble("Distance in km (one way): ", Ticket.MAX_DISTANCE_KM);
        boolean isReturn = input.readYesNo("Return journey? (Y/N): ");
        int seat = input.readInt("VIP seat number (1-" + VipTicket.VIP_SEATS + "): ", 1, VipTicket.VIP_SEATS);
        try {
            Ticket ticket = new VipTicket(id, name, destination, km, isReturn, seat);
            manager.addTicket(ticket);
            printSaved(ticket);
        } catch (InvalidTicketException e) {
            System.out.println("Ticket not saved: " + e.getMessage());
        }
    }

    /** Keeps asking until the name passes the Ticket class's own validation. */
    private String readValidName() {
        while (true) {
            String name = input.readNonBlank("Passenger name: ");
            if (name.matches("[A-Za-z][A-Za-z .'-]*")) {
                return name;
            }
            System.out.println("  Error: name may only contain letters, spaces, apostrophes, dots and hyphens.");
        }
    }

    private void printSaved(Ticket t) {
        System.out.println();
        System.out.println("Ticket saved successfully.");
        printDetails(t);
    }

    private void printDetails(Ticket t) {
        System.out.println("  Ticket ID   : " + t.getTicketId());
        System.out.println("  Type        : " + t.getTicketType());
        System.out.println("  Passenger   : " + t.getPassengerName());
        System.out.println("  Destination : " + t.getDestination());
        System.out.printf ("  Distance    : %.1f km (%s, %d trip%s)%n", t.getDistanceKm(),
                t.isReturnJourney() ? "return" : "single", t.getNumberOfTrips(),
                t.getNumberOfTrips() > 1 ? "s" : "");
        if (t instanceof VipTicket vip) {
            System.out.println("  VIP seat    : " + vip.getSeatNumber());
        }
        System.out.printf ("  Base fare   : UGX %,.0f%n", t.getBaseFare());
        System.out.printf ("  Discount    : UGX %,.0f%s%n", t.getDiscountAmount(),
                t.isDiscountApplicable() ? " (7% return journey)" : "");
        System.out.printf ("  Amount due  : UGX %,.0f%n", t.calculateCharge());
    }

    // ------------------------------------------------------------------
    // Report, find, remove, summary
    // ------------------------------------------------------------------

    private void showReport() {
        System.out.println("--- Ticket report (sorted by type, then passenger name) ---");
        if (manager.isEmpty()) {
            System.out.println("No tickets have been sold yet.");
            return;
        }
        System.out.printf("%-10s | %-8s | %-20s | %-12s | %10s | %-6s | %16s%n",
                "ID", "Type", "Passenger", "Destination", "Distance", "Trip", "Charge");
        System.out.println("-".repeat(102));
        List<Ticket> sorted = manager.getSortedTickets();
        for (Ticket t : sorted) {
            // Polymorphism: toString() calls t.calculateCharge(), which runs
            // the StandardTicket or VipTicket version depending on the object.
            System.out.println(t);
        }
        System.out.println("-".repeat(102));
        System.out.printf("%d ticket(s). Total charges: UGX %,.0f%n",
                sorted.size(), manager.getTotalCharges());
    }

    private void findTicket() {
        System.out.println("--- Find a ticket ---");
        String id = input.readNonBlank("Enter ticket ID (e.g. MM823-001): ");
        try {
            Ticket t = manager.findTicket(id);
            printDetails(t);
        } catch (TicketNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeTicket() {
        System.out.println("--- Remove a ticket ---");
        String id = input.readNonBlank("Enter ticket ID to remove: ");
        try {
            Ticket t = manager.findTicket(id);
            printDetails(t);
            if (input.readYesNo("Are you sure you want to remove this ticket? (Y/N): ")) {
                manager.removeTicket(t.getTicketId());
                System.out.println("Ticket " + t.getTicketId() + " has been removed.");
            } else {
                System.out.println("Removal cancelled. Ticket " + t.getTicketId() + " was kept.");
            }
        } catch (TicketNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSummary() {
        System.out.println("--- Summary ---");
        System.out.println("Standard tickets      : " + manager.countByType("Standard"));
        System.out.println("VIP tickets           : " + manager.countByType("VIP"));
        System.out.println("Total tickets         : " + manager.getTicketCount());
        System.out.printf ("Total charges         : UGX %,.0f%n", manager.getTotalCharges());
        System.out.printf ("Total discount given  : UGX %,.0f%n", manager.getTotalDiscount());
    }
}
