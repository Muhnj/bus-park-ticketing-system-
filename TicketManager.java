import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TicketManager.java
 * Stores all tickets in an ArrayList and provides add, find, remove,
 * the sorted report list and the totals.
 *
 * Report sort order (Registration digit A = 8, range 7 to 9):
 *   type of ticket first, then passenger name alphabetically, using thenComparing.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class TicketManager {

    /** Initials (MUNIRU Mujuzi) + last three digits of VU-BIT-2607-1823-E. */
    public static final String ID_PREFIX = "MM823-";

    /** Sort by ticket type, then by passenger name (A = 8). */
    public static final Comparator<Ticket> REPORT_ORDER =
            Comparator.comparing(Ticket::getTicketType)
                      .thenComparing(Ticket::getPassengerName, String.CASE_INSENSITIVE_ORDER);

    private final ArrayList<Ticket> tickets = new ArrayList<>();
    private int nextNumber = 1;

    /** Gives the next ID, e.g. MM823-001. IDs are never reused. */
    public String generateNextId() {
        return String.format("%s%03d", ID_PREFIX, nextNumber);
    }

    /** Adds a ticket and moves the ID counter on. */
    public void addTicket(Ticket ticket) throws InvalidTicketException {
        if (ticket == null) {
            throw new InvalidTicketException("Cannot add an empty ticket.");
        }
        for (Ticket t : tickets) {
            if (t.getTicketId().equalsIgnoreCase(ticket.getTicketId())) {
                throw new InvalidTicketException("A ticket with ID " + ticket.getTicketId() + " already exists.");
            }
        }
        tickets.add(ticket);
        nextNumber++;
    }

    /** Finds a ticket by ID (not case sensitive). */
    public Ticket findTicket(String ticketId) throws TicketNotFoundException {
        String wanted = (ticketId == null) ? "" : ticketId.trim();
        for (Ticket t : tickets) {
            if (t.getTicketId().equalsIgnoreCase(wanted)) {
                return t;
            }
        }
        throw new TicketNotFoundException(wanted);
    }

    /** Removes a ticket by ID. */
    public Ticket removeTicket(String ticketId) throws TicketNotFoundException {
        Ticket t = findTicket(ticketId);
        tickets.remove(t);
        return t;
    }

    /** Returns a sorted copy of the tickets, so the stored list is not exposed. */
    public List<Ticket> getSortedTickets() {
        List<Ticket> copy = new ArrayList<>(tickets);
        copy.sort(REPORT_ORDER);
        return copy;
    }

    /** Total of all charges, worked out polymorphically. */
    public double getTotalCharges() {
        double total = 0;
        for (Chargeable c : tickets) {
            total += c.calculateCharge();
        }
        return total;
    }

    /** Total discount given on all return journeys. */
    public double getTotalDiscount() {
        double total = 0;
        for (Ticket t : tickets) {
            total += t.getDiscountAmount();
        }
        return total;
    }

    public int countByType(String type) {
        int count = 0;
        for (Ticket t : tickets) {
            if (t.getTicketType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }
}
