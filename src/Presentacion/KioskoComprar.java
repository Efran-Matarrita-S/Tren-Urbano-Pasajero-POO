package Presentacion;

import Conceptos.Precio;
import Conceptos.Tipo;
import javax.swing.*;

public class KioskoComprar extends JFrame {

    private Tipo tipo;
    private Precio precio;

    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    private JLabel lblPrecio;
    private JLabel lblFecha;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JButton btnComprar;
    private JButton btnCancelar;

    public KioskoComprar(Tipo tipo, Precio precio) {
        this.tipo = tipo;
        this.precio = precio;

        configurarVentana();
        crearComponentes();
        agregarComponentes();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("Comprar Tiquete");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
    }

    private void crearComponentes() {
        lblTitulo = new JLabel("Comprar Tiquete: " + tipo.getNombre());
        lblTitulo.setBounds(40, 20, 350, 30);

        lblDescripcion = new JLabel("<html>Descripción: " + tipo.getDescripcion() + "</html>");
        lblDescripcion.setBounds(40, 60, 350, 50);

        lblPrecio = new JLabel("Precio: ₡" + precio.getPrecio());
        lblPrecio.setBounds(40, 120, 350, 25);

        lblFecha = new JLabel("Fecha de vigencia: " + precio.getFecha());
        lblFecha.setBounds(40, 150, 350, 25);

        lblNombre = new JLabel("Nombre del comprador:");
        lblNombre.setBounds(40, 190, 200, 25);

        txtNombre = new JTextField();
        txtNombre.setBounds(200, 190, 180, 25);

        btnComprar = new JButton("Comprar");
        btnComprar.setBounds(100, 250, 110, 35);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(230, 250, 110, 35);
    }

    private void agregarComponentes() {
        add(lblTitulo);
        add(lblDescripcion);
        add(lblPrecio);
        add(lblFecha);
        add(lblNombre);
        add(txtNombre);
        add(btnComprar);
        add(btnCancelar);
    }

    private void configurarEventos() {
        btnCancelar.addActionListener(e -> dispose());

        btnComprar.addActionListener(e -> {
            String nombre = txtNombre.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del comprador",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                this,
                "Compra registrada (falta guardar en XML)\nComprador: " + nombre,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
