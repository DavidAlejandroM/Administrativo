package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.vistasMetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ManejaBDMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.CumplimientoMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.GestionEstudianteMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Meta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase que controla la asginacion de cumplimientos a los estudiantes asociados a una meta.
 */
public class Cumplimiento extends AppCompatActivity {

    private Intent intenncion;
    private Bundle bundle;
    private int idMeta;
    private ListaMetas meta;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Meta> metasPorEstudiante;
    private TextView campo;
    private ListView lista;
    private CustomListAdapterMC customListAdapter;
    private ArrayList<Meta> metas;
    private CumplimientoMeta cumplimiento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_cumplimiento);
        intenncion = getIntent();
        bundle = intenncion.getExtras();
        idMeta = bundle.getInt("META");
        setMeta();
        cargarTextos();

        estudiantes = obtenerListaEstudiantes();
        Collections.sort(estudiantes);
        lista = (ListView)findViewById(R.id.list_cumplimiento);
        customListAdapter = new CustomListAdapterMC(getApplicationContext()
                ,R.layout.activity_list_metas_cumplimiento);
        customListAdapter.clear();
        if(estudiantes != null){
            for(Estudiante estudiante : estudiantes){
                estudiante.setGestorMetas(new GestionEstudianteMeta());
                customListAdapter.add(estudiante);
            }
        }
        customListAdapter.notifyDataSetChanged();
        if(customListAdapter != null && lista!=null && estudiantes!=null ){
            lista.setAdapter(customListAdapter);
        }

        lista.setClickable(true);
        lista.setItemsCanFocus(false);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CheckBox cumple = (CheckBox)view.findViewById(R.id.cumple);
                final CheckBox noCumple = (CheckBox)view.findViewById(R.id.noCumple);
                cumple.setEnabled(true);
                noCumple.setEnabled(true);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    //Metodo que captura el evento de aceptacion para guardar los cumplimientos seleccionados
    public void guardarCumplimientos(View vista){
        Estudiante estudiante;
        for(int i=0;i<estudiantes.size(); i++){
            estudiante = estudiantes.get(i);
            if(estudiante.getGestorMetas().asignacionCumplimiento()){
                boolean estadoCumplimiento = estudiante.getGestorMetas().cumplimiento();
                metasPorEstudiante = ManejaBDMetas.retornarMetasPorEstudiante(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),
                        idMeta, estudiante.getIdentificacion());
                for(int j=0; j<metasPorEstudiante.size(); j++){
                    Meta m = metasPorEstudiante.get(j);
                    if(estadoCumplimiento) cumplimiento = new CumplimientoMeta(m.getId(), Calendar.getInstance().getTime(),1);
                    else cumplimiento = new CumplimientoMeta(m.getId(), Calendar.getInstance().getTime(),0);
                    ManejaBDMetas.agregarRegistro(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), cumplimiento);
                }
            }
        }mensaje("Se asignaron correctamente los cumplimientos");
        onRestart();
    }

    private void setMeta(){
        meta = ManejaBDMetas.obtenerMeta(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()), idMeta);
    }

    // Metodo para cargar los textos correspondientes que componen la vista
    private void cargarTextos(){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);
        String fecha = dia+"/"+mes+"/"+año;
        campo = (TextView)findViewById(R.id.fecha); campo.setText(fecha);
        campo = (TextView)findViewById(R.id.nombreMeta); campo.setText(meta.getNombre());
        campo = (TextView)findViewById(R.id.tipoMeta); campo.setText(meta.getTipo());
    }

    // Metodo para cargar los estudiantes pertenecientes al grupo que se va a mostrar en pantalla
    private ArrayList<Estudiante> obtenerListaEstudiantes(){
        metas = ManejaBDMetas.retornarDatos(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),2,idMeta);
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        Estudiante e;
        for(int i=0; i<metas.size(); i++){
            e = ManejaBDMetas.obtenerEstudiante(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),
                        metas.get(i).getEstudianteId());
            if(!lista.contains(e)) lista.add(e);
        }
        return(lista);
    }

    // Mostrar un mensjae en pantalla
    private void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
