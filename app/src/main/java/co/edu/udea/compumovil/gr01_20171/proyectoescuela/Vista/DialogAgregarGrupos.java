package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class DialogAgregarGrupos extends AppCompatActivity {

    OperacionesBaseDeDatos datos;
    Button guardarGp,finalizar;
    EditText grado,ngrupo;
    EditText filas, columnas;
    List list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_agregar_grupos);
        guardarGp = (Button)findViewById(R.id.btn_agregarGrupo);
        finalizar=(Button)findViewById(R.id.btn_finalizar_grupos);
        grado = (EditText)findViewById(R.id.numGrado);
        ngrupo = (EditText)findViewById(R.id.idenGrupo);
        filas = (EditText) findViewById(R.id.nro_filas);
        columnas = (EditText) findViewById(R.id.nro_columnas);


        /*list = new ArrayList();
        ArrayAdapter arrayAdapter;
        for (int i = 2; i <= 6; i++){
            list.add(i);
        }
        arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, list);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        columnas.setAdapter(arrayAdapter);
        filas.setAdapter(arrayAdapter);*/


       getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());


        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Acciona el botón para guardar el grupo ingresado, llevándolo a la base de datos
     * @param v: Ingresa un objeto de tipo View
     */
    public  void clickGuardarGrupo(View v){
        if (!grado.getText().toString().isEmpty() && !ngrupo.getText().toString().isEmpty()) {
            try{
                datos.getDb().beginTransaction();

                Grupo grupo = new Grupo(Integer.parseInt(grado.getText().toString()),
                        ngrupo.getText().toString(),Integer.parseInt(filas.getText().toString()),
                        Integer.parseInt(columnas.getText().toString()));
                datos.insertarGrupo(grupo);
                datos.getDb().setTransactionSuccessful();
                Toast.makeText(getApplicationContext(),"Grupo agregado",Toast.LENGTH_SHORT).show();
                grado.setText("");
                ngrupo.setText("");

            }

            catch(Exception e){e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Se ha producido un error",Toast.LENGTH_SHORT).show();}
            finally{
                datos.getDb().endTransaction();

            }

        }else{
            Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_SHORT).show();
        }

    }




}
