package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Created by alejandro on 17/03/17.
 */

public class DialogSubCategoria extends DialogFragment {

    private OperacionesBaseDeDatos manager;
    private ImageButton ibtn_agregar;
    private EditText et_sub_categoria;
    private int idCategoria;
    private ArrayList<Subcategoria> subcategorias;
    private ListView lv_subcategorias;
    private View vistadialog;
    private TextView tv_titulo;

    private String STRING_TITULO = "titulo";
    private String STRING_ID_CATEGORIA = "idCategoria";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seg_cog_dialog_sub_categorias,container,false);
        vistadialog = view;
        manager = OperacionesBaseDeDatos.obtenerInstancia(getActivity().getApplicationContext());


        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setDataListView();

        //Toast.makeText(getContext(),"dialogActivo",Toast.LENGTH_SHORT).show();
    }

    private void init(final View view)
    {
        tv_titulo = (TextView) view.findViewById(R.id.tv_dialog_titulo);
        ibtn_agregar = (ImageButton) view.findViewById(R.id.ibtn_dialog_agregar);
        et_sub_categoria = (EditText) view.findViewById(R.id.et_dialog_add_nombre_subcategoria);

        Bundle args = getArguments();
        idCategoria = args.getInt(STRING_ID_CATEGORIA);
        String titulo = args.getString(STRING_TITULO);

        tv_titulo.setText(titulo);

        ibtn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Subcategoria sub = new Subcategoria(idCategoria,et_sub_categoria.getText().toString());
                if(manager.insertarSubCategorias(sub))
                {

                    Toast.makeText(getActivity().getApplicationContext(),"Insertado",Toast.LENGTH_SHORT).show();
                    setDataListView();
                    et_sub_categoria.setText("");
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"No Insertado",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().recreate();
    }

    private void setDataListView()
    {
        subcategorias = manager.obtenerSubCategoriasFromCategoriaId(idCategoria);
        String[] strSubCategorias = new String[subcategorias.size()];
        int size = subcategorias.size();
        for (int i = 0;i<size;i++)
        {
            strSubCategorias[size-i-1] = subcategorias.get(i).getNombre();
        }
        lv_subcategorias  = (ListView) vistadialog.findViewById(R.id.lv_dialog_subcategorias);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(vistadialog.getContext(),android.R.layout.simple_list_item_1,strSubCategorias);

        lv_subcategorias.setAdapter(adapter);
    }
}
