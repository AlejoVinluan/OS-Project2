/*
 * Information Desk has 1 role - assign number sequentially to each customer
 *  Each number is stored in DMV.customerNumber
 */

public class InformationDesk implements Runnable{
    // Current number is used to assign number to Customer Thread
    private static int currNumber;
    public InformationDesk(){currNumber = 1;}

    @Override
    public void run(){
        try{
            while(DMV.infoDeskRunning){
                // Information Desk announces they're ready
                DMV.informationDeskReady.release();
                // Information Desk takes next customer in line
                DMV.customerInfoDeskReady.acquire();
                
                // Choose the first customer located in the information desk
                Customer currCustomer = DMV.infoDeskLine.remove();
                System.out.println("Customer " + currCustomer.getId() + " gets number " + currNumber + ", enters waiting room");
                
                // Assigns a number to the customer, storing in "customerNumber" array
                currNumber++;
                // Inform customer that the transaction has completed
                DMV.infoDeskComplete.release();
            }
        } catch (InterruptedException e){
            System.out.println("Information desk failed. " + e);
        }
    }
}
