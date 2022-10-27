public class Announcer implements Runnable{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");

        try{
            while(DMV.announcerRunning){
                // Signal to the customers that the announcer is ready to announce numbers.
                DMV.waitingAreaReady.release();
                // Wait until a customer is ready to approach the announcer.
                DMV.customerWaitingAreaReady.acquire();
                System.out.println("Announcer calls number " + DMV.waitingAreaNumber);
                // Wait until the customer moves to agent line
                try {
                    Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Choose the first customer in line at the waiting area.
                Customer currCustomer = DMV.waitingAreaLine.remove();
                System.out.println(currCustomer.customerId + " moves to agent line.");
                // Inform the rest of the DMV that the waiting area can accomodate one more person
                DMV.waitingAreaComplete.release();
                // Increment the waitingArea number so that the next customer can approach
                DMV.waitingAreaNumber++;
            }
        } catch (InterruptedException e){
            System.out.println("Announcer failed. " + e);
        }
        
    }
}