package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ContratoEscuela;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Materia;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class EstadisticaModel extends AppCompatActivity {
    private BarChart chart;
    private float barWidth;
    private float barSpace;
    private float groupSpace;
    private List<String> valX;
    private List<Integer> valSi, valNo;
    private int tipoEstadistica;
    private boolean abrirBarra;
    private Materia materia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_model);
        valX = (ArrayList<String>) getIntent().getSerializableExtra("valX");
        valSi = (List) getIntent().getSerializableExtra("valSi");
        valNo = (List) getIntent().getSerializableExtra("valNo");
        materia = (Materia) getIntent().getSerializableExtra("materia");
        tipoEstadistica = (int)getIntent().getIntExtra("tipoEstadistica",1);
        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;
        chart = (BarChart) findViewById(R.id.barChart);
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        int groupCount = 6;

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        for (int i = 1; i <= valX.size(); i++) {
            yVals1.add(new BarEntry(i, (float) valSi.get(i - 1), valX.get(i-1)));
            yVals2.add(new BarEntry(i, (float) valNo.get(i - 1), valX.get(i-1)));

        }

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "SI");
        set1.setColor(Color.GREEN);
        set2 = new BarDataSet(yVals2, "NO");
        set2.setColor(Color.RED);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(getIntent().getBooleanExtra("abrirBarra",false));
        chart.invalidate();


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMaximum(valX.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(valX));
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setBackgroundColor(Color.rgb(252,236,233));
/*        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.DKGRAY);
            }
        });*/
       chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
               crearVistaBarra(e);
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    public void crearVistaBarra(Entry e){
        OperacionesBaseDeDatos manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());
        if (tipoEstadistica== 1){
            EstadisticaCognitiva estadistica = new EstadisticaCognitiva(manager);
            estadistica.setIdEstudiante(getIntent().getIntExtra("idEstudiante",0));
            String nombreBarra = (String) e.getData();
            Categoria cat = manager.obtenerCategoria(1,nombreBarra);
            ArrayList<Subcategoria> subcategorias = manager.obtenerSubCategoriasFromCategoriaId(cat.getId());
            ArrayList<Integer> valSi=estadistica.asignarValoresSi(subcategorias);
            ArrayList<Integer> valNo= estadistica.asignarValoresNo(subcategorias);


                Intent intent = new Intent(this,EstadisticaModel.class);
                intent.putStringArrayListExtra("valX",estadistica.listarSubCategorias(cat.getId()));
                intent.putExtra("valSi",valSi );
                intent.putExtra("valNo", valNo);
                intent.putExtra("abrirBarra", false);
                startActivity(intent);

        }




        /*Intent intent = new Intent(this,PantallaPpal.class);
        startActivity(intent);*/
    }

        public void setValX(){


        }

        public void setValY(){


        }
}
