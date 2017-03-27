package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class EstadisticaEtico extends Activity {
    private ArrayList<Estudiante> estudiantes;
    private Grupo grupo;
    private int[] contadores;

    private OperacionesBaseDeDatos manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_etico);



        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        grupo = (Grupo) getIntent().getSerializableExtra("GRUPO");

    }


    private void crearGridView()
    {
        estudiantes = manager.obtenerEstudiantesDB(grupo);

        estudiantes = completarEstudiantes(estudiantes,grupo.getColumnas()*grupo.getFilas());


        EstudianteAdapter adapter = new EstudianteAdapter(this, estudiantes);

        GridView gridEstudiante = (GridView) findViewById(R.id.grid_view_ubicacion_etico);

        gridEstudiante.setAdapter(adapter);

        gridEstudiante.setNumColumns(grupo.getFilas());

        gridEstudiante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(estudiantes.get(position).getIdentificacion()!=0){
                    Intent intent = new Intent(EstadisticaEtico.this, EstadisticaEticoEstudiante.class);
                    intent.putExtra("id",estudiantes.get(position).getIdentificacion());
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        estudiantes = manager.obtenerEstudiantesDB(grupo);

        crearGridView();
        super.onResume();
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
}