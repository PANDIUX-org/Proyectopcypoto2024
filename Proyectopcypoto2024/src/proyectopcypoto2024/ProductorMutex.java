package proyectopcypoto2024;

import java.util.concurrent.locks.ReentrantLock;
 
import java.util.Random;
 
public class ProductorMutex extends Thread{
    private final DibujaTanke t;
    private ReentrantLock mutex;
    private Random random;
    ProductorMutex(DibujaTanke tanque){
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
                        t.agregarAgua();
                        //seccion critica
                    }
                    mutex.unlock();
                    sleep(random.nextInt(501) + epsilon);
            }catch(Exception e){}
        }
    }
}