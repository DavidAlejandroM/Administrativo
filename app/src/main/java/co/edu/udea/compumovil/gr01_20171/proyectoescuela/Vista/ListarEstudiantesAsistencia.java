package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.CustomListAdapter;

public class ListarEstudiantesAsistencia extends AppCompatActivity {

    static ArrayList<Estudiante> estudiantes;
    Grupo grupo;
    ListView list;
    static OperacionesBaseDeDatos datos;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_estudiantes_asistencia);
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        intent = getIntent();
        bundle = intent.getExtras();
        grupo = (Grupo) intent.getSerializableExtra("GRUPO");
        estudiantes = retornaEstudiantes(grupo);
        list = (ListView)findViewById(R.id.list);
        final AdapterListaEstudiantesAsistencia adapterListaEstudiantesAsistencia =
                new AdapterListaEstudiantesAsistencia(getApplicationContext(),R.layout.activity_adapter_lista_estudiantes_asistencia);
        adapterListaEstudiantesAsistencia.clear();
        if(estudiantes != null) {
            for (Estudiante estudiante : estudiantes) {
                adapterListaEstudiantesAsistencia.add(estudiante);
            }
        }

        /**
         * Muestra la informacion de asistencia para el estudiante clickeado
         * */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estudiante estudiante = estudiantes.get(position);
                Intent intentp = new Intent(ListarEstudiantesAsistencia.this,InfoAsistenciaEstudiante.class);
                intentp.putExtra("ESTUDIANTE",estudiante);
                startActivity(intentp);
            }
        });
        adapterListaEstudiantesAsistencia.notifyDataSetChanged();
        if(adapterListaEstudiantesAsistencia != null && list!=null && estudiantes!=null ){
            list.setAdapter(adapterListaEstudiantesAsistencia);
        }


    }

    /**
     * Trae todos los estudiantes de la base de datos correspondientes al grupo que se está
     * manejando en ese momento.
     * @param grupo: El grupo que se está manejando en el momento de ejecución
     * @return Un ArrayList con todos los estudiantes pertenecientes al mismo grupo
     */
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

    /**
     *Permite al accionar el botón mostrar las estadístcas grupales
     * @param view
     */
        public void clickGrupales(View view){
            intent = new Intent(this,InfoAsistenciaGrupo.class);
            intent.putExtra("GRUPO",grupo);
            startActivity(intent);
        }
 }


