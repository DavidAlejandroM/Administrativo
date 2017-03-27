package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class EstadisticaEticoEstudiante extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Estudiante estudiante;
    private ImageView iv_foto;
    private TextView tv_nombre;
    private TextView tv_apellido;
    private TextView tv_identificacion;

    private ListView lv_subcategorias;
    private OperacionesBaseDeDatos manager;
    private ArrayList<Categoria> categorias;
    private ArrayList<Subcategoria> subcategorias;
    private ArrayList<Seguimiento> seguimientos;
    private String[] nombreCategorias;
    private Intent intent;
    private Spinner sp_categorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_etico_estudiante);
        int id = getIntent().getIntExtra("id",0);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        estudiante = manager.obtenerEstudiante(id);
        llenarDatosEstudiante();
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estadistico);
        llenarGridViewSubcategorias("Todas");
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarSpinner();
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estadistico);
        sp_categorias.setOnItemSelectedListener(this);
        llenarGridViewSubcategorias(sp_categorias.getSelectedItem().toString());
    }

    private void llenarDatosEstudiante(){

        tv_nombre = (TextView) findViewById(R.id.nombre_estudiante_etico);
        tv_apellido = (TextView) findViewById(R.id.apellido_estudiante_etico);
        tv_identificacion = (TextView) findViewById(R.id.identificacion_estudiante_etico);
        iv_foto = (ImageView) findViewById(R.id.foto_estudiante_etico);

        tv_nombre.setText(estudiante.getNombres());
        tv_apellido.setText(estudiante.getApellidos());
        tv_identificacion.setText(Integer.toString(estudiante.getIdentificacion()));
        Uri uri = pathToUri(estudiante.getFoto());
        if (!uri.equals(Uri.EMPTY))
        {
            iv_foto.setImageURI(pathToUri(estudiante.getFoto()));
        }
        else
        {
            iv_foto.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private Uri pathToUri(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists())
        {
            return Uri.fromFile(imgFile);

        }
        return Uri.EMPTY;
    }

    private  void llenarGridViewSubcategorias(String categoria){

        lv_subcategorias = (ListView) findViewById(R.id.list_view_subcategorias_estadistico);
        final ArrayList<Subcategoria> todasSubcategorias = new ArrayList<Subcategoria>();
        categorias = manager.obtenerCategorias(2);
        if(!categoria.equals("Todas")){
            for(int i =0;i<categorias.size();i++){
                Categoria c = categorias.get(i);
                if(!c.getNombre().equals(categoria)){
                    categorias.remove(i);
                }
            }
        }
        if(categorias == null){
            return;
        }

        for(int x=0;x<categorias.size();x++) {
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(x).getId());
            for (int y=0;y<subcategorias.size();y++){
                todasSubcategorias.add(subcategorias.get(y));
            }
        }

        CategoriaAdapter adaptador = new CategoriaAdapter(getApplicationContext(), todasSubcategorias,categorias,estudiante);


        lv_subcategorias.setAdapter(adaptador);
        lv_subcategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getApplicationContext(),EstadisticaEticoEstudianteFecha.class);
                intent.putExtra("estudiante",estudiante);
                intent.putExtra("subcategoria", todasSubcategorias.get(position));
                startActivity(intent);

            }
        });



    }

    public void llenarSpinner(){
        categorias = manager.obtenerCategorias(2);
        if(categorias == null){
            return;
        }
        nombreCategorias = new String[categorias.size()+1];

        int size=categorias.size();
        nombreCategorias[0]="Todas";
        for(int x=1;x<=size;x++) {
            nombreCategorias[x]= categorias.get(x-1).getNombre();
        }
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estadistico);
        sp_categorias.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nombreCategorias));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estadistico);
        llenarGridViewSubcategorias(sp_categorias.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
