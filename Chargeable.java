/**
 * Chargeable.java
 * Any record in the system that can work out what the customer owes.
 *
 * Author: MUNIRU Mujuzi (VU-BIT-2607-1823-E)
 */
public interface Chargeable {

    /**
     * Works out the amount the customer must pay, in Uganda shillings,
     * after any discount has been applied.
     *
     * @return the final charge in UGX
     */
    double calculateCharge();
}
