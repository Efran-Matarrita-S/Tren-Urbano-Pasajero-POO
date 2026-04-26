/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

/**
 *
 * @author matar
 */
public class Tipo {
    private String id;
    private String nombre;
    private String descripcion;
    private String imagen;

    public Tipo(String id, String nombre, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getDescripcion() { 
        return descripcion; 
    }
    public String getImagen() { 
        return imagen; 
    }

    public void setId(String id) { 
        this.id = id; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    public void setImagen(String imagen) { 
        this.imagen = imagen; 
    }

    @Override
    public String toString() {
        return "Tipo{id=" + id + ", nombre=" + nombre + "}";
    }
    
}
