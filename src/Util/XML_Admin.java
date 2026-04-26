/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Conceptos.Tipo;
import Conceptos.Precio;
import Conceptos.Tiquete;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XML_Admin {

    // ─────────────────────────────────────────
    //  TIPOS
    // ─────────────────────────────────────────

    public static ArrayList<Tipo> cargarTipos(String ruta) {
        ArrayList<Tipo> lista = new ArrayList<>();
        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("tipo");
            for (int i = 0; i < nodos.getLength(); i++) {
                Element el = (Element) nodos.item(i);
                String id          = el.getAttribute("id");
                String nombre      = el.getElementsByTagName("nombre").item(0).getTextContent();
                String descripcion = el.getElementsByTagName("descripcion").item(0).getTextContent();
                String imagen      = el.getElementsByTagName("imagen").item(0).getTextContent();
                lista.add(new Tipo(id, nombre, descripcion, imagen));
            }
        } catch (Exception e) {
            System.out.println("Error cargando tipos: " + e.getMessage());
        }
        return lista;
    }

    public static void guardarTipos(ArrayList<Tipo> lista, String ruta) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("tipos");
            doc.appendChild(raiz);

            for (Tipo t : lista) {
                Element tipo = doc.createElement("tipo");
                tipo.setAttribute("id", t.getId());

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(t.getNombre());

                Element descripcion = doc.createElement("descripcion");
                descripcion.setTextContent(t.getDescripcion());

                Element imagen = doc.createElement("imagen");
                imagen.setTextContent(t.getImagen());

                tipo.appendChild(nombre);
                tipo.appendChild(descripcion);
                tipo.appendChild(imagen);
                raiz.appendChild(tipo);
            }
            escribirArchivo(doc, ruta);
        } catch (Exception e) {
            System.out.println("Error guardando tipos: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    //  PRECIOS
    // ─────────────────────────────────────────

    public static ArrayList<Precio> cargarPrecios(String ruta) {
        ArrayList<Precio> lista = new ArrayList<>();
        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("precio");
            for (int i = 0; i < nodos.getLength(); i++) {
                Element el = (Element) nodos.item(i);
                String id     = el.getAttribute("id");
                String tipo   = el.getElementsByTagName("tipo").item(0).getTextContent();
                double precio = Double.parseDouble(
                                  el.getElementsByTagName("precio").item(0).getTextContent());
                String fecha  = el.getElementsByTagName("fecha").item(0).getTextContent();
                lista.add(new Precio(id, tipo, precio, fecha));
            }
        } catch (Exception e) {
            System.out.println("Error cargando precios: " + e.getMessage());
        }
        return lista;
    }

    public static void guardarPrecios(ArrayList<Precio> lista, String ruta) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("precios");
            doc.appendChild(raiz);

            for (Precio p : lista) {
                Element precio = doc.createElement("precio");
                precio.setAttribute("id", p.getId());

                Element tipo = doc.createElement("tipo");
                tipo.setTextContent(p.getTipo());

                Element monto = doc.createElement("precio");
                monto.setTextContent(String.valueOf(p.getPrecio()));

                Element fecha = doc.createElement("fecha");
                fecha.setTextContent(p.getFecha());

                precio.appendChild(tipo);
                precio.appendChild(monto);
                precio.appendChild(fecha);
                raiz.appendChild(precio);
            }
            escribirArchivo(doc, ruta);
        } catch (Exception e) {
            System.out.println("Error guardando precios: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    //  TIQUETES
    // ─────────────────────────────────────────

    public static ArrayList<Tiquete> cargarTiquetes(String ruta) {
        ArrayList<Tiquete> lista = new ArrayList<>();
        try {
            File archivo = new File(ruta);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("tiquete");
            for (int i = 0; i < nodos.getLength(); i++) {
                Element el = (Element) nodos.item(i);
                String id       = el.getAttribute("id");
                String nombre   = el.getElementsByTagName("nombre").item(0).getTextContent();
                String precioId = el.getElementsByTagName("precio_id").item(0).getTextContent();
                lista.add(new Tiquete(id, nombre, precioId));
            }
        } catch (Exception e) {
            System.out.println("Error cargando tiquetes: " + e.getMessage());
        }
        return lista;
    }

    public static void guardarTiquetes(ArrayList<Tiquete> lista, String ruta) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("tiquetes");
            doc.appendChild(raiz);

            for (Tiquete t : lista) {
                Element tiquete = doc.createElement("tiquete");
                tiquete.setAttribute("id", t.getId());

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(t.getNombre());

                Element precioId = doc.createElement("precio_id");
                precioId.setTextContent(t.getPrecioId());

                tiquete.appendChild(nombre);
                tiquete.appendChild(precioId);
                raiz.appendChild(tiquete);
            }
            escribirArchivo(doc, ruta);
        } catch (Exception e) {
            System.out.println("Error guardando tiquetes: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    //  UTILIDAD INTERNA
    // ─────────────────────────────────────────

    private static void escribirArchivo(Document doc, String ruta) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(ruta));
        transformer.transform(source, result);
    }
}
