package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Seguimiento;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase que controla la asignacion del seguimiento cognitivo
 */
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
    private String textAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seg_cog_estudiante_activity);
        int id = getIntent().getIntExtra("id",0);


        estudiante = manager.obtenerEstudiante(id);
        incializarComponente();

    }

    /**
     * Inicializa la vista de acuerdo a la taxonomía BLOOM para su asignacion y creacion de subcategorias
     */

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
     * Evento al seleccionar la materia, se asigna a la variable global la materia
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

    /**
     * Evento a cada uno de las listas de categoria, para realizar la asignacion del cumplimiento.
     */
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


    /**
     * Metodo que captura el evento de la pulsacion larga e identifica si fue un CUMPLIO o un NO CUMPLIO y la guarda en la base de datos
     * @param view Vista que tiene el evento
     * @param position Posicion de la lista de las subcategorias
     * @param id Identificacion de estudiante a la cual se le va a hacer la asignacion
     */
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

        btn_si.setColorFilter(Color.GREEN);


        btn_no.setImageResource(R.drawable.ic_close_circle_grey600_24dp);

        //btn_si.setBackgroundColor(getColor(R.color.transparente));
        btn_si.setBackgroundColor(Color.TRANSPARENT);
        //btn_no.setBackgroundColor(getColor(R.color.transparente));
        btn_no.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        btn_si.setLayoutParams(params);



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
                textAlert="Confirmar SI";
                boolean response = motrarAlerta(textAlert);

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
                textAlert="Confirmar NO";
                boolean response = motrarAlerta(textAlert);

                ll.removeAllViews();

            }
        });


        lastItemView = view;
    }


    /**
     * Asigna los datos a la vista de subcategorias
     * @param lv
     * @param data
     */
    private void setDataListView(ListView lv,String[] data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,data);

        lv.setAdapter(adapter);
    }

    /**
     * Se encarga de mostrar el mensaje para confirmar la nueva asignacion
     * @param textAlert
     * @return
     */
    private boolean motrarAlerta(String textAlert) {
        new AlertDialog.Builder(SegCogEstudiante.this)
                .setTitle(textAlert)
                .setIcon(R.drawable.checkbox_marked_circle)

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

    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Aplicar
    * @param view
     */
    public void clickAgregarSubAplicar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.aplicar));
    }

    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Analizar
     * @param view Objeto de la vista
     */
    public void clickAgregarSubAnalizar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.analizar));
    }

    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Comprender
     * @param view Objeto de la vista
     */
    public void clickAgregarSubComprender(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.comprender));
    }

    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Crear
     * @param view Objeto de la vista
     */
    public void clickAgregarSubCrear(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.crear));
    }
    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Reecordar
     * @param view Objeto de la vista
     */
    public void clickAgregarSubRecordar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.recordar));
    }
    /**
     * Muestra el dialogo para añadir subcategorias de la categoria Evaluar
     * @param view Objeto de la vista
     */
    public void clickAgregarSubEvaluar(View view)
    {
        OpenDialogSubCategorias(getResources().getString(R.string.evaluar));
    }

    /**
     * Recibe con la ubicacion de la imagen puede pasarla a URI
     * @param imgPath
     * @return
     */
    private Uri pathToUri(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists())
        {
            return Uri.fromFile(imgFile);

        }
        return Uri.EMPTY;
    }

    /**
     * Abre la vista para añadir subcategorias de acuerdo al nombre de la categoria
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

    /**
     * Creacion la lista de la subcategorias que se van añadiendo para asignarla en la base de datos
     * @param nombreCategoria
     * @param lv Vista de la lista
     */

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