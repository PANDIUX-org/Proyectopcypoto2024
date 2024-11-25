package proyectopcypoto2024;

import java.util.concurrent.CyclicBarrier;

public class ProductorBarreras extends Thread {
    private DibujaTanke panel;
    private CyclicBarrier barrera;

    public ProductorBarreras(DibujaTanke panel, CyclicBarrier barrera) {
        this.panel = panel;
        this.barrera = barrera;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Intentamos agregar agua al tanque si no está lleno
                synchronized (panel) {
                    if (!panel.isTanqueLleno()) {
                        panel.agregarAgua();
                        System.out.println("ProductorBarreras: Agua agregada.");
                    } else {
                        System.out.println("ProductorBarreras: Tanque lleno. Esperando.");
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