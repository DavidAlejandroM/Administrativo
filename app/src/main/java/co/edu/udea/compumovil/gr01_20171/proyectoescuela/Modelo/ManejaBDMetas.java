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

public class ManejaBDMetas {

    public static void agregarRegistro(OperacionesBaseDeDatos operador,ListaMetas nuevaMeta){
        try{
        operador.getDb().beginTransaction();

            boolean b = operador.agregarMeta(nuevaMeta);
        operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    public static void agregarRegistro(OperacionesBaseDeDatos operador, Meta meta){
        // Agregar metodo para verificar que un estudiante no tenga la meta registrada
        try{
            operador.getDb().beginTransaction();
            operador.asignarMeta(meta);
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    /*public static void agregarRegistro(OperacionesBaseDeDatos operador, Meta meta){
        try{
            operador.getDb().beginTransaction();
            ArrayList<Meta> metasEstudiante = operador.validarMetaAEstudiante(meta.getListaMetasId(),meta.getEstudianteId());
            Calendar a = Calendar.getInstance();
            //
            boolean asignarMeta = false;
            for (int i = 0; i < metasEstudiante.size(); i++){
                a.setTime(metasEstudiante.get(i).getFechaInicio());
                a.add(Calendar.DAY_OF_YEAR,metasEstudiante.get(i).getDuracion());
                if(a.getTime().compareTo(Calendar.getInstance().getTime())>0){
                    asignarMeta = true;
                }
            }
            if(!asignarMeta){
                boolean b = operador.asignarMeta(meta);
            }

            operador.getDb().setTransactionSuccessful();

        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }*/

    public static void agregarRegistro(OperacionesBaseDeDatos operador, CumplimientoMeta cumplimiento){
        try{
            operador.getDb().beginTransaction();
            operador.asignarCumplimiento(cumplimiento);
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    public static ArrayList<CumplimientoMeta> recuperaCumplimientos(OperacionesBaseDeDatos operador, int idMeta){
        return(operador.recuperarCumplimientos(idMeta));
    }

    public static void borrarMeta(OperacionesBaseDeDatos operador, int idMeta){
        try {
            operador.getDb().beginTransaction();
            operador.borrarMeta(idMeta + "");
            operador.getDb().setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {operador.getDb().endTransaction();}
    }

    public static ArrayList<ListaMetas> listarMetas(OperacionesBaseDeDatos operador){
        return(operador.listarMetas());
    }

    public static ArrayList retornarDatos(OperacionesBaseDeDatos operador, int clave, int id){
        switch (clave){
            case 0: return (operador.listarMetas());
            case 1: return (operador.listarMetasEstudiante());
            case 2: return (operador.obtenerMetasPorIdListaMtas(id));
        }
        return (null);
    }

    public static ArrayList retornarMetasPorEstudiante(OperacionesBaseDeDatos operador, int idListaMetas, int idEstudiante){
        return (operador.obtenerMetasEstudiante(idListaMetas, idEstudiante));
    }

    public static Estudiante obtenerEstudiante(OperacionesBaseDeDatos operador, int id){
        return (operador.obtenerEstudiante(id));
    }

    public static ListaMetas obtenerMeta(OperacionesBaseDeDatos operador, int id){
        return (operador.obtenerMeta(id));
    }
}