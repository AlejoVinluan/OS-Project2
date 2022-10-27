public class Agent implements Runnable{
    private int id;

    public Agent(int id){
        this.id = id;
    }

    @Override
    public void run(){
        System.out.println("Agent " + this.id + " created.");
        try{
            // Determine which boolean controls this while(true) loop
            boolean running = (id == 0) ? DMV.agentZeroRunning : DMV.agentOneRunning;
            while(running){
                // Signal customers that the agent is ready to serve them
                DMV.agentReady.release();
                // Signal to customers which agent is ready (0 vs 1)
                DMV.agentSemaphore[id].release();
                // Wait until the customer is ready to complete the transaction with them
                DMV.customerAgentReady.acquire();
                // Agent waits for customer to approach
                try {
                    Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Agent selects the first customer in line
                Customer currCustomer = DMV.agentLine.remove();
                System.out.println("Agent " + id + " is serving customer " + currCustomer.customerId);

                System.out.println("Agent " + id + " asks customer " + currCustomer.getId() + " to take photo and eye exam.");
                // Give customer isntructions on how to complete the photo and eye exam.
                DMV.photoEyeExamInstruction.release();
                // Wait until the customer completes their photo and eye exam.
                DMV.photoEyeExamComplete.acquire();
                // Agent processes and creates license
                try {
                    Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Signal to the customers that their license is complete.
                DMV.licenseSemaphore.release();
                // Signal to the customer that the agent has completed their transaction.
                DMV.agentComplete.release();
            }
        } catch (InterruptedException e){
            System.out.println("Agent failed. " + e);
        }
    }
}