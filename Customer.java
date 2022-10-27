import java.lang.Math;
import java.util.Random;

public class Customer implements Runnable {

    private Random random = new Random();
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
            Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Customer " + customerId + " created, enters DMV.");
        try{
            // Customer "walks" to Information Desk line
            try {
                Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Customer begins to wait in Information Desk line
            DMV.infoDeskLine.add(this);
            // Inform the Information Desk that a customer is ready for the transaction
            DMV.customerInfoDeskReady.release();
            // Customer proceeds to information desk
            DMV.informationDeskReady.acquire();

            // Customer waits until Information desk transaction has completed
            DMV.infoDeskComplete.acquire();

            // Customer "walks" to Waiting Area Line
            try {
                Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Starting at Waiting Area

            // Customer approaches waiting area for Announcer
            DMV.waitingAreaLine.add(this);
            DMV.customerWaitingAreaReady.release();
            DMV.waitingAreaReady.acquire();

            // Customer "walks" to Waiting Area Line
            try {
                Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Customer waits for Agent to be ready.
            DMV.agentLine.add(this);
            // Custoemr waits until the waiting area is done serving customer
            DMV.waitingAreaComplete.acquire();
            // Customer informs agent they are ready for the transaction.
            DMV.customerAgentReady.release();
            // Customer waits until the agent is ready before proceeding
            DMV.agentReady.acquire();


            /*
             * Customer will randomly choose between both agents before proceeding.
             */
            int agent;
            if(random.nextBoolean()){
                DMV.agentSemaphore[0].acquire();
                agent = 0;
            } else {
                DMV.agentSemaphore[1].acquire();
                agent = 1;
            }
            
            // Customer waits for their instruction on how to complete photo and eye exam.
            DMV.photoEyeExamInstruction.acquire();
            System.out.println("Customer " + customerId + " completes photo and eye exam.");
            // Customer completes photo and eye exam
            try {
                Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Customer informs Agent that they have completed the photo and eye exam.
            DMV.photoEyeExamComplete.release();
            System.out.println("Customer " + customerId + " gets license and departs.");
            // Customer waits until their license is ready from the agent.
            DMV.licenseSemaphore.acquire();
            // Customer is completed with agent.
            DMV.agentSemaphore[agent].release();
            // Customer waits until the agent confirms they are done with transaction.
            DMV.agentComplete.acquire();
            // Customer waits until they are joined.
            DMV.joinedSemaphore[customerId].acquire();
            System.out.println("Customer " + customerId + " was joined.");
        } catch (InterruptedException e){
            System.out.println("Customer failed at Information Desk stage. " + e);
        }
   
    }
}