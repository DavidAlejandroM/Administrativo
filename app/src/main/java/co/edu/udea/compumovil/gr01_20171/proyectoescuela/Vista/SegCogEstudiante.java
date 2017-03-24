package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class SegCogEstudiante extends Activity {

    private String APROVACION = "si";
    private String RECHAZO = "no";

    private boolean confirmar = false;

    private ListView lv_aplicar;
    private ListView lv_analizar;
    private ListView lv_comprender;
    private ListView lv_crear;
    private ListView lv_recordar;
    private ListView lv_evaluar;

    private ImageView iv_foto;
    private TextView tv_nombre_apellido;
    private Spinner sp_materias;

    private Seguimiento seguimiento;
    private Estudiante estudiante;
    private Materia materia;
    private OperacionesBaseDeDatos manager;

    private ArrayList<Subcategoria> arrayListAplicar;
    private ArrayList<Subcategoria> arrayListAnalizar;
    private ArrayList<Subcategoria> arrayListComprender;
    private ArrayList<Subcategoria> arrayListCrear;
    private ArrayList<Subcategoria> arrayListRecordar;
    private ArrayList<Subcategoria> arrayListEvaluar;
    private ArrayList<Materia> materias;

    private View lastItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seg_cog_estudiante_activity);
        int id = getIntent().getIntExtra("id",0);
        manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        estudiante = manager.obtenerEstudiante(id);

        incializarComponente();

    }

    private void incializarComponente() {

        lv_aplicar  = (ListView) findViewById(R.id.lv_aplicar);
        lv_analizar  = (ListView) findViewById(R.id.lv_analizar);
        lv_comprender  = (ListView) findViewById(R.id.lv_compreder);
        lv_crear  = (ListView) findViewById(R.id.lv_crear);
        lv_recordar  = (ListView) findViewById(R.id.lv_recordar);
        lv_evaluar  = (ListView) findViewById(R.id.lv_evaluar);

        sp_materias = (Spinner) findViewById(R.id.sp_seg_cog_materias);
        iv_foto = (ImageView) findViewById(R.id.iv_seg_cog_foto_estudiante);
        tv_nombre_apellido = (TextView) findViewById(R.id.tv_seg_cog_nombre_estudiante);

        tv_nombre_apellido.setText(estudiante.getNombres()+" "+ estudiante.getApellidos());
        Uri uri = pathToUri(estudiante.getFoto());
        if (!uri.equals(Uri.EMPTY))
        {
            iv_foto.setImageURI(pathToUri(estudiante.getFoto()));
        }
        else
        {
            iv_foto.setImageResource(R.mipmap.ic_launcher);
        }

        materias = manager.obtenerMaterias();
        materia = materias.get(0);
        int n = materias.size();
        String[] strMaterias = new String[n];
        for(int i = 0; i < n; i++)
        {
            strMaterias[i] = materias.get(i).getNombre();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strMaterias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_materias.setAdapter(dataAdapter);


        crearListView(getResources().getString(R.string.aplicar),lv_aplicar);
        crearListView(getResources().getString(R.string.analizar),lv_analizar);
        crearListView(getResources().getString(R.string.comprender),lv_comprender);
        crearListView(getResources().getString(R.string.crear),lv_crear);
        crearListView(getResources().getString(R.string.recordar),lv_recordar);
        crearListView(getResources().getString(R.string.evaluar),lv_evaluar);

        eventoSpinner();
        eventosListViews();




    }

    /**
     * evento al seleccionar la materia asigna a la variable global la materia que se seleccionó
     */
    private void eventoSpinner() {

        sp_materias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                materia = materias.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eventosListViews() {
        lv_aplicar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
        lv_evaluar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
        lv_recordar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
        lv_crear.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
        lv_comprender.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
        lv_analizar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventosItems(view,position,id);
                return false;
            }
        });
    }



    private void eventosItems(final View view, int position, long id)
    {
        if(lastItemView != null)
        {
            LinearLayout lastLinear = (LinearLayout) lastItemView.findViewById(R.id.contenedor_item_subcategoria);
            lastLinear.removeAllViews();
        }


        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.contenedor_item_subcategoria);
        ImageButton btn_si = new ImageButton(view.getContext());
        ImageButton btn_no = new ImageButton(view.getContext());

        btn_si.setImageResource(R.drawable.ic_checkbox_marked_circle_black_24dp);
        btn_si.setColorFilter(getColor(R.color.green_color_icon));

        btn_no.setImageResource(R.drawable.ic_close_circle_grey600_24dp);

        btn_si.setBackgroundColor(getColor(R.color.transparente));
        btn_no.setBackgroundColor(getColor(R.color.transparente));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        btn_si.setLayoutParams(params);
        btn_no.setLayoutParams(params);



        ll.addView(btn_si);
        ll.addView(btn_no);

        seguimiento = new Seguimiento((int) id,
                estudiante.getIdentificacion(),
                null,
                null,
                1,
                materia.getId());

        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date myDate = new Date();
                String fecha = new SimpleDateFormat("dd-MM-yyyy").format(myDate);

                seguimiento.setEstado(APROVACION);
                seguimiento.setFecha(fecha);
                seguimiento.setIdMateria(materia.getId());

                boolean response = motrarAlerta();

                ll.removeAllViews();
            }


        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date myDate = new Date();
                String fecha = new SimpleDateFormat("dd-MM-yyyy").format(myDate);

                seguimiento.setEstado(RECHAZO);
                seguimiento.setFecha(fecha);
                seguimiento.setIdMateria(materia.getId());

                boolean response = motrarAlerta();

                ll.removeAllViews();

            }
        });


        lastItemView = view;
    }




    private void setDataListView(ListView lv,String[] data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,data);

        lv.setAdapter(adapter);
    }

    private boolean motrarAlerta() {
        new AlertDialog.Builder(SegCogEstudiante.this)
                .setTitle("Alerta")
                .setMessage("¿Desea continuar con la operación?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(manager.insertarSeguimiento(seguimiento))
                        {
                            seguimiento = null;
                            Toast.makeText(getApplicationContext(),"Seguimiento Insertado",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Seguimiento NO Insertado",Toast.LENGTH_SHORT).show();
                        }
                    }})
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(getApplicationContext(),"Operación Cancelada",Toast.LENGTH_SHORT).show();
                    }}).show();
        return confirmar;
    }

    /*eventos de agregar subcategoria*/
    public void clickAgregarSubAplicar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.aplicar));
    }


    public void clickAgregarSubAnalizar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.analizar));
    }

    public void clickAgregarSubComprender(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.comprender));
    }

    public void clickAgregarSubCrear(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.crear));
    }

    public void clickAgregarSubRecordar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.recordar));
    }

    public void clickAgregarSubEvaluar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.evaluar));
    }

    private Uri pathToUri(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists())
        {
            return Uri.fromFile(imgFile);

        }
        return Uri.EMPTY;
    }

    /**
     *
     * @param nombreCategoria
     */
    private void OpenDialogSubCategorias(String nombreCategoria) {
        //obtenemos el id de la categoria
        Categoria categoria = manager.obtenerCategoria(1,nombreCategoria);
        int id = categoria.getId();

        //creamos los argumentos y le inyectamos el id de la categoria para enviarla al dialog
        Bundle args = new Bundle();
        args.putInt("idCategoria",id);
        args.putString("titulo",nombreCategoria);

        FragmentManager fm = getFragmentManager();
        DialogFragment dialogFragment = new DialogSubCategoria();
        dialogFragment.setArguments(args);
        dialogFragment.show(fm,"dialogAplicar");
    }

    private void crearListView(String nombreCategoria, ListView lv)
    {
        Categoria categoria = manager.obtenerCategoria(1,nombreCategoria);
        int id = categoria.getId();
        ArrayList<Subcategoria> subcategorias = manager.obtenerSubCategoriasFromCategoriaId(id);

        if(nombreCategoria.equals(getResources().getString(R.string.aplicar)))
        {
            arrayListAplicar = subcategorias;
        }
        else if(nombreCategoria.equals(getResources().getString(R.string.analizar)))
        {
            arrayListAnalizar = subcategorias;
        }
        else if (nombreCategoria.equals(getResources().getString(R.string.comprender)))
        {
            arrayListComprender = subcategorias;
        }
        else if (nombreCategoria.equals(getResources().getString(R.string.aplicar)))
        {
            arrayListCrear = subcategorias;
        }
        else if (nombreCategoria.equals(getResources().getString(R.string.evaluar)))
        {
            arrayListEvaluar = subcategorias;
        }
        else if (nombreCategoria.equals(getResources().getString(R.string.recordar)))
        {
            arrayListRecordar = subcategorias;
        }


        SubCategoriaAdapter adapter = new SubCategoriaAdapter(this, subcategorias);

        lv.setAdapter(adapter);
    }



}
