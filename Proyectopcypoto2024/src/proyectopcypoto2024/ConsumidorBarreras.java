package proyectopcypoto2024;

import java.util.concurrent.CyclicBarrier;

public class ConsumidorBarreras extends Thread {
    private DibujaTanke panel;
    private CyclicBarrier barrera;

    public ConsumidorBarreras(DibujaTanke panel, CyclicBarrier barrera) {
        this.panel = panel;
        this.barrera = barrera;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Intentamos quitar agua del tanque si no está vacío
                synchronized (panel) {
                    if (!panel.getAgua().isEmpty()) {
                        panel.quitarAgua();
                        System.out.println("ConsumidorBarreras: Agua retirada.");
                    } else {
                        System.out.println("ConsumidorBarreras: Tanque vacío. Esperando.");
                    }
                }
                
                // Esperamos a que todos los hilos lleguen a la barrera
                barrera.await();
                
                // Pequeña pausa para observar el comportamiento
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}