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
                System.out.println("AgentReady RELEASE");
                DMV.agentSemaphore[id].release();
                DMV.customerAgentReady.acquire();


                DMV.agentComplete.release();
                System.out.println("AgentComplete DONE2");
            }
        } catch (InterruptedException e){
            System.out.println("Agent failed. " + e);
        }
    }
}