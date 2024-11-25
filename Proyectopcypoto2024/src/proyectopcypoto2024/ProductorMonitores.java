package proyectopcypoto2024;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ProductorMonitores extends Thread {
    private DibujaTanke t;
    private Random random;
    private Lock lock;
    private Condition condition;
    private Semaphore semaforoMonitores;

    public ProductorMonitores(DibujaTanke tanque, Lock lock, Condition condition, Semaphore semaforoMonitores) {
        t = tanque;
        this.lock = lock;
        this.condition = condition;
        this.semaforoMonitores = semaforoMonitores;
        random = new Random();
    }

    public void run() {
        while (true) {
            long epsilon = random.nextLong(5);
            try {
                semaforoMonitores.acquire(); // Espera el semáforo
                synchronized (t) {
                    while (t.isTanqueLleno()) {
                        t.wait();
                    }
                    t.agregarAgua(); 
                    t.notifyAll();
                }
                semaforoMonitores.release(); // Libera el semáforo
                sleep(random.nextInt(500) + epsilon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}