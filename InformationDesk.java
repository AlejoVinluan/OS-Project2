public class InformationDesk implements Runnable{
    // Current number is used to assign number to Customer Thread
    private static int currNumber; 
    public InformationDesk(){currNumber = 0;}

    @Override
    public void run(){
        try{
            boolean taco = true;
            while(taco){
                DMV.informationDeskReady.release();
                DMV.customerInfoDeskReady.acquire();
                System.out.println("Assigning " + currNumber + " to customer " + DMV.customerAtInfoDesk);
                currNumber++;
                DMV.informationDeskReady.acquire();
                taco = currNumber >= 20 ? false : true;
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
