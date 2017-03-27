package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador.vistasMetas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase que soporta el listado de estudiantes pertenecientes a un grupo, en la vista de cumplimiento.
 */
public class CustomListAdapterMC extends ArrayAdapter<Estudiante>{

    Context context;
    int resource;
    ImageView imageEst;
    TextView nombreEst;
    TextView apellidoEst;
    private ArrayList<Boolean> itemSelection;
    private ArrayList<Boolean> itemSelection2;
    Estudiante est;
    private CheckBox cumple, noCumple;

    public CustomListAdapterMC(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        itemSelection = new ArrayList<>();
        itemSelection2 = new ArrayList<>();
    }

    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_list_metas_cumplimiento, null, true);

        }
        try{
            if(itemSelection.size()<100){
                itemSelection.add(false);
                itemSelection2.add(false);
            }
            cumple = (CheckBox)convertView.findViewById(R.id.cumple);
            noCumple = (CheckBox)convertView.findViewById(R.id.noCumple);
            cumple.setTag(position);
            cumple.setOnClickListener(
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

                            itemSelection2.set(position,!check.isChecked());
                            est.getGestorMetas().setCumplimiento(true);
                            est.getGestorMetas().setAsignacionCumplimiento(true);
                        }
                    }
            );
            noCumple.setTag(position);
            noCumple.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            CheckBox check = (CheckBox)v;
                            itemSelection2.set(position, check.isChecked());
                            est = null;
                            for(int i=0 ; i<getCount() ; i++){
                                if(i==position){
                                    est = getItem(i);
                                    break;
                                }
                            }

                            itemSelection.set(position,!check.isChecked());
                            est.getGestorMetas().setCumplimiento(false);
                            est.getGestorMetas().setAsignacionCumplimiento(true);
                        }
                    }
            );}catch (Exception e){e.printStackTrace();}

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
        cumple = (CheckBox)convertView.findViewById(R.id.cumple);
        noCumple = (CheckBox)convertView.findViewById(R.id.noCumple);
        cumple.setChecked(itemSelection.get(position));
        noCumple.setChecked(itemSelection2.get(position));
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
