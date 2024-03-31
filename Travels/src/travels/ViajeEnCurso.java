package travels;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Date;
import java.io.Serializable;

public class ViajeEnCurso extends JPanel implements Serializable{
    private ImageIcon imagenVehiculo;
    private boolean primeraVezIniciado = false;
    private volatile int xPosition = 620; // Posición inicial
    private final String inicioRuta, finRuta, tipoVehiculo, distanciaTotal;
    private Double velocidad;
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
        
        setPreferredSize(new Dimension(800, 200));
        setBackground(Color.WHITE);
        setLayout(null);
        JLabel labelFinalRuta = new JLabel("fin: " + finRuta);
        JLabel labelDistancia = new JLabel("Distancia: " + distancia);
        this.add(labelFinalRuta);
        this.add(labelDistancia);
        labelFinalRuta.setBounds(5, 0, 100, 20);
        labelDistancia.setBounds(5, 22, 100, 20);
        JLabel labelinicio = new JLabel("Inicio: " + inicioRuta);
        this.add(labelinicio);
        labelinicio.setBounds(670, 0, 130, 20);
        JButton iniciarViajeButton = new JButton("Iniciar");        
        invertirImagen(); // Invierte la imagen
        iniciarViajeButton.addActionListener(e -> iniciarAnimacion());
        this.add(iniciarViajeButton);
        iniciarViajeButton.setBounds(670, 20, 70, 20);//x,y,w,h
    }
    

public void iniciarAnimacion() {
    if (!primeraVezIniciado) {
        // La primera vez que se inicia la animación, establece la velocidad
        velocidad = calcularVelocidad(distanciaTotal);
        primeraVezIniciado = true; // Marca que ya se inició por primera vez
    }
    if (!enMovimiento.getAndSet(true)) {
        invertirImagen(); // Invierte la imagen
        animacionDireccion *= -1; // Invierte la dirección de la animación
        
    }
Thread animacionThread = new Thread(() -> {
        while (animacionDireccion == -1 && xPosition > 110 || animacionDireccion == 1 && xPosition < getWidth() - 110 - imagenVehiculo.getIconWidth() / 10) {
            xPosition += animacionDireccion; // Ajusta este valor para cambiar la "velocidad" de desplazamiento de la imagen
            try {
                Thread.sleep(velocidad.longValue()); // Convierte velocidad a long
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Sale del hilo si se interrumpe
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
            xPosition = 80; // Si la dirección es hacia la izquierda, ajusta a la posición inicial
        } else {
            xPosition = 620; // Si la dirección es hacia la derecha, ajusta a la posición final
        }
    }

    g.drawImage(imagenVehiculo.getImage(), xPosition, yOffset, imageWidth, imageHeight, this);
}


    private double calcularVelocidad(String distancia) {
        double distanciaNumerica = Double.parseDouble(distancia.replaceAll("[^0-9.]", "")); // Asegúrate de remover correctamente los caracteres no numéricos
        // Ajusta la fórmula para calcular la velocidad en función de la distancia
        // Esta fórmula es un ejemplo y puede ser ajustada según necesites
        return Math.max(50, distanciaNumerica * 800 / 500 ); // Aumenta el denominador para disminuir la velocidad
    }
}