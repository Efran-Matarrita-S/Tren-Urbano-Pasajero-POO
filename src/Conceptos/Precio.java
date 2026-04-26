/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author matar
 */
public class Precio {
    private String id;
    private String tipo;   // referencia al id del Tipo
    private double precio;
    private String fecha;  // formato YYYY-MM-DD

    public Precio(String id, String tipo, double precio, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getId() {
        return id; 
    }
    public String getTipo() { 
        return tipo; 
    }
    public double getPrecio() { 
        return precio; 
    }
    public String getFecha() { 
        return fecha; 
    }

    public void setId(String id) { 
        this.id = id; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    public void setPrecio(double precio) { 
        this.precio = precio; 
    }
    public void setFecha(String fecha) { 
        this.fecha = fecha; 
    }

    @Override
    public String toString() {
        return "Precio{id=" + id + ", tipo=" + tipo + ", precio=" + precio + "}";
    }
    
}
