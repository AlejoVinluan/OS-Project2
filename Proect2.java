class Project2 {

    static Customer[] customerArr;
    public static void main(String[] args){
        // Creates Information Desk thread
        InformationDesk informationDesk = new InformationDesk();

        // Creates Announcer Thread
        Announcer announcer = new Announcer();

        // Creates Agents thread
        //  2 Agents required
        Agent agentZero = new Agent(0);
        Agent agentOne = new Agent(1);

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

        /**
         * Start all threads, in order of Information Desk, Announcer, Agents.
         *  Customers started after all 3 above started, and starting DMV process.
         */
        informationDesk.start();
        announcer.start();
        agentZero.start();
        agentOne.start();

        for(int i = 1; i <= 20; i++){
            customerArr[i].start();
        }

    }
    public static class InformationDesk extends Thread{
        public InformationDesk(){}
        @Override
        public void run(){
            System.out.println("Information desk created.");
        }
    }

    public static class Announcer extends Thread{
        public Announcer(){}
        @Override
        public void run(){
            System.out.println("Announcer created.");
        }
    }

    public static class Agent extends Thread{
        private int id;
        public Agent(int id){
            this.id = id;
        }
        @Override
        public void run(){
            System.out.println("Agent " + this.id + " created.");
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
        @Override
        public void run(){
            /*
             * Once starting customer thread, makes thread sleep for 1000ms,
             *  gives the Information Desk, Announcer, and Agent threads time to start.
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Customer " + customerId + " created, enters DMV.");
        }
    }
}