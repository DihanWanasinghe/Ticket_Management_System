/**
 * This class represents a Ticket in the Ticket Management System
 * <p>
 *     Contains details such as the unique ID to represent each ticket.
 *     And other mandatory attributes such  as ticketPrice and the eventName
 * <p/>
 * <p>
 * <b>Example Use Case</b>
 * <pre>
 *     Ticket ticket= new Ticket(2, 500.0,"Musical")
 *     System.out.println(ticket);
 * <pre/>
 * </p>
 */

public class Ticket {
    String ticketID;
    float ticketPrice;

    String eventName;

    public Ticket(String ticketID, float ticketPrice, String eventName) {
        this.ticketID = ticketID;
        this.ticketPrice = ticketPrice;
        this.eventName = eventName;
    }

    public String getTicketID() {
        return ticketID;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public String getEventName() {
        return eventName;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    /**
     Print out the Details of  a Ticket
     @return Details are printed as a String

     */

    @Override
    public String toString() {

        String ticketPriceFormat = String.format("%.2f", ticketPrice);
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", ticketPrice= " + ticketPriceFormat +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}

