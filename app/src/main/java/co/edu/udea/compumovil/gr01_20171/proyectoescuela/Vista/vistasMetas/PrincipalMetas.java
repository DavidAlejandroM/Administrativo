package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.vistasMetas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ManejaBDMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.GestionEstudianteMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Meta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.ListarEstudiantes;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.PantallaConfiguracion;

public class PrincipalMetas extends AppCompatActivity {

    private static ArrayList<Estudiante> estudiantes;
    private Grupo grupo;
    private ListView list;
    private static OperacionesBaseDeDatos datos;
    private Intent intent;
    private Bundle bundle;
    private Spinner opciones;
    private ListaMetas metaSeleccionada;
    private ArrayList<ListaMetas> metas;
    private CustomListAdapterM customListAdapter;
    private Meta metaPorEstudiante;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_metas);
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        Toolbar barra = (Toolbar)findViewById(R.id.barra);
        setSupportActionBar(barra);
        opciones = (Spinner)findViewById(R.id.opcionesMetas);
        listarMetas();
        metaPorEstudiante = new Meta();

        getApplicationContext().deleteDatabase("pedidos.db");
        intent = getIntent();
        bundle = intent.getExtras();
        grupo = (Grupo) intent.getSerializableExtra("GRUPO");
        //
        estudiantes = retornaEstudiantes(grupo);
        Collections.sort(estudiantes);
        list = (ListView)findViewById(R.id.list_metas);
        customListAdapter = new CustomListAdapterM(getApplicationContext()
                ,R.layout.activity_list_metas);
        customListAdapter.clear();
        if(estudiantes != null){
            for(Estudiante estudiante : estudiantes){
                estudiante.setGestorMetas(new GestionEstudianteMeta());
                customListAdapter.add(estudiante);
            }
        }
        customListAdapter.notifyDataSetChanged();
        if(customListAdapter != null && list!=null && estudiantes!=null ){
            list.setAdapter(customListAdapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private static ArrayList<Estudiante> retornaEstudiantes(Grupo grupo){
        try {
            datos.getDb().beginTransaction();
            estudiantes = datos.obtenerEstudiantesDB(grupo);
            datos.getDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            datos.getDb().endTransaction();
        }
        return estudiantes;
    }

    private void activarCrear(){
        Intent intencion = new Intent(this, CreacionMeta.class);
        startActivity(intencion);
    }

    private void activarCumplimiento(){
        int seleccion = opciones.getSelectedItemPosition();
        if (seleccion == -1){
            mensaje("Se debe seleccionar una meta", 0);
            return;
        }
        metaSeleccionada = metas.get(seleccion);
        Intent intencion = new Intent(this, Cumplimiento.class);
        intencion.putExtra("META", metaSeleccionada.getId());
        startActivity(intencion);
    }

    // Seleccion de Grupo
    private void listarMetas(){
        metas = ManejaBDMetas.listarMetas(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()));
        int tamaño = metas.size();
        String[] nombreMetas = new String[tamaño];
        for(int i=0; i<tamaño; i++){
            nombreMetas[i] = metas.get(i).getNombre();
        }
        opciones.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nombreMetas));
    }

    // Asignacion de Meta
    private void asignarMeta(){
        int seleccion = opciones.getSelectedItemPosition();
        if (seleccion == -1){
            mensaje("Se debe seleccionar una meta", 0);
            return;
        }
        metaSeleccionada = metas.get(seleccion);
        for (int i=0; i<estudiantes.size(); i++){
            if(estudiantes.get(i).getGestorMetas().estado()){
                setMeta(estudiantes.get(i));
                ManejaBDMetas.agregarRegistro(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), metaPorEstudiante);
            }
        }
        mensaje("Se asignó exitosamente la meta a los estudiantes seleccionados", 1);
    }

    // Borrar una Meta de la lista de Metas
    private void borrarMeta(){
        int seleccion = opciones.getSelectedItemPosition();
        metaSeleccionada = metas.get(seleccion);

        ManejaBDMetas.borrarMeta(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),metaSeleccionada.getId());
        onRestart();
    }

    // Metodos apoyo Meta Grupal

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionSeleccionada) {
        int id = opcionSeleccionada.getItemId();

        switch (id){
            case (R.id.opcionCrear): activarCrear();
                break;
            case (R.id.opcionAsignarMI): asignarMeta();
                break;
            case (R.id.opcionBorrar):borrarMeta();
                break;
            case (R.id.opcionAsignarMG):
                break;
            case (R.id.opcionCumplimiento): activarCumplimiento();
                break;
        }
        return(true);
    }

    private void setMeta(Estudiante estudiante){
        metaPorEstudiante.setEstudianteId(estudiante.getIdentificacion());
        metaPorEstudiante.setListaMetasId(metaSeleccionada.getId());
        metaPorEstudiante.setFechaInicio(Calendar.getInstance().getTime());
        metaPorEstudiante.setDuracion(estudiante.getGestorMetas().getDuracionMeta());
    }

    private void mensaje(String mensaje, int clave){
        if(clave == 0) Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /*private void prueba(){
        ArrayList<Meta> l = ManejaBDMetas.retornarDatos(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), 1);
        for(int i=0; i<l.size(); i++){
            Estudiante e = ManejaBDMetas.obtenerEstudiante(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), l.get(i).getEstudianteId());
            Log.d("MENSAJE", e.getNombres()+"");
            ListaMetas m = ManejaBDMetas.obtenerMeta(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), l.get(i).getListaMetasId());
            Log.d("MENSAJE", m.getNombre());
        }
    }*/
}
