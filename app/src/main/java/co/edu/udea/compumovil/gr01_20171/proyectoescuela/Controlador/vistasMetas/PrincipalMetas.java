package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador.vistasMetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ManejaBDMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.GestionEstudianteMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Meta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase que controla la vista principal del submodulo de metas.
 */
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
    private EditText campoEdicion;
    private Button boton;
    private CheckBox duracionEq;
    private boolean diasEquivalentesActivado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_metas);
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        Toolbar barra = (Toolbar)findViewById(R.id.barra);
        setSupportActionBar(barra);
        opciones = (Spinner)findViewById(R.id.opcionesMetas);
        campoEdicion = (EditText)findViewById(R.id.duracionGlobal);
        duracionEq = (CheckBox)findViewById(R.id.duracionEq);
        diasEquivalentesActivado = false;
        boton = (Button)findViewById(R.id.aceptarMetaGrupal);
        cambiarEstadoComponentesMG(false);
        listarMetas();
        metaPorEstudiante = new Meta();
        getApplicationContext().deleteDatabase("pedidos.db");
        intent = getIntent();
        bundle = intent.getExtras();
        grupo = (Grupo) intent.getSerializableExtra("GRUPO");
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
        duracionEq.setClickable(true);
        duracionEq.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        duracionEq = (CheckBox)v;
                        if(duracionEq.isChecked()){
                            diasEquivalentesActivado = true;
                            cmabiarEstadoCampo(true);
                        }else{
                            diasEquivalentesActivado = false;
                            cmabiarEstadoCampo(false);
                        }
                    }
                }
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    // Metodo para asignacion de meta grupal
    public void asignarMetaGrupal(View vista){
        if(campoEdicion.getText().toString().compareTo("")==0){
            mensaje("Debe agregar una duracion(dias) global para todos los estudiantes ",0);
            return;
        }
        int seleccion = opciones.getSelectedItemPosition();
        if (seleccion == -1){
            mensaje("Se debe seleccionar una meta", 0);
            return;
        }
        metaSeleccionada = metas.get(seleccion);
        for(int i=0; i<estudiantes.size(); i++){
            setMeta(estudiantes.get(i), 1);
            ManejaBDMetas.agregarRegistro(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), metaPorEstudiante);
        }
        mensaje("Se asignó correctamente la meta grupal", 0);
        onRestart();
    }


    // Metodo para cargar los estudiantes pertenecientes al grupo que se va a mostrar en pantalla
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


    // Metodo para activar la vista de creacion de metas
    private void activarCrear(){
        Intent intencion = new Intent(this, CreacionMeta.class);
        startActivity(intencion);
    }


    // Metodo para activar la vista de asignacion de cumplimientos
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

    // Metodo para activar la vista de estadisticas
    private void activarEstadisticas(){
        int seleccion = opciones.getSelectedItemPosition();
        if (seleccion == -1){
            mensaje("Se debe seleccionar una meta", 0);
            return;
        }
        metaSeleccionada = metas.get(seleccion);
        Intent intencion = new Intent(this, Estadistica.class);
        intencion.putExtra("IDMETA",metaSeleccionada.getId());
        startActivity(intencion);
    }

    // Metodo para cargar en el menu desplegable de metas, las metas disponibles en la base de datos.
    private void listarMetas(){
        metas = ManejaBDMetas.listarMetas(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()));
        int tamaño = metas.size();
        String[] nombreMetas = new String[tamaño];
        for(int i=0; i<tamaño; i++){
            nombreMetas[i] = metas.get(i).getNombre();
        }
        opciones.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nombreMetas));
    }

    // Metodo para asignar una meta a los estudiantes seleccionados actualmente en la vista (con sus respectivas duraciones)
    private void asignarMeta(){
        int seleccion = opciones.getSelectedItemPosition();
        if (seleccion == -1){
            mensaje("Se debe seleccionar una meta", 0);
            return;
        }
        metaSeleccionada = metas.get(seleccion);
        for (int i=0; i<estudiantes.size(); i++){
            if(estudiantes.get(i).getGestorMetas().estado()){
                if(diasEquivalentesActivado) setMeta(estudiantes.get(i), 1);
                else setMeta(estudiantes.get(i), 0);
                ManejaBDMetas.agregarRegistro(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), metaPorEstudiante);
            }
        }
        mensaje("Se asignó exitosamente la meta a los estudiantes seleccionados", 1);
        onRestart();
    }

    // Borrar una Meta de la lista de Metas
    private void borrarMeta(){
        int seleccion = opciones.getSelectedItemPosition();
        metaSeleccionada = metas.get(seleccion);
        ManejaBDMetas.borrarMeta(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),metaSeleccionada.getId());
        onRestart();
    }

    // Metodos apoyo Meta Grupal
    private void cambiarEstadoComponentesMG(boolean estado){
        campoEdicion.setEnabled(estado);
        boton.setEnabled(estado);
    }
    private void cmabiarEstadoCampo(boolean estado){
        campoEdicion.setEnabled(estado);
    }
    //

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
                cambiarEstadoComponentesMG(true);
                duracionEq.setEnabled(false);
                break;
            case (R.id.opcionCumplimiento): activarCumplimiento();
                break;
            case (R.id.opcionEstadistica): activarEstadisticas();
                break;
        }
        return(true);
    }
    //

    // Metodo para cargar datos a una nueva meta que sera ingresada en la base de datos
    private void setMeta(Estudiante estudiante, int clave){
        metaPorEstudiante.setEstudianteId(estudiante.getIdentificacion());
        metaPorEstudiante.setListaMetasId(metaSeleccionada.getId());
        metaPorEstudiante.setFechaInicio(Calendar.getInstance().getTime());
        if(clave == 0) metaPorEstudiante.setDuracion(estudiante.getGestorMetas().getDuracionMeta());
        else metaPorEstudiante.setDuracion(Integer.parseInt(campoEdicion.getText().toString()));
    }

    // Mostrar un mensaje en pantalla
    private void mensaje(String mensaje, int clave){
        if(clave == 0) Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
