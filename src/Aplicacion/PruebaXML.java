/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aplicacion;

/**
 *
 * @author Angie Mariela
 */


import Conceptos.Tipo;
import Conceptos.Precio;
import Conceptos.Tiquete;
import Util.XML_Admin;
import java.util.ArrayList;

public class PruebaXML {

    public static void main(String[] args) {
        ArrayList<Tipo> tipos = XML_Admin.cargarTipos("Data/tipos.xml");
        ArrayList<Precio> precios = XML_Admin.cargarPrecios("Data/precios.xml");
        ArrayList<Tiquete> tiquetes = XML_Admin.cargarTiquetes("Data/tiquetes.xml");
        
        System.out.println("\nRELACION TIPOS + PRECIOS:");

        for (Precio p : precios) {
            for (Tipo t : tipos) {
                if (p.getTipo().equals(t.getId())) {
                    System.out.println(
                        t.getNombre() + " → ₡" + p.getPrecio() + " (Fecha: " + p.getFecha() + ")"
                    );
                }
            }
        }

        System.out.println("TIPOS:");
        for (Tipo t : tipos) {
            System.out.println(t);
        }

        System.out.println("\nPRECIOS:");
        for (Precio p : precios) {
            System.out.println(p);
        }

        System.out.println("\nTIQUETES:");
        for (Tiquete tq : tiquetes) {
            System.out.println(tq);
        }
    }
}
