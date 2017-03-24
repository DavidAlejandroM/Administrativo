package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class Ubicacion extends AppCompatActivity {

    private OperacionesBaseDeDatos manager;
    private ArrayList<Estudiante> estudiantes;
    private String GRUPO = "GRUPO";
    private int filas;
    private int columnas;

    private int lastIdentificacion;
    private int lastFila;
    private int identificacion;
    private int fila;
    private Grupo grupo;

    private View lastView = null;
    private GridView gv_ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        init();
    }

    private void init()
    {

        grupo = (Grupo) getIntent().getSerializableExtra(GRUPO);

        estudiantes = manager.obtenerEstudiantesDB(grupo);


        estudiantes = completarEstudiantes(estudiantes,grupo.getColumnas()*grupo.getFilas());


        int a = estudiantes.size();

        gv_ubicacion = (GridView) findViewById(R.id.grid_view_ubicacion_estudiante);
        gv_ubicacion.setNumColumns(filas);

        EstudianteAdapter adapter = new EstudianteAdapter(this,estudiantes);

        gv_ubicacion.setAdapter(adapter);

        eventosGridViewItem();
    }

    private ArrayList<Estudiante> completarEstudiantes(ArrayList<Estudiante> estudiantes, int n)
    {
        ArrayList<Estudiante> estudiantesFull = new ArrayList<Estudiante>();
        int contador = estudiantes.size();
        int filaAnt = 0;
        int j;
        for (int i = 0 ; i < contador ; i++)
        {

            int filaAct = estudiantes.get(i).getPosFila();
            while (filaAnt+1 != filaAct)
            {
                Estudiante e = new Estudiante(0,"","","",0,"",filaAnt+1,0);
                estudiantesFull.add(e);
                filaAnt++;
            }
            estudiantesFull.add(estudiantes.get(i));
            filaAnt = filaAct;
        }
        contador =estudiantesFull.size();
        if( contador < n)
        {
            int valorFila = estudiantesFull.get(contador-1).getPosFila() + 1;

            for(int i = contador ; i < n; i++)
            {
                Estudiante e = new Estudiante(0,"","","",0,"",valorFila,0);
                estudiantesFull.add(e);
                valorFila++;
            }
        }

        return estudiantesFull;
    }

    private void eventosGridViewItem()
    {
        gv_ubicacion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                CardView c = (CardView) view.findViewById(R.id.cv_item_estudiante);

                c.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.background_card_view));

                if(lastView != null)
                {
                    if((int) id != lastIdentificacion)
                    {
                        identificacion = (int) id;
                        fila = estudiantes.get(position).getPosFila();


                        motrarAlerta();

                    }

                    c.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                    CardView cardViewLast = (CardView) lastView.findViewById(R.id.cv_item_estudiante);
                    cardViewLast.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                    lastView = null;
                    //preguntar intercambiar




                    //lastIdentificacion = 0;
                }
                else
                {
                    lastView = view;
                    lastIdentificacion = (int) id;
                    lastFila = estudiantes.get(position).getPosFila();
                }
                return false;
            }
        });
    }

    private void start() {

        this.recreate();
    }

    private void motrarAlerta() {
        new AlertDialog.Builder(Ubicacion.this)
                .setTitle("INTERCAMBIO")
                .setMessage("¿Desea intercambiar los estudiantes seleccionados?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        manager.actualizarFila(identificacion,lastFila);
                        manager.actualizarFila(lastIdentificacion,fila);

                        Toast.makeText(getApplicationContext(),"Intercambio REALIZADO" ,Toast.LENGTH_SHORT).show();
                        fila = 0;
                        identificacion = 0;
                        lastFila = 0;
                        lastIdentificacion = 0;

                        start();

                    }})
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getApplicationContext(),"Operación Cancelada",Toast.LENGTH_SHORT).show();
                    }}).show();

    }


}
