package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ContratoEscuela;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class PantallaProfesor extends AppCompatActivity {

    OperacionesBaseDeDatos datos;
    FloatingActionButton agregar;
    EditText grado ;
    EditText ngrupo;
    FloatingActionButton subirE;
    FloatingActionButton agregarM;
    ArrayList<String> gruposString = new ArrayList<>();
    ArrayList<Grupo> grupos = new ArrayList<>();
    ArrayList<Grupo> totalGrupos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_profesor);

        grado = (EditText)findViewById(R.id.numGrado);
        ngrupo = (EditText)findViewById(R.id.idenGrupo);
        subirE=(FloatingActionButton) findViewById(R.id.btn_subirEstudiantes);
        agregarM=(FloatingActionButton)findViewById(R.id.btn_materias);
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        //totalGrupos= datos.obtenerGruposDB();

    }

    /**
     * Organiza el ArrayList con los grupos tipo String para mostrarlos en un Alert Dialog
     * y que el ususario pueda seleccionar el grupo que desee.
     * @param a: Arraylist con los grupos a mostrar.
     * @return Alert Dialog con los grupos contenidos en el.
     */
    public AlertDialog listarGrupos(ArrayList<String> a){
        AlertDialog.Builder builder = new AlertDialog.Builder(PantallaProfesor.this);
        final CharSequence[] items = new CharSequence[a.size()];
        for (int i = 0; i < a.size(); i++){
            items[i] = a.get(i);
        }
        builder.setTitle("Grupos actuales").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent ingresar = new Intent(PantallaProfesor.this,AgregarEstudiantes.class);
                ingresar.putExtra("GRUPO",grupos.get(which));
                startActivity(ingresar);
            }
        });

        return builder.create();
    }
    /**
     * Toma los grupos que se tiene registrados hastta el momento y los procesa para mosrarlos
     * como una cadena completa de Strings de la siguiente forma "1-a"
     * @param a Entra un Arraylist de tipo grupo con los grupos almacenados actualmente.
     * @return Arraylist con los grupos del tipo String.
     */
    public ArrayList<String> convertirGrupos (ArrayList<Grupo> a){
        ArrayList<String> gruposs = new ArrayList<>();
        Grupo aux;
        for (int i = 0; i < a.size(); i++){
            aux = a.get(i);
            gruposs.add(String.valueOf(aux.getCurso()) + "-" + aux.getGrupo());
        }
        return gruposs;
    }

    /**Acciona y pasa a la siguiente actividad para agregar los grupos
     *
     * @param v : Recibe una vista
     */
    public void clickAgregarGrupo(View v){
        Intent ingresar = new Intent(PantallaProfesor.this,DialogAgregarGrupos.class);
        startActivity(ingresar);
    }

    /**
     * Acciona el botón y permite registrar cada uno de los estudiantes del grupo
     * @param v: Recibe una vista
     */
    public void clickSubirEstudiantes(View v){
        grupos= datos.obtenerGruposDB();
        gruposString=convertirGrupos(grupos);
        AlertDialog dialog = listarGrupos(gruposString);
        dialog.show();
    }

    /**
     * Acciona el botón y permite pasa la actividad para agregar las materias a dictar.
     * @param view: Recibe una vista
     */
    public void clickMaterias(View view)
    {
        Intent intent = new Intent(this, MateriaAdicion.class);
        startActivity(intent);
    }



}
