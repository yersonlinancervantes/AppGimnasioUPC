package com.example.appporkys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appporkys.entity.Porcino;
import com.example.appporkys.entity.Usuario;
import com.example.appporkys.repositories.local.Usuario.SessionManagement;
import com.example.appporkys.repositories.remote.ServiceFactory;
import com.example.appporkys.repositories.remote.request.PorcinoService;
import com.example.appporkys.repositories.remote.request.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarPorcinoFragment newInstance(String param1, String param2) {
        ListarPorcinoFragment fragment = new ListarPorcinoFragment();
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

    private ListView listViewPorcinos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listar_porcino, container, false);
        listViewPorcinos = v.findViewById(R.id.listPorcinos);

        Button btnMoveRegisterPorcino = v.findViewById(R.id.btnMoveRegisterPorcino);

        btnMoveRegisterPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),
                        RegistroPorcinoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
*/
                //action_listarPorcino_to_registrarPorcinoFragment
                Navigation.findNavController(v).navigate(R.id.action_listarPorcino_to_registrarPorcinoFragment);
            }

        });
        List<Porcino> listPorcino = new ArrayList<Porcino>();
        final AdapterListView adapter = new AdapterListView(getContext(),listPorcino);


        PorcinoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(PorcinoService.class);
        Call<List<Porcino>> call = jsonPlaceHolderApi.getPorcino();
        call.enqueue(new Callback<List<Porcino>>() {
            public void onResponse(Call<List<Porcino>> call, Response<List<Porcino>> response) {

                Porcino porcino = new Porcino();

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                }
                else{
                    List<Porcino> porcinoList = response.body();
                    for(Porcino por: porcinoList) {
                        listPorcino.add(new Porcino(por.getId(), por.getNombre(), por.getFechaCompra(), por.getUrl(), por.getGenero(), por.getRaza()));
                    }
                }

                listViewPorcinos.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Porcino>> call, Throwable t) {
                Toast.makeText(getActivity(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });




        /*listViewPorcinos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"Hello",Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_listarPorcino_to_listarControlPorcinoFragment);
            }
        });*/

        return v;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}