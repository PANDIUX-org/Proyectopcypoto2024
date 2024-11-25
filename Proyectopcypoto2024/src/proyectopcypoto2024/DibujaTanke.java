package proyectopcypoto2024;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYSeries;

public class DibujaTanke extends JPanel {
    JLabel etiqueta;
    private JLabel porcentaje;
    private List<Rectangle2D> agua;
    private int y = 580;
    private final int capacidadMaxima = 20;
    private String nombrePanel;
    private XYSeries datosGrafica;
    private int tiempo; // Para representar el tiempo en las gráficas

    DibujaTanke(String nombrePanel) {
        setBackground(Color.white);
        etiqueta = new JLabel(nombrePanel);
        porcentaje = new JLabel("Porcentaje: 0%");
        agua = new ArrayList<>();
        setLayout(null);

        etiqueta.setBounds(10, 150, 100, 25); // Mover etiqueta más a la izquierda
        porcentaje.setBounds(10, 600, 100, 25); // Mover porcentaje más a la izquierda

        add(etiqueta);
        add(porcentaje);
        
        datosGrafica = new XYSeries(nombrePanel);
        datosGrafica.add(0, 0); // Iniciar en tiempo 0 y porcentaje 0
        tiempo = 0;
    }
    
    public XYSeries getDatosGrafica() {
        return datosGrafica;
    }

    public synchronized void agregarAgua() {
        if (agua.size() < capacidadMaxima) {
            agua.add(new Rectangle2D.Double(10, y, 40, 20)); // Hacer agua más estrecha y mover más a la izquierda
            y -= 20;
            porcentaje.setText(agua.size() * 5 + "%"); // texto del porcentaje
            tiempo++;
            datosGrafica.add(tiempo, agua.size() * 5); // Actualizar gráfica
            repaint();
            System.out.println("Agua agregada");
        }
    }

    public synchronized void quitarAgua() {
        if (!agua.isEmpty()) {
            agua.remove(agua.size() - 1);
            y += 20;
            porcentaje.setText(agua.size() * 5 + "%"); // texto del porcentaje
            tiempo++;
            datosGrafica.add(tiempo, agua.size() * 5); // Actualizar gráfica
            repaint();
            System.out.println("Agua quitada");
        }
    }

    public synchronized boolean isTanqueLleno() {
        return agua.size() >= capacidadMaxima;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.draw(new Rectangle.Double(10, 200, 40, 400)); // Hacer tanque más estrecho y mover más a la izquierda
        g2.setColor(Color.BLUE);

        synchronized (this) { // Sincronizar el acceso a la lista de agua
            for (Rectangle2D rect : agua) {
                g2.fill(rect);
            }
        }
    }

    public synchronized List<Rectangle2D> getAgua() {
        return new ArrayList<>(agua); // Devolver una copia para evitar problemas de concurrencia
    }

    public synchronized void setAgua(List<Rectangle2D> agua) {
        this.agua = new ArrayList<>(agua); // Crear una copia para evitar problemas de concurrencia
    }
}