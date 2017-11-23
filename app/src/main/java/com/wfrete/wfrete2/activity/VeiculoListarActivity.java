package com.wfrete.wfrete2.activity;

import android.app.Fragment;
import android.content.Intent;
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

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.VeiculoAdapter;
import com.wfrete.wfrete2.dao.VeiculoDAO;
import com.wfrete.wfrete2.model.Veiculo;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class VeiculoListarActivity extends Fragment {

    private static final int ID_COMANDO_NOVO_CADASTRO = 1;
    private static final int ID_COMANDO_EDITAR_REG = 7;
    private FloatingActionButton fab;
    View viewVeiculo;

    RecyclerView recyclerViewListaVeiculo;
    VeiculoAdapter veiculoAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewVeiculo = inflater.inflate(R.layout.lista_veiculo, container, false);
        return viewVeiculo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        fab = (FloatingActionButton) view.findViewById(R.id.fabVeiculo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), VeiculoCadastrarActivity.class);
                startActivityForResult(i,ID_COMANDO_NOVO_CADASTRO);
            }
        });

        configurarRecycler(view);

    }


    public void btEditarVeiculoOnClick(Veiculo veiculo){
        Intent i = new Intent(getActivity(), VeiculoCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("veiculo", veiculo);
        startActivityForResult(i,ID_COMANDO_EDITAR_REG);
    }


    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaVeiculo = (RecyclerView)view.findViewById(R.id.rvListaVeiculos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaVeiculo.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        VeiculoDAO dao = new VeiculoDAO(view.getContext());
        veiculoAdapter = new VeiculoAdapter(dao.ListarVeiculos(), this);
        recyclerViewListaVeiculo.setAdapter(veiculoAdapter);
        recyclerViewListaVeiculo.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo veiculo cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("veiculo")) {
                        Veiculo veiculo = (Veiculo) data.getSerializableExtra("veiculo");
                        veiculo = new VeiculoDAO(getView().getContext()).veiculoById(veiculo.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        veiculoAdapter.adicionarVeiculo(veiculo);

                        Snackbar.make(getView(), "Veiculo salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == ID_COMANDO_EDITAR_REG){

                //retornou com um veiculo alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("veiculo")) {
                        Veiculo veiculo = (Veiculo) data.getSerializableExtra("veiculo");
                        veiculo = new VeiculoDAO(getView().getContext()).veiculoById(veiculo.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        veiculoAdapter.atualizarVeiculo(veiculo);

                        Snackbar.make(getView(), "Veiculo salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }

        }

    }
}
