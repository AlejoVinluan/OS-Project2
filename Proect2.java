class Project2 {

    static Customer[] customerArr;
    public static void main(String[] args){
        // Creates Information Desk thread


        /*
         * Create 20 Customer threads, storing their ID in customerArray
         *  Customers in customerArr are stored from 1-20
         */
        customerArr = new Customer[21];
        for(int i = 1; i <= 20; i++){
            // Creates customer object, storing Id "i" for customer
            Customer cust = new Customer(i);
            // Stores customer with ID in customer array
            customerArr[i] = cust;
        }

    }
    public static class InformationDesk extends Thread{
        public InformationDesk(){

        }
        @Override
        public void run(){
            System.out.println("Information desk created.");
        }
    }
    public static class Customer extends Thread {
        private int customerId;
        /*
         * Stores value of customer id in thread
         * @param custNum - customer Id passed in from Customer object
         */
        public Customer (int custNum){
            this.customerId = custNum;
        }
        // run() occurs when Thread.start() method is used in main
        @Override
        public void run(){
            System.out.println("I am customer " + customerId);
        }
    }
}