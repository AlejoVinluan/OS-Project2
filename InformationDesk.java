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
            System.out.println(Thread.currentThread().getName());
            // Attempt to receive a customer
            /*
            int custId = Math.toIntExact(Thread.currentThread().getId());
            System.out.println(custId);
            */
            // Assign the current number to the current thread
            //System.out.println("Assigning number " + currNumber + " to customer " + custId);

            // Store this number in the DMV's customerNumber array.
            //  array[Customer ID] = Current Number
            //DMV.customerNumber[custId] = currNumber;

            // Increment number so next customer will receive the next subsequent number
            currNumber++;

            // Informs customer that the transaction has completed
            DMV.informationDeskDone.release();
            /* 
            try {
                DMV.customerInfoDeskReady.acquire();
                // Find ID of customer at Information Desk
                int custId = Math.toIntExact(Thread.currentThread().getId());
                System.out.println(custId);
                // Assign the current number to the current thread
                //System.out.println("Assigning number " + currNumber + " to customer " + custId);

                // Store this number in the DMV's customerNumber array.
                //  array[Customer ID] = Current Number
                DMV.customerNumber[custId] = currNumber;

                // Increment number so next customer will receive the next subsequent number
                currNumber++;

                // Informs customer that the transaction has completed
                DMV.informationDeskDone.release();
            }  catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        }
    }
}
