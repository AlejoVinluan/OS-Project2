public class Agent implements Runnable{
    private int id;
    public Agent(int id){
        this.id = id;
    }
    @Override
    public void run(){
        System.out.println("Agent " + this.id + " created.");
    }
}