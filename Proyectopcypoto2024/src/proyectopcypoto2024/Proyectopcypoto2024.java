package proyectopcypoto2024;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.CyclicBarrier;
import org.jfree.chart.ChartPanel;

public class Proyectopcypoto2024 extends JFrame {
    private DibujaTanke panelAsync;
    private DibujaTanke panelMutex;
    private DibujaTanke panelSemaforos;
    private DibujaTanke panelVC;
    private DibujaTanke panelMonitores;
    private DibujaTanke panelBarreras;
    private ProductorAsync productorA;
    private ConsumidorAsync consumidorA;
    private ProductorMutex productorM;
    private ConsumidorMutex consumidorM;
    private ProductorSemaforo productorS;
    private ConsumidorSemaforo consumidorS;
    private ProductorMonitores productorMo;
    private ConsumidorMonitores consumidorMo;
    private ProductorBarreras productorB;
    private ConsumidorBarreras consumidorB;
    private ProductorVC productorVC;
    private ConsumidorVC consumidorVC;
    private CyclicBarrier barrera;
    private ReentrantLock lock;
    private Condition condicion;
    private final Semaphore semaforo = new Semaphore(1);
    private Semaphore semaforoMonitores = new Semaphore(1); // Semáforo para Monitores

    Proyectopcypoto2024() {
        setTitle("Proyecto Programación Concurrente y Paralela Otoño 2024");
        setSize(1610, 870); // Ajustar el tamaño de la ventana
        inicioComponentes();
        
    }

    private void inicioComponentes() {
        lock = new ReentrantLock();
        condicion = lock.newCondition();
        barrera = new CyclicBarrier(2);
        lock = new ReentrantLock();
        condicion = lock.newCondition();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600); // Dividir la ventana en 600 px y el resto
        
        
        JPanel panelTanques = new JPanel();
        panelTanques.setLayout(new GridLayout(-1, 6, 2, 0)); // 3 filas, 2 columnas
        
        panelAsync = new DibujaTanke("Async");
        panelMutex = new DibujaTanke("Mutex");
        panelSemaforos = new DibujaTanke("Semaforo");
        panelVC = new DibujaTanke("VC");
        panelMonitores = new DibujaTanke("Monitores");
        panelBarreras = new DibujaTanke("Barreras");

        productorM = new ProductorMutex(panelMutex);
        consumidorM = new ConsumidorMutex(panelMutex);
        productorA = new ProductorAsync(panelAsync);
        consumidorA = new ConsumidorAsync(panelAsync);
        productorS = new ProductorSemaforo(panelSemaforos, semaforo);
        consumidorS = new ConsumidorSemaforo(panelSemaforos, semaforo);
        productorMo = new ProductorMonitores(panelMonitores, lock, condicion, semaforoMonitores);
        consumidorMo = new ConsumidorMonitores(panelMonitores, lock, condicion, semaforoMonitores);
        productorB = new ProductorBarreras(panelBarreras, barrera);
        consumidorB = new ConsumidorBarreras(panelBarreras, barrera);
        productorVC = new ProductorVC(panelVC, lock, condicion);
        consumidorVC = new ConsumidorVC(panelVC, lock, condicion);

        productorM.start();
        consumidorM.start();
        productorA.start();
        consumidorA.start();
        productorS.start();
        consumidorS.start();
        productorMo.start();
        consumidorMo.start();
        productorVC.start();
        consumidorVC.start();
        productorB.start();
        consumidorB.start();

        panelTanques.add(panelAsync);
        panelTanques.add(panelMutex);
        panelTanques.add(panelSemaforos);
        panelTanques.add(panelVC);
        panelTanques.add(panelMonitores);
        panelTanques.add(panelBarreras);
        
        // Panel derecho para las gráficas
        JPanel panelGraficas = new JPanel();
        panelGraficas.setLayout(new GridLayout(3, 2, -5, -5)); // Igual que los tanques
        panelGraficas.setPreferredSize(new Dimension(150, 50));


        // Crear gráficas para cada tanque
        panelGraficas.add(crearGrafica(panelAsync.getDatosGrafica(), "Async"));
        panelGraficas.add(crearGrafica(panelMutex.getDatosGrafica(), "Mutex"));
        panelGraficas.add(crearGrafica(panelSemaforos.getDatosGrafica(), "Semaforo"));
        panelGraficas.add(crearGrafica(panelVC.getDatosGrafica(), "VC"));
        panelGraficas.add(crearGrafica(panelMonitores.getDatosGrafica(), "Monitores"));
        panelGraficas.add(crearGrafica(panelBarreras.getDatosGrafica(), "Barreras"));

        // Añadir paneles al JSplitPane
        splitPane.setLeftComponent(new JScrollPane(panelTanques));
        splitPane.setRightComponent(new JScrollPane(panelGraficas));

        // Añadir JSplitPane al JFrame
        add(splitPane);
    }
    
    private ChartPanel crearGrafica(XYSeries datos, String titulo) {
    XYSeriesCollection dataset = new XYSeriesCollection(datos);
    JFreeChart chart = ChartFactory.createXYLineChart(
            titulo,
            "Tiempo",
            "Porcentaje",
            dataset,
            PlotOrientation.VERTICAL,
            false, // Sin leyenda
            true,
            false
    );
    return new ChartPanel(chart);
}
    
    public static void main(String[] args) {
        Proyectopcypoto2024 fr = new Proyectopcypoto2024();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }
}