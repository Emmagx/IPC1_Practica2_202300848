package travels;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Date;
public class ViajeEnCurso extends JPanel {
    private ImageIcon imagenVehiculo;
    private volatile int xPosition = 600; // Posición inicial
    private final String inicioRuta, finRuta, tipoVehiculo, distanciaTotal;
    private int velocidad;
    private AtomicBoolean enMovimiento = new AtomicBoolean(true);
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm:ss");
    private int animacionDireccion = -1;
    
    public ViajeEnCurso(Vehiculo vehiculo, String inicioRuta, String finRuta, String distancia) {
        this.imagenVehiculo = vehiculo.getImagen();
        this.inicioRuta = inicioRuta;
        this.finRuta = finRuta;
        this.tipoVehiculo = vehiculo.getTipo();
        this.distanciaTotal = distancia;
        this.velocidad = calcularVelocidad(distancia);
        
        setPreferredSize(new Dimension(800, 100));
        setBackground(Color.WHITE);
        
        JLabel labelFinalRuta = new JLabel("Punto final de la ruta: " + inicioRuta);
        JLabel labelDistancia = new JLabel("Distancia de la ruta: " + distancia);
        this.add(labelFinalRuta);
        this.add(labelDistancia);
        
        JButton iniciarViajeButton = new JButton("Iniciar Viaje");
        invertirImagen(); // Invierte la imagen
        iniciarViajeButton.addActionListener(e -> iniciarAnimacion());
        this.add(iniciarViajeButton);
    }

public void iniciarAnimacion() {
    
    if (!enMovimiento.getAndSet(true)) {
        invertirImagen(); // Invierte la imagen
        animacionDireccion *= -1; // Invierte la dirección de la animación
        
    }
    Thread animacionThread = new Thread(() -> {
        while (animacionDireccion == -1 && xPosition > 30 || animacionDireccion == 1 && xPosition < getWidth() - 30 - imagenVehiculo.getIconWidth() / 10) {
            xPosition += animacionDireccion;
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Termina el hilo si es interrumpido durante el sueño
            }
            repaint();
        }
        enMovimiento.set(false);
        // No es necesario ajustar xPosition aquí porque ya está en la posición correcta después del bucle.
        repaint(); // Vuelve a dibujar la imagen en su posición final
    }, "Animacion-Viaje-" + inicioRuta + "-a-" + finRuta);
    animacionThread.start();
}
    private void invertirImagen() {
        int width = imagenVehiculo.getIconWidth();
        int height = imagenVehiculo.getIconHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Dibuja la imagen original en la imagen del buffer con una transformación que la invierte
        g2d.drawImage(imagenVehiculo.getImage(), 0, 0, width, height, width, 0, 0, height, null);
        g2d.dispose();

        // Reemplaza la imagen original por la imagen del buffer
        imagenVehiculo = new ImageIcon(bufferedImage);
  }
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int imageWidth = imagenVehiculo.getIconWidth() / 10;
    int imageHeight = imagenVehiculo.getIconHeight() / 10;
    int yOffset = 30; // Ajusta si es necesario

    // Dibuja la imagen estática en la última posición si la animación está detenida
    if (!enMovimiento.get()) {
        if (animacionDireccion == -1) {
            xPosition = 30; // Si la dirección es hacia la izquierda, ajusta a la posición inicial
        } else {
            xPosition = getWidth() - 30 - imageWidth; // Si la dirección es hacia la derecha, ajusta a la posición final
        }
    }

    g.drawImage(imagenVehiculo.getImage(), xPosition, yOffset, imageWidth, imageHeight, this);
}


    private int calcularVelocidad(String distancia) {
        int distanciaNumerica = Integer.parseInt(distancia.replaceAll("[^0-9]", ""));
//        return Math.max(100, distanciaNumerica/100000 ); velocidad normal
          return Math.max(10, distanciaNumerica/100000 ); //velocidad pruebas
    }
}
