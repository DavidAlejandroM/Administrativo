package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.AlertDialog;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;

/**
 * Created by DEIRY on 21/03/2017.
 */

public class EstadisticaCognitiva {

    OperacionesBaseDeDatos manager;
    private int idEstudiante;
    private Materia materia;
    private ArrayList<Categoria> categorias;


    public EstadisticaCognitiva(OperacionesBaseDeDatos manager) {
        this.manager= manager;
        categorias = manager.obtenerCategorias(1);


    }

    /**
     * Lista el nombre de todas las categorias
     * @param categorias Lista de todas los objetos categorias
     * @return Lista de tipo String almacenando el nombre de todas las categorias
     */
    public ArrayList<String> listarCategorias(ArrayList<Categoria> categorias){
        ArrayList<String> nombreCategorias = new ArrayList<>();
        for (int i=0;i< categorias.size();i++){
            nombreCategorias.add(categorias.get(i).getNombre());

        }
        return nombreCategorias;
    }

    /**
     * Lista el nombre de todas las categorias
     * @param estudiantes Lista de todas los objetos categorias
     * @return Lista de tipo String almacenando el nombre de todas las categorias
     */
    public ArrayList<String> listarEstudiantes(ArrayList<Estudiante> estudiantes){
        ArrayList<String> nombreEstudiante = new ArrayList<>();
        for (int i=0;i< estudiantes.size();i++){
            nombreEstudiante.add(estudiantes.get(i).getNombres());

        }
        return nombreEstudiante;
    }


    /**
     *Lista el nombre de todas las subcategorias que contiene la categoria, haciendo la consulta en la base de datos
     *
     * @return Una lista de todos los nombres de las subcategorias
     */
    public ArrayList<String> listarSubCategorias(int id){
        ArrayList<String> nombreSubcategoria = new ArrayList<>();
        ArrayList<Subcategoria> subcategorias = manager.obtenerSubCategoriasFromCategoriaId(id);

        for (int i=0;i< subcategorias.size();i++){
            nombreSubcategoria.add(subcategorias.get(i).getNombre());
        }

        return  nombreSubcategoria;
    }

    public ArrayList<Integer> asignarValoresNo(ArrayList<Subcategoria> subcategorias){
        ArrayList<Integer> valsNo = new ArrayList<>();
        for (int i=0; i< subcategorias.size();i++){
            int valNo = (int)manager.countSeguimientoFromIdSubcategoriIdEstudiante(subcategorias.get(i).getId(),idEstudiante,"no");
            subcategorias.get(i).setValorNo(valNo);
            valsNo.add(valNo);
        }
        return valsNo;

    }

    public ArrayList<Integer> asignarValoresSi(ArrayList<Subcategoria> subcategorias){
        ArrayList<Integer> valsSi = new ArrayList<>();
        for (int i=0; i< subcategorias.size();i++){
            int valSi = (int)manager.countSeguimientoFromIdSubcategoriIdEstudiante(subcategorias.get(i).getId(),idEstudiante,"si");

            valsSi.add(valSi);
        }
        return valsSi;

    }



/*
    */
/**
 *Metodo encargado de obtener la cantidad de SI que se encuentra en esa subcategoria
 * @param sub Subcategoria para obtener los valores de si que contiene
 *
 * @return  Cantidad de SI que hay en esa categoria
 *//*


    public int obtenerValSiSubcategoria (Subcategoria sub){
        return (int)manager.countSeguimientoFromIdSubcategoriIdEstudiante(sub.getId(),idEstudiante,"si");
    }



    */
/**
 * Metodo encargado de obtener la cantidad de NO que se encuentra en esa subcategoria
 * @param sub ubcategoria para obtener los valores de NO que contiene
 * @return antidad de NO que hay en esa categoria
 *//*

    public int obtenerValNoSubcategoria(Subcategoria sub){
        return (int)manager.countSeguimientoFromIdSubcategoriIdEstudiante(sub.getId(),idEstudiante,"no");
    }
*/


    /**
     *Metodo encargado de obtener la cantidad de SI que se encuentra en esa subcategoria
     * @param sub Subcategoria para obtener los valores de si que contiene     *
     * @return  Cantidad de SI que hay en esa categoria
     */

    public int obtenerValSiSubcategoria(Subcategoria sub, int idEstudiante){
        return (int)manager.countSeguimientoFromIdSubcategoriIdEstudianteIdMateria(sub.getId(),idEstudiante,materia.getId(),"si");
    }

    /**
     * Metodo encargado de obtener la cantidad de NO que se encuentra en esa subcategoria
     * @param sub ubcategoria para obtener los valores de NO que contiene
     * @return antidad de NO que hay en esa categoria
     */
    public int obtenerValNoSubcategoria (Subcategoria sub, int idEstudiante){
        return (int)manager.countSeguimientoFromIdSubcategoriIdEstudianteIdMateria(sub.getId(),idEstudiante,materia.getId(),"no" );
    }

