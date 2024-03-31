package travels;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;

public class Travels {
    private static GUI gui;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EstadoAplicacion estadoCargado = cargarEstado("estadoAplicacion.bin");
            gui = new GUI(estadoCargado);
            setGUI(gui); // Nuevo método para establecer la referencia de GUI
        });
    }

    public static void setGUI(GUI gui) {
        Travels.gui = gui;
    }
    public static void agregarNuevoViaje(RegistroCSV nuevoRegistro, ViajeRealizado.Viaje nuevoViaje) {
        // Suponiendo que GUI tiene acceso al estado de la aplicación y puede actualizarlo
        gui.agregarNuevoViaje(nuevoRegistro, nuevoViaje);
    }

    public static void guardarEstado(EstadoAplicacion estado, String archivoDestino) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoDestino))) {
            out.writeObject(estado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EstadoAplicacion cargarEstado(String archivoOrigen) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoOrigen))) {
            return (EstadoAplicacion) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

