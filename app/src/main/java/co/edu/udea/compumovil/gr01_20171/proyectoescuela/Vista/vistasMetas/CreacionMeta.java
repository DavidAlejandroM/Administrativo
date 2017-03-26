package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.vistasMetas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ManejaBDMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.ListaMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase que controla la vista de creacion de metas.
 */
public class CreacionMeta extends AppCompatActivity {

    private String nombre;
    private EditText campo;
    private RadioButton r1,r2;
    private ListaMetas nuevaMeta;
    private String tipo;
    private ManejaBDMetas manejador;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas_crear);
        r1 = (RadioButton)findViewById(R.id.opcCog);
        r2 = (RadioButton)findViewById(R.id.opcComp);
        campo = (EditText)findViewById(R.id.nombreMeta);
    }

    // Metodo que captura el evento de aceptacion para crear una nueva meta
    public void crearMeta(View vista){
        if(validarCondiciones()){
            if(r1.isChecked())tipo = "Cognitiva";
            else tipo = "Comportamental";
            nuevaMeta = new ListaMetas(nombre, tipo);
            OperacionesBaseDeDatos op = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
            ManejaBDMetas.agregarRegistro(op, nuevaMeta);
            mensaje("Meta Creada");
            refresh();

        }else{
            mensaje("Nombre vacio o tipo de meta no seleccionado");
        }
    }

    // Mostrar un mensaje en pantalla
    private void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Metodo para validar que los campos asociados a la meta a crear esten correctamente diligenciados
    private boolean validarCondiciones() {
        nombre = campo.getText().toString();
        if (nombre.compareTo("") == 0) return (false);
        if(!r1.isChecked()&&!r2.isChecked())return (false);
        return (true);
    }

    // Metodo para actualizar los componentes visuales de la vista
    private void refresh(){
        campo.setText("");
        r1.setChecked(false);
        r2.setChecked(false);
    }
}
