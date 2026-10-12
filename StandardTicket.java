/**
 * StandardTicket.java
 * An ordinary bus ticket charged at UGX 150 per km.
 * Return journeys cover the distance twice and get a 7% discount.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class StandardTicket extends Ticket {

    /** Standard fare: UGX 150 per km. */
    public static final double RATE_PER_KM = 150;

    public StandardTicket(String ticketId, String passengerName, String destination,
                          double distanceKm, boolean returnJourney) throws InvalidTicketException {
        super(ticketId, passengerName, destination, distanceKm, returnJourney);
    }

    @Override
    public double calculateCharge() {
        double fare = RATE_PER_KM * getDistanceKm() * getNumberOfTrips();
        if (isDiscountApplicable()) {
            fare = fare - (fare * DISCOUNT_RATE);
        }
        return fare;
    }

    @Override
    public double getRatePerKm() {
        return RATE_PER_KM;
    }

    @Override
    public String getTicketType() {
        return "Standard";
    }
}
