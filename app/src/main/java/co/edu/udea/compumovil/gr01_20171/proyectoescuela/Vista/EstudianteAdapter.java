package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alejandro on 9/03/17.
 */

public class EstudianteAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Estudiante> estudiantes;

    /*variable temporales*/
    private String[] nombres;
    private String[] apellidos;
    private String[] fotos;

    /**
     * la idea es trabajar con una clase estudiante que ya tendria como atributos nombre apellido y
     * fotos
     * @param context
     * @param nombres
     * @param apellidos
     */

    public EstudianteAdapter(Context context, ArrayList<Estudiante> estudiantes)
    {
    //public EstudianteAdapter(Context context, String[] nombres, String[] apellidos) {
        this.context = context;
        this.estudiantes = estudiantes;
        /*this.nombres = nombres;
        this.apellidos = apellidos;*/
    }

    @Override
    public int getCount()
    {
        //return nombres.length;
        return estudiantes.size();
    }

    @Override
    public Object getItem(int position) {
        //return nombres[position];
        return estudiantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return nombres[position].hashCode();
        return ((long) estudiantes.get(position).getIdentificacion());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_estudiante,parent,false);
        }

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_item_estudiante_nombre);
        TextView tvApellido = (TextView) convertView.findViewById(R.id.tv_item_estudiante_apellido);
        ImageView ivFoto = (ImageView) convertView.findViewById(R.id.iv_item_estudiante_foto);

       //tvNombre.setText(this.nombres[position]);
        //tvApellido.setText(this.apellidos[position]);
        Estudiante estudiante = estudiantes.get(position);
        Uri uri = pathToUri(estudiante.getFoto());
        if (!uri.equals(Uri.EMPTY)){
            ivFoto.setImageURI(pathToUri(estudiante.getFoto()));
        }else{
            ivFoto.setImageResource(R.mipmap.ic_launcher);
        }
        tvNombre.setText(estudiante.getNombres());
        tvApellido.setText(estudiante.getApellidos());

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
