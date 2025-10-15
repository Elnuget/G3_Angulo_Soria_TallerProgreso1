import javax.swing.*;

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


