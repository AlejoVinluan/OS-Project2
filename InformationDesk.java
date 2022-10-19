public class InformationDesk implements Runnable{
    // Current number is used to assign number to Customer Thread
    private static int currNumber; 
    public InformationDesk(){currNumber = 0;}

    @Override
    public void run(){
        System.out.println("Information desk created.");

        // Information Desk is open until DMV closes the station
        while(true){
            // Information Desk is stating that they are ready to accept a customer
            DMV.informationDeskReady.release();

            // Attempt to receive a customer
            try {
                DMV.customerInfoDeskReady.acquire();
            } catch (InterruptedException e) {
                System.out.println("Unable to acquire customer at Information Desk. " + e);
            }
            // Find ID of customer at Information Desk
            int custId = Math.toIntExact(Thread.currentThread().getId());
            // Assign the current number to the current thread
            System.out.println("Assigning number " + currNumber + " to customer " + custId);
            // Store this number in the DMV's customerNumber array.
            //  array[Customer ID] = Current Number
            DMV.customerNumber[custId] = currNumber;
            // Increment number so next customer will receive the next subsequent number
            currNumber++;
            
            
        }
    }
}
