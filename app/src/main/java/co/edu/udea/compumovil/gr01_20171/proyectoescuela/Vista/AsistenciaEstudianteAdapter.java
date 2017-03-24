package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Asistencia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Created by ACER on 18/03/2017.
 */

public class AsistenciaEstudianteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Estudiante> estudiantes;
    private static Asistencia asistenciaC;

    /*variable temporales*/
    private String[] nombres;
    private String[] apellidos;
    private String[] fotos;
    private static OperacionesBaseDeDatos datos;

    public AsistenciaEstudianteAdapter(Context context, ArrayList<Estudiante> estudiantes)
    {
        context.deleteDatabase("pedidos.db");
        datos = OperacionesBaseDeDatos.obtenerInstancia(context);
        //public EstudianteAdapter(Context context, String[] nombres, String[] apellidos) {
        this.context = context;
        this.estudiantes = estudiantes;
        /*this.nombres = nombres;
        this.apellidos = apellidos;*/
    }

    @Override
    public int getCount() {
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

        Uri uri = pathToUri(this.estudiantes.get(position).getFoto());
        ImageView imageEst;

        imageEst = (ImageView)convertView.findViewById(R.id.iv_item_estudiante_foto);
        if (!uri.equals(Uri.EMPTY)){
            imageEst.setImageURI(uri);
        }else{
            imageEst.setImageResource(R.mipmap.ic_launcher);
        }

        asistenciaC = retornaAsistenciaDia(Long.toString(getItemId(position))
                ,giveDate());
        if(asistenciaC!=null && asistenciaC.getAsistencia()!=null)
        {
            if(asistenciaC.getAsistencia().equals("faltó"))
            {
                LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.contenedor_item_estudiante);
                ll.setBackgroundColor(Color.RED);
            }else
            {
                LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.contenedor_item_estudiante);
                ll.setBackgroundColor(Color.YELLOW);
            }
        }

        tvNombre.setText(this.estudiantes.get(position).getNombres());
        tvApellido.setText(this.estudiantes.get(position).getApellidos());

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

    public static String giveDate() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());

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
}
