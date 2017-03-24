package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO;

import java.io.Serializable;

public class Materia implements Serializable{
    private int id;
    private String nombre;

    public Materia(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int idMateria) {
        this.id = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
