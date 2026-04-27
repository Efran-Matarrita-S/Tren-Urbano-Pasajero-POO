/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Conceptos.Precio;
import Conceptos.Tipo;
import Conceptos.Tiquete;
import Util.XML_Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminTiquetes extends JFrame {

    private ArrayList<Tiquete> listaTiquetes;
    private ArrayList<Precio> listaPrecios;
    private ArrayList<Tipo> listaTipos;

    // Filtros
    private JTextField txtFiltroId, txtFiltroFecha, txtFiltroNombre;
    private JComboBox<String> cmbFiltroTipo;
    private JButton btnBuscar, btnSalir;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public AdminTiquetes() {
        listaTiquetes = XML_Admin.cargarTiquetes("Data/tiquetes.xml");
        listaPrecios  = XML_Admin.cargarPrecios("Data/precios.xml");
        listaTipos    = XML_Admin.cargarTipos("Data/tipos.xml");
        configurarVentana();
        crearComponentes();
        cargarTabla(listaTiquetes);
    }

    private void configurarVentana() {
        setTitle("Consultar Tiquetes");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // ── Título ──
        JLabel lblTitulo = new JLabel("Consultar Tiquetes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(0, 102, 153));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setPreferredSize(new Dimension(0, 35));
        add(lblTitulo, BorderLayout.NORTH);

        // ── Panel filtros ──
        JPanel panelFiltros = new JPanel(null);
        panelFiltros.setPreferredSize(new Dimension(0, 90));
        panelFiltros.setBorder(BorderFactory.createEtchedBorder());

        JLabel lId = new JLabel("ID:");
        lId.setBounds(20, 15, 25, 25);
        txtFiltroId = new JTextField();
        txtFiltroId.setBounds(48, 15, 80, 25);

        JLabel lFecha = new JLabel("Fecha:");
        lFecha.setBounds(150, 15, 50, 25);
        txtFiltroFecha = new JTextField();
        txtFiltroFecha.setBounds(205, 15, 100, 25);

        JLabel lTipo = new JLabel("Tipo:");
        lTipo.setBounds(330, 15, 40, 25);
        cmbFiltroTipo = new JComboBox<>();
        cmbFiltroTipo.addItem("Todos");
        for (Tipo t : listaTipos) {
            cmbFiltroTipo.addItem(t.getNombre());
        }
        cmbFiltroTipo.setBounds(375, 15, 140, 25);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(540, 15, 90, 25);

        JLabel lNombre = new JLabel("Nombre:");
        lNombre.setBounds(20, 50, 65, 25);
        txtFiltroNombre = new JTextField();
        txtFiltroNombre.setBounds(90, 50, 180, 25);

        panelFiltros.add(lId);          panelFiltros.add(txtFiltroId);
        panelFiltros.add(lFecha);       panelFiltros.add(txtFiltroFecha);
        panelFiltros.add(lTipo);        panelFiltros.add(cmbFiltroTipo);
        panelFiltros.add(btnBuscar);
        panelFiltros.add(lNombre);      panelFiltros.add(txtFiltroNombre);

        add(panelFiltros, BorderLayout.NORTH);

        // ── Tabla ──
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Tipo", "Precio", "Fecha"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        btnBuscar.addActionListener(e -> aplicarFiltros());
        btnSalir.addActionListener(e -> dispose());
    }

    private void cargarTabla(ArrayList<Tiquete> lista) {
        modeloTabla.setRowCount(0);
        for (Tiquete t : lista) {
            Precio precio = buscarPrecio(t.getPrecioId());
            String nombreTipo = "";
            String montoStr = "";
            String fechaStr = "";

            if (precio != null) {
                Tipo tipo = buscarTipo(precio.getTipo());
                nombreTipo = tipo != null ? tipo.getNombre() : precio.getTipo();
                montoStr   = String.valueOf(precio.getPrecio());
                fechaStr   = precio.getFecha();
            }

            modeloTabla.addRow(new Object[]{
                t.getId(), t.getNombre(), nombreTipo, montoStr, fechaStr
            });
        }
    }

    private void aplicarFiltros() {
        String filtroId     = txtFiltroId.getText().trim().toLowerCase();
        String filtroFecha  = txtFiltroFecha.getText().trim().toLowerCase();
        String filtroNombre = txtFiltroNombre.getText().trim().toLowerCase();
        String filtroTipo   = (String) cmbFiltroTipo.getSelectedItem();

        ArrayList<Tiquete> filtrados = new ArrayList<>();

        for (Tiquete t : listaTiquetes) {
            Precio precio = buscarPrecio(t.getPrecioId());
            String nombreTipo = "";
            String fecha = "";

            if (precio != null) {
                Tipo tipo = buscarTipo(precio.getTipo());
                nombreTipo = tipo != null ? tipo.getNombre() : "";
                fecha = precio.getFecha();
            }

            boolean cumple = true;

            if (!filtroId.isEmpty() && !t.getId().toLowerCase().contains(filtroId))
                cumple = false;

            if (!filtroNombre.isEmpty() && !t.getNombre().toLowerCase().contains(filtroNombre))
                cumple = false;

            if (!filtroFecha.isEmpty() && !fecha.toLowerCase().contains(filtroFecha))
                cumple = false;

            if (!"Todos".equals(filtroTipo) && !nombreTipo.equals(filtroTipo))
                cumple = false;

            if (cumple) filtrados.add(t);
        }

        cargarTabla(filtrados);
    }

    private Precio buscarPrecio(String idPrecio) {
        for (Precio p : listaPrecios) {
            if (p.getId().equals(idPrecio)) return p;
        }
        return null;
    }

    private Tipo buscarTipo(String idTipo) {
        for (Tipo t : listaTipos) {
            if (t.getId().equals(idTipo)) return t;
        }
        return null;
    }
}