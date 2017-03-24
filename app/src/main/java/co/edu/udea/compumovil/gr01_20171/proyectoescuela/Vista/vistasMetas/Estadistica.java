package co.edu.udea.compumovil.gr01_20171.proyectoescuela.Vista.vistasMetas;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import java.util.ArrayList;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.ManejaBDMetas;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.OperacionesBaseDeDatos;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.CumplimientoMeta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Estudiante;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.Modelo.POJO.Meta;
import co.edu.udea.compumovil.gr01_20171.proyectoescuela.R;

public class Estadistica extends Activity{

    private BarChart chart;
    float barWidth;
    float barSpace;
    float groupSpace;
    Bundle bundle;
    private Intent intencion;
    ArrayList<CumplimientoMeta> cumplimiento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_estadistica);
        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;
        intencion = getIntent();
        bundle = intencion.getExtras();
        int idMeta = bundle.getInt("IDMETA");
        chart = (BarChart)findViewById(R.id.barChart);
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        ArrayList xVals = new ArrayList();
        ArrayList<Meta> metaPorEstudiante = ManejaBDMetas.retornarDatos(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),2,idMeta);
        //recuperar estudiantes que tengan idMetaporestudiante
        int groupCount = metaPorEstudiante.size();
        ArrayList<ArrayList<CumplimientoMeta>> listarCumplimiento = new ArrayList<>();
        Estudiante est;

        for (int i = 0; i < groupCount; i++) {
            est = ManejaBDMetas.obtenerEstudiante(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext()),
                    metaPorEstudiante.get(i).getEstudianteId());
            xVals.add(est.getNombres()+" "+est.getApellidos());
            listarCumplimiento.add(ManejaBDMetas.recuperaCumplimientos(OperacionesBaseDeDatos.obtenerInstancia(getApplicationContext())
                    ,metaPorEstudiante.get(i).getId()));
            Log.d("MENSAJE", metaPorEstudiante.get(i).getId()+"");
        }

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        int cantidadCumplimiento[];

        for (int i = 0; i < listarCumplimiento.size(); i++) {
            cantidadCumplimiento = calcularCantCumplimientos(listarCumplimiento.get(i));
            yVals1.add(new BarEntry(i+1,cantidadCumplimiento[1]));
            yVals2.add(new BarEntry(i+1,cantidadCumplimiento[0]));
        }

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Cumplió");
        set1.setColor(Color.YELLOW);
        set2 = new BarDataSet(yVals2, "No Cumplió");
        set2.setColor(Color.RED);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
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
        xAxis.setDrawGridLines(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        chart.getAxisRight().setEnabled(false);
    }

    private int[] calcularCantCumplimientos(ArrayList<CumplimientoMeta> lista ){
        CumplimientoMeta registro;
        int[] vector = new int[2];
        for(int i=0; i<lista.size(); i++){
            registro = lista.get(i);
            if(registro.getEstado() == 0) vector[0]++;
            else vector[1]++;
        }
        return (vector);
    }
}