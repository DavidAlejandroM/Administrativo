package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo;

import java.util.UUID;

/**
 * CLASE ENCARGADA DE GUARDAR LOS METADATOS CORRESPONDIENTEAS A LAS TABLAS DE LA DB
 * ES IMPORTANTE UTILIZAR EL METODO QUE GENERA ID PARA AQUELLAS CLASES QUE TIENEN ESTE MÉTODO
 * DISPONIBLE
 */

public class ContratoEscuela {

    interface ColumnasEstudiante{
        String EST_IDENTIFICACION = "est_identificacion";
        String EST_NOMBRES = "est_nombres";
        String EST_APELLIDOS = "est_apellidos";
        String EST_FOTO = "est_foto";
        String EST_GRP_CURSO = "est_grp_curso";
        String EST_GRP_GRUPO = "est_grp_grupo";
        String EST_POS_COL = "est_pos_col";
        String EST_POS_FILA = "est_pos_fila";
    }

    interface ColumnasGrupo{
        String GRP_CURSO = "grp_curso";
        String GRP_GRUPO = "grp_grupo";
        String GRP_FILAS = "grp_filas";
        String GRP_COLUMNAS = "grp_columnas";
    }

    interface ColumnasMaterias{
        String MTA_ID = "mta_id";
        String MTA_NOMBRE = "mta_nombre";
    }

    interface ColumnasMateriasEstudiantes{
        String MEST_MTA_ID = "mest_mta_id";
        String MEST_EST_ID = "mest_est_id";
    }

    interface ColumnasAsistencia{
        String AST_ASISTENCIA = "ast_asistencia";
        String AST_FECHA = "ast_fecha";
        String AST_EST_ID = "ast_est_id";
    }

    interface ColumnasSeguimiento{
        String SEG_ID = "seg_id";
        String SEG_SUBC_ID = "seg_subc_id";
        String SEG_EST_ID = "seg_est_id";
        String SEG_ESTADO = "seg_estado";
        String SEG_FECHA = "seg_fecha";
        String SEG_TIPO = "seg_tipo";
        String SEG_MAT_ID = "seg_mat_id";
    }

    interface ColumnasSubcategorias{
        String SUBC_ID = "subc_id";
        String SUBC_CAT_ID = "subc_cat_id";
        String SUBC_NOMBRE = "subc_nombre";
        String SUBC_ICONO = "subc_icono";
    }

    interface ColumnasCategorias{
        String CAT_ID = "cat_id";
        String CAT_NOMBRE = "cat_nombre";
        String CAT_TIPO = "cat_tipo";
    }

    interface ColumnasMetas{
        String MET_ID = "met_id";
        String MET_LISTMET_ID = "met_listmet_id";
        String MET_ID_GPEST_ID = "met_id_gpest_id";
        String MET_FECHA_INICIO = "met_fecha_inicio";
        String MET_DURACION = "met_duracion";

    }

    interface ColumnasCumplimientoMetas{
        String CUMPLIMIENTO_ID = "cumplimiento_id";
        String MET_FECHA = "met_fecha";
        String MET_ESTADO = "met_estado";
        String MET_ID = "met_id";
    }

    interface ColumnasGrupoEstudiantes{
        String GPEST_ID = "gpest_id";
        String GPEST_NOMBRE = "gpest_nombre";
    }

    interface ColumnasListaMetas{
        String LISTMET_ID = "listmet_id";
        String LISTMET_NOMBRE = "listmet_nombre";
    }

    interface ColumnasListaGrupoEstudiantes{
        String GPLISTEST_GPEST_ID = "gplistest_gpest_id";
        String GPELISTEST_EST_IDENTIFICACION = "gplistest_est_identificacion";
    }


    public static class Estudiantes implements ColumnasEstudiante{

    }

    public static class Grupos implements ColumnasGrupo{

    }

    public static class Materias implements ColumnasMaterias{
        public static String generarIdMaterias(){
            return "MTA"+ UUID.randomUUID().toString();
        }
    }

    public static class MateriaEstudiante implements ColumnasMateriasEstudiantes{

    }

    public static class Asistencia implements ColumnasAsistencia{
        public static String generarIdAsistencia(){
            return "AST-" + UUID.randomUUID().toString();
        }
    }

    public static class Seguimiento implements ColumnasSeguimiento{
        public static String generarIdSeguimiento(){
            return "SEG-" + UUID.randomUUID().toString();
        }
    }

    public static class Subcategorias implements ColumnasSubcategorias{
        public static String generarIdSubcategorias(){
            return "SUBC-" + UUID.randomUUID().toString();
        }
    }

    public static class Metas implements ColumnasMetas{
        public static String generarIdMetas(){
            return "MET-" + UUID.randomUUID().toString();
        }
    }

    public static class ListaMetas implements ColumnasListaMetas{
        public static String generarIdListaMetas(){
            return "LISTMET-" + UUID.randomUUID().toString();
        }
    }

    public static class GrupoEstudiantes implements ColumnasGrupoEstudiantes{
        public static String generarIdGrupoEstudiantes(){
            return "GPEST-" + UUID.randomUUID().toString();
        }
    }

    public static class Categorias implements ColumnasCategorias{
        public static String generarIdCategorias(){
            return "CAT-" + UUID.randomUUID().toString();
        }
    }

    public static class ListaGrupoEstudiantes implements ColumnasListaGrupoEstudiantes{

    }
/*
    public static class CumplimientoMetas implements ColumnasCumplimientoMetas{

    }*/

    private ContratoEscuela(){}


}
