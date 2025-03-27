/**
 * Structure representing the details of a customer
 * Each customer object can work as a separate thread
 * <p>
 *     <strong>Use Case</strong>
 *     <pre>
 *         int customerRetrievalRate =2;
 *         int maxBuyingLimit =2;
 *         TicketPool ticketPool= new TicketPool();
 *       Customer customer= new Customer(customerRetrievalRate,maxBuyingLimit,ticketPool) ;
 *       Thread customerThread =new Thread(customer, "Customer ID -1");
 *       customerThread.start();
 *
 *     </pre>
 * </p>
 *
 */
public class Customer implements  Runnable {

    TicketPool ticketPool;
    int customerRetrievalRate;
    int maxBuyingLimit;

    /**
     * Constructor for building a Customer object
     * @param ticketBuyingRate  Time difference between the purchasing time of two adjacent tickets by a single customer
     * @param maxBuyingLimit    Maximum Ticket count  a customer can buy
     * @param ticketPool        TicketPool object - shared object space fot ticket adding  and  purchasing
     */
    public Customer( int ticketBuyingRate, int maxBuyingLimit,TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = ticketBuyingRate;
        this.maxBuyingLimit = maxBuyingLimit;
    }

    /**
     * When the thread is run, Customer may start buying tickets from the ticketPool(removeTicket())
     * This action is however conditioned by maxBuyingLimit and ticketBuyingRate
     */

    public void run(){

        for(int i=0;i<maxBuyingLimit;i++){
            ticketPool.removeTicket();
            try {
                Thread.sleep(customerRetrievalRate*1000);
            }catch (InterruptedException e){
                System.err.println("Error in Customer Class" );
            }
        }
    }
}
