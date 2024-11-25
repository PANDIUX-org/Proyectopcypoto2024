package proyectopcypoto2024;

import java.util.concurrent.Semaphore;

public class ProductorSemaforo extends Thread {
    private DibujaTanke t;
    private Semaphore semaforo;

    public ProductorSemaforo(DibujaTanke tanque, Semaphore semaforo) {
        this.t = tanque;
        this.semaforo = semaforo;
    }

    public void run() {
        while (true) {
            try {
                semaforo.acquire(); // Espera su turno
                t.agregarAgua();    // Sección crítica: agrega agua
                semaforo.release(); // Libera el turno para el consumidor
                Thread.sleep(500);  // Tiempo de espera para simular producción
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}