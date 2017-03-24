package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;



public class EstadisticaEticoEstudianteFecha extends Activity {

    private Estudiante estudiante;
    private Subcategoria subcategoria;
    private OperacionesBaseDeDatos manager;
    private TextView nombreEst,nombreCat,nombreSub,cantidad;
    private ListView lv;
    private ArrayList<Categoria> categorias;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_etico_estudiante_fecha);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        estudiante = (Estudiante)getIntent().getExtras().getSerializable("estudiante");
        subcategoria = (Subcategoria)getIntent().getExtras().getSerializable("subcategoria");
        llenarDatos();
    }

    private void llenarDatos(){
        nombreEst = (TextView) findViewById(R.id.nombre_estudiante_estadistica_especifico);
        nombreCat = (TextView) findViewById(R.id.nombre_categoria_estadistica_especifico);
        nombreSub = (TextView) findViewById(R.id.nombre_subcategoria_estadisticia_especifico);
        cantidad = (TextView) findViewById(R.id.cantidad_subcategoria_estadisticia_especifico);
        lv = (ListView) findViewById(R.id.lv_estadistica_etico_especifico);
        imagen = (ImageView) findViewById(R.id.foto_estudiante_estadistica_fecha);

        Categoria c = obtenerCategoriaFromSub(subcategoria);
        ArrayList<Seguimiento> seguimientosTotales = manager.obtenerSeguimientoFromIdCategoriaIdEstudiante(c.getId(),estudiante.getIdentificacion());
        ArrayList<String> seguimientoSubcategoria = obtenerSeguimientoEspecifico(subcategoria,seguimientosTotales);

        ;

        Uri uri = pathToUri(estudiante.getFoto());
        if (!uri.equals(Uri.EMPTY))
        {
            imagen.setImageURI(pathToUri(estudiante.getFoto()));
        }
        else
        {
            imagen.setImageResource(R.mipmap.ic_launcher);
        }
        nombreEst.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        nombreSub.setText("Sub: "+subcategoria.getNombre());
        nombreCat.setText("Cat: "+c.getNombre());
        cantidad.setText("Cantidad: "+Integer.toString(seguimientoSubcategoria.size()));
        String[] fechas = obtenerFechas(seguimientoSubcategoria);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fechas);

        lv.setAdapter(adaptador);



    }

    private Categoria obtenerCategoriaFromSub(Subcategoria subcategoria){
        Categoria c= null;
        categorias = manager.obtenerCategorias(2);
        ArrayList<Subcategoria> subs;
        for (int i = 0;i<categorias.size();i++){
            subs = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            for(int j =0;j<subs.size();j++){
                String s1 = subs.get(j).getNombre();
                String s2 = subcategoria.getNombre();
                if(s1.equalsIgnoreCase(s2)){
                    c = categorias.get(i);
                    return(c);
                }
            }
        }
        return(c);
    }

    private ArrayList<String> obtenerSeguimientoEspecifico(Subcategoria subcategoria,ArrayList<Seguimiento> seguimientos){
        ArrayList<String> resultado = new ArrayList<String>();
        for(int i =0;i<seguimientos.size();i++){
            int x = seguimientos.get(i).getIdSubSeg();
            int y = subcategoria.getId();
            if(x==y){
                resultado.add("Fecha: "+seguimientos.get(i).getFecha());
            }
        }
        return(resultado);
    }

    private String[] obtenerFechas(ArrayList<String> seguimientos){
        String[] resultado = new String[seguimientos.size()];
        for(int i =0;i<seguimientos.size();i++){
            resultado[i]=seguimientos.get(i);
        }
        return(resultado);
    }

    private Uri pathToUri(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists())
        {
            return Uri.fromFile(imgFile);

        }
        return Uri.EMPTY;
    }
}