    /**
     * Metodo asignado para contar las veces que gano (SI) por cada subcategoria, y asi mismo
     * con todas las subcategorias, que se encuentra en la categoria correspondiente
     * @param subcategorias Lista de todas las subcategorias de la categoria correspondiente
     * @return Cantidad de veces que gano SI en todas las subcategorias
     */
    public int obtenerValSiSubcategorias(ArrayList<Subcategoria> subcategorias, int idEstudiante){
        int gano=0;
        for (int i=0;i<subcategorias.size();i++){
            int valSi = obtenerValSiSubcategoria(subcategorias.get(i),idEstudiante);
            int valNo = obtenerValNoSubcategoria(subcategorias.get(i),idEstudiante);

            if (valSi ==0 && valNo==0){
                return 0;
            }
            if (valSi>= valNo){
                gano++;
            }
        }
        return gano;
    }

    /**
     * Metodo asignado para contar las veces que no cumplio (NO) por cada subcategoria, y asi mismo
     * con todas las subcategorias, que se encuentra en la categoria correspondiente
     * @param subcategorias Lista de todas las subcategorias de la categoria correspondiente
     * @return Cantidad de veces que no cumplio NO en todas las subcategorias
     */
    public int obtenerValNoSubcategorias(ArrayList<Subcategoria> subcategorias, int idEstudiante){
        int perdio=0;
        for (int i=0;i<subcategorias.size();i++){
            int valSi = obtenerValSiSubcategoria(subcategorias.get(i),idEstudiante);
            int valNo = obtenerValNoSubcategoria(subcategorias.get(i),idEstudiante);

            if (valSi ==0 && valNo==0){
                return 0;
            }
            if (valNo> valSi){
                perdio++;
            }
        }
        return perdio;
    }

    /**
     * Metodo asignado para obtener los valores de Si de todas las categorias para graficar
     * @param categorias Categorias de la taxonomia de Bloom
     * @return Un lista de todos los valores SI por cada categoria
     */
    public ArrayList<Integer> obtenerValSiCategorias(ArrayList<Categoria> categorias, int idEstudiante){

        this.idEstudiante = idEstudiante;
        ArrayList<Integer> valSi = new ArrayList<>();
        ArrayList<Subcategoria> subcategorias;
        for (int i=0;i<categorias.size();i++){
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            valSi.add(obtenerValSiSubcategorias(subcategorias, idEstudiante));
        }
        return valSi;
    }

    /**
     * Metodo asignado para obtener los valores de NO de todas las categorias para graficar
     * @param categorias Categorias de la taxonomia de Bloom
     * @return Un lista de todos los valores NO por cada categoria
     */
    public ArrayList<Integer> obtenerValNoCategorias(ArrayList<Categoria> categorias, int idEstudiante){

        this.idEstudiante = idEstudiante;
        ArrayList<Integer> valNo = new ArrayList<>();
        ArrayList<Subcategoria> subcategorias;
        for (int i=0;i<categorias.size();i++){
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            valNo.add(obtenerValNoSubcategorias(subcategorias, idEstudiante));
        }
        return valNo;
    }

    int obtenerSiCategorias(ArrayList<Categoria> categorias, int idEstudiante){

        this.idEstudiante = idEstudiante;
        int gano =0;
        int valSi,valNo;
        ArrayList<Subcategoria> subcategorias;

        for (int i=0; i<categorias.size();i++){
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            valSi = obtenerValSiSubcategorias(subcategorias,idEstudiante);
            valNo = obtenerValNoSubcategorias(subcategorias, idEstudiante);
            if (valSi ==0 && valNo==0){
                return 0;
            }
            if (valSi>= valNo){
                gano++;
            }

        }
        return gano;
    }

    int obtenerNoCategorias(ArrayList<Categoria> categorias, int idEstudiante){

        this.idEstudiante = idEstudiante;
        int perdio =0;
        int valSi,valNo;
        ArrayList<Subcategoria> subcategorias;

        for (int i=0; i<categorias.size();i++){
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            valSi = obtenerValSiSubcategorias(subcategorias, idEstudiante);
            valNo = obtenerValNoSubcategorias(subcategorias, idEstudiante);
            if (valSi ==0 && valNo==0){
                return 0;
            }
            if (valNo>valSi ){
                perdio++;
            }

        }
        return perdio;
    }

    public ArrayList<Integer> obtenerValSiGeneral(ArrayList<Estudiante> estudiantes){

        ArrayList<Integer> valSi= new ArrayList<>();

        this.categorias = manager.obtenerCategorias(1);
        for (int i=0;i<estudiantes.size();i++){
            int idEstudiante = estudiantes.get(i).getIdentificacion();
            valSi.add(obtenerSiCategorias(categorias,idEstudiante ));
        }

        return valSi;
    }

    public ArrayList<Integer> obtenerValNoGeneral(ArrayList<Estudiante> estudiantes){
        ArrayList<Integer> valNo= new ArrayList<>();

        categorias = manager.obtenerCategorias(1);
        for (int i=0;i<estudiantes.size();i++){
            int idEstudiante = estudiantes.get(i).getIdentificacion();
            valNo.add(obtenerNoCategorias(categorias,idEstudiante ));
        }

        return valNo;

    }



    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

}
