import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * This class provides the structure for  the ticket pool , a shared object where tickets get added for purchase and gets removed when a customer purchase them
 * Designed based on the creational Design Pattern - Singleton as only one object needed
 * </p>
 *
 * <p>
 *     <strong>Use Case</strong>
 *     <pre>
 *         TicketPool ticketPool =  TicketPool.getTicketPool(20,FileWriter fileWriter,BufferedWriter bufferedWriter,20);
 *         ticketPool.addTicket(new Ticket());
 *         ticketPool.removeTicket();
 *
 *
 *     </pre>
 *
 * </p>
 */

public class TicketPool {
    int maximumTicketCapacity;
    LinkedList<Ticket> ticketList;

    int currentIssuedTicketCount =0;
    int currentBoughtTickets =0;

    FileWriter fileWriter= null;
    BufferedWriter bufferedWriter=null;

    //total tickets that can be issued by all the vendors if it isn't conditioned by maxTicketCapacity
    int totalPossibleSalesByVendors;

    private static TicketPool ticketPool;

    Lock lock = new ReentrantLock();



    private TicketPool(int maximumTicketCapacity,FileWriter fileWriter,BufferedWriter bufferedWriter,int totalPossibleSalesByVendors) {

        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketList = new LinkedList<Ticket>();
        this.fileWriter=fileWriter;
        this.bufferedWriter=bufferedWriter;
        this.totalPossibleSalesByVendors=totalPossibleSalesByVendors;


    }

    /**
     * Creates a TicketPool object , only if a ticket pool object isn't already created
     * @param maximumTicketCapacity maximum total  amount of tickets that can be issued for the Event by every vendor
     * @param fileWriter FileWriter object for writing logs for a file
     * @param bufferedWriter BufferedWriter object for  writing logs in line by line using the FileWriter object
     * @return ticketPool object
     */

    public  static  TicketPool getTicketPool(int maximumTicketCapacity,FileWriter fileWriter,BufferedWriter bufferedWriter,int totalPossibleSalesFromVendors ){
        if(ticketPool == null){

            ticketPool= new TicketPool(maximumTicketCapacity,fileWriter,bufferedWriter,totalPossibleSalesFromVendors);
        }
        return  ticketPool;

    }

    /**
     * Adds  a ticket to the LinkedList object called TicketList .
     * Increase the value of currentIssuedTicketCount variable by 1
     * @param ticket Ticket object representing a single ticket for the event
     */

    public synchronized void  addTicket(Ticket ticket) {


            if (currentIssuedTicketCount >= Math.min(maximumTicketCapacity,totalPossibleSalesByVendors)) {

                return;
            }
            ticketList.add(ticket);

            currentIssuedTicketCount++;


                try {
                    lock.lock();
                    bufferedWriter.write(" Ticket Issued by: " + Thread.currentThread().getName() + " Currently Issued Tickets: " + currentIssuedTicketCount);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            System.out.println(" Ticket Issued by: " + Thread.currentThread().getName() + " Currently Issued Tickets: " + currentIssuedTicketCount );

            notifyAll();

    }

    /**
     * Removes a ticket from the LinkedList called ticketList
     * If no ticket is available to remove , wait till a ticket is added to the ticketList
     * Only if the currentIssuedTicketCount hasn't reached the maxTicketCapacity
     * @return
     */

    public synchronized void  removeTicket( ){


               while (ticketList.isEmpty()) {


                   try {
                       if(currentBoughtTickets>=Math.min(maximumTicketCapacity,totalPossibleSalesByVendors)){
                           return;
                       }
                       wait();

                   } catch (InterruptedException e) {

                       System.err.println("Error in pool - remove ticket");
                   }
               }
               Ticket ticket = ticketList.remove();
               currentBoughtTickets+=1;
               notifyAll();

               synchronized (bufferedWriter) {
                   try {
                       bufferedWriter.write("Ticket bought by- " + Thread.currentThread().getName() + " current size is: " + ticketList.size() + " Ticket information: " + ticket);
                       bufferedWriter.newLine();

                       bufferedWriter.flush();
                   } catch (IOException e) {

                       e.printStackTrace();

                   }
               }

               System.out.println("Ticket bought by- " + Thread.currentThread().getName() + " current size is: " + ticketList.size() + " Ticket information: " + ticket);

    }


}
