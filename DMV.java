import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

// Main class that handles all transactions, holds semaphores, etc.
public class DMV {

    // Customer array to handle all 20 customers
    private static Thread[] customerArr;

    /*
     * Each boolean is used to stop the threads once all of the transactions have been completed.
     * This occurs after all customer threads confirm they have completed (via join)
     */
    public static boolean infoDeskRunning = true;
    public static boolean announcerRunning = true;
    public static boolean agentZeroRunning = true;
    public static boolean agentOneRunning = true;

    /*
     * Semaphores
     *  - 1st parameter is the default number of Semaphores available
     *  - 2nd parameter is a boolean to allow Threads to acquire semaphores in FIFO manner
     */

    // informationDeskReady will be used when the Info Desk is ready to acquire a customer
    // customerInfoDeskReady will be used when a Customer is ready to utilize the Information Desk
    // infoDeskComplete will be used to acknowledge both parties are complete in their transaction
    public static Semaphore informationDeskReady = new Semaphore(0,true);
    public static Semaphore customerInfoDeskReady = new Semaphore(0,true);
    public static Semaphore infoDeskComplete = new Semaphore(0,true);
    public static Queue<Customer> infoDeskLine = new LinkedList<>();
        
    // waitingAreaReady will be used to inform customers that waiting area is ready
    // customerWaitingAreaReady is used to inform waitingArea that customer is ready
    // waitingAreaComplete is used to tell customers that waitingArea has completed
    // waitingAreaNumber is the number currently being served
    // waitingAreaLine is a Queue data structure of Customers that simulates the line at waitingArea
    public static Semaphore waitingAreaReady = new Semaphore(3,true);
    public static Semaphore customerWaitingAreaReady = new Semaphore(0,true);
    public static Semaphore waitingAreaComplete = new Semaphore(0,true);
    public static int waitingAreaNumber = 1;
    public static Queue<Customer> waitingAreaLine = new LinkedList<>();

    // Agent Semahpores
    // agentReady is used to inform customers that agent is ready
    // agentSemaphore[] used to show which agent is currently ready
    // customerAgentReady informs Agent that customer is ready
    // agentComplete infoms Customer that Agent has completed transaction
    // agentLine is a queue data structure that allows Customers to form a queue to see agent
    public static Semaphore agentReady = new Semaphore(0,true);
    public static Semaphore[] agentSemaphore = new Semaphore[]{
        new Semaphore(0,true),
        new Semaphore(0,true)
    };
    public static Semaphore customerAgentReady = new Semaphore(0,true);
    public static Semaphore agentComplete = new Semaphore(0, true);
    public static Queue<Customer> agentLine = new LinkedList<>();

    // photoEyeExamInstruction is used to signal Customers the instructions from Agent
    // photoEyeExamComplete is used to inform Agents that customers have completed their Photo/Eye exam
    // licenseSemaphore is used so Customers can wait to receive their license from their Agents
    public static Semaphore photoEyeExamInstruction = new Semaphore(0, true);
    public static Semaphore photoEyeExamComplete = new Semaphore(0, true);
    public static Semaphore licenseSemaphore = new Semaphore(0,true);

    // joinedSempahore is used to confirm each customer thread has been joined.
    public static Semaphore[] joinedSemaphore = new Semaphore[21];

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
            joinedSemaphore[i] = new Semaphore(0,true);
            Customer cust = new Customer(i);
            Thread custom = new Thread(cust);
            // Stores customer with ID in customer array
            customerArr[i] = custom;
            custom.start();
        }

        /*
         * Join each customer thread
         */
        for(int i = 1; i <= 20; i++){
            try {
                joinedSemaphore[i].release();
                customerArr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
         * Once all customer threaeds have been joined, shut off the remaining threads
         *  by breaking their while(true) loops.
         */
        infoDeskRunning = false;
        announcerRunning = false;
        agentZeroRunning = false;
        agentOneRunning = false;

        System.out.println("Done.");
    }
}