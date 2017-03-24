package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
/**
 *
 * @author andres.pinov
 */
public class Subcategoria implements Serializable{
    private int id;
    private int idCat;
    private String nombre;
    private String icono;
    private int valorSi;
    private int valorNo;

    public Subcategoria(int idCat, String nombre) {
        this.idCat = idCat;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getValorSi() { return valorSi;  }

    public void setValorSi(int valorSi) { this.valorSi = valorSi; }

    public int getValorNo() {
        return valorNo;
    }

    public void setValorNo(int valorNo) {
        this.valorNo = valorNo;
    }
}
