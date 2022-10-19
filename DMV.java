import java.util.concurrent.Semaphore;

// Main class that handles all transactions, holds semaphores, etc.
public class DMV {

    private static Customer[] customerArr;
    /*
     * Semaphores
     *  - 1st parameter is the default number of Semaphores available
     *  - 2nd parameter is a boolean to allow Threads to acquire semaphores in FIFO manner
     */

     // informationDeskReady will be used when the Info Desk is ready to acquire a customer
     //  customerReady will also be used when Customer is ready at Info Desk
    public static Semaphore informationDeskReady = new Semaphore(0,true);
    public static Semaphore customerInfoDeskReady = new Semaphore(0,true);
    // Variables used by Information Desk to assign customer a number
    public static void main(String[] args){
        // Creates Information Desk thread
        InformationDesk informationDesk = new InformationDesk();

        // Creates Announcer Thread
        Announcer announcer = new Announcer();

        // Creates Agents thread
        //  2 Agents required
        Agent agentZero = new Agent(0);
        Agent agentOne = new Agent(1);

        /*
         * Create 20 Customer threads, storing their ID in customerArray
         *  Customers in customerArr are stored from 1-20
         */
        customerArr = new Customer[21];
        for(int i = 1; i <= 20; i++){
            // Creates customer object, storing Id "i" for customer
            Customer cust = new Customer(i);
            // Stores customer with ID in customer array
            customerArr[i] = cust;
        }

        /**
         * Start all threads, in order of Information Desk, Announcer, Agents.
         *  Customers started after all 3 above started, and starting DMV process.
         */
        informationDesk.start();
        announcer.start();
        agentZero.start();
        agentOne.start();

        for(int i = 1; i <= 20; i++){
            customerArr[i].start();
        }

    }
}