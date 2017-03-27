package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador.vistasMetas.PrincipalMetas;

public class PantallaPpal extends AppCompatActivity {

    Grupo grupo;
    Intent intent;
    Bundle bundle;

    int tipoVista;

    private OperacionesBaseDeDatos manager;
    private String GRUPO = "GRUPO";
    private EstadisticaCognitiva estadistica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ppal);
        intent = getIntent();
        bundle = intent.getExtras();

        grupo = (Grupo) intent.getSerializableExtra(GRUPO);
        tipoVista = (int) intent.getIntExtra("tipoVista",0);

        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        //obtener extra del grupo que esta seleccionado
        Intent intent = getIntent();
        grupo = (Grupo) intent.getSerializableExtra(GRUPO);


        if (tipoVista == 2)
        {
            LinearLayout ll = (LinearLayout) findViewById(R.id.contenedor_botones_principal);
            ImageButton button = (ImageButton) findViewById(R.id.btn_ubicacion);
            ll.removeView(button);

        }
    }

    public void ClckIrSeguimientoCognitivo(View view)
    {



        if (tipoVista == 1){
            Intent intent = new Intent(this,SeguimientoCognitivo.class);
            intent.putExtra("GRUPO",grupo);
            intent.putExtra("tipoVista",tipoVista);
            startActivity(intent);
        }else{
            AlertDialog dialog = listarOpciones();
            dialog.show();


            }
        }
        public AlertDialog listarOpciones(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final CharSequence[] items = {"General","Estudiante"};
            final Intent intent = new Intent(this,SeguimientoCognitivo.class);
            builder.setTitle("Seleccione una opción").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0){

                    estadisticaGeneral();

                    }else{
                        intent.putExtra("GRUPO",grupo);
                        intent.putExtra("tipoVista",tipoVista);
                        startActivity(intent);
                    }

                }
            });
            return builder.create();
        }


    public void estadisticaGeneral(){
        estadistica = new EstadisticaCognitiva(manager);
        ArrayList<Estudiante> estudiantes = manager.obtenerEstudiantesDB(grupo);
        ArrayList<Categoria> categorias = manager.obtenerCategorias(1);
        final ArrayList<Materia> materias =  manager.obtenerMaterias();
        estadistica.setMateria(materias.get(0));
        Intent intent = new Intent(getApplicationContext(),EstadisticaModel.class);
        intent.putStringArrayListExtra("valX", estadistica.listarEstudiantes(estudiantes));
        intent.putExtra("valSi",estadistica.obtenerValSiGeneral(estudiantes));
        intent.putExtra("valNo", estadistica.obtenerValNoGeneral(estudiantes));
        intent.putExtra("abrirBarra", false);
        intent.putExtra("tipoEstadistica", 1);
        intent.putExtra("titulo","General Estudiantes");
        startActivity(intent);
    }


    public void ClckIrSeguimientoEtico(View view)
    {
        if (tipoVista == 1)
        {
            intent = new Intent(PantallaPpal.this, SeguimientoEtico.class);
            intent.putExtra("GRUPO",grupo);

        }
        else if (tipoVista == 2)
        {
            intent = new Intent(this,EstadisticaEtico.class);
            intent.putExtra("GRUPO",grupo);
        }
        startActivity(intent);
    }


    public void ClickIrAsistencia(View view) {

        if (tipoVista == 1)
        {
            intent = new Intent(PantallaPpal.this, AsistenciaV.class);
            intent.putExtra("GRUPO",grupo);

        }
        else if (tipoVista == 2)
        {
            intent = new Intent(this,ListarEstudiantesAsistencia.class);
            intent.putExtra("GRUPO",grupo);
        }
        startActivity(intent);
    }

    public void ClickIrMetas(View view)
    {
        if (tipoVista == 1)
        {
            intent = new Intent(this, PrincipalMetas.class);
            intent.putExtra("GRUPO",grupo);

        }
        else if (tipoVista == 2)
        {
            //intent = new Intent(this,.class);
            intent.putExtra("GRUPO",grupo);
        }
        startActivity(intent);


    }

    public void ClickIrUbicacion(View view)
    {
        intent = new Intent(this,Ubicacion.class);
        intent.putExtra(GRUPO,grupo);
        startActivity(intent);
    }



}









