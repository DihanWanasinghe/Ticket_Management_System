/**
 * Structure representing the details of a customer
 * Each customer object can work as a separate thread
 * <p>
 *     <strong>Use Case</strong>
 *     <pre>
 *         TicketPool ticketPool = TicketPool.getTicketPool(20,FileWriter fileWriter,BufferedWriter bufferedWriter);
 *          int total tickets =5;
 *          int ticketReleaseRate=2;
 *          vendor = new Vendor(ticketPool,totalTickets,ticketReleaseRate);
 *          Thread vendorThread = new Thread(vendor,"Vendor ID-1");
 *          vendorThread.start();
 *
 *     </pre>
 * </p>
 */
public class Vendor implements Runnable {

    TicketPool ticketPool;

    int totalTickets;
    int ticketReleaseRate;

    /**
     * Constructor for building a Vendor object
     * @param ticketPool TicketPool object - shared object space fot ticket adding  and  purchasing
     * @param totalTickets Maximum Ticket count  a vendor  can add to the ticketPool
     * @param ticketIssuingRate Time difference between the adding  time of two adjacent tickets by a single vendor
     */
    public Vendor(  TicketPool ticketPool, int totalTickets,int ticketIssuingRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate=ticketIssuingRate;
    }
    /**
     * When the thread is run, Vendor  may start adding tickets to  the ticketPool(addTicket())
     * This action is however conditioned by totalTickets and ticketReleaseRate
     */
    public void run(){

        for(int i=0;i<totalTickets;i++) {


            String name = Thread.currentThread().getName();
            String ID = name.replace("Vendor ID:","");

            Ticket ticket = new Ticket(ID+"-"+i,500,"Normal Event ");
            ticketPool.addTicket(ticket);
            try {
                Thread.sleep(ticketReleaseRate*1000);
            }catch (InterruptedException e){

                System.err.println("Error in Vendor Class");
            }


        }
    }
}
