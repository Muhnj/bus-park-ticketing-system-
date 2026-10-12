/**
 * Ticket.java
 * Abstract base class for every bus ticket sold at the bus park.
 * It holds the fields shared by all tickets and leaves calculateCharge()
 * abstract, so each type of ticket must supply its own pricing rule.
 *
 * Discount rule (Registration digit B = 2, so P = 2 + 5 = 7%):
 *   Return journeys get a 7% discount on the fare.
 *   A return journey covers the distance twice (there and back).
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public abstract class Ticket implements Chargeable {

    /** P% discount for return journeys: B = 2, so P = 7%. */
    public static final double DISCOUNT_RATE = 0.07;

    /** Longest distance accepted for a single journey, in km. */
    public static final double MAX_DISTANCE_KM = 2000;

    private String ticketId;
    private String passengerName;
    private String destination;
    private double distanceKm;
    private boolean returnJourney;   // true means the 7% discount applies

    /**
     * Creates a ticket. Every value is checked through the setters, so an
     * invalid value stops the object from being created.
     */
    public Ticket(String ticketId, String passengerName, String destination,
                  double distanceKm, boolean returnJourney) throws InvalidTicketException {
        setTicketId(ticketId);
        setPassengerName(passengerName);
        setDestination(destination);
        setDistanceKm(distanceKm);
        setReturnJourney(returnJourney);
    }

    // ---------------------------------------------------------------
    // Abstract methods: each subclass must provide these
    // ---------------------------------------------------------------

    /** Each ticket type works out its own charge (from Chargeable). */
    @Override
    public abstract double calculateCharge();

    /** The price per kilometre for this type of ticket. */
    public abstract double getRatePerKm();

    /** A short label for the type of ticket, e.g. "Standard" or "VIP". */
    public abstract String getTicketType();

    // ---------------------------------------------------------------
    // Shared behaviour
    // ---------------------------------------------------------------

    /** 2 trips for a return journey, otherwise 1. */
    public int getNumberOfTrips() {
        return returnJourney ? 2 : 1;
    }

    /** The fare before any discount. */
    public double getBaseFare() {
        return getRatePerKm() * distanceKm * getNumberOfTrips();
    }

    /** The amount taken off by the discount (0 if it does not apply). */
    public double getDiscountAmount() {
        return getBaseFare() - calculateCharge();
    }

    // ---------------------------------------------------------------
    // Getters and validating setters (encapsulation)
    // ---------------------------------------------------------------

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) throws InvalidTicketException {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new InvalidTicketException("Ticket ID cannot be blank.");
        }
        this.ticketId = ticketId.trim();
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) throws InvalidTicketException {
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new InvalidTicketException("Passenger name cannot be blank.");
        }
        if (!passengerName.trim().matches("[A-Za-z][A-Za-z .'-]*")) {
            throw new InvalidTicketException(
                    "Passenger name may only contain letters, spaces, apostrophes, dots and hyphens.");
        }
        this.passengerName = passengerName.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) throws InvalidTicketException {
        if (destination == null || destination.trim().isEmpty()) {
            throw new InvalidTicketException("Destination cannot be blank.");
        }
        this.destination = destination.trim();
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) throws InvalidTicketException {
        if (Double.isNaN(distanceKm) || distanceKm <= 0) {
            throw new InvalidTicketException("Distance must be greater than 0 km.");
        }
        if (distanceKm > MAX_DISTANCE_KM) {
            throw new InvalidTicketException(
                    "Distance cannot be more than " + (int) MAX_DISTANCE_KM + " km.");
        }
        this.distanceKm = distanceKm;
    }

    public boolean isReturnJourney() {
        return returnJourney;
    }

    /** The discount applies only to return journeys. */
    public boolean isDiscountApplicable() {
        return returnJourney;
    }

    public void setReturnJourney(boolean returnJourney) {
        this.returnJourney = returnJourney;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-8s | %-20s | %-12s | %7.1f km | %-6s | UGX %,12.0f",
                ticketId, getTicketType(), passengerName, destination, distanceKm,
                returnJourney ? "Return" : "Single", calculateCharge());
    }
}
