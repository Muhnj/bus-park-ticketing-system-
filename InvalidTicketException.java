/**
 * InvalidTicketException.java
 * Thrown when a ticket is given an invalid value, such as a blank passenger
 * name or a distance that is zero or negative.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class InvalidTicketException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidTicketException(String message) {
        super(message);
    }
}
