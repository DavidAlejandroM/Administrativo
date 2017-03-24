package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO;

public class Categoria
{
    private int id;
    private String nombre;
    private int tipo;

    /**
     * Constructor de la clase Categoria
     * @param nombre
     * @param tipo el tipo de dato es entero si es 1 es COGNITIVA   si es 2 es COMPORTAMENTAL
     */

    public Categoria(String nombre,int tipo) {
        this.nombre = nombre;
        this.tipo = tipo;

    }

    public Categoria(String nombre,int tipo, int id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {

        return tipo;
    }
}
