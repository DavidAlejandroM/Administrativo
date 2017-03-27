package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Created by alejandro on 17/03/17.
 */

public class SubCategoriaAdapter extends BaseAdapter
{
    private ArrayList<Subcategoria> subcategorias;
    private Context context;

    public SubCategoriaAdapter(Context context, ArrayList<Subcategoria> subcategorias) {
        this.context = context;
        this.subcategorias = subcategorias;
    }

    @Override
    public int getCount() {
        return subcategorias.size();
    }

    @Override
    public Subcategoria getItem(int position) {
        return subcategorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) subcategorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //layout que se va inflar
            convertView = inflater.inflate(R.layout.item_sub_categoria,parent,false);
        }

        TextView tv_nombre = (TextView) convertView.findViewById(R.id.tv_item_sub_categoria_nombre);
        //TextView tv_id = (TextView) convertView.findViewById(R.id.tv_item_sub_categori_id);

        Subcategoria subcategoria = subcategorias.get(position);

        tv_nombre.setText(subcategoria.getNombre());
        //tv_id.setText(String.valueOf(subcategoria.getId()));

        return convertView;
    }
}