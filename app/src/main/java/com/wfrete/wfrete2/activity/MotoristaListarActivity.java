package com.wfrete.wfrete2.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.MainActivity;
import com.wfrete.wfrete2.adapter.MotoristaAdapter;
import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.service.MotoristaService;
import com.wfrete.wfrete2.dao.MotoristaDAO;
import com.wfrete.wfrete2.model.Motorista;
import com.wfrete.wfrete2.util.Constantes;

import org.json.JSONObject;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class MotoristaListarActivity extends Fragment {

    private FloatingActionButton fab;
    View viewMotorista;

    RecyclerView recyclerViewListaMotorista;
    MotoristaAdapter motoristaAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewMotorista = inflater.inflate(R.layout.lista_motorista, container, false);
        return viewMotorista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fab = (FloatingActionButton) view.findViewById(R.id.fabMotorista);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MotoristaCadastrarActivity.class);
                startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });


        configurarRecycler(view);

    }

    public void btEditarMotoristaOnClick(Motorista motorista){

        Intent i = new Intent(getActivity(), MotoristaCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("motorista", motorista);
        startActivityForResult(i,Constantes.ID_COMANDO_EDITAR_REG);

    }


    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaMotorista = (RecyclerView)view.findViewById(R.id.rvListaMotoristas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaMotorista.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        MotoristaDAO dao = new MotoristaDAO(view.getContext());
        motoristaAdapter = new MotoristaAdapter(dao.ListarMotoristas(), this);
        recyclerViewListaMotorista.setAdapter(motoristaAdapter);
        recyclerViewListaMotorista.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == Constantes.ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo motorista cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("motorista")) {
                        Motorista motorista = (Motorista) data.getSerializableExtra("motorista");
                        motorista = new MotoristaDAO(getView().getContext()).motoristaById(motorista.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        motoristaAdapter.adicionarMotorista(motorista);

                        Snackbar.make(getView(), "Motorista salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == Constantes.ID_COMANDO_EDITAR_REG){

                //retornou com um motorista alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("motorista")) {
                        Motorista motorista = (Motorista) data.getSerializableExtra("motorista");
                        motorista = new MotoristaDAO(getView().getContext()).motoristaById(motorista.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        motoristaAdapter.atualizarMotorista(motorista);

                        Snackbar.make(getView(), "Motorista salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }

        }

    }
}
