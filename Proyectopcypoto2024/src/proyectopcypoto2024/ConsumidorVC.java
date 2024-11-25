package proyectopcypoto2024;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class ConsumidorVC extends Thread {
    private DibujaTanke panel;
    private ReentrantLock lock;
    private Condition condicion;
    private Random random;

    public ConsumidorVC(DibujaTanke panel, ReentrantLock lock, Condition condicion) {
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
                    // Esperar mientras el tanque esté vacío
                    while (panel.getAgua().isEmpty()) {
                        condicion.await();
                    }
                    // Consumir agua
                    panel.quitarAgua();
                    // Señalar que hay espacio disponible
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