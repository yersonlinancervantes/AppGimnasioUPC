package com.example.appporkys;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appporkys.entity.Porcino;
import com.example.appporkys.entity.Reporte;
import com.example.appporkys.repositories.remote.ServiceFactory;
import com.example.appporkys.repositories.remote.request.PorcinoService;
import com.example.appporkys.repositories.remote.request.ReporteService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReporteCompraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReporteCompraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReporteCompraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReporteCompraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReporteCompraFragment newInstance(String param1, String param2) {
        ReporteCompraFragment fragment = new ReporteCompraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reporte_compra, container, false);
        barChart = v.findViewById(R.id.barChart);

        barChart.getLegend().setEnabled(true);

        barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,0));
        barEntries.add(new BarEntry(2,0));
        barEntries.add(new BarEntry(3,0));
        barEntries.add(new BarEntry(4,0));
        barEntries.add(new BarEntry(5,0));

        barDataSet = new BarDataSet(barEntries,"Leyenda");

        barData = new BarData(barDataSet);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        String[] a = new String[]{"Cant Cerdos Hembras", "Cant Cerdos Machos", "P.C Total Hembras", "P.C Total Machos","P.C Total"};

        l.setExtra(ColorTemplate.VORDIPLOM_COLORS,a);

        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        Description des = new Description();
        des.setText("");
        barChart.setDescription(des);
        barChart.notifyDataSetChanged();

        getEntries();

        return v;
    }

    private void getEntries(){

        ReporteService jsonPlaceHolderApi = ServiceFactory.retrofit.create(ReporteService.class);
        Call<List<Reporte>> call = jsonPlaceHolderApi.getReporte();
        call.enqueue(new Callback<List<Reporte>>() {
            public void onResponse(Call<List<Reporte>> call, Response<List<Reporte>> response) {

                Reporte reporte = null;

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                }
                else{
                    List<Reporte> reporteList = response.body();
                    for(Reporte rep: reporteList) {
                        reporte = new Reporte(rep.getCantCerdosHembras(),rep.getCantCerdosMachos(),rep.getPcTotalHembras(),rep.getPcTotalMacho(),rep.getPcTotal());
                        break;
                    }
                    ArrayList auxBarEntries = new ArrayList<>();

                    auxBarEntries.add(new BarEntry(1,reporte.getCantCerdosHembras()));
                    auxBarEntries.add(new BarEntry(2,reporte.getCantCerdosMachos()));
                    auxBarEntries.add(new BarEntry(3,Math.round(reporte.getPcTotalHembras())));
                    auxBarEntries.add(new BarEntry(4,Math.round(reporte.getPcTotalMacho())));
                    auxBarEntries.add(new BarEntry(5,Math.round(reporte.getPcTotal())));

                    BarDataSet auxBarDataSet = new BarDataSet(auxBarEntries,"Leyenda");
                    BarData auxBarData = new BarData(auxBarDataSet);

                    barChart.setData(auxBarData);
                    auxBarDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    auxBarDataSet.setValueTextColor(Color.BLACK);
                    auxBarDataSet.setValueTextSize(16f);
                    barChart.notifyDataSetChanged();
                    barChart.invalidate();

                }
            }

            @Override
            public void onFailure(Call<List<Reporte>> call, Throwable t) {
                Toast.makeText(getActivity(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}