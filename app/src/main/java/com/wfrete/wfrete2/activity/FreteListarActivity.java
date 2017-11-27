package com.wfrete.wfrete2.activity;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Toast;


import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.FreteAdapter;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.util.Constantes;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class FreteListarActivity extends Fragment {

    private FloatingActionButton fab;
    View viewFrete;

    RecyclerView recyclerViewListaFrete;
    FreteAdapter freteAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewFrete = inflater.inflate(R.layout.lista_frete, container, false);
        return viewFrete;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fab = (FloatingActionButton) view.findViewById(R.id.fabFretes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FreteCadastrarActivity.class);
                startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        configurarRecycler(view);

    }

    public void btEditarFreteOnClick(Frete frete){
        Intent i = new Intent(getActivity(), FreteCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("frete", frete);
        startActivityForResult(i,Constantes.ID_COMANDO_EDITAR_REG);
    }

    public void listarLctosFrete(Frete frete){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new LctoListarActivity(frete)).commit();
    }

    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaFrete = (RecyclerView)view.findViewById(R.id.rvListaFretes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaFrete.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        FreteDAO dao = new FreteDAO(view.getContext());
        freteAdapter = new FreteAdapter(dao.ListarFretes(), this);
        recyclerViewListaFrete.setAdapter(freteAdapter);
        recyclerViewListaFrete.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == Constantes.ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo frete cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("frete")) {

                        Frete frete = (Frete) data.getSerializableExtra("frete");
                        frete = new FreteDAO(getView().getContext()).freteById(frete.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        
                        freteAdapter.adicionarFrete(frete);

                        Snackbar.make(getView(), "Frete salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == Constantes.ID_COMANDO_EDITAR_REG){

                //retornou com um frete alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("frete")) {
                        Frete frete = (Frete) data.getSerializableExtra("frete");
                        frete = new FreteDAO(getView().getContext()).freteById(frete.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.

                        freteAdapter.atualizarFrete(frete);

                        Snackbar.make(getView(), "Frete salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
                //nesse caso, retornou que excluiu o frete.
                else if (resultCode == 13){

                    if (data.hasExtra("frete")) {
                        Frete frete = (Frete) data.getSerializableExtra("frete");
                        frete = new FreteDAO(getView().getContext()).freteById(frete.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        freteAdapter.removerFrete(frete);
                        Snackbar.make(getView(), "Frete Excluído com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
            }

        }

    }
}
