import java.util.concurrent.Semaphore;

// Main class that handles all transactions, holds semaphores, etc.
public class DMV {

    // Customer array to handle all 20 customers
    private static Thread[] customerArr;

    /*
     * Semaphores
     *  - 1st parameter is the default number of Semaphores available
     *  - 2nd parameter is a boolean to allow Threads to acquire semaphores in FIFO manner
     */

    // informationDeskReady will be used when the Info Desk is ready to acquire a customer
    public static Semaphore informationDesk = new Semaphore(1,true);
    // Variables used by Information Desk to assign customer a number
    //public static int[] customerNumber = new int[21];


    public static void main(String[] args){
        // Creates Information Desk Runnable Object
        InformationDesk informationDesk = new InformationDesk();

        // Creates Announcer "Runnable Object"
        Announcer announcer = new Announcer();

        // Creates Agents "Runnable Object"
        //  2 Agents required
        Agent agent0 = new Agent(0);
        Agent agent1 = new Agent(1);

        /**
         * Start all threads, in order of Information Desk, Announcer, Agents.
         *  Customers started after all 3 above started, and starting DMV process.
         */
        Thread infoDesk = new Thread(informationDesk);
        Thread announce = new Thread(announcer);
        Thread agentZero = new Thread(agent0);
        Thread agentOne = new Thread(agent1);
        infoDesk.start();
        announce.start();
        agentZero.start();
        agentOne.start();

        /*
         * Create 20 Customer runnable objects and, storing their ID in customerArray
         *  Customers in customerArr are stored from 1-20. Also starts the customer threads.
         */
        customerArr = new Thread[21];
        for(int i = 1; i <= 20; i++){
            // Creates customer object, storing Id "i" for customer
            Customer cust = new Customer(i);
            Thread custom = new Thread(cust);
            // Stores customer with ID in customer array
            customerArr[i] = custom;
            custom.start();
        }

        for(int i = 1; i <= 20; i++){
            try {
                customerArr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        infoDesk.interrupt();
        /* 
        announce.interrupt();
        agentZero.interrupt();
        agentOne.interrupt();

        for(int i = 1; i <= 20; i++){
            customerArr[i].interrupt(); 
        }
        */
    }
}