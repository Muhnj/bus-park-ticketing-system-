/**
 * TicketNotFoundException.java
 * Thrown when no ticket with the requested ID exists in the system.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class TicketNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public TicketNotFoundException(String ticketId) {
        super("No ticket found with ID \"" + ticketId + "\".");
    }
}
