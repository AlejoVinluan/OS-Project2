public class InformationDesk implements Runnable{
    private static int currNumber; 
    public InformationDesk(){currNumber = 0;}
    @Override
    public void run(){
        System.out.println("Information desk created.");
        // Information Desk is open until DMV closes the station
        while(true){
            // Information Desk is stating that they are ready to accept a customer
            DMV.informationDeskReady.release();
            try {
                DMV.customerInfoDeskReady.acquire();
            } catch (InterruptedException e) {
                System.out.println("Unable to acquire customer at Information Desk. " + e);
            }
            System.out.println("Assigning number " + currNumber + " to customer " + Thread.currentThread().getName());
        }
    }
}
