package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.vistasMetas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Clase que soporta el listado de estudiantes pertenecientes a un grupo, en la vista principal de metas.
 */
public class CustomListAdapterM extends ArrayAdapter<Estudiante> {

    Context context;
    int resource;
    ImageView imageEst;
    TextView nombreEst;
    TextView apellidoEst;
    CheckBox seleccion;
    EditText  duracionMeta;
    private ArrayList<Boolean> itemSelection;
    private ArrayList<String> duracionSelection;
    Estudiante est;

    public CustomListAdapterM(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        itemSelection = new ArrayList<Boolean>();
        duracionSelection = new ArrayList<String>();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_list_metas, null, true);
        }

        try{
            itemSelection.add(false);
            seleccion = (CheckBox) convertView.findViewById(R.id.estudianteSeleccionado);
            duracionMeta = (EditText)convertView.findViewById(R.id.duracion);
            seleccion.setTag(position);
            seleccion.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            CheckBox check = (CheckBox)v;
                            itemSelection.set(position, check.isChecked());
                            est = null;
                            for(int i=0 ; i<getCount() ; i++){
                                if(i==position){
                                    est = getItem(i);
                                    break;
                                }
                            }
                            if(est.getGestorMetas().estado()){
                                est.getGestorMetas().setEstado(false);
                            }else est.getGestorMetas().setEstado(true);
                        }
                    }
            );

            duracionSelection.add("");
            duracionMeta.setOnEditorActionListener(new TextView.OnEditorActionListener(){

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean procesado = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        EditText edit = (EditText)v;
                        duracionSelection.set(position, edit.getText().toString());
                        Estudiante est = null;
                        for(int i=0 ; i<getCount() ; i++){
                            if(i==position){
                                est = getItem(i);
                                break;
                            }
                        }
                        String textoDuracion = edit.getText().toString();
                        if(textoDuracion.compareTo("")!=0)
                            est.getGestorMetas().setDuracionMeta(Integer.parseInt(textoDuracion));
                        else est.getGestorMetas().setDuracionMeta(0);

                        // Ocultar teclado virtual
                        InputMethodManager imm =
                                (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        procesado = true;
                    }
                    return(procesado);
                }
            });

        }catch (Exception e){e.printStackTrace();}

        Estudiante estudiante = getItem(position);

        Uri uri = pathToUri(estudiante.getFoto());
        imageEst = (ImageView)convertView.findViewById(R.id.imgEstM);

        if (!uri.equals(Uri.EMPTY)){
            imageEst.setImageURI(pathToUri(estudiante.getFoto()));
        }else{
            imageEst.setImageResource(R.mipmap.ic_launcher);
        }

        nombreEst = (TextView)convertView.findViewById(R.id.txtNombreM);
        nombreEst.setText(estudiante.getNombres());
        apellidoEst = (TextView)convertView.findViewById(R.id.txtApellidoM);
        apellidoEst.setText(estudiante.getApellidos());
        nombreEst.setTextColor(Color.BLACK);
        apellidoEst.setTextColor(Color.BLACK);
        seleccion = (CheckBox)convertView.findViewById(R.id.estudianteSeleccionado);
        duracionMeta = (EditText)convertView.findViewById(R.id.duracion);

        duracionMeta.setText(duracionSelection.get(position));
        seleccion.setChecked(itemSelection.get(position));
        return convertView;
    }

    private Uri pathToUri(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists())
        {
            return Uri.fromFile(imgFile);

        }
        return Uri.EMPTY;
    }
}
