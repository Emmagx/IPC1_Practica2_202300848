package travels;
import javax.swing.SwingUtilities;

public class Travels {

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new GUI();
    });
}

}

