public class Agent implements Runnable{
    private int id;

    public Agent(int id){
        this.id = id;
    }

    @Override
    public void run(){
        System.out.println("Agent " + this.id + " created.");
        try{
            while(true){
                DMV.agentReady.release();
                DMV.agentSemaphore[id].release();
                DMV.customerAgentReady.acquire();
                // Agent waits for customer to approach
                try {
                    Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Customer currCustomer = DMV.agentLine.remove();
                System.out.println("Agent " + id + " is serving customer " + currCustomer.customerId);

                System.out.println("Agent " + id + " asks customer " + currCustomer.getId() + " to take photo and eye exam.");
                DMV.photoEyeExamInstruction.release();
                DMV.photoEyeExamComplete.acquire();
                // Agent processes and creates license
                try {
                    Thread.sleep((long) (Math.random() * (2000 - 1000 + 1) + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DMV.licenseSemaphore.release();
                DMV.agentComplete.release();
            }
        } catch (InterruptedException e){
            System.out.println("Agent failed. " + e);
        }
    }
}