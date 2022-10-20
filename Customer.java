// Used to simulate waiting times
import java.util.Random;

public class Customer implements Runnable {

    private Random rand;

    public int customerId;
    /*
     * Stores value of customer id in thread
     * @param custNum - customer Id passed in from Customer object
     */
    public Customer (int custNum){
        this.customerId = custNum;
    }

    public int getId(){
        return customerId;
    }

    @Override
    public void run(){
        /*
         * Once starting customer thread, makes thread sleep for 1000ms,
         *  gives the Information Desk, Announcer, and Agent threads time to start.
         */
        try {
            Thread.sleep(rand.nextInt((5000-1000) + 1) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Customer " + customerId + " created, enters DMV.");

        /*
         * Information Desk transaction in try/catch block below.
         */
        try{
            // Inform the Information Desk that a customer is ready for the transaction
            DMV.customerInfoDeskReady.release();

            // Customer proceeds to information d esk
            DMV.informationDeskReady.acquire();
            System.out.println(customerId + " goes to information desk.");

            // Inform the information desk which customer is currently there

            DMV.customerAtInfoDesk = customerId;

            // Customer waits until Information desk transaction has completed
            DMV.infoDeskComplete.acquire();
        } catch (InterruptedException e){
            System.out.println("Customer failed at Information Desk stage. " + e);
        }

        /*
         * General format for Announcer stage 
         *  1) Obtain Announcer Semaphore
         *  2) Wait until Agent is ready
         *  3) Join Agent stage
         *  4) Release announcer semaphore
         */
        
         

        
    }
}