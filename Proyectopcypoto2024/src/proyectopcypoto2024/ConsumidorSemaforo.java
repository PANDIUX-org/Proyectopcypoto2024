package proyectopcypoto2024;

import static java.lang.Thread.sleep;
import java.util.Random;

import java.util.concurrent.Semaphore;

public class ConsumidorSemaforo extends Thread {
    private DibujaTanke t;
    private Semaphore semaforo;

    public ConsumidorSemaforo(DibujaTanke tanque, Semaphore semaforo) {
        this.t = tanque;
        this.semaforo = semaforo;
    }

    public void run() {
        while (true) {
            try {
                semaforo.acquire(); // Espera su turno
                t.quitarAgua();     // Sección crítica: quita agua
                semaforo.release(); // Libera el turno para el productor
                Thread.sleep(500);  // Tiempo de espera para simular consumo
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}