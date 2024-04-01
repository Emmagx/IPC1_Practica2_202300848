package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ViajeEnCurso extends JPanel implements Serializable {
    Date fechaHoraInicio = new Date();
    Date fechaHoraFin = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private HistorialViajesPanel historialPanel;
    private ViajeRealizado.Viaje viaje;
    private ImageIcon imagenVehiculo;
    private JLabel lblCombustible;
    private double gasolinaConsumida = 0;
    private volatile int xPosition = 620; // Posición inicial
    private final String inicioRuta, finRuta, tipoVehiculo, distanciaTotal;
    private Double velocidad;
    private AtomicBoolean enMovimiento = new AtomicBoolean(false);
    private int animacionDireccion = -1; // Define la dirección inicial de la animación para ir hacia la izquierda
    private JButton btnRecargarCombustible;
    private Vehiculo vehiculo;
    private boolean primeraVezIniciado = false;

    
    public ViajeEnCurso(Vehiculo vehiculo, String inicioRuta, String finRuta, String distancia,HistorialViajesPanel historialPanel) {
        this.vehiculo = vehiculo;
        this.imagenVehiculo = vehiculo.getImagen();
        this.inicioRuta = inicioRuta;
        this.finRuta = finRuta;
        this.tipoVehiculo = vehiculo.getTipo();
        this.distanciaTotal = distancia;
        this.velocidad = calcularVelocidad(distancia);
        this.historialPanel = historialPanel;
        configurarPanel();
    }

    private void configurarPanel() {
        setPreferredSize(new Dimension(800, 200));
        setBackground(Color.WHITE);
        setLayout(null);

        agregarComponentes();
    }

    private void agregarComponentes() {
        JLabel labelFinalRuta = new JLabel("Fin: " + finRuta);
        JLabel labelDistancia = new JLabel("Distancia: " + distanciaTotal);
        JLabel labelInicio = new JLabel("Inicio: " + inicioRuta);
        invertirImagen();
        labelFinalRuta.setBounds(5, 0, 100, 20);
        labelDistancia.setBounds(5, 22, 100, 20);
        labelInicio.setBounds(670, 0, 130, 20);

        add(labelFinalRuta);
        add(labelDistancia);
        add(labelInicio);

        JButton iniciarViajeButton = new JButton("Iniciar");
        iniciarViajeButton.setBounds(670, 20, 70, 20);
        iniciarViajeButton.addActionListener(e -> iniciarAnimacion());
        add(iniciarViajeButton);

        lblCombustible = new JLabel(String.format("%.2f", vehiculo.getCombustibleActual()) + " g");
        
        add(lblCombustible);

        btnRecargarCombustible = new JButton("Recargar");
        
        btnRecargarCombustible.addActionListener(e -> recargarCombustible());
        btnRecargarCombustible.setVisible(false);
        add(btnRecargarCombustible);
    }

    private void iniciarAnimacion() {
        if (!enMovimiento.get() && primeraVezIniciado) {
            recargarCombustible();
            animacionDireccion *= -1; // Invierte la dirección de la animación
            invertirImagen(); // Invierte la imagen
            // Ajusta la posición inicial basada en la nueva dirección
            xPosition = (animacionDireccion == -1) ? getWidth() - 110 - imagenVehiculo.getIconWidth() / 10 : 110;
    }
        if (enMovimiento.compareAndSet(false, true)) {
            double distanciaTotalNumerica = Double.parseDouble(distanciaTotal.replaceAll("[^0-9.]", ""));
            double combustibleNecesarioTotal = distanciaTotalNumerica * vehiculo.getGastoCombustible();
            double framesEstimados = (670);
            double combustibleConsumidoPorFrame = (combustibleNecesarioTotal / framesEstimados)*12;
            primeraVezIniciado = true;
            ejecutarAnimacion(combustibleConsumidoPorFrame);            
        }
    }

    private void recargarCombustible() {
        vehiculo.recargarCombustible();
//        lblCombustible.setText("Combustible: " + String.format("%.2f", vehiculo.getCombustibleActual()) + " galones");
        if (!enMovimiento.get()) {
            primeraVezIniciado = false;
            iniciarAnimacion();
        }
        btnRecargarCombustible.setVisible(false);
    }

private void ejecutarAnimacion(double pixelsPerFrame) {
    Thread animacionThread = new Thread(() -> {
        while (animacionDireccion == -1 && xPosition > 110 || animacionDireccion == 1 && xPosition < getWidth() - 110 - imagenVehiculo.getIconWidth() / 10) {
            vehiculo.consumirCombustible(pixelsPerFrame); // Consumimos combustible por frame
            gasolinaConsumida += pixelsPerFrame/10; // Acumulas el consumo de gasolina
            xPosition += animacionDireccion; // Movemos la imagen según la dirección
            lblCombustible.setBounds(xPosition+50, 20, 200, 20);
            btnRecargarCombustible.setBounds(xPosition+50, 60, 100, 20);
            SwingUtilities.invokeLater(() -> {
                lblCombustible.setText(String.format("%.2f", vehiculo.getCombustibleActual()) + " g");
                repaint(); // Repintamos para actualizar la posición de la imagen
            });

            if (vehiculo.getCombustibleActual() <= 0) {
                enMovimiento.set(false); // Detenemos la animación si nos quedamos sin combustible
                SwingUtilities.invokeLater(() -> btnRecargarCombustible.setVisible(true)); // Mostramos el botón para recargar
                break;
            }

            try {
                Thread.sleep(velocidad.longValue());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                enMovimiento.set(false);
            }
        }

        // Registro del viaje una vez completado el recorrido
        fechaHoraFin = new Date(); // Marcamos el fin del viaje
        registrarViajeFinalizado(gasolinaConsumida);

        enMovimiento.set(false); // Aseguramos que la animación se marque como detenida al final
    }, "Animacion-Viaje");
    animacionThread.start();
}
public void registrarViajeFinalizado(double gasolinaConsumidaFinal) {
    // Creamos el objeto Viaje
    System.out.println("Añadiendo viaje a la tabla");
    ViajeRealizado.Viaje nuevoViaje = new ViajeRealizado.Viaje(
        inicioRuta,
        finRuta,
        distanciaTotal,
        vehiculo,
        fechaHoraInicio,
        fechaHoraFin,
        gasolinaConsumidaFinal
    );

    System.out.println("Agregando viaje a la lista");
    historialPanel.agregarViajeATabla(nuevoViaje); // ¡Esta es la clave!

    // Si hay más actualizaciones que hacer, asegúrate de llamar a historialPanel.actualizarTablaConViajes(...)
}
    
    private void invertirImagen() {
        BufferedImage bufferedImage = new BufferedImage(imagenVehiculo.getIconWidth(), imagenVehiculo.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(imagenVehiculo.getImage(), 0, 0, imagenVehiculo.getIconWidth(), imagenVehiculo.getIconHeight(), imagenVehiculo.getIconWidth(), 0, 0, imagenVehiculo.getIconHeight(), null);
        g2d.dispose();
        imagenVehiculo = new ImageIcon(bufferedImage);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenVehiculo.getImage(), xPosition, 30, imagenVehiculo.getIconWidth() / 10, imagenVehiculo.getIconHeight() / 10, this);
    }

    private double calcularVelocidad(String distancia) {
        double distanciaNumerica = Double.parseDouble(distancia.replaceAll("[^\\d.]", ""));
        // Esta fórmula calcula la velocidad basada en la distancia total. Puedes ajustarla según sea necesario.
        return Math.max(50, 1000 / distanciaNumerica);
    }
}
