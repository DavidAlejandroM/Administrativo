package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnGrupo, btnEst, btnConf;
    ArrayList<String> cadenaGrupos = new ArrayList<>();
    ArrayList<Grupo> grupos = new ArrayList<>();
    Intent intent;
    OperacionesBaseDeDatos datos ;
    ArrayList<String> gruposString = new ArrayList<>();


    /**
     * Tipo de vista 1=Añadir datos  2=Estadistica
     */
    int tipoVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        btnGrupo = (ImageButton)findViewById(R.id.btnGrupo);
        btnGrupo.setOnClickListener(this);
        btnEst = (ImageButton)findViewById(R.id.btnEstadisticas);
        btnEst.setOnClickListener(this);
        btnConf = (ImageButton)findViewById(R.id.btnConfiguracion);
        btnConf.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //Intent intent;

            case R.id.btnGrupo:
                grupos = datos.obtenerGruposDB();
               cadenaGrupos= convertirGrupos(grupos);
                AlertDialog dialog = listarGrupos(cadenaGrupos);
                dialog.show();
                tipoVista = 1;
                break;
            case R.id.btnEstadisticas:

                grupos= datos.obtenerGruposDB();
                gruposString= convertirGrupos(grupos);
                AlertDialog dialogo = listarGruposEstadisticas(gruposString);
                dialogo.show();

                tipoVista = 2;

                break;
            case R.id.btnConfiguracion:
                intent = new Intent(this,PantallaConfiguracion.class);
                startActivity(intent);
                break;
        }
    }

    public AlertDialog listarGrupos(ArrayList<String> a){
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioActivity.this);
        final CharSequence[] items = new CharSequence[a.size()];
        for (int i = 0; i < a.size(); i++){
            items[i] = a.get(i);
        }
        intent = new Intent(this,PantallaPpal.class);
        builder.setTitle("Grupos actuales").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.putExtra("GRUPO",grupos.get(which));
                intent.putExtra("tipoVista",tipoVista);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    /**
     * Organiza el ArrayList con los grupos tipo String para mostrarlos en un Alert Dialog
     * y que el ususario pueda seleccionar el grupo que desee.
     * @param a: Arraylist con los grupos a mostrar.
     * @return Alert Dialog con los grupos contenidos en el.
     */
    public AlertDialog listarGruposEstadisticas(ArrayList<String> a){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] items = new CharSequence[a.size()];
        for (int i = 0; i < a.size(); i++){
            items[i] = a.get(i);
        }
        intent = new Intent(this,PantallaPpal.class);
        builder.setTitle("Grupos actuales").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.putExtra("GRUPO",grupos.get(which));
                intent.putExtra("tipoVista",tipoVista);
                startActivity(intent);
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
}
