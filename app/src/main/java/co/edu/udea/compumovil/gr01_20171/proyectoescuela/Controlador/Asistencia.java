package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class Asistencia extends AppCompatActivity {
    private ArrayList<Estudiante> estudiantes;
    private Grupo grupo;
    private OperacionesBaseDeDatos manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        grupo = (Grupo) getIntent().getSerializableExtra("GRUPO");
        estudiantes = manager.obtenerEstudiantesDB(grupo);

        crearGridView();

    }

    private void crearGridView() {


        EstudianteAdapter adapter = new EstudianteAdapter(this, estudiantes);

        GridView gridEstudiante = (GridView) findViewById(R.id.grid_view_ubicacion);

        gridEstudiante.setAdapter(adapter);

        gridEstudiante.setNumColumns(4);
        gridEstudiante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
