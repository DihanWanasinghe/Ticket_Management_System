import Configurations.Configuration;
import Configurations.ConfigurationManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    /**
     *  Validates the User Inputs
     * @param scanner Scanner object for taking inputs from the user via the console
     * @param message Instruction for user to make the input
     * @return  return the validated integer
     */
    private static int  integerValidation(Scanner scanner, String message) {
      int value;
      while (true){

          System.out.println(message);
          if(scanner.hasNextInt()){

              value=scanner.nextInt();
              if(value>0){
                  return value;
              }else {
                  System.out.println("Please enter a positive value");
              }
          }else {
              System.out.println("Input must be a whole number");
              scanner.next();
          }
      }
    }

        public static void main(String[] args) {


       // Creation of Scanner object and Configurations.Configuration object for user inputs

        Scanner scanner = new Scanner(System.in);
        Configuration configuration=new Configuration();
        //FileWriter and BufferedWriter objects are declared and initialized with null for writting logs
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter=null;

        // User input variables are declared and initialized with a default value if 0
        int maxTicketCapacity=0;
        int ticketReleaseRate=0;
        int customerRetrievalRate=0;
        int vendorCount=0;
        int totalTickets=0;
        int customerCount=0;
        int maxBuyingLimit=0;
        int totalPossibleSalesByVendors =0;

        while (true) {
            // Checks whether user want ot load the previous configuration or apply new configurations via user Inputs
            System.out.println("Do you want the previous Configurations? Press 'Y' for yes and 'N' for No ");
            String response = scanner.next();
            if (response.equalsIgnoreCase("Y") ) {
                // Configurations.Configuration object is loaded with previous configuration details via a json file
              configuration=  ConfigurationManager.loadConfiguration("Configurations.json");
              if(configuration!=null){
               // details of the configuration object are passed into variable  previously initialized with the default of 0
                  maxTicketCapacity = configuration.getMaxTicketCapacity();
                  ticketReleaseRate = configuration.getTicketReleaseRate();
                  customerRetrievalRate = configuration.getCustomerRetrievalRate();
                  vendorCount = configuration.getVendorCount();
                  customerCount = configuration.getCustomerCount();
                  maxBuyingLimit = configuration.getMaxBuyingLimit();
                  totalTickets = configuration.getTotalTickets();
                  break;


              }
             /*
             in a case where the configuration object becomes null because the mentioned file doesn't exist
             assign a Configurations.Configuration object again to configuration
              */
              configuration=new Configuration();

            // user inputs   details
                // each input is validated
                //every validated input is saved into configuration object

            } else if (response.equalsIgnoreCase("N")  ) {
                maxTicketCapacity = integerValidation(scanner,"Enter maximum ticket capacity: ");
                configuration.setMaxTicketCapacity(maxTicketCapacity);

                ticketReleaseRate = integerValidation(scanner,"Enter ticket release rate : ");
                configuration.setTicketReleaseRate(ticketReleaseRate);

                customerRetrievalRate = integerValidation(scanner,"Enter customer retrieval rate : ");
                configuration.setCustomerRetrievalRate(customerRetrievalRate);

                while (true) {

                    vendorCount = integerValidation(scanner,"Enter number of vendors: ");
                    configuration.setVendorCount(vendorCount);


                    customerCount = integerValidation(scanner,"Enter number of customers: ");
                    configuration.setCustomerCount(customerCount);


                    totalTickets = integerValidation(scanner,"Enter total number of tickets: ");
                    configuration.setTotalTickets(totalTickets);

                    maxBuyingLimit = integerValidation(scanner,"Enter maximum buying limit per customer: ");
                    configuration.setMaxBuyingLimit(maxBuyingLimit);

                    // checks whether some inputs are within the appropriate range
                    // total ticket a single vendor can issue, total ticket single customer can buy should be lower than maximum ticket capacity
                    // customer count and vendor count must be lower than the maxTicketCapacity , taking into the fact that each customer buying a single ticket ,
                    // or each vendor issuing a single ticket
                    //else there would be more tickets thar the maxTicketCapacity allows
                    if(totalTickets>maxTicketCapacity || maxBuyingLimit>maxTicketCapacity|| customerCount>maxTicketCapacity || vendorCount>maxTicketCapacity){
                        System.out.println("Values should be lower than 'Maximum Ticket Capacity'");
                    }else {
                        // if every user input is validated , save the config object details into a json file for future use
                        ConfigurationManager.saveConfiguration(configuration,"Configurations.json");
                        break;
                    }

                }
                break;

            /*If user enters anything other that "y","Y","N","n"
            * loop again
            * */

            }else {
                System.out.println("Please enter a Valid Input");
                continue;
            }
        }
        // total tickets that can be issued by all the vendors if it isn't conditioned by maxTicketCapacity
        totalPossibleSalesByVendors=vendorCount*totalTickets;

        //assign objects for fileWriter and bufferedWriter
        try {
            fileWriter =new FileWriter("logs.txt",true);
            bufferedWriter=new BufferedWriter(fileWriter);

        }catch(IOException e){

            e.printStackTrace();
        }
        // ticket pool object  , Vendor array based on the vendor count , Thread list creation
        TicketPool ticketPool = TicketPool.getTicketPool(maxTicketCapacity,fileWriter,bufferedWriter,totalPossibleSalesByVendors);
        Vendor []vendors = new Vendor[vendorCount];
        List<Thread> threads = new ArrayList<>();

        for(int i=0;i<vendorCount;i++){
            //create vendor objects and assign them to the array
            vendors[i] = new Vendor(ticketPool,totalTickets,ticketReleaseRate);
            // make threads for each vendor object
            Thread vendorThread = new Thread(vendors[i],"Vendor ID:"+i);
            // thread is also added to the Thread list
            threads.add(vendorThread);
            // vendor thread is started and run() method is invoked
            vendorThread.start();
        }

        //customer array created based n the customerCount
        Customer [] customers = new Customer[customerCount];

        for(int j=0;j<customerCount;j++){
            //customer object is created and assigned to the array
            customers[j]= new Customer(customerRetrievalRate,maxBuyingLimit,ticketPool) ;
            // thread object is created from the vendor object
            Thread customerTherad = new Thread(customers[j], "Customer ID:"+j);
            // thread is added to the threads List
            threads.add(customerTherad);
            // thread is started and run() is invoked
            customerTherad.start();
        }

        // every thread created and started until here should be complete its execution to reach line 188
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + thread.getName());
            }
        }

        //closing bufferedWriter with the fileWriter
        try {
            bufferedWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}