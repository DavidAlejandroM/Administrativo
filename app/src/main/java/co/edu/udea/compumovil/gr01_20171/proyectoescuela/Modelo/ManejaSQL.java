package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * CLASE ENCARGA DE LA CREACIÓN DE LA DB
 */

public class ManejaSQL extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "escuela.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    public ManejaSQL(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    interface Tablas{
        String TBL_ESTUDIANTE = "tbl_estudiante";
        String TBL_GRUPO = "tbl_grupo";
        String TBL_MATERIAS = "tbl_materias";
        String TBL_MATERIAS_ESTUDIANTE = "tbl_materias_estudiante";
        String TBL_SEGUIMIENTO = "tbl_seguimiento";
        String TBL_SUBCATEGORIAS = "tbl_subcategorias";
        String TBL_CATEGORIAS = "tbl_categorias";
        String TBL_ASISTENCIA = "tbl_asistencia";
        String TBL_META = "tbl_meta";
        String TBL_LISTA_METAS = "tbl_lista_metas";
        String TBL_GRUPOS_LISTA_ESTUDIANTES = "tbl_grupos_lista_estudiantes";
        String TBL_GRUPOS_ESTUDIANTES = "tbl_grupos_estudiantes";
        String TBL_CUMPLIMIENTO_METAS = "tbl_cumplimiento_metas";
    }

    /**
     * CLASE QUE GENERA LAS REFERENCIAS DE LAS LLAVES FORANEAS
     * */
   /* interface Referencias{

        String ID_CATEGORIAS = String.format("REFERENCES %s(%s)",
                Tablas.TBL_CATEGORIAS, ContratoEscuela.Categorias.CAT_ID);

        String ID_SUBCATEGORIAS = String.format("REFERENCES %s(%s)",
                Tablas.TBL_SUBCATEGORIAS, ContratoEscuela.Subcategorias.SUBC_CAT_ID);

        String ID_SEGUIMIENTO = String.format("REFERENCES %s(%s)",
                Tablas.TBL_SEGUIMIENTO, ContratoEscuela.co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento.SEG_ID);

        String ID_LISTA_METAS = String.format("REFERENCES %s(%s)",
                Tablas.TBL_LISTA_METAS, ContratoEscuela.co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas.LISTMET_ID);

        String ID_METAS = String.format("REFERENCES %s(%s)",
                Tablas.TBL_META, ContratoEscuela.Metas.MET_ID);

        String ID_GRUPOS_ESTUDIANTES = String.format("REFERENCES %s(%s)",
                Tablas.TBL_GRUPOS_ESTUDIANTES, ContratoEscuela.co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.GrupoEstudiantes.GPEST_ID);

        String ID_MATERIAS = String.format("REFERENCES %s(%s)",
                Tablas.TBL_MATERIAS, ContratoEscuela.Materias.MTA_ID);
    }*/

    /**
     * CLASE QUE GENERA LAS SENTENCIAS PARA LA CREACION DE LAS TABLAS Y SUS RESPECTIVAS
     * COLUMNAS EN LA BASE DE DATOS.
     *
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String s = "PRAGMA foreign_kets=ON";
        db.execSQL(s);

        //GRUPO
        s = String.format("CREATE TABLE %s ( %s INTEGER ," +
                        "%s VARCHAR(2),%s INTEGER, %s INTEGER )",
                Tablas.TBL_GRUPO, ContratoEscuela.Grupos.GRP_CURSO, ContratoEscuela.Grupos.GRP_GRUPO,
                ContratoEscuela.Grupos.GRP_FILAS, ContratoEscuela.Grupos.GRP_COLUMNAS);
        db.execSQL(s);

        //ESTUDIANTE**
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY ," +
                        "%s VARCHAR(20),%s VARCHAR(20),%s VARCHAR(50),%s INTEGER,%s VARCHAR(2)" +
                        ",%s INTEGER,%s INTEGER )",
                Tablas.TBL_ESTUDIANTE, ContratoEscuela.Estudiantes.EST_IDENTIFICACION,
                ContratoEscuela.Estudiantes.EST_NOMBRES,ContratoEscuela.Estudiantes.EST_APELLIDOS,
                ContratoEscuela.Estudiantes.EST_FOTO,ContratoEscuela.Estudiantes.EST_GRP_CURSO,
                ContratoEscuela.Estudiantes.EST_GRP_GRUPO, ContratoEscuela.Estudiantes.EST_POS_FILA,
                ContratoEscuela.Estudiantes.EST_POS_COL);
        db.execSQL(s);

        //CATEGORIA
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "%s VARCHAR(50), %s INTEGER)",
                Tablas.TBL_CATEGORIAS,
                ContratoEscuela.Categorias.CAT_ID,
                ContratoEscuela.Categorias.CAT_NOMBRE,
                ContratoEscuela.Categorias.CAT_TIPO);
        db.execSQL(s);

        //SUBCATEGORIAS**
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER,%s VARCHAR(50),%s VARCHAR(50) )",
                Tablas.TBL_SUBCATEGORIAS,
                ContratoEscuela.Subcategorias.SUBC_ID, ContratoEscuela.Subcategorias.SUBC_CAT_ID,
                ContratoEscuela.Subcategorias.SUBC_NOMBRE, ContratoEscuela.Subcategorias.SUBC_ICONO);
        db.execSQL(s);

        //SEGUIMIENTO**
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER,%s INTEGER,%s VARCHAR(30),%s DATE,%s INTEGER(1),%s INTEGER(2) )",
                Tablas.TBL_SEGUIMIENTO, ContratoEscuela.Seguimiento.SEG_ID, ContratoEscuela.Seguimiento.SEG_SUBC_ID,
                ContratoEscuela.Seguimiento.SEG_EST_ID, ContratoEscuela.Seguimiento.SEG_ESTADO,
                ContratoEscuela.Seguimiento.SEG_FECHA, ContratoEscuela.Seguimiento.SEG_TIPO,
                ContratoEscuela.Seguimiento.SEG_MAT_ID);
        db.execSQL(s);

        //LISTAMETAS
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "%s VARCHAR(50))",
                Tablas.TBL_LISTA_METAS, ContratoEscuela.ListaMetas.LISTMET_ID,
                ContratoEscuela.ListaMetas.LISTMET_NOMBRE);
        db.execSQL(s);

        //GRUPOSESTUDIANTES
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "%s VARCHAR(50))",
                Tablas.TBL_GRUPOS_ESTUDIANTES, ContratoEscuela.GrupoEstudiantes.GPEST_ID,
                ContratoEscuela.GrupoEstudiantes.GPEST_NOMBRE);
        db.execSQL(s);

        //METAS**
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "%s INTEGER,%s INTEGER,%s DATE,%s INTEGER )",
                Tablas.TBL_META, ContratoEscuela.Metas.MET_ID, ContratoEscuela.Metas.MET_LISTMET_ID
                , ContratoEscuela.Metas.MET_ID_GPEST_ID, ContratoEscuela.Metas.MET_FECHA_INICIO,
                ContratoEscuela.Metas.MET_DURACION);
        db.execSQL(s);

        //ASISTENCIA
        s = String.format("CREATE TABLE %s ( %s VARCHAR(40) ," +
                        "%s INTEGER, %s VARCHAR(20) )",
                Tablas.TBL_ASISTENCIA, ContratoEscuela.Asistencia.AST_FECHA, ContratoEscuela.Asistencia.AST_EST_ID,
                ContratoEscuela.Asistencia.AST_ASISTENCIA);
        db.execSQL(s);

        //GRUPOSLISTAESTUDIANTES
        s = String.format("CREATE TABLE %s ( %s INTEGER  ," +
                        "%s INTEGER)",
                Tablas.TBL_GRUPOS_LISTA_ESTUDIANTES, ContratoEscuela.ListaGrupoEstudiantes.GPELISTEST_EST_IDENTIFICACION,
                ContratoEscuela.ListaGrupoEstudiantes.GPLISTEST_GPEST_ID);
        db.execSQL(s);

        //MATERIAS
        s = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s VARCHAR(20))",
                Tablas.TBL_MATERIAS, ContratoEscuela.Materias.MTA_ID,
                ContratoEscuela.Materias.MTA_NOMBRE);
        db.execSQL(s);

        //MATERIASESTUDIANTES
        s=String.format("CREATE TABLE %s ( %s INTEGER ," +
                        "%s INTEGER)",
                Tablas.TBL_MATERIAS_ESTUDIANTE, ContratoEscuela.MateriaEstudiante.MEST_MTA_ID,
                ContratoEscuela.MateriaEstudiante.MEST_EST_ID);
        db.execSQL(s);

        /**
        *INSERTANDO CATEGORIAS DE COGNITIVO
        */
        //APLICAR
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.aplicar));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);
        //ANALIZAR
        valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.analizar));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);
        //COMPRENDER
        valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.comprender));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);
        //CREAR
        valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.crear));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);
        //RECORDAR
        valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.recordar));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);
        //EVALUAR
        valores = new ContentValues();
        valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, contexto.getResources().getString(R.string.evaluar));
        valores.put(ContratoEscuela.Categorias.CAT_TIPO,1);
        db.insert(Tablas.TBL_CATEGORIAS,null,valores);

        //INSERTANDO MATERIA GENERAL
        valores = new ContentValues();
        valores.put(ContratoEscuela.Materias.MTA_NOMBRE, "GENERAL");
        db.insertOrThrow(ManejaSQL.Tablas.TBL_MATERIAS, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_ASISTENCIA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_CATEGORIAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_GRUPO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_GRUPOS_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_GRUPOS_LISTA_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_LISTA_METAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_MATERIAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_MATERIAS_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_META);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_SEGUIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TBL_SUBCATEGORIAS);

        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }
}
