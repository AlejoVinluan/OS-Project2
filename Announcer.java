public class Announcer extends Thread{
    public Announcer(){}
    @Override
    public void run(){
        System.out.println("Announcer created.");
    }
}