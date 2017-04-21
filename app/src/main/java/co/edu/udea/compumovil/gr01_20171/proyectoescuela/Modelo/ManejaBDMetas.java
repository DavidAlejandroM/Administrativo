package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo;

import android.content.Context;
import android.graphics.Region;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.CumplimientoMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Meta;

/**
 * Clase fachada que actua como intermediario entre las vistas del submodulo de metas
 * y las clases del modelo que acceden a la base de datos.
 */
public class ManejaBDMetas {

    // Metodo sobrecargado para agregar un registro a una respectiva tabla en la base de datos
    public static void agregarRegistro(OperacionesBaseDeDatos operador,ListaMetas nuevaMeta){
        try{
        operador.getDb().beginTransaction();

            boolean b = operador.agregarMeta(nuevaMeta);
        operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    public static void agregarRegistro(OperacionesBaseDeDatos operador, Meta meta){
        try{
            operador.getDb().beginTransaction();
            operador.asignarMeta(meta);
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    public static void agregarRegistro(OperacionesBaseDeDatos operador, CumplimientoMeta cumplimiento){
        try{
            operador.getDb().beginTransaction();
            operador.asignarCumplimiento(cumplimiento);
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }
    //

    // Metodo que recupera los cumplimientos asociados a una misma meta
    public static ArrayList<CumplimientoMeta> recuperaCumplimientos(OperacionesBaseDeDatos operador, int idMeta){
        return(operador.recuperarCumplimientos(idMeta));
    }

    // Borrar un registro de la lista de metas en la base de datos
    public static void borrarMeta(OperacionesBaseDeDatos operador, int idMeta){
        try {
            operador.getDb().beginTransaction();
            operador.borrarMeta(idMeta + "");
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    // Listar todas las metas (tabla lista de metas) disponibles en la base de datos
    public static ArrayList<ListaMetas> listarMetas(OperacionesBaseDeDatos operador){
        return(operador.listarMetas());
    }

    // Metodo para registros datos de la base de datos de acuerdo a una clave entrada como parametro
    public static ArrayList retornarDatos(OperacionesBaseDeDatos operador, int clave, int id){
        switch (clave){
            case 0: return (operador.listarMetas());
            case 1: return (operador.listarMetasEstudiante());
            case 2: return (operador.obtenerMetasPorIdListaMtas(id));
        }
        return (null);
    }

    // Se obtienen todas los registros de metas asocidos a la misma meta y el mismo estudiante
    public static ArrayList retornarMetasPorEstudiante(OperacionesBaseDeDatos operador, int idListaMetas, int idEstudiante){
        return (operador.obtenerMetasEstudiante(idListaMetas, idEstudiante));
    }

    // Se recupera el estudiante identificado con la llave primaria ingresada como parametro
    public static Estudiante obtenerEstudiante(OperacionesBaseDeDatos operador, int id){
        return (operador.obtenerEstudiante(id));
    }

    // Se recupera el registro de lista metas correspondiente a la clave primaria ingresada como parametro
    public static ListaMetas obtenerMeta(OperacionesBaseDeDatos operador, int id){
        return (operador.obtenerMeta(id));
    }
}