package com.example.appporkys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.appporkys.entity.PartoPorcino;
import com.example.appporkys.repositories.local.ControlPorcino.ControlPorcinoSQLiteManager;
import com.example.appporkys.repositories.local.PartoPorcino.PartoPorcinoSQLiteManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarPartoPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarPartoPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarPartoPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarPartoPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarPartoPorcinoFragment newInstance(String param1, String param2) {
        ListarPartoPorcinoFragment fragment = new ListarPartoPorcinoFragment();
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
        }
    }

    private ListView listViewPartoPorcinos;
    List<PartoPorcino> listPartoPorcinos;
    private PartoPorcinoSQLiteManager partoPorcinoSQLiteManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listar_parto_porcino, container, false);

        listViewPartoPorcinos = v.findViewById(R.id.listViewPartoPorcinos);
        partoPorcinoSQLiteManager = new PartoPorcinoSQLiteManager(getActivity());
        listPartoPorcinos = partoPorcinoSQLiteManager.getPartoPorcinoAll(porcinoId);

        Button btnNuevoPartoPorcino = v.findViewById(R.id.btnNuevoPartoPorcino);

        final AdapterListViewPartoPorcino adapter = new AdapterListViewPartoPorcino(getContext(),listPartoPorcinos);

        listViewPartoPorcinos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnNuevoPartoPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("porcinoId", String.valueOf(porcinoId));
                Navigation.findNavController(v).navigate(R.id.action_listarPartoPorcinoFragment_to_registrarPartoPorcinoFragment,bundle);
            }
        });

        return v;
    }
}