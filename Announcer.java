public class Announcer implements Runnable{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");
    }
}