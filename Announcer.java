public class Announcer implements Runnable{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");

        try{
            while(true){
                System.out.println("Announcer calls number " + DMV.waitingAreaNumber);
                DMV.waitingAreaReady.release();
                DMV.customerWaitingAreaReady.acquire();

                System.out.println("Customer reaches announcer");

                DMV.waitingAreaComplete.acquire();
                DMV.waitingAreaNumber++;
                if(DMV.waitingAreaNumber == 2){break;}
            }
        } catch (InterruptedException e){
            System.out.println("Announcer failed. " + e);
        }
    }
}