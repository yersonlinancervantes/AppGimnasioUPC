package com.example.appporkys;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appporkys.entity.ControlPorcino;
import com.example.appporkys.repositories.local.ControlPorcino.ControlPorcinoSQLiteManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarControlPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarControlPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarControlPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarControlPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarControlPorcinoFragment newInstance(String param1, String param2) {
        ListarControlPorcinoFragment fragment = new ListarControlPorcinoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    int porcinoId = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            porcinoId = Integer.parseInt(getArguments().getString("porcinoId"));
            //Toast.makeText(getActivity(),"ID =>"+id,Toast.LENGTH_SHORT).show();
        }
    }

    private ListView listViewControlPorcinos;
    List<ControlPorcino> listControlPorcino;
    private ControlPorcinoSQLiteManager controlPorcinoSQLiteManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_listar_control_porcino, container, false);

        listViewControlPorcinos = v.findViewById(R.id.listViewControlPorcinos);
        controlPorcinoSQLiteManager = new ControlPorcinoSQLiteManager(getActivity());
        listControlPorcino = controlPorcinoSQLiteManager.getControlPorcinoAll(porcinoId);

        Button btnNuevoControlPorcino = v.findViewById(R.id.btnNuevoControlPorcino);

        final AdapterListViewControlPorcino adapter = new AdapterListViewControlPorcino(getContext(),listControlPorcino);

        //listControlPorcino.add(new ControlPorcino(1,2,20.0,"10-10-2021","Dosis 1","Lorem ipsum dolor sit amet consectetur adipiscing "));

        listViewControlPorcinos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnNuevoControlPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("porcinoId", String.valueOf(porcinoId));
                Navigation.findNavController(v).navigate(R.id.action_listarControlPorcinoFragment_to_registroControlPorcinoFragment,bundle);
            }
        });

        return v;
    }
}