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

            // Starting at Waiting Area

            DMV.customerWaitingAreaReady.release();
            while(customerId != DMV.customerNumber[DMV.waitingAreaNumber]){
                Thread.sleep(1000);
            }
            DMV.waitingAreaReady.acquire();
            // Customer waits for Agent to be raedy.
            // DMV.agentReady.acquire();
            DMV.waitingAreaComplete.release();
            
        } catch (InterruptedException e){
            System.out.println("Customer failed at Information Desk stage. " + e);
        }

        
    }
}