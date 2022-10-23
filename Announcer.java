public class Announcer implements Runnable{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");

        /*
        try{
            while(true){
                System.out.println("Announcer calls number " + DMV.waitingAreaNumber);
                DMV.waitingAreaReady.release();
                DMV.customerWaitingAreaReady.acquire();

                System.out.println("Customer reaches announcer");

                DMV.waitingAreaComplete.acquire();
                DMV.waitingAreaNumber++;
            }
            
        } catch (InterruptedException e){
            System.out.println("Announcer failed. " + e);
        }
        */
    }
}