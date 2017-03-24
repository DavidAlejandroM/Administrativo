package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class MateriaAdicion extends AppCompatActivity {

    private OperacionesBaseDeDatos manager;
    private EditText et_nombre_materia;
    private ListView lv_materias;
    private ArrayList<Materia> materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_materia);

        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());

        init();
    }

    private void init() {
        materias = manager.obtenerMaterias();

        et_nombre_materia = (EditText) findViewById(R.id.et_materia_nombre);
        lv_materias = (ListView) findViewById(R.id.lv_materias);

        refrescarListView(lv_materias,materias);
    }

    private void refrescarListView(ListView lv_materias, ArrayList<Materia> materias) {
        int n = materias.size();
        String[] strMaterias = new String[n];
        for(int i = 0; i < n; i++)
        {
            strMaterias[n - 1 - i] = materias.get(i).getNombre();
        }

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,strMaterias);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.item_simple_1,strMaterias);

        lv_materias.setAdapter(adapter);
    }

    public void clickAgregarMateria(View view)
    {
        String nombre = et_nombre_materia.getText().toString();
        if (!nombre.isEmpty())
        {
            Materia materia = new Materia(nombre);
            if (manager.insertarMaterias(materia))
            {
                Toast.makeText(getApplicationContext(),"Materia Agregada",Toast.LENGTH_LONG).show();
                materias = manager.obtenerMaterias();
                refrescarListView(lv_materias,materias);
                et_nombre_materia.setText("");
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Materia NO Agregada Intentarlo Mas Tarde",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No dejar el campo Vacio",Toast.LENGTH_LONG).show();
        }
    }

}
