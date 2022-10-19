public class InformationDesk implements Runnable{
    // Current number is used to assign number to Customer Thread
    private static int currNumber; 
    public InformationDesk(){currNumber = 0;}

    @Override
    public void run(){
        System.out.println("Information desk created.");
        boolean test_bool = true;
        int test = 0;
        // Information Desk is open until DMV closes the station
        while(test_bool){
            System.out.println("info desk doing stuff");
            if(test == 20){
                return;
            }
            test += 1;
        }
    }
}
