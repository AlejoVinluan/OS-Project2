// Used to simulate waiting times
import java.util.Random;

public class Customer implements Runnable {

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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Customer " + customerId + " created, enters DMV.");

        /*
         * Information Desk transaction in try/catch block below.
         */
        try{
            DMV.infoDeskLine.add(this);
            // Inform the Information Desk that a customer is ready for the transaction
            DMV.customerInfoDeskReady.release();

            // Customer proceeds to information desk
            DMV.informationDeskReady.acquire();
            System.out.println(customerId + " goes to information desk.");

            // Inform the information desk which customer is currently there
            DMV.customerAtInfoDesk = customerId;
            // Customer waits until Information desk transaction has completed
            DMV.infoDeskComplete.acquire();

            // Starting at Waiting Area

            DMV.customerWaitingAreaReady.release();
            while(customerId != DMV.customerNumber[DMV.waitingAreaNumber]){
                Thread.sleep(1000);
            }
            DMV.waitingAreaReady.acquire();
            // Customer waits for Agent to be ready.
            // DMV.agentReady.acquire();

            DMV.agentReady.acquire();
            System.out.println("Customer " + customerId + ":AgentReady ACQUIRE");
            DMV.waitingAreaComplete.release();
            DMV.customerAgentReady.release();

            int agent;
            if(DMV.agentSemaphore[0].tryAcquire()){
                agent = 0;
            } else {
                DMV.agentSemaphore[1].acquire();
                agent = 1;
            }

            System.out.println("Agent " + agent + " serving customer " + customerId);
            System.out.println("Agent " + agent + " asks customer " + customerId + " to take photo and eye exam.");
            System.out.println("Customer " + customerId + " completes photo and eye exam.");
            System.out.println("Customer " + customerId + " gets license and departs.");

            DMV.agentComplete.acquire();
        } catch (InterruptedException e){
            System.out.println("Customer failed at Information Desk stage. " + e);
        }
   
    }
}