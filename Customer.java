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
            DMV.informationDeskReady.acquire();
            DMV.customerInfoDeskReady.release();
            System.out.println(customerId + " goes to information desk.");
            DMV.customerAtInfoDesk = customerId;
            // Customer leaves info desk
            DMV.infoDeskComplete.release();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}