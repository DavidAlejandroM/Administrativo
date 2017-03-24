package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO;

public class GestionEstudianteMeta {
    private boolean estado; // Correspone a si el estudiante esta seleccionado en la vista
    private int duracionMeta;
    private boolean cumplimiento; // Corresponde a si el estudiante cumplio o no alguna meta
    private boolean asignacionCumplimiento; // Corresponde si al estudiante se le asigno un cumplimiento, sea true o false

    public boolean estado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getDuracionMeta() {
        return duracionMeta;
    }

    public void setDuracionMeta(int duracionMeta) {
        this.duracionMeta = duracionMeta;
    }

    public boolean cumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(boolean cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public boolean asignacionCumplimiento() {
        return asignacionCumplimiento;
    }

    public void setAsignacionCumplimiento(boolean asignacionCumplimiento) {
        this.asignacionCumplimiento = asignacionCumplimiento;
    }
}
