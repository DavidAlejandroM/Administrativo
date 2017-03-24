package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Created by Andrés Moreno on 14/03/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Estudiante> {

    ArrayList<Estudiante> estudiantes;
    Context context;
    int resource;
    static OperacionesBaseDeDatos datos;

    public CustomListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Estudiante> objects) {
        super(context, resource, objects);
        this.estudiantes = objects;
        this.context = context;
        this.resource = resource;
    }

    public CustomListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_layout, null, true);
        }
        try{
        Button delbtn = (Button) convertView.findViewById(R.id.eliminarEst);
        delbtn.setTag(position);
        datos = OperacionesBaseDeDatos.obtenerInstancia(convertView.getContext());
        delbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Estudiante est = null;
                        for(int i=0 ; i<getCount() ; i++){
                            if(i==position){
                                est = getItem(i);
                            }
                        }
                        datos.borrarEstudiante(Integer.toString(est.getIdentificacion()));
                        remove(est);
                        notifyDataSetChanged();
                    }
                }
        );}catch (Exception e){e.printStackTrace();}
        Estudiante estudiante = getItem(position);
        ImageView imageEst;
        TextView nombreEst;
        TextView apellidoEst;
        TextView idEst;
        Uri uri = pathToUri(estudiante.getFoto());
        imageEst = (ImageView)convertView.findViewById(R.id.imgEst);

        if (!uri.equals(Uri.EMPTY)){
        imageEst.setImageURI(pathToUri(estudiante.getFoto()));
        }else{
            imageEst.setImageResource(R.mipmap.ic_launcher);
        }

        nombreEst = (TextView)convertView.findViewById(R.id.txtNombre);
        nombreEst.setText(estudiante.getNombres());

        apellidoEst = (TextView)convertView.findViewById(R.id.txtApellido);
        apellidoEst.setText(estudiante.getApellidos());

        idEst = (TextView)convertView.findViewById(R.id.txtIde);
        idEst.setText(Integer.toString(estudiante.getIdentificacion()));

        nombreEst.setTextColor(Color.BLACK);
        apellidoEst.setTextColor(Color.BLACK);
        idEst.setTextColor(Color.BLACK);
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
