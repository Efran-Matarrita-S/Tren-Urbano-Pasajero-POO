package Presentacion;

import Conceptos.Precio;
import Conceptos.Tipo;
import Conceptos.Tiquete;
import Util.XML_Admin;
import java.util.ArrayList;
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
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del comprador",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            ArrayList<Tiquete> tiquetes = XML_Admin.cargarTiquetes("Data/tiquetes.xml");

            String nuevoId = generarNuevoId(tiquetes);

            Tiquete nuevoTiquete = new Tiquete(
                nuevoId,
                nombre,
                precio.getId()
            );

            tiquetes.add(nuevoTiquete);

            XML_Admin.guardarTiquetes(tiquetes, "Data/tiquetes.xml");

            ImageIcon icon = new ImageIcon("Imagenes/" + tipo.getImagen());
            java.awt.Image img = icon.getImage().getScaledInstance(330, 185, java.awt.Image.SCALE_SMOOTH);            ImageIcon iconEscalado = new ImageIcon(img);

            JLabel lblImagen = new JLabel(iconEscalado);
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel lblTexto = new JLabel(
                "<html><center>"
                + "<b>Compra registrada correctamente.</b><br><br>"
                + "Comprador: " + nombre + "<br>"
                + "Tiquete: " + tipo.getNombre() + "<br>"
                + "Precio: ₡" + precio.getPrecio()
                + "</center></html>"
            );

            JPanel panelMensaje = new JPanel();
            panelMensaje.setLayout(new java.awt.BorderLayout(10, 10));
            panelMensaje.add(lblImagen, java.awt.BorderLayout.NORTH);
            panelMensaje.add(lblTexto, java.awt.BorderLayout.CENTER);

            JOptionPane.showMessageDialog(
                this,
                panelMensaje,
                "Compra exitosa",
                JOptionPane.PLAIN_MESSAGE
            );
            dispose();  
        });
        }
        private String generarNuevoId(ArrayList<Tiquete> tiquetes) {
        int mayor = 0;

        for (Tiquete t : tiquetes) {
            try {
                int idActual = Integer.parseInt(t.getId());
                if (idActual > mayor) {
                    mayor = idActual;
                }
            } catch (NumberFormatException ex) {
                // Si algún ID no es número, se ignora.
            }
        }

        int nuevo = mayor + 1;

        return String.format("%03d", nuevo);
    }
}
