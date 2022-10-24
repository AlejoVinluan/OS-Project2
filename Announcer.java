public class Announcer implements Runnable{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");

        try{
            while(true){
                DMV.waitingAreaReady.release();
                DMV.customerWaitingAreaReady.acquire();
                System.out.println("Announcer calls number " + DMV.waitingAreaNumber);
                try {
                Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                Customer currCustomer = DMV.waitingAreaLine.remove();
                System.out.println(currCustomer.customerId + " moves to agent line.");
                DMV.waitingAreaComplete.release();
                DMV.waitingAreaNumber++;
            }
        } catch (InterruptedException e){
            System.out.println("Announcer failed. " + e);
        }
        
    }
}