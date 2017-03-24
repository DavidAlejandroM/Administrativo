package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class PantallaConfiguracion extends AppCompatActivity {

    ArrayList<String> gruposString = new ArrayList<>();
    ArrayList<Grupo> grupos = new ArrayList<>();
    Intent intent;
    OperacionesBaseDeDatos datos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion);
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

    }

    /**
     * Acciona el botón "Ver Grupos" , obtienes los grupos de la base de datos y los muestra
     * @param view: le entra una vista como parámetro.
     */
    public void clickVerGrupos(View view){
        grupos= datos.obtenerGruposDB();
        gruposString=convertirGrupos(grupos);
        AlertDialog dialog = listarGrupos(gruposString);
        dialog.show();
    }

    /**
     * Acciona el botón "Agregar Elementos" y pasa a la siguiente Activity para agregar los elementos.
     * @param view : Entra una vista como parámetro.
     */
    public void clickAgregarElementos(View view){
        Intent vistaProfe = new Intent(PantallaConfiguracion.this,PantallaProfesor.class);
        startActivity(vistaProfe);
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

    /**
     * Organiza el ArrayList con los grupos tipo String para mostrarlos en un Alert Dialog
     * y que el ususario pueda seleccionar el grupo que desee.
     * @param a: Arraylist con los grupos a mostrar.
     * @return Alert Dialog con los grupos contenidos en el.
     */
    public AlertDialog listarGrupos(ArrayList<String> a){
        AlertDialog.Builder builder = new AlertDialog.Builder(PantallaConfiguracion.this);
        final CharSequence[] items = new CharSequence[a.size()];
        for (int i = 0; i < a.size(); i++){
            items[i] = a.get(i);
        }
        builder.setTitle("Grupos actuales").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent listar = new Intent(PantallaConfiguracion.this,ListarEstudiantes.class);
                listar.putExtra("GRUPO",grupos.get(which));
                startActivity(listar);
            }
        });

        return builder.create();
    }

}
