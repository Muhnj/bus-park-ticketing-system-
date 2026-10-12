/**
 * VipTicket.java
 * A VIP bus ticket charged at UGX 250 per km, with a reserved seat in the
 * VIP section (seats 1 to 20).
 * Return journeys cover the distance twice and get a 7% discount.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public class VipTicket extends Ticket {

    /** VIP fare: UGX 250 per km. */
    public static final double RATE_PER_KM = 250;

    /** Number of seats in the VIP section of the bus. */
    public static final int VIP_SEATS = 20;

    private int seatNumber;

    public VipTicket(String ticketId, String passengerName, String destination,
                     double distanceKm, boolean returnJourney, int seatNumber)
            throws InvalidTicketException {
        super(ticketId, passengerName, destination, distanceKm, returnJourney);
        setSeatNumber(seatNumber);
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
        return "VIP";
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) throws InvalidTicketException {
        if (seatNumber < 1 || seatNumber > VIP_SEATS) {
            throw new InvalidTicketException(
                    "VIP seat number must be between 1 and " + VIP_SEATS + ".");
        }
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "  (Seat " + seatNumber + ")";
    }
}
