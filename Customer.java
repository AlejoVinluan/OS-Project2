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

        // Customer joins line at the Information Desk
        //DMV.customerInfoDeskReady.release();
        try{
            DMV.informationDeskReady.acquire();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        
        // Customer will wait until the transaction with transaction desk is done
        try {
            DMV.informationDeskDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Customer " + customerId + ": has number " + DMV.customerNumber[customerId]);
    }
}