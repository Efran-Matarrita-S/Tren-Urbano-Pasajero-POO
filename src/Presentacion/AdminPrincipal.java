/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import javax.swing.*;
import java.awt.*;

public class AdminPrincipal extends JFrame {

    public AdminPrincipal() {
        configurarVentana();
        crearMenu();
        crearContenido();
    }

    private void configurarVentana() {
        setTitle("TUP - Tren Urbano de Pasajeros - ADMINISTRADOR");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        // Menú Tiquetes
        JMenu menuTiquetes = new JMenu("Tiquetes");

        JMenuItem itemCompras = new JMenuItem("Compras");
        itemCompras.addActionListener(e -> abrirVentana("compras"));

        JMenuItem itemTipos = new JMenuItem("Tipos");
        itemTipos.addActionListener(e -> abrirVentana("tipos"));

        JMenuItem itemPrecios = new JMenuItem("Precios y Vigencia");
        itemPrecios.addActionListener(e -> abrirVentana("precios"));

        menuTiquetes.add(itemCompras);
        menuTiquetes.add(itemTipos);
        menuTiquetes.add(itemPrecios);

        menuBar.add(menuArchivo);
        menuBar.add(menuTiquetes);
        setJMenuBar(menuBar);
    }

    private void crearContenido() {
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL imgURL = getClass().getResource("/Imagenes/tren.jpg");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } else {
            lblImagen.setText("TUP - Tren Urbano de Pasajeros");
            lblImagen.setFont(new Font("Segoe UI", Font.BOLD, 24));
        }

        add(lblImagen, BorderLayout.CENTER);
    }

    private void abrirVentana(String tipo) {
        switch (tipo) {
            case "compras":
                AdminTiquetes v1 = new AdminTiquetes();
                v1.setLocationRelativeTo(this);
                v1.setVisible(true);
                break;
            case "tipos":
                AdminTipos v2 = new AdminTipos();
                v2.setLocationRelativeTo(this);
                v2.setVisible(true);
                break;
            case "precios":
                AdminPrecios v3 = new AdminPrecios();
                v3.setLocationRelativeTo(this);
                v3.setVisible(true);
                break;
        }
    }
}
