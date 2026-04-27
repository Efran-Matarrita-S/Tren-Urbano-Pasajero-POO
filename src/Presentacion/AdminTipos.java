/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Conceptos.Tipo;
import Util.XML_Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AdminTipos extends JFrame {

    private ArrayList<Tipo> listaTipos;

    // Campos superiores
    private JTextField txtId, txtNombre, txtImagen, txtDescripcion;
    private JButton btnNuevo, btnModificar, btnBorrar, btnVerImagen, btnSalir;

    // Tabla inferior
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public AdminTipos() {
        listaTipos = XML_Admin.cargarTipos("Data/tipos.xml");
        configurarVentana();
        crearComponentes();
        cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Tipos de Tiquete");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // ── Panel título ──
        JLabel lblTitulo = new JLabel("Tipos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(0, 102, 153));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setPreferredSize(new Dimension(0, 35));
        add(lblTitulo, BorderLayout.NORTH);

        // ── Panel superior (formulario) ──
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(0, 130));
        panelForm.setBorder(BorderFactory.createEtchedBorder());

        JLabel lId = new JLabel("ID:");
        lId.setBounds(20, 15, 30, 25);
        txtId = new JTextField();
        txtId.setBounds(55, 15, 80, 25);

        JLabel lNombre = new JLabel("Nombre:");
        lNombre.setBounds(160, 15, 65, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(230, 15, 180, 25);

        JLabel lDesc = new JLabel("Descripcion:");
        lDesc.setBounds(20, 55, 90, 25);
        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(115, 55, 295, 25);

        JLabel lImg = new JLabel("Imagen:");
        lImg.setBounds(20, 90, 65, 25);
        txtImagen = new JTextField();
        txtImagen.setBounds(90, 90, 220, 25);

        btnVerImagen = new JButton("Ver Imagen");
        btnVerImagen.setBounds(320, 90, 100, 25);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(450, 15, 100, 25);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(450, 50, 100, 25);

        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(450, 85, 100, 25);

        panelForm.add(lId);        panelForm.add(txtId);
        panelForm.add(lNombre);    panelForm.add(txtNombre);
        panelForm.add(lDesc);      panelForm.add(txtDescripcion);
        panelForm.add(lImg);       panelForm.add(txtImagen);
        panelForm.add(btnVerImagen);
        panelForm.add(btnNuevo);
        panelForm.add(btnModificar);
        panelForm.add(btnBorrar);

        add(panelForm, BorderLayout.NORTH);

        // ── Tabla inferior ──
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripcion", "Imagen"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Evento selección de fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtId.setText((String) modeloTabla.getValueAt(fila, 0));
                txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
                txtDescripcion.setText((String) modeloTabla.getValueAt(fila, 2));
                txtImagen.setText((String) modeloTabla.getValueAt(fila, 3));
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);

        // ── Panel inferior con tabla y botón Salir ──
        JPanel panelInferior = new JPanel(new BorderLayout());
        btnSalir = new JButton("Salir");
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSalir.add(btnSalir);
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(panelSalir, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.CENTER);

        // ── Eventos botones ──
        btnNuevo.addActionListener(e -> nuevoTipo());
        btnModificar.addActionListener(e -> modificarTipo());
        btnBorrar.addActionListener(e -> borrarTipo());
        btnVerImagen.addActionListener(e -> verImagen());
        btnSalir.addActionListener(e -> dispose());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Tipo t : listaTipos) {
            modeloTabla.addRow(new Object[]{
                t.getId(), t.getNombre(), t.getDescripcion(), t.getImagen()
            });
        }
    }

    private void nuevoTipo() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String desc = txtDescripcion.getText().trim();
        String img = txtImagen.getText().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Tipo t : listaTipos) {
            if (t.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "Ya existe un tipo con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        listaTipos.add(new Tipo(id, nombre, desc, img));
        XML_Admin.guardarTipos(listaTipos, "Data/tipos.xml");
        cargarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Tipo creado correctamente.");
    }

    private void modificarTipo() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tipo t = listaTipos.get(fila);
        t.setNombre(txtNombre.getText().trim());
        t.setDescripcion(txtDescripcion.getText().trim());
        t.setImagen(txtImagen.getText().trim());

        XML_Admin.guardarTipos(listaTipos, "Data/tipos.xml");
        cargarTabla();
        JOptionPane.showMessageDialog(this, "Tipo modificado correctamente.");
    }

    private void borrarTipo() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea borrar este tipo?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            listaTipos.remove(fila);
            XML_Admin.guardarTipos(listaTipos, "Data/tipos.xml");
            cargarTabla();
            limpiarCampos();
        }
    }

    private void verImagen() {
        String nombreImg = txtImagen.getText().trim();

        if (nombreImg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay imagen especificada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File archivo = new File("Imagenes/" + nombreImg);

        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo: " + nombreImg, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialogo = new JDialog(this, "Imagen del tiquete", true);
        dialogo.setSize(900, 620);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        ImageIcon icon = new ImageIcon("Imagenes/" + nombreImg);
        Image img = icon.getImage().getScaledInstance(760, 430, Image.SCALE_SMOOTH);
        JLabel lblImg = new JLabel(new ImageIcon(img));
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnOk);

        dialogo.add(lblImg, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

 
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtImagen.setText("");
    }
}
