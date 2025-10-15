import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel principal;
    private JTabbedPane tabbedPane1;
    private JComboBox cboMarca;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtCantidadBoleto;
    private JButton btnComprarBoleto;
    private JLabel lblCantitadBoletosTotalQG;
    private JLabel lblCantitadBoletosTotalQC;
    private JLabel lblCantitadBoletosTotalQL;
    private JLabel lblCantitadBoletosRestantesQG;
    private JLabel lblCantitadBoletosRestantesQC;
    private JLabel lblCantitadBoletosRestantesQL;
    private JLabel lblVentasTotalesQG;
    private JLabel lblVentasTotalesQC;
    private JLabel lblVentasTotalesQL;
    private JLabel lblVentasTotalesRutas;
    private JTextArea textArea1;
    private JLabel lblValorRuta;

    // Instancia del gestor de boletos
    private GestorBoletos gestorBoletos;
    
    public Ventana() {
        // Inicializar el gestor de boletos
        gestorBoletos = new GestorBoletos();
        
        // Configurar el área de texto
        textArea1.setEditable(false);
        textArea1.setText("=== COMPRAS REALIZADAS ===\n");
        
        // Configurar el evento del botón comprar
        btnComprarBoleto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarCompra();
            }
        });

        // Configurar el evento del combo box para actualizar el precio de la ruta
        cboMarca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutaSeleccionada = (String) cboMarca.getSelectedItem();
                double precio = gestorBoletos.getPrecioPorRuta(rutaSeleccionada);
                lblValorRuta.setText(String.format("$%.2f", precio));
            }
        });
    }
    
    private void procesarCompra() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText().trim();
            String cedula = txtCedula.getText().trim();
            String ruta = (String) cboMarca.getSelectedItem();
            String cantidadTexto = txtCantidadBoleto.getText().trim();
            
            // Validar campos vacíos
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(principal, "Por favor ingrese el nombre del pasajero.", 
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(principal, "Por favor ingrese la cédula.", 
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtCedula.requestFocus();
                return;
            }
            
            if (cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(principal, "Por favor ingrese la cantidad de boletos.", 
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtCantidadBoleto.requestFocus();
                return;
            }
            
            // Validar cédula (solo números y longitud)
            if (!cedula.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(principal, "La cédula debe contener exactamente 10 dígitos.", 
                    "Cédula inválida", JOptionPane.ERROR_MESSAGE);
                txtCedula.requestFocus();
                return;
            }
            
            // Validar cantidad (debe ser número entero)
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(principal, "La cantidad debe ser un número entero.", 
                    "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
                txtCantidadBoleto.requestFocus();
                return;
            }
            
            // Validar la compra usando el gestor
            String errorValidacion = gestorBoletos.validarCompra(cedula, ruta, cantidad);
            if (errorValidacion != null) {
                JOptionPane.showMessageDialog(principal, errorValidacion, 
                    "Error en la compra", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear y procesar el boleto
            double precio = gestorBoletos.getPrecioPorRuta(ruta);
            Boleto nuevoBoleto = new Boleto(cedula, nombre, ruta, cantidad, precio);
            gestorBoletos.agregarCompra(nuevoBoleto);
            
            // Actualizar área de texto con la nueva compra
            actualizarAreaCompras();
            
            // Mostrar mensaje de éxito
            double total = nuevoBoleto.getPrecioTotal();
            String mensaje = String.format("¡Compra realizada exitosamente!\n\n" +
                "Pasajero: %s\n" +
                "Ruta: %s\n" +
                "Cantidad: %d boletos\n" +
                "Precio unitario: $%.2f\n" +
                "Total a pagar: $%.2f", 
                nombre, ruta, cantidad, precio, total);
            
            JOptionPane.showMessageDialog(principal, mensaje, 
                "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            limpiarCampos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(principal, 
                "Error al procesar la compra: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarAreaCompras() {
        if (gestorBoletos.estaVacia()) {
            textArea1.setText("=== COMPRAS REALIZADAS ===\nNo hay compras registradas.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("=== COMPRAS REALIZADAS ===\n");
            sb.append(String.format("Total de compras: %d\n", gestorBoletos.tamano()));
            sb.append(String.format("Total recaudado: $%.2f\n\n", gestorBoletos.getTotalRecaudado()));
            sb.append(gestorBoletos.toString().replace("=== HISTORIAL DE COMPRAS ===\n", ""));
            textArea1.setText(sb.toString());
            lblCantitadBoletosTotalQG.setText(String.valueOf(gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_GUAYAQUIL)));
            lblCantitadBoletosTotalQC.setText(String.valueOf(gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_CUENCA)));
            lblCantitadBoletosTotalQL.setText(String.valueOf(gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_LOJA)));
            lblCantitadBoletosRestantesQG.setText(String.valueOf(gestorBoletos.getBoletosDisponibles(GestorBoletos.RUTA_QUITO_GUAYAQUIL)));
            lblCantitadBoletosRestantesQC.setText(String.valueOf(gestorBoletos.getBoletosDisponibles(GestorBoletos.RUTA_QUITO_CUENCA)));
            lblCantitadBoletosRestantesQL.setText(String.valueOf(gestorBoletos.getBoletosDisponibles(GestorBoletos.RUTA_QUITO_LOJA)));
            lblVentasTotalesQG.setText(String.format("$%.2f", gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_GUAYAQUIL) * gestorBoletos.getPrecioPorRuta(GestorBoletos.RUTA_QUITO_GUAYAQUIL)));
            lblVentasTotalesQC.setText(String.format("$%.2f", gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_CUENCA) * gestorBoletos.getPrecioPorRuta(GestorBoletos.RUTA_QUITO_CUENCA)));
            lblVentasTotalesQL.setText(String.format("$%.2f", gestorBoletos.getBoletosVendidos(GestorBoletos.RUTA_QUITO_LOJA) * gestorBoletos.getPrecioPorRuta(GestorBoletos.RUTA_QUITO_LOJA)));
            lblVentasTotalesRutas.setText(String.format("$%.2f", gestorBoletos.getTotalRecaudado()));
        }
        
        // Hacer scroll hacia abajo para mostrar la última compra
        textArea1.setCaretPosition(textArea1.getDocument().getLength());
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtCantidadBoleto.setText("");
        txtNombre.requestFocus();
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Venta de Boletos");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}


