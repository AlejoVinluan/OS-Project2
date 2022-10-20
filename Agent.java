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
                DMV.agentAvailableSemaphore.release();
                DMV.agentSemaphore[id].release();
            }
        } catch (InterruptedException e){
            System.out.println("Agent failed. " + e);
        }


    }


}