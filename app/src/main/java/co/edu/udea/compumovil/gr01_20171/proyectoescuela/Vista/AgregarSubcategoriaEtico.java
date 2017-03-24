package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class AgregarSubcategoriaEtico extends Activity {

    private EditText nombre;
    private Spinner sp_categorias;
    private String[] nombreCategorias;
    private OperacionesBaseDeDatos manager;
    private ArrayList<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_subcategoria_etico);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        llenarSpinner();
    }

    public void clickDevolverse(View view) {
        onBackPressed();
    }

    public void clickSubcategoriaEtico(View view){
        nombre = (EditText) findViewById(R.id.agregar_nombre_subcategoria_etico);
        String nom = nombre.getText().toString();
        if(nom.equalsIgnoreCase("")){
            Toast mensaje = Toast.makeText(this,"No se ha llenado el campo correctamente", Toast.LENGTH_SHORT);
            mensaje.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
            mensaje.show();
            return;
        }
        sp_categorias = (Spinner) findViewById(R.id.spinner_categorias);
        String nomCategoria = sp_categorias.getSelectedItem().toString();
        Categoria categoria = manager.obtenerCategoria(2,nomCategoria);
        Subcategoria subcategoria = new Subcategoria(categoria.getId(),nombre.getText().toString());
        manager.insertarSubCategorias(subcategoria);
        Toast mensaje = Toast.makeText(this,"La subcategoria "+nombre.getText().toString()+" ha sido agregada", Toast.LENGTH_SHORT);
        mensaje.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
        mensaje.show();
    }

    public void llenarSpinner(){
        categorias = manager.obtenerCategorias(2);
        if(categorias == null){
            return;
        }
        nombreCategorias = new String[categorias.size()];

        int size=categorias.size();
        for(int x=0;x<size;x++) {
            nombreCategorias[x]= categorias.get(x).getNombre();

        }
        sp_categorias = (Spinner) findViewById(R.id.spinner_categorias);
        sp_categorias.setAdapter(new ArrayAdapter<String>(this, R.layout.item_simple_1, nombreCategorias));
    }
}
