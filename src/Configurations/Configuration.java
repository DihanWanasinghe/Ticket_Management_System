package Configurations;

/**
 * This class contains Configurations.Configuration details
 * <p>
 *     Application requires user inputs to work with.
 *     Configurations.Configuration objects saves the user inputs to a Configurations.Configuration object
 *     Previous User inputs can be retrieved from Configurations.Configuration objects when the application starts,
 *     when a configuration object works together with the Configurations.ConfigurationManager class.\
 *     This class contains getters and setters for the user inputs.
 *
 * </p>
 *
 * <p>
 *     <b>Use Case</b>
 *     <pre>
 *     Configurations.Configuration configuration=new Configurations.Configuration();
 *     configuration.setMaxTicketCapacity(maxTicketCapacity);
 *     maxTicketCapacity = configuration.getMaxTicketCapacity();
 *      </pre>
 * </p>
 */

public class Configuration {

    int maxTicketCapacity;
    int totalTickets;
    int vendorCount;
    int customerCount;
    int ticketReleaseRate;
    int customerRetrievalRate;
    int maxBuyingLimit;



    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxBuyingLimit() {
        return maxBuyingLimit;
    }

    public void setMaxBuyingLimit(int maxBuyingLimit) {
        this.maxBuyingLimit = maxBuyingLimit;
    }
}
