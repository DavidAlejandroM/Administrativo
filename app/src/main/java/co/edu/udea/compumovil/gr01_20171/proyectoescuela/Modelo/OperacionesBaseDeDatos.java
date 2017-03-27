package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.*;

/**
 * Clase auxiliar que implementa a {@link ManejaSQL} para llevar a cabo el CRUD
 * sobre las entidades existentes.
 * ¡¡¡¡¡¡IMPORTANTE FALTA IMPLEMENTAR LAS OPERACIONES CORRESPONDIENTES A LAS TABLAS DE
 *  MATERIASXESTUDIANTE!!!!!!!
 */


public final class OperacionesBaseDeDatos {

    private static ManejaSQL baseDatos;

    private static OperacionesBaseDeDatos instancia = new OperacionesBaseDeDatos();

    private OperacionesBaseDeDatos() {
    }

    public static OperacionesBaseDeDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new ManejaSQL(contexto);
        }
        return instancia;
    }


    /**
     * Método para agregar estudiantes a la tabala de estudiantes
     */

    public void insertarEstudiante(co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante estudiante) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Estudiantes.EST_IDENTIFICACION, estudiante.getIdentificacion());
        valores.put(ContratoEscuela.Estudiantes.EST_NOMBRES, estudiante.getNombres());
        valores.put(ContratoEscuela.Estudiantes.EST_APELLIDOS, estudiante.getApellidos());
        valores.put(ContratoEscuela.Estudiantes.EST_FOTO, estudiante.getFoto());
        valores.put(ContratoEscuela.Estudiantes.EST_GRP_CURSO, estudiante.getCurso());
        valores.put(ContratoEscuela.Estudiantes.EST_GRP_GRUPO, estudiante.getGrupo());
        valores.put(ContratoEscuela.Estudiantes.EST_POS_COL, estudiante.getPosColumna());
        valores.put(ContratoEscuela.Estudiantes.EST_POS_FILA, estudiante.getPosFila());
        db.insertOrThrow(ManejaSQL.Tablas.TBL_ESTUDIANTE, null, valores);
    }

    public boolean insertarEstudiante2(co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante estudiante) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String query = String.format("SELECT MAX(%s) %s FROM %s WHERE %s = %s AND %s = '%s'",
                ContratoEscuela.ColumnasEstudiante.EST_POS_FILA,
                ContratoEscuela.ColumnasEstudiante.EST_POS_FILA,
                ManejaSQL.Tablas.TBL_ESTUDIANTE,
                ContratoEscuela.ColumnasEstudiante.EST_GRP_CURSO,
                estudiante.getCurso(),
                ContratoEscuela.ColumnasEstudiante.EST_GRP_GRUPO,
                estudiante.getGrupo());
        int filamax = 0;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            filamax = cursor.getInt(0);
        }

        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Estudiantes.EST_IDENTIFICACION, estudiante.getIdentificacion());
        valores.put(ContratoEscuela.Estudiantes.EST_NOMBRES, estudiante.getNombres());
        valores.put(ContratoEscuela.Estudiantes.EST_APELLIDOS, estudiante.getApellidos());
        valores.put(ContratoEscuela.Estudiantes.EST_FOTO, estudiante.getFoto());
        valores.put(ContratoEscuela.Estudiantes.EST_GRP_CURSO, estudiante.getCurso());
        valores.put(ContratoEscuela.Estudiantes.EST_GRP_GRUPO, estudiante.getGrupo());
        valores.put(ContratoEscuela.Estudiantes.EST_POS_COL, estudiante.getPosColumna());
        valores.put(ContratoEscuela.Estudiantes.EST_POS_FILA, filamax+1);
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_ESTUDIANTE, null, valores);
        if (response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para agregar grupos a la tabla de grupos
     */

    public void insertarGrupo(Grupo grupo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Grupos.GRP_CURSO, grupo.getCurso());
        valores.put(ContratoEscuela.Grupos.GRP_GRUPO, grupo.getGrupo());
        valores.put(ContratoEscuela.Grupos.GRP_FILAS, grupo.getFilas());
        valores.put(ContratoEscuela.Grupos.GRP_COLUMNAS, grupo.getColumnas());
        db.insertOrThrow(ManejaSQL.Tablas.TBL_GRUPO, null, valores);
    }

    /**
     * Método para agregar categorias a la tabla de categorias
     */

    public void insertarCategorias(Categoria categoria) {

        if(obtenerCategoria(categoria.getTipo(),categoria.getNombre()) == null)
        {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put(ContratoEscuela.Categorias.CAT_NOMBRE, categoria.getNombre());
            valores.put(ContratoEscuela.Categorias.CAT_TIPO,categoria.getTipo());
            //db.insertOrThrow(ManejaSQL.Tablas.TBL_CATEGORIAS, null, valores);
            long l = db.insert(ManejaSQL.Tablas.TBL_CATEGORIAS,null,valores);
            long d = l;
        }


    }

    /**
     * Método para insertar subcategorias en la tabla correspondiente
     */
    public boolean insertarSubCategorias(Subcategoria subcategoria) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Subcategorias.SUBC_CAT_ID, subcategoria.getIdCat());
        valores.put(ContratoEscuela.Subcategorias.SUBC_NOMBRE, subcategoria.getNombre());
        valores.put(ContratoEscuela.Subcategorias.SUBC_ICONO, subcategoria.getIcono());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_SUBCATEGORIAS, null, valores);

        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para materias a la base de datos
     */

    public boolean insertarMaterias(Materia materia) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Materias.MTA_NOMBRE, materia.getNombre());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_MATERIAS, null, valores);

        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para agregar asistencia a la tlaba de asistencia
     */
    public void insertarAsistencia(Asistencia asistencia) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Asistencia.AST_EST_ID, asistencia.getIdEstudiante());
        valores.put(ContratoEscuela.Asistencia.AST_FECHA, asistencia.getFecha().toString());
        valores.put(ContratoEscuela.Asistencia.AST_ASISTENCIA, asistencia.getAsistencia());
        db.insertOrThrow(ManejaSQL.Tablas.TBL_ASISTENCIA, null, valores);
    }

    /*
     *Método para agregar una nueva meta a la lista de metas (tbl_lista_metas)
     */
    public boolean agregarMeta(ListaMetas nuevaMeta) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.ListaMetas.LISTMET_NOMBRE,nuevaMeta.getNombre());
        valores.put(ContratoEscuela.ListaMetas.MET_TIPO, nuevaMeta.getTipo());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_LISTA_METAS,null,valores);

        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para asignar una meta a un estudiante (tbl_metas)
     */
    public boolean asignarMeta(Meta meta){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Metas.EST_ID, meta.getEstudianteId());
        valores.put(ContratoEscuela.Metas.LISTMETA_ID, meta.getListaMetasId());
        valores.put(ContratoEscuela.Metas.MET_FECHA_INICIO, String.valueOf(meta.getFechaInicio()));
        valores.put(ContratoEscuela.Metas.MET_DURACION, meta.getDuracion());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_METAS,null,valores);

        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para asignar un cumplimiento a una meta
     */
    public boolean asignarCumplimiento(CumplimientoMeta cumplimiento){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.CumplimientoMetas.MET_ID, cumplimiento.getMetaId());
        valores.put(ContratoEscuela.CumplimientoMetas.MET_FECHA, String.valueOf(cumplimiento.getFecha()));
        valores.put(ContratoEscuela.CumplimientoMetas.MET_ESTADO, cumplimiento.getEstado());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_CUMPLIMIENTO_METAS,null,valores);

        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Método para borrar una meta de la tabla ListaMetas
     */
    public boolean borrarMeta(String idMeta) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?", ContratoEscuela.ListaMetas.LISTMET_ID);
        String[] whereArgs = {idMeta};
        int resultado = db.delete(ManejaSQL.Tablas.TBL_LISTA_METAS, whereClause, whereArgs);
        return resultado > 0;
    }

    /**
     * Método para insertar seguimiento en la tabla de seguimiento
     */
    public boolean insertarSeguimiento(Seguimiento seguimiento) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Seguimiento.SEG_SUBC_ID, seguimiento.getIdSubSeg());
        valores.put(ContratoEscuela.Seguimiento.SEG_EST_ID, seguimiento.getIdEst());
        valores.put(ContratoEscuela.Seguimiento.SEG_ESTADO, seguimiento.getEstado());
        valores.put(ContratoEscuela.Seguimiento.SEG_FECHA, seguimiento.getFecha().toString());
        valores.put(ContratoEscuela.Seguimiento.SEG_TIPO, seguimiento.getTipo());
        valores.put(ContratoEscuela.Seguimiento.SEG_MAT_ID, seguimiento.getIdMateria());
        long response = db.insertOrThrow(ManejaSQL.Tablas.TBL_SEGUIMIENTO, null, valores);
        if(response != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void actualizarFila(int identificacionEstudiante, int posicionFila)
    {
        if(identificacionEstudiante != 0) {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put(ContratoEscuela.ColumnasEstudiante.EST_POS_FILA, posicionFila);
            db.update(ManejaSQL.Tablas.TBL_ESTUDIANTE,
                    valores,
                    ContratoEscuela.Estudiantes.EST_IDENTIFICACION +" = "+String.valueOf(identificacionEstudiante),
                    null);
        }
    }

    /**
     * Método para obtener datos de la base de datos es necesario pasar por parametro
     * la secuencia que indica que datos se deberían retornar.
     */
    public Cursor obtenerDataDB(String sentence) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        return db.rawQuery(sentence, null);
    }

    /**
     * Obtiene los registros de los estudiantes que pertenezcan al grupo pasado por parametro
     *
     * */
    public ArrayList<Estudiante> obtenerEstudiantesDB(Grupo grupo) {
        String consulta;

        if(grupo != null){

        consulta = String.format("SELECT %s.* FROM %s WHERE (%s=%s AND %s='%s') ORDER BY %s ASC",ManejaSQL.Tablas.TBL_ESTUDIANTE
                ,ManejaSQL.Tablas.TBL_ESTUDIANTE,
                ContratoEscuela.Estudiantes.EST_GRP_CURSO,grupo.getCurso(),
                ContratoEscuela.Estudiantes.EST_GRP_GRUPO,grupo.getGrupo(),
                ContratoEscuela.Estudiantes.EST_POS_FILA);

        }else{
         consulta = String.format("SELECT * FROM %s ORDER BY %s ASC", ManejaSQL.Tablas.TBL_ESTUDIANTE,
                 ContratoEscuela.Estudiantes.EST_POS_FILA);}

        Cursor estudiantes = obtenerDataDB(consulta);

        estudiantes.getCount();
        Estudiante estudiante;
        ArrayList<Estudiante> estudiantesAL = new ArrayList<>();
        estudiantesAL.clear();

        if(estudiantes.moveToFirst()){
            do{
            estudiante = new Estudiante(estudiantes.getInt(0),estudiantes.getString(1),estudiantes.getString(2),
                    estudiantes.getString(3),estudiantes.getInt(4),estudiantes.getString(5),estudiantes.getInt(6),

                    estudiantes.getInt(7));
            estudiantesAL.add(estudiante);
            }while(estudiantes.moveToNext());
        }
          return estudiantesAL;
        }


    /**
     *
     * @return
     */
    public ArrayList<Grupo> obtenerGruposDB() {
        String consulta = "SELECT * FROM tbl_grupo";
        Cursor grupos = obtenerDataDB(consulta);
        Grupo grupo;
        ArrayList<Grupo> grupoAL = new ArrayList<>();
        if(grupos.getCount() != 0)
        {
            if (grupos.moveToFirst()) {
                do {
                    grupo = new Grupo(grupos.getInt(0), grupos.getString(1),grupos.getInt(2),grupos.getInt(3));
                    grupoAL.add(grupo);
                } while (grupos.moveToNext());
            }

        }
        return grupoAL;
    }

    /**
     * Obtiene todos los registros de asistencia de la base de datos
     * */
    public ArrayList<Asistencia> obtenerAsistencia(){
        String consulta = String.format("SELECT * FROM %s",ManejaSQL.Tablas.TBL_ASISTENCIA);
        Cursor asistencia = obtenerDataDB(consulta);
        Asistencia asist;
        ArrayList<Asistencia> asistenciaAL = new ArrayList<>();
        if(asistencia.moveToFirst()){
            do{
                asist = new Asistencia(asistencia.getString(0),asistencia.getInt(1),asistencia.getString(2));
                asistenciaAL.add(asist);
            }while(asistencia.moveToNext());
        }
        return asistenciaAL;
    }

    /**
     * Se actualizan los valores pertenecientes al registro de asistencia cuyos campos idEstudiante y fecha
     * coincidan con los ingresados por parametro a traves del objeto asistencia.
     * */
    public boolean actualizarAsistencia(Asistencia asistencia){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ContratoEscuela.Asistencia.AST_FECHA,asistencia.getFecha());
        valores.put(ContratoEscuela.Asistencia.AST_EST_ID,asistencia.getIdEstudiante());
        valores.put(ContratoEscuela.Asistencia.AST_ASISTENCIA,asistencia.getAsistencia());
        String selection = String.format("%s=? AND %s=?",
                ContratoEscuela.Asistencia.AST_FECHA, ContratoEscuela.Asistencia.AST_EST_ID);
        final String[] whereArgs = {asistencia.getFecha(),Integer.toString(asistencia.getIdEstudiante())};

        int resultado = db.update(ManejaSQL.Tablas.TBL_ASISTENCIA, valores, selection, whereArgs);

        return resultado > 0;

    }

    /**
     * Obtiene los registros de asistencias pertenecientes al estudiante cuyo id ha sido pasado por parametro
     *
     * */
    public ArrayList<Asistencia> obtenerAsistenciaEstudiante(String idEstudiante){
        String consulta;

        consulta = String.format("SELECT %s.* FROM %s WHERE (%s=%s)",ManejaSQL.Tablas.TBL_ASISTENCIA
                    ,ManejaSQL.Tablas.TBL_ASISTENCIA,
                    ContratoEscuela.Asistencia.AST_EST_ID,idEstudiante);

        Cursor asistencias = obtenerDataDB(consulta);
        Asistencia asistencia;
        ArrayList<Asistencia> asistenciasAL = new ArrayList<>();
        asistenciasAL.clear();

        if(asistencias.moveToFirst()){
            do{
                asistencia = new Asistencia(asistencias.getString(0),asistencias.getInt(1),asistencias.getString(2));
                asistenciasAL.add(asistencia);
            }while(asistencias.moveToNext());
        }
        return asistenciasAL;
    }

    /**
     * Retorna el registro de fecha de un día especifico para un estudiante
     * @param idEstudiante
     * @return
     */
    public Asistencia obtenerAsistenciaEstudianteDia(String idEstudiante,String fecha){
        String consulta;

        consulta = String.format("SELECT %s.* FROM %s WHERE (%s=%s AND %s='%s')",ManejaSQL.Tablas.TBL_ASISTENCIA
                ,ManejaSQL.Tablas.TBL_ASISTENCIA,
                ContratoEscuela.Asistencia.AST_EST_ID,idEstudiante,
                ContratoEscuela.Asistencia.AST_FECHA,fecha);

        Cursor asistencias = obtenerDataDB(consulta);
        Asistencia asistencia= new Asistencia();

        if(asistencias.moveToFirst()){
            do{
                asistencia = new Asistencia(asistencias.getString(0),asistencias.getInt(1),asistencias.getString(2));
            }while(asistencias.moveToNext());
        }
        return asistencia;
    }

    /**
     * Borra el registro del estudiante cuyo id haya sido pasado por parametros de la tabla tbl_estudiantes
     * */
    public boolean borrarEstudiante(String idEstudiante){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?", ContratoEscuela.Estudiantes.EST_IDENTIFICACION);
        String[] whereArgs = {idEstudiante};
        int resultado = db.delete(ManejaSQL.Tablas.TBL_ESTUDIANTE, whereClause, whereArgs);
        return resultado > 0;
    }

    // Recuperar Lista de Metas Creadas
    public ArrayList<ListaMetas> listarMetas(){
        String consulta = "SELECT * FROM tbl_lista_metas";
        Cursor cursor = obtenerDataDB(consulta);
        ListaMetas meta;
        ArrayList<ListaMetas> lista = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                meta = new ListaMetas(cursor.getString(1), cursor.getString(2));
                meta.setId(cursor.getInt(0));
                lista.add(meta);
            }while(cursor.moveToNext());
        }
        return lista;
    }

    // Recuperar Lista de Metas Asignadas
    public ArrayList<Meta> listarMetasEstudiante(){
        String consulta = "SELECT * FROM tbl_meta";
        Cursor cursor = obtenerDataDB(consulta);
        Meta meta;
        ArrayList<Meta> metasEstudiante = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                meta = new Meta(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)), new Date(),
                        Integer.parseInt(cursor.getString(4)));

                String s= cursor.getString(3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                Date d=new Date();
                try {
                    d=  dateFormat.parse(s);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                meta.setFechaInicio(d);
                metasEstudiante.add(meta);
            }while(cursor.moveToNext());
        }
        return metasEstudiante;
    }

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

    // Se recupera el registro de lista metas correspondiente a la clave primaria ingresada como parametro
    public ListaMetas obtenerMeta(int id) {
        String consulta;
        consulta = String.format("SELECT * FROM tbl_lista_metas WHERE ("+
                ContratoEscuela.ColumnasListaMetas.LISTMET_ID+" = %s)", id);
        Cursor cursor = obtenerDataDB(consulta);
        ListaMetas meta;
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            meta = new ListaMetas(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2));}
        else meta = null;
        return(meta);
    }

    // Se recuperan los cumplimientos asociados a una meta
    public ArrayList<CumplimientoMeta> recuperarCumplimientos(int idMeta){
        String consulta = String.format("SELECT * FROM tbl_cumplimiento_metas WHERE ("+
                ContratoEscuela.ColumnasCumplimientoMetas.MET_ID+" = %s)", idMeta);
        Cursor cursor = obtenerDataDB(consulta);
        CumplimientoMeta cumplimiento;
        ArrayList<CumplimientoMeta> lista = new ArrayList<CumplimientoMeta>();
        if(cursor.moveToFirst()){
            do{
                cumplimiento = new CumplimientoMeta(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        new Date(),
                        Integer.parseInt(cursor.getString(3)));
                String s= cursor.getString(2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                Date d = new Date();
                try {
                    d = dateFormat.parse(s);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cumplimiento.setFecha(d);
                lista.add(cumplimiento);
            }while(cursor.moveToNext());
        }
        return (lista);
    }


    //se recuperan las metas que tengan un id de lista metas
    public ArrayList<Meta> obtenerMetasPorIdListaMtas(int idListaMetas){
        String consulta = String.format("SELECT * FROM tbl_meta WHERE ("+
                ContratoEscuela.ColumnasMetas.LISTMETA_ID+" = %s)", idListaMetas);
        Cursor cursor = obtenerDataDB(consulta);
        Meta meta;
        ArrayList<Meta> listarMetas = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                meta = new Meta(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)), new Date(),
                        Integer.parseInt(cursor.getString(4)));
                String s = cursor.getString(3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

                Date d = new Date();
                try {
                    d = dateFormat.parse(s);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                meta.setFechaInicio(d);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(meta.getFechaInicio());
                calendar.add(Calendar.DAY_OF_YEAR,meta.getDuracion());
                if(calendar.getTime().compareTo(Calendar.getInstance().getTime()) >= 0){
                    listarMetas.add(meta);
                }

            }while(cursor.moveToNext());
        }
        return listarMetas;
    }

    //se recuperan las metas correspondientes a un estudiante y que tengan un id(idListaMetas) de lista metas
    public ArrayList<Meta> obtenerMetasEstudiante(int idListaMetas, int idEstudiante){
        String consulta = String.format("SELECT * FROM tbl_meta WHERE ("+
                        ContratoEscuela.ColumnasMetas.LISTMETA_ID+" = %s AND "+ContratoEscuela.ColumnasMetas.EST_ID+" = %s)",
                idListaMetas,idEstudiante);
        Cursor cursor = obtenerDataDB(consulta);
        Meta meta;
        ArrayList<Meta> listarMetas = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                meta = new Meta(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)), new Date(),
                        Integer.parseInt(cursor.getString(4)));
                String s = cursor.getString(3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

                Date d = new Date();
                try {
                    d = dateFormat.parse(s);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                meta.setFechaInicio(d);
                //borrarMetaPorIdListaMetas(idListaMetas);
                listarMetas.add(meta);
            }while(cursor.moveToNext());
        }
        return listarMetas;
    }

    //borra de la tabla tabla_meta
    public boolean borrarMetaPorIdListaMetas(int idMeta) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?", ContratoEscuela.ColumnasMetas.LISTMETA_ID);
        String[] whereArgs = {idMeta+""};
        int resultado = db.delete(ManejaSQL.Tablas.TBL_METAS, whereClause, whereArgs);
        return resultado > 0;
    }

    public Estudiante obtenerEstudiante(int id) {
        String consulta;
            consulta = String.format("SELECT * FROM tbl_estudiante WHERE ("+
                    ContratoEscuela.ColumnasEstudiante.EST_IDENTIFICACION+" = %s)", id);

        Cursor cursor = obtenerDataDB(consulta);
        Estudiante estudiante;
        if(cursor.getCount() == 1)
        {
            cursor.moveToFirst();
            estudiante = new Estudiante(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7));
        }
        else
        {
            estudiante = null;
        }


        return estudiante;
    }

    public Categoria obtenerCategoria(int tipo, String nombre) {
        String consulta;

        consulta = String.format("SELECT * FROM %s WHERE (%s = %s AND %s = '%s')",

                ManejaSQL.Tablas.TBL_CATEGORIAS,
                ContratoEscuela.Categorias.CAT_TIPO,
                tipo,
                ContratoEscuela.Categorias.CAT_NOMBRE,
                nombre);

        Cursor cursor = obtenerDataDB(consulta);
        Categoria categoria;
        if(cursor.getCount() == 1)
        {
            cursor.moveToFirst();
            categoria = new Categoria(cursor.getString(1),cursor.getInt(2),cursor.getInt(0));
        }
        else
        {
            categoria = null;
        }

        return categoria;
    }

    public ArrayList<Categoria> obtenerCategorias(int id) {
        String consulta;
        consulta = String.format("SELECT * FROM %s WHERE %s = %s",

                ManejaSQL.Tablas.TBL_CATEGORIAS,
                ContratoEscuela.ColumnasCategorias.CAT_TIPO,
                id);

        Cursor cursor = obtenerDataDB(consulta);

        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount();i++)
        {
            Categoria categoria = new Categoria(cursor.getString(1),cursor.getInt(2),cursor.getInt(0));
            categorias.add(categoria);
            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }

        }


        return categorias;
    }

    public ArrayList<Subcategoria> obtenerSubCategoriasFromCategoriaId(int id) {
        String consulta;
        consulta = String.format("SELECT * FROM %s WHERE (%s = %s)",
                ManejaSQL.Tablas.TBL_SUBCATEGORIAS,
                ContratoEscuela.ColumnasSubcategorias.SUBC_CAT_ID,
                id);

        Cursor cursor = obtenerDataDB(consulta);

        ArrayList<Subcategoria> subcategorias = new ArrayList<Subcategoria>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount();i++)
        {
            Subcategoria subcategoria = new Subcategoria(cursor.getInt(1),cursor.getString(2));
            subcategoria.setId(cursor.getInt(0));
            subcategorias.add(subcategoria);
            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }
        }

        return subcategorias;
    }

    /**
     * obtiene todas las materias desde la table de materias y las lleva a un ArrayList
     * @return retorna ArrayList con todas las Materias en la base de datos
     */
    public ArrayList<Materia> obtenerMaterias() {
        String consulta;
        consulta = String.format("SELECT * FROM %s",
                ManejaSQL.Tablas.TBL_MATERIAS);

        Cursor cursor = obtenerDataDB(consulta);

        ArrayList<Materia> materias = new ArrayList<Materia>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount();i++)
        {
            Materia materia = new Materia(cursor.getString(1));
            materia.setId(cursor.getInt(0));
            materias.add(materia);
            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }
        }

        return materias;
    }

    public ArrayList<Seguimiento> obtenerSeguimientoFromIdCategoriaIdEstudiante(int idCategoria,int idEstudiante)
    {
        ArrayList<Seguimiento> seguimientos = new ArrayList<Seguimiento>();
        String query = String.format("SELECT  * FROM tbl_seguimiento  " +
                "INNER JOIN tbl_subcategorias ON tbl_seguimiento.seg_subc_id = " +
                "tbl_subcategorias.subc_id WHERE tbl_subcategorias.subc_cat_id = %s AND " +
                "tbl_seguimiento.seg_est_id = %s",idCategoria,idEstudiante);

        Cursor cursor = obtenerDataDB(query);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount();i++)
        {
            Seguimiento seguimiento = new Seguimiento(cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6));
            seguimientos.add(seguimiento);

            if (!cursor.isLast())
            {
                cursor.moveToNext();
            }
        }

        return seguimientos;
    }

    public int countSeguimientoFromIdSubcategoriIdEstudiante(int idSubCategoria,int idEstudiante, String estado)
    {
        String query = String.format("SELECT  * FROM %s WHERE %s = %s AND %s = %s AND %s = '%s'",
                ManejaSQL.Tablas.TBL_SEGUIMIENTO,
                ContratoEscuela.ColumnasSeguimiento.SEG_EST_ID,
                idEstudiante,
                ContratoEscuela.ColumnasSeguimiento.SEG_SUBC_ID,
                idSubCategoria,ContratoEscuela.ColumnasSeguimiento.SEG_ESTADO, estado);
        Cursor cursor = obtenerDataDB(query);

        return cursor.getCount();
    }

    public int countSeguimientoFromIdSubcategoriIdEstudianteIdMateria(int idSubCategoria,int idEstudiante, int idMateria,String estado)
    {
        String query = String.format("SELECT  * FROM %s WHERE %s = %s AND %s = %s AND %s = '%s' AND %s = %s",
                ManejaSQL.Tablas.TBL_SEGUIMIENTO,
                ContratoEscuela.ColumnasSeguimiento.SEG_EST_ID,
                idEstudiante,
                ContratoEscuela.ColumnasSeguimiento.SEG_SUBC_ID,
                idSubCategoria,ContratoEscuela.ColumnasSeguimiento.SEG_ESTADO,
                estado,
                ContratoEscuela.Seguimiento.SEG_MAT_ID,
                idMateria);

        Cursor cursor = obtenerDataDB(query);

        return cursor.getCount();
    }

}
