package proyectopcypoto2024;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class ProductorVC extends Thread {
    private DibujaTanke panel;
    private ReentrantLock lock;
    private Condition condicion;
    private Random random;

    public ProductorVC(DibujaTanke panel, ReentrantLock lock, Condition condicion) {
        this.panel = panel;
        this.lock = lock;
        this.condicion = condicion;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                try {
                    // Esperar mientras el tanque este lleno
                    while (panel.isTanqueLleno()) {
                        condicion.await();
                    }
                    // Producir agua
                    panel.agregarAgua();
                    // Señalar que hay agua disponible
                    condicion.signalAll();
                } finally {
                    lock.unlock();
                }
                // Tiempo de espera controlado
                Thread.sleep(random.nextInt(300) + 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}