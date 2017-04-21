package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class AgregarCategoriaEtico extends Activity {

    private EditText nombre;
    private OperacionesBaseDeDatos manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria_etico);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
    }

    /**
     * Devolverse de vista
     * @param view Objeto vista para devolver
     */
    public void clickDevolverse(View view) {
        onBackPressed();
    }

    /**
     * 
     * @param view
     */
    public void clickCategoriaEtico(View view){
        nombre = (EditText) findViewById(R.id.agregar_nombre_categoria_etico);
        String nom = nombre.getText().toString();
        if(nom.equalsIgnoreCase("")){
            Toast mensaje = Toast.makeText(this,"No se ha llenado el campo correctamente", Toast.LENGTH_SHORT);
            mensaje.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
            mensaje.show();
            return;
        }

        Categoria categoria = new Categoria(nombre.getText().toString(),2);
        manager.insertarCategorias(categoria);


        Toast mensaje = Toast.makeText(this,"La categoria "+nombre.getText().toString()+" ha sido agregada", Toast.LENGTH_SHORT);
        mensaje.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
        mensaje.show();
    }
}
