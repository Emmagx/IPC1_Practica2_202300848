package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class ViajeEnCurso extends JPanel {
    private ImageIcon imagenVehiculo;
    private int xPosition = 600;
    private final String inicioRuta;
    private final String finRuta;
    private final String tipoVehiculo;
    private int velocidad; // Añadido para controlar la velocidad de la animación
    private boolean enMovimiento = true;
    private final String distanciaTotal;

    public ViajeEnCurso(Vehiculo vehiculo, String inicioRuta, String finRuta, String distancia) {
        this.imagenVehiculo = vehiculo.getImagen();
        this.inicioRuta = inicioRuta;
        this.finRuta = finRuta;
        this.tipoVehiculo = vehiculo.getTipo();
        this.distanciaTotal = distancia; // Distancia total del viaje
        this.velocidad = calcularVelocidad(distancia); // Calcula la velocidad basada en la distancia
        setPreferredSize(new Dimension(800, 100));
        setBackground(Color.WHITE);
        JLabel labelFinalRuta = new JLabel("Punto final de la ruta: " + inicioRuta);
        JLabel labelDistancia = new JLabel("Distancia de la ruta: " + distancia);
        add(labelFinalRuta);
        add(labelDistancia);
        labelFinalRuta.setLocation(10, 10);
        JButton iniciarViajeButton = new JButton("Iniciar Viaje");
        iniciarViajeButton.addActionListener(e -> iniciarAnimacion());
        add(iniciarViajeButton);
    }
    

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        // Escala la imagen del vehículo y la dibuja repetida tres veces
        if (imagenVehiculo != null && enMovimiento) {
            int imageWidth = imagenVehiculo.getIconWidth() / 10; // Escala la imagen a un tercio de su tamaño
            int imageHeight = imagenVehiculo.getIconHeight() / 10;
            int yOffset = getHeight() - 70; // Posición vertical para la imagen

            g.drawImage(imagenVehiculo.getImage(),
                        xPosition,
                        yOffset,
                        imageWidth,
                        imageHeight,
                        this);
        }
    }
    public void iniciarAnimacion() {
        Timer timer = new Timer(velocidad, e -> {
            xPosition--;
            if (xPosition > getWidth() + 70) {
                enMovimiento = false;
                ((Timer) e.getSource()).stop();
            }
            repaint();
        });
        timer.start();
    }

    private int calcularVelocidad(String distancia) {
    // Convertir la distancia a un valor numérico y calcular la velocidad
    // Un ejemplo simple podría ser: cuanto mayor es la distancia, más lento va el vehículo
    int distanciaNumerica = Integer.parseInt(distancia.replaceAll("[^0-9]", ""));
    return Math.max(10, 1000 / distanciaNumerica); 
}

    
}
