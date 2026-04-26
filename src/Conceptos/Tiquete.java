/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author matar
 */
public class Tiquete {
    private String id;
    private String nombre;   // nombre del comprador
    private String precioId; // referencia al id del Precio

    public Tiquete(String id, String nombre, String precioId) {
        this.id = id;
        this.nombre = nombre;
        this.precioId = precioId;
    }

    public String getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getPrecioId() { 
        return precioId; 
    }

    public void setId(String id) { 
        this.id = id; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public void setPrecioId(String precioId) { 
        this.precioId = precioId; 
    }

    @Override
    public String toString() {
        return "Tiquete{id=" + id + ", nombre=" + nombre + ", precioId=" + precioId + "}";
    }
    
}
