package proyectopcypoto2024;

import static java.lang.Thread.sleep;
import java.util.Random;
public class ConsumidorAsync extends Thread{
    private DibujaTanke t;
    private Random random;
    ConsumidorAsync(DibujaTanke tanque){
        t = tanque;
        random = new Random();
    }
    public void run(){
 
        while(true){
            long epsilon = random.nextLong(5);
            try{
 
                    t.quitarAgua();
 
                sleep(random.nextInt(501) + epsilon);
            }catch(Exception e){}
        }
    }
}