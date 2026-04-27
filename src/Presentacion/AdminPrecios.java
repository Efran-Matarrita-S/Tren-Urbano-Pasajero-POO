/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Conceptos.Precio;
import Conceptos.Tipo;
import Util.XML_Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminPrecios extends JFrame {

    private ArrayList<Precio> listaPrecios;
    private ArrayList<Tipo> listaTipos;

    // Campos superiores
    private JComboBox<String> cmbNombre;
    private JTextField txtPrecio, txtFecha;
    private JButton btnNuevo, btnModificar, btnBorrar, btnSalir;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public AdminPrecios() {
        listaPrecios = XML_Admin.cargarPrecios("Data/precios.xml");
        listaTipos = XML_Admin.cargarTipos("Data/tipos.xml");
        configurarVentana();
        crearComponentes();
        cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Precios y Vigencia");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // ── Título ──
        JLabel lblTitulo = new JLabel("Precios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(0, 102, 153));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setPreferredSize(new Dimension(0, 35));
        add(lblTitulo, BorderLayout.NORTH);

        // ── Panel formulario ──
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(0, 120));
        panelForm.setBorder(BorderFactory.createEtchedBorder());

        JLabel lNombre = new JLabel("Nombre:");
        lNombre.setBounds(20, 20, 65, 25);

        // ComboBox con los nombres de los tipos
        cmbNombre = new JComboBox<>();
        for (Tipo t : listaTipos) {
            cmbNombre.addItem(t.getNombre());
        }
        cmbNombre.setBounds(90, 20, 160, 25);

        JLabel lPrecio = new JLabel("Precio:");
        lPrecio.setBounds(280, 20, 55, 25);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(340, 20, 120, 25);

        JLabel lFecha = new JLabel("Fecha:");
        lFecha.setBounds(20, 60, 55, 25);
        txtFecha = new JTextField();
        txtFecha.setBounds(80, 60, 120, 25);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(500, 15, 120, 25);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(500, 50, 120, 25);

        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(500, 85, 120, 25);

        panelForm.add(lNombre);    panelForm.add(cmbNombre);
        panelForm.add(lPrecio);    panelForm.add(txtPrecio);
        panelForm.add(lFecha);     panelForm.add(txtFecha);
        panelForm.add(btnNuevo);
        panelForm.add(btnModificar);
        panelForm.add(btnBorrar);

        add(panelForm, BorderLayout.NORTH);

        // ── Tabla ──
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Precio", "Fecha"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Evento selección de fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                String nombreTipo = (String) modeloTabla.getValueAt(fila, 1);
                txtPrecio.setText(String.valueOf(modeloTabla.getValueAt(fila, 2)));
                txtFecha.setText((String) modeloTabla.getValueAt(fila, 3));
                cmbNombre.setSelectedItem(nombreTipo);
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);

        // ── Panel inferior ──
        JPanel panelInferior = new JPanel(new BorderLayout());
        btnSalir = new JButton("Salir");
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSalir.add(btnSalir);
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(panelSalir, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.CENTER);

        // ── Eventos ──
        btnNuevo.addActionListener(e -> nuevoPrecio());
        btnModificar.addActionListener(e -> modificarPrecio());
        btnBorrar.addActionListener(e -> borrarPrecio());
        btnSalir.addActionListener(e -> dispose());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Precio p : listaPrecios) {
            String nombreTipo = buscarNombreTipo(p.getTipo());
            modeloTabla.addRow(new Object[]{
                p.getId(), nombreTipo,
                p.getPrecio(), p.getFecha()
            });
        }
    }

    private String buscarNombreTipo(String idTipo) {
        for (Tipo t : listaTipos) {
            if (t.getId().equals(idTipo)) {
                return t.getNombre();
            }
        }
        return idTipo;
    }

    private String buscarIdTipo(String nombreTipo) {
        for (Tipo t : listaTipos) {
            if (t.getNombre().equals(nombreTipo)) {
                return t.getId();
            }
        }
        return "";
    }

    private String generarNuevoId() {
        int mayor = 0;
        for (Precio p : listaPrecios) {
            try {
                int id = Integer.parseInt(p.getId());
                if (id > mayor) mayor = id;
            } catch (NumberFormatException ex) {}
        }
        return String.format("%03d", mayor + 1);
    }

    private void nuevoPrecio() {
        String nombreTipo = (String) cmbNombre.getSelectedItem();
        String precioStr = txtPrecio.getText().trim();
        String fecha = txtFecha.getText().trim();

        if (precioStr.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Precio y Fecha son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            String idTipo = buscarIdTipo(nombreTipo);
            String nuevoId = generarNuevoId();
            listaPrecios.add(new Precio(nuevoId, idTipo, precio, fecha));
            XML_Admin.guardarPrecios(listaPrecios, "Data/precios.xml");
            cargarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Precio creado correctamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPrecio() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un precio de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Precio p = listaPrecios.get(fila);
            p.setTipo(buscarIdTipo((String) cmbNombre.getSelectedItem()));
            p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            p.setFecha(txtFecha.getText().trim());
            XML_Admin.guardarPrecios(listaPrecios, "Data/precios.xml");
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Precio modificado correctamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarPrecio() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un precio de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea borrar este precio?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            listaPrecios.remove(fila);
            XML_Admin.guardarPrecios(listaPrecios, "Data/precios.xml");
            cargarTabla();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtPrecio.setText("");
        txtFecha.setText("");
        if (cmbNombre.getItemCount() > 0) cmbNombre.setSelectedIndex(0);
    }
}
