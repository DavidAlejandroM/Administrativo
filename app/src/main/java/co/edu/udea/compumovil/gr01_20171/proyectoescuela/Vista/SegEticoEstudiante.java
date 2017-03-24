package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class SegEticoEstudiante extends Activity implements AdapterView.OnItemSelectedListener{



    private Estudiante estudiante;
    private OperacionesBaseDeDatos manager;

    private ImageView iv_foto;
    private TextView tv_nombre;
    private TextView tv_apellido;
    private TextView tv_identificacion;
    private ListView lv_subcategorias;

    private ArrayList<Categoria> categorias;
    private ArrayList<Subcategoria> subcategorias;
    private String[] nombreCategorias;
    private String[] nombreSubcategorias;
    private int[] contadores;
    private String APROVACION = "si";
    private Spinner sp_categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seg_etico_estudiante);
        int id = getIntent().getIntExtra("id",0);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        estudiante = manager.obtenerEstudiante(id);
        llenarDatosEstudiante();
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estudiante);
        llenarGridViewSubcategorias("Todas");


    }

    @Override
    protected void onResume() {
        llenarGridViewCategorias();
        super.onResume();
        llenarSpinner();
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estudiante);
        sp_categorias.setOnItemSelectedListener(this);
        llenarGridViewSubcategorias(sp_categorias.getSelectedItem().toString());

    }



    private void llenarGridViewCategorias() {
        categorias = manager.obtenerCategorias(2);
        if(categorias == null){
           return;
        }
        nombreCategorias = new String[categorias.size()];

        int size=categorias.size();
        for(int x=0;x<size;x++) {
            nombreCategorias[x]= categorias.get(x).getNombre();
        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombreCategorias);
        GridView gridEstudiante = (GridView) findViewById(R.id.grid_view_categorias);
        gridEstudiante.setAdapter(adaptador);
        gridEstudiante.setNumColumns(nombreCategorias.length);

    }


    private  void llenarGridViewSubcategorias(String categoria){

        lv_subcategorias = (ListView) findViewById(R.id.list_view_subcategorias);
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
        nombreCategorias = new String[categorias.size()];

        int size=categorias.size();
        for(int x=0;x<size;x++) {
            nombreCategorias[x]= categorias.get(x).getNombre();
        }
        for(int x=0;x<size;x++) {
            subcategorias = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(x).getId());
            for (int y=0;y<subcategorias.size();y++){
                todasSubcategorias.add(subcategorias.get(y));
            }
        }
        if(todasSubcategorias.size()==0){
            return;
        }
        //Ya deberia funcionar
        CategoriaAdapter adaptador = new CategoriaAdapter(getApplicationContext(), todasSubcategorias,categorias,-1);
        lv_subcategorias.setAdapter(adaptador);
        lv_subcategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Seguimiento seguimiento = new Seguimiento(todasSubcategorias.get(position).getId(),
                        estudiante.getIdentificacion(),APROVACION,giveDate(),2,1);
                manager.insertarSeguimiento(seguimiento);
                Toast.makeText(getApplicationContext(),"El estudiante ha realizado "+todasSubcategorias.get(position).getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
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

    public void clickIrAgregarCategoria(View view){
        Intent intent = new Intent(this,AgregarCategoriaEtico.class);
        startActivity(intent);
    }

    public void clickIrAgregarSubcategoria(View view){
        Intent intent = new Intent(this,AgregarSubcategoriaEtico.class);
        startActivity(intent);
    }

    public static String giveDate() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());

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
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estudiante);
        sp_categorias.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nombreCategorias));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sp_categorias = (Spinner) findViewById(R.id.spinner_etico_estudiante);
        llenarGridViewSubcategorias(sp_categorias.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
