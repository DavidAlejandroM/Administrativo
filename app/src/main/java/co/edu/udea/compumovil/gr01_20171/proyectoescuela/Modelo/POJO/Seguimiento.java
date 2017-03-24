package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO;

import java.io.Serializable;
import java.util.Date;

public class Seguimiento implements Serializable{
    private int idSeg;
    private int idSubSeg;
    private int idEst;
    private String estado;
    private String fecha;
    private int tipo;
    private int idMateria;

    public Seguimiento(int idSubSeg, int idEst, String estado, String fecha, int tipo,int idMateria) {
        this.idSubSeg = idSubSeg;
        this.idEst = idEst;
        this.estado = estado;
        this.fecha = fecha;
        this.tipo = tipo;
        this.idMateria = idMateria;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdSeg() {
        return idSeg;
    }

    public void setIdSeg(int idSeg) {
        this.idSeg = idSeg;
    }

    public int getIdSubSeg() {
        return idSubSeg;
    }

    public void setIdSubSeg(int idSubSeg) {
        this.idSubSeg = idSubSeg;
    }

    public int getIdEst() {
        return idEst;
    }

    public void setIdEst(int idEst) {
        this.idEst = idEst;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}