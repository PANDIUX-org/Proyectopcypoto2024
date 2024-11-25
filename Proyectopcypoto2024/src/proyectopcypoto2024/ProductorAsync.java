package proyectopcypoto2024;

import java.util.Random;
public class ProductorAsync extends Thread{
    private DibujaTanke t;
    private Random random;
    ProductorAsync(DibujaTanke tanque){
        t = tanque;
        random = new Random();
    }
    public void run(){
        while(true){
            long epsilon = random.nextLong(5);
            try{
 
                        t.agregarAgua();
                        //seccion critica

                    sleep(random.nextInt(501) + epsilon);
            }catch(Exception e){}
        }
    }
}