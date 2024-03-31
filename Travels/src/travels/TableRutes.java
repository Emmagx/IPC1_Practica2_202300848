package travels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableRutes implements Serializable{
    JTable rutas;
    DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID", "INICIO", "FIN", "Distancia"}, 0);
    ArrayList<RegistroCSV> registros = new ArrayList<>();
    private static File selectedFile;
    public ArrayList<String> inicios = new ArrayList<>();
    public ArrayList<String> finals = new ArrayList<>();

    public void setTripsPanel(TripsPanel tripsPanel) {
    }


    public class SubirCSV extends JPanel implements ActionListener  {
        
        JButton button = new JButton("Subir Archivo (CSV)");
            
            
            JButton editButton = new JButton("Editar Distancia");
            JButton addButton = new JButton("Agregar Ruta");
            JButton deleteButton = new JButton("Eliminar Ruta");

            public SubirCSV() {

                this.setLayout(new BorderLayout(10, 10)); // Usa BorderLayout con espacios
                button.addActionListener(this);
                
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para el botón
                buttonPanel.add(button);

                this.add(buttonPanel, BorderLayout.NORTH); // Añade el panel del botón en la parte superior

                // Crea el modelo de la tabla con las columnas especificadas
                
                rutas = new JTable(modeloTabla);

                // Personaliza la fuente de la tabla y el encabezado
                rutas.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Fuente de la tabla
                rutas.setRowHeight(20); // Altura de las filas

                JTableHeader header = rutas.getTableHeader(); // Encabezado de la tabla
                header.setBackground(Color.DARK_GRAY); // Color de fondo
                header.setForeground(Color.WHITE); // Color del texto
                header.setFont(new Font("SansSerif", Font.BOLD, 16)); // Fuente del encabezado

                JScrollPane scrollPane = new JScrollPane(rutas); // Añade la tabla a un JScrollPane
                this.add(scrollPane, BorderLayout.CENTER); // Añade el JScrollPane en el centro para que la tabla ocupe todo el espacio disponible


                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarDialogoEdicion();
                    }
                });
                buttonPanel.add(editButton);

                addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarDialogoAgregar();
                    }
                });
                buttonPanel.add(addButton);


                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarDialogoEliminar();
                    }
                });
                buttonPanel.add(deleteButton);

            }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == button) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setCurrentDirectory(new File("."));
                            int response = fileChooser.showOpenDialog(this); // 'this' se refiere al JPanel
                            if (response == JFileChooser.APPROVE_OPTION) {
                                selectedFile = fileChooser.getSelectedFile();
                                leerArchivoSeleccionado();

                            } else {
                                System.out.println("Selección de archivo cancelada o ventana cerrada.");
                            }
                        }


                    }

        private void leerArchivoSeleccionado() {
            registros.clear(); // Limpia la lista antes de cargar nuevos datos
    if (selectedFile == null) {
        System.out.println("No hay archivo seleccionado.");
        return; // Si no hay archivo seleccionado, salimos de la función.
    }
    String ruta = selectedFile.getAbsolutePath();
    try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
        String line;
        DefaultTableModel modelo = (DefaultTableModel) rutas.getModel();
        modelo.setRowCount(0); // Limpia el modelo para nuevos datos

        // Ignorar la primera fila del documento (nombres de columnas)
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] row = line.split(","); // Asumiendo que tu CSV usa comas como separador
            if (row.length == 4) { // Asegúrate de que cada fila tenga 4 columnas
                RegistroCSV registro = new RegistroCSV(row[0], row[1], row[2], row[3]);
                registros.add(registro);
//                inicios.add(registro.getInicio()); // Añade el inicio a la lista de inicios
//                finals.add(registro.getFin()); // Añade el fin a la lista de finales
            }
        }

        
        // Ahora que la lista registros está completa, llenamos la tabla con sus datos
        llenarTablaDesdeRegistros();

        
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Después de cargar los datos, ajustamos el ancho de las columnas
    ajustarAnchoColumnas(rutas);

        }



    
    public void cargarRegistros(ArrayList<RegistroCSV> nuevosRegistros) {
        registros = nuevosRegistros; // Reemplaza los registros actuales con los nuevos
        llenarTablaDesdeRegistros(); // Actualiza la tabla UI
    }

    private void mostrarDialogoEdicion() {
        JTextField idField = new JTextField(5);
        JTextField distanciaField = new JTextField(5);

        JPanel dialogPanel = new JPanel();
        dialogPanel.add(new JLabel("ID:"));
        dialogPanel.add(idField);
        dialogPanel.add(Box.createHorizontalStrut(15)); // Espaciador
        dialogPanel.add(new JLabel("Nueva Distancia:"));
        dialogPanel.add(distanciaField);

        int result = JOptionPane.showConfirmDialog(null, dialogPanel, 
                 "Editar Distancia", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            editarDistancia(idField.getText(), distanciaField.getText());
        }
    }
    
    private void editarDistancia(String id, String nuevaDistancia) {
    for (RegistroCSV registro : registros) {
        if (registro.getId().equals(id)) {
            registro.setDistancia(nuevaDistancia);
            break;
        }
            }
            // Actualiza el modelo de la tabla
            llenarTablaDesdeRegistros();
            escribirRegistrosACSV();
        }
    private void escribirRegistrosACSV() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(selectedFile))) {
        // Escribe la línea de encabezado, si es necesaria
        pw.println("ID,INICIO,FIN,DISTANCIA");

        // Escribe cada registro en el archivo
        for (RegistroCSV registro : registros) {
            pw.println(registro.getId() + "," + registro.getInicio() + "," + registro.getFin() + "," + registro.getDistancia());
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al escribir en el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void mostrarDialogoAgregar() {
    JTextField inicioField = new JTextField(5);
    JTextField finField = new JTextField(5);
    JTextField distanciaField = new JTextField(5);

    JPanel dialogPanel = new JPanel(new GridLayout(0, 2));
    dialogPanel.add(new JLabel("Inicio:"));
    dialogPanel.add(inicioField);
    dialogPanel.add(new JLabel("Fin:"));
    dialogPanel.add(finField);
    dialogPanel.add(new JLabel("Distancia:"));
    dialogPanel.add(distanciaField);

    int result = JOptionPane.showConfirmDialog(null, dialogPanel, 
             "Agregar Nueva Ruta", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        agregarRuta(inicioField.getText(), finField.getText(), distanciaField.getText());
    }
}
private void agregarRuta(String inicio, String fin, String distancia) {
    // Generar nuevo ID
    int nuevoId = registros.isEmpty() ? 1 : Integer.parseInt(registros.get(registros.size() - 1).getId()) + 1;

    // Crear nuevo registro
    RegistroCSV nuevoRegistro = new RegistroCSV(String.valueOf(nuevoId), inicio, fin, distancia);
    registros.add(nuevoRegistro);

    // Añadir a la tabla
    DefaultTableModel modelo = (DefaultTableModel) rutas.getModel();
    modelo.addRow(new Object[]{nuevoRegistro.getId(), nuevoRegistro.getInicio(), nuevoRegistro.getFin(), nuevoRegistro.getDistancia()});

    // Opcionalmente, escribir cambios en el archivo CSV
    escribirRegistrosACSV();
}
    private void mostrarDialogoEliminar() {
    String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la ruta a eliminar:");
    if (id != null && !id.trim().isEmpty()) {
        eliminarRuta(id.trim());
    }
}

    private void eliminarRuta(String id) {
    RegistroCSV rutaAEliminar = null;
    for (RegistroCSV registro : registros) {
        if (registro.getId().equals(id)) {
            rutaAEliminar = registro;
            break;
        }
    }
    if (rutaAEliminar != null) {
        registros.remove(rutaAEliminar);
        llenarTablaDesdeRegistros();
        escribirRegistrosACSV();
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró una ruta con el ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
    }
    }


}
    public void ajustarAnchoColumnas(JTable tabla) {
        // Asegura que el layout de la tabla esté actualizado para medir correctamente el contenido.
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        final TableColumnModel columnModel = tabla.getColumnModel();
        for (int columna = 0; columna < tabla.getColumnCount(); columna++) {
            int anchoMaximo = 50; // Ancho mínimo de la columna
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                TableCellRenderer cellRenderer = tabla.getCellRenderer(fila, columna);
                Component c = tabla.prepareRenderer(cellRenderer, fila, columna);
                int ancho = c.getPreferredSize().width + tabla.getIntercellSpacing().width;
                anchoMaximo = Math.max(anchoMaximo, ancho);
            }
            // Considera el ancho del encabezado de la columna
            TableColumn columnaTabla = columnModel.getColumn(columna);
            TableCellRenderer headerRenderer = columnaTabla.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = tabla.getTableHeader().getDefaultRenderer();
            }
            Component componenteEncabezado = headerRenderer.getTableCellRendererComponent(tabla, columnaTabla.getHeaderValue(), false, false, 0, columna);
            anchoMaximo = Math.max(anchoMaximo, componenteEncabezado.getPreferredSize().width);

            // Configura el ancho preferido de la columna
            columnModel.getColumn(columna).setPreferredWidth(anchoMaximo);
        }
    }
    public  void cargarRegistros(ArrayList<RegistroCSV> nuevosRegistros) {
        this.registros = nuevosRegistros; // Actualiza la lista de registros
        llenarTablaDesdeRegistros(); // Llena la tabla de la UI con los nuevos datos
    }

    public ArrayList<RegistroCSV> getRegistros(){
            return registros;
        }
    public void llenarTablaDesdeRegistros() {
    inicios.clear();
    finals.clear();
    DefaultTableModel modelo = (DefaultTableModel) rutas.getModel();
    
    modelo.setRowCount(0);
    for (RegistroCSV registro : registros) {
        modelo.addRow(new Object[]{registro.getId(), registro.getInicio(), registro.getFin(), registro.getDistancia()});
        inicios.add(registro.getInicio());
        finals.add(registro.getFin()); // Aquí estaba el error, ahora está corregido
        System.out.println("Finales " + finals);
        System.out.println("Inicios " + inicios);
        ajustarAnchoColumnas(rutas);
    }
}
public void setRutaArchivoSeleccionado(String rutaArchivo) {
        if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
            selectedFile = new File(rutaArchivo);
        } 
}
public String getRutaArchivoSeleccionado() {
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }
}       