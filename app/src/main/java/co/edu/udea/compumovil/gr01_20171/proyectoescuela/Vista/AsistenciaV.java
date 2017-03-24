package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Grupo;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class AsistenciaV extends AppCompatActivity {

    private static Asistencia asistenciaC;
    private static ArrayList<Estudiante> estudiantes;
    private Grupo grupo;
    private int[] contadores;
    private static OperacionesBaseDeDatos datos;
    Intent intent;
    Bundle bundle;
    private int tipoVista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_v);

        intent = getIntent();
        bundle = intent.getExtras();
        grupo = (Grupo) intent.getSerializableExtra("GRUPO");
        tipoVista = (int) intent.getIntExtra("tipoVista",1);
        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        estudiantes = retornaEstudiantes(grupo);

        CrearGridView();

        Button addRegistros = (Button) findViewById(R.id.btnAddRegistros);
        Button modRegistros = (Button) findViewById(R.id.btnModRegistros);

        addRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i<estudiantes.size();i++){
                    switch (contadores[i]){
                        case 1:
                        {
                            agregarAsistencia(1,i);
                            break;
                        }
                        case 2:
                        {
                            agregarAsistencia(2,i);
                            break;
                        }
                        case 3:
                        {
                            break;
                        }
                    }

                }
            }
        });


        modRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i<estudiantes.size();i++){
                    switch (contadores[i]){
                        case 1:
                        {
                            modificarAsistencia(1,i);
                            break;
                        }
                        case 2:
                        {
                            modificarAsistencia(2,i);
                            break;
                        }
                        case 3:
                        {
                            break;
                        }
                    }

                }
            }
        });

    }


    private void CrearGridView() {
        //estudiantes = completarEstudiantes(estudiantes,grupo.getColumnas()*grupo.getFilas());

        contadores = new int[estudiantes.size()];
        for (int i = 0; i < contadores.length;i++)
        {
            contadores[i] = 0;
        }


        final AsistenciaEstudianteAdapter adapte = new AsistenciaEstudianteAdapter(this,estudiantes);


        final GridView gridEstudiante = (GridView) findViewById(R.id.grid_view_ubicacion);

        gridEstudiante.setAdapter(adapte);

        gridEstudiante.setNumColumns(4);


        gridEstudiante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AsistenciaV.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                contadores[position]++;
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.contenedor_item_estudiante);

                switch (contadores[position])
                {
                    case 1:
                    {
                        ll.setBackgroundColor(Color.RED);
                        break;
                    }
                    case 2:
                    {
                        ll.setBackgroundColor(Color.YELLOW);
                        break;
                    }
                    case 3:
                    {
                        ll.setBackgroundColor(Color.TRANSPARENT);
                        contadores[position] = 0;
                        break;
                    }
                }
            }
        });


    }

    public static String giveDate() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());

    }


    public void modificarAsistencia(int indicador,int posicionEst){
        co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia asistencia;

        String estado = "";

        String date = giveDate();

        switch (indicador){
            case 1:
                estado = "faltó";
                break;
            case 2:
                estado = "tarde";
                break;
        }
        asistencia = new co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia(date,
                estudiantes.get(posicionEst).getIdentificacion(),estado );
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        try{
            datos.getDb().beginTransaction();
            datos.actualizarAsistencia(asistencia);
            datos.getDb().setTransactionSuccessful();
            Toast.makeText(getApplicationContext(),"Registros modificados",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Se ha producido un error",Toast.LENGTH_SHORT).show();
        }finally{
            datos.getDb().endTransaction();
        }
    }

    public void agregarAsistencia(int indicador,int posicionEst){
        co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia asistencia;

        String estado = "";

        String date = giveDate();

        switch (indicador){
            case 1:
                estado = "faltó";
                break;
            case 2:
                estado = "tarde";
                break;
        }
        asistencia = new co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia(date,
                estudiantes.get(posicionEst).getIdentificacion(),estado );
        datos = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        try{
            datos.getDb().beginTransaction();
            datos.insertarAsistencia(asistencia);
            datos.getDb().setTransactionSuccessful();
            Toast.makeText(getApplicationContext(),"Registros ingresados",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Se ha producido un error",Toast.LENGTH_SHORT).show();
        }finally{
            datos.getDb().endTransaction();
        }

    }

    private static ArrayList<Estudiante> retornaEstudiantes(Grupo grupo){
        try {
            datos.getDb().beginTransaction();
            estudiantes = datos.obtenerEstudiantesDB(grupo);
            datos.getDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            datos.getDb().endTransaction();
        }
        return estudiantes;
    }

    private  static Asistencia retornaAsistenciaDia(String id, String fecha){
        Asistencia asistencia=new Asistencia();
        try {
            datos.getDb().beginTransaction();
            asistencia = datos.obtenerAsistenciaEstudianteDia(id,fecha);
            datos.getDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            datos.getDb().endTransaction();
        }
        return asistencia;

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
