package proyectopcypoto2024;

import java.awt.geom.Rectangle2D;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
 
public class ConsumidorMutex extends Thread{
    private DibujaTanke t;
    private ReentrantLock mutex;
    private Random random;
    ConsumidorMutex(DibujaTanke tanque){
        t = tanque;
        mutex = new ReentrantLock();
        random = new Random();
    }
    public void run(){
        while(true){
            long epsilon = random.nextLong(500);
            try{
                if(mutex.tryLock()){
                    mutex.lock();
                    t.quitarAgua();
                }
                mutex.unlock();
                sleep(random.nextInt(501) + epsilon);
            }catch(Exception e){}
        }
    }
}