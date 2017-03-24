package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class CategoriaAdapter extends BaseAdapter {

    private  Context context;
    private ArrayList<Subcategoria> subcategorias;
    private ArrayList<Subcategoria> subcategoriasEnCategoria;
    private ArrayList<Categoria> categorias;
    private Subcategoria subcategoria;
    private Categoria categoria;
    private OperacionesBaseDeDatos manager;
    private ListView lv_subcategorias_etico;
    private String[] nombreSubcategorias;
    private TextView nombreCategoria;
    private TextView nombreSubcategoria;
    private TextView tv_cantidad;
    private int cantidad;
    private ArrayList<Subcategoria> subs;
    Estudiante estudiante;


    public CategoriaAdapter(Context context, ArrayList<Subcategoria> subcategorias, ArrayList<Categoria> categorias, int cantidad)
    {
        this.context = context;
        this.subcategorias = subcategorias;
        this.categorias = categorias;
        this.cantidad = cantidad;
        manager = OperacionesBaseDeDatos.obtenerInstancia(context);

    }

    public CategoriaAdapter(Context context, ArrayList<Subcategoria> subcategorias, ArrayList<Categoria> categorias, Estudiante estudiante)
    {
        this.context = context;
        this.subcategorias = subcategorias;
        this.categorias = categorias;
        this.estudiante = estudiante;
        manager = OperacionesBaseDeDatos.obtenerInstancia(context);
    }

    @Override
    public int getCount()
    {
        return subcategorias.size();
    }

    @Override
    public Object getItem(int position) {
        return subcategorias.get(position);
    }

    @Override
    public long getItemId(int i) { return subcategorias.get(i).getId(); }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_categoria_adapter,parent,false);

        }

        nombreCategoria = (TextView) convertView.findViewById(R.id.subCategoria_nombre_categoria);
        nombreSubcategoria = (TextView) convertView.findViewById(R.id.Subcategoria_nombre_subcategoria);
        tv_cantidad = (TextView) convertView.findViewById(R.id.Subcategoria_cantidad_subcategoria);

        categoria = obtenerCategoriaFromSub(subcategorias.get(position),position);


        if(cantidad != -1){
            cantidad = manager.countSeguimientoFromIdSubcategoriIdEstudiante(subcategorias.get(position).getId(),
                    estudiante.getIdentificacion(),"si");
            tv_cantidad.setText(Integer.toString(cantidad));
        }


        nombreCategoria.setText(categoria.getNombre());
        nombreSubcategoria.setText(subcategorias.get(position).getNombre());
        return convertView;
    }

    private Categoria obtenerCategoriaFromSub(Subcategoria subcategoria, int position){
        Categoria c= null;
        categorias = manager.obtenerCategorias(2);
        ArrayList<Subcategoria> subs;
        for (int i = 0;i<categorias.size();i++){
            subs = manager.obtenerSubCategoriasFromCategoriaId(categorias.get(i).getId());
            for(int j =0;j<subs.size();j++){
                String s1 = subs.get(j).getNombre();
                String s2 = subcategorias.get(position).getNombre();
                if(s1.equalsIgnoreCase(s2)){
                    c = categorias.get(i);
                    return(c);
                }
            }
        }
        return(c);
    }
}
