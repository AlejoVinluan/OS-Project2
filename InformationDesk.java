import java.util.Arrays;

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
            boolean infoDeskOpen = true;
            while(infoDeskOpen){
                // Information Desk announces they're ready
                DMV.informationDeskReady.release();
                // Information Desk takes next customer in line
                DMV.customerInfoDeskReady.acquire();

                Customer curr = DMV.infoDeskLine.remove();
                System.out.println(curr.getId() + " goes to information desk.");
                
                // Assigns a number to the customer, storing in "customerNumber" array
                System.out.println("Assigning " + currNumber + " to customer " + DMV.customerAtInfoDesk);
                DMV.customerNumber[DMV.customerAtInfoDesk] = currNumber;
                // Increment the current number for the next customer
                DMV.customerNumber[currNumber] = DMV.customerAtInfoDesk;
                currNumber++;

                // Inform customer that the transaction has completed
                DMV.infoDeskComplete.release();
                if(currNumber >= 21){
                    System.out.println(Arrays.toString(DMV.customerNumber));
                    System.exit(0);
                }
            }
        } catch (InterruptedException e){
            System.out.println("Information desk failed. " + e);
        }
    }
}
