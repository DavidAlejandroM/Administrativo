package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Controlador;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Categoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Subcategoria;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

/**
 * Clase responsable de la creación de la grafica para la estadistica
 */
public class EstadisticaModel extends Activity {
    /**
     * Grafico principal
     */
    private BarChart chart;
    private float barWidth;
    private float barSpace;
    private float groupSpace;

    /**
     * Lista de los nombres que se asigna a las barras
     */
    private List<String> valX;

    /**
     * Lista de los valores de SI y NO Cumplió para graficar
     */
    private List<Integer> valSi, valNo;
    /**
     * Suiche que valida si debe tener evento las barra para desplegar las subcategorias
     */
    private boolean abrirBarra;

    /**
     * Inicializa los valores y las propiedades de los graficos
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_model);

        TextView et_titulo = (TextView) findViewById(R.id.titulo_estadisticas);
        et_titulo.setText(getIntent().getStringExtra("titulo"));

        valX = (ArrayList<String>) getIntent().getSerializableExtra("valX");
        valSi = (List) getIntent().getSerializableExtra("valSi");
        valNo = (List) getIntent().getSerializableExtra("valNo");
        inicializarComponente();
    }

    /**
     * Inicializa los valores y las propiedades de los graficos
     */

    public void inicializarComponente(){

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;
        chart = (BarChart) findViewById(R.id.barChart);
        chart.setDescription(null);
        chart.setPinchZoom(true);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        int groupCount = 6;


        chart.setData(asignarValoresBarra());
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(getIntent().getBooleanExtra("abrirBarra",false));
        chart.invalidate();

        crearLegenda();
        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
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


        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true);
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
    /**
     * Metodo que se encarga de crear la vista de las subcategorias cuando se genera el evento sobre la barra
     * @param e Objeto que se aplicó el evento
     */
    public void crearVistaBarra(Entry e){
        OperacionesBaseDeDatos manager = OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext());


            EstadisticaCognitiva estadistica = new EstadisticaCognitiva(manager);
            estadistica.setIdEstudiante(getIntent().getIntExtra("idEstudiante",0));
            String nombreBarra = (String) e.getData();
            Categoria cat = manager.obtenerCategoria(1,nombreBarra);
            ArrayList<Subcategoria> subcategorias = manager.obtenerSubCategoriasFromCategoriaId(cat.getId());
            ArrayList<Integer> valSi=estadistica.asignarValSiSubcategorias(subcategorias);
            ArrayList<Integer> valNo= estadistica.asignarValNoSubcategorias(subcategorias);

                Intent intent = new Intent(this,EstadisticaModel.class);
                intent.putStringArrayListExtra("valX",estadistica.listarSubCategorias(cat.getId()));
                intent.putExtra("valSi",valSi );
                intent.putExtra("valNo", valNo);
                intent.putExtra("titulo","SubCategorías de "+cat.getNombre());
                intent.putExtra("abrirBarra", false);
                startActivity(intent);


    }

    /**
     * Asgina los valores Y (verticales) para poder realizar las barras del SI cumplió y NO cumplió
     * @return Barra donde contiene los datos Y
     */

    public BarData asignarValoresBarra(){

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        for (int i = 1; i <= valX.size(); i++) {
            yVals1.add(new BarEntry(i, (float) valSi.get(i - 1), valX.get(i-1)));
            yVals2.add(new BarEntry(i, (float) valNo.get(i - 1), valX.get(i-1)));

        }

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Cumplió");
        set1.setColor(Color.rgb(175,203,122));
        set2 = new BarDataSet(yVals2, "No cumplió");
        set2.setColor(Color.rgb(253,116,116));
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());

        return data;
    }

    /**
     * Crear la leyenda en la esquina superior dereha para poder interpretar el grafico
     */
    public void crearLegenda(){

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(25f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);


    }



}
