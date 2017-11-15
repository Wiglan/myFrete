package com.wfrete.wfrete2.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.wfrete.wfrete2.adapter.LctoAdapter;
import com.wfrete.wfrete2.dao.LctoDAO;
import com.wfrete.wfrete2.model.Lcto;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class LctoListarActivity extends Fragment {

    private static final int ID_COMANDO_NOVO_CADASTRO = 1;
    private static final int ID_COMANDO_EDITAR_REG = 7;
    private FloatingActionButton fab;
    View viewLcto;

    RecyclerView recyclerViewListaLcto;
    LctoAdapter lctoAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLcto = inflater.inflate(R.layout.lista_lcto, container, false);
        return viewLcto;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fab = (FloatingActionButton) view.findViewById(R.id.fabLctos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LctoCadastrarActivity.class);
                startActivityForResult(i,ID_COMANDO_NOVO_CADASTRO);
            }
        });

        configurarRecycler(view);

    }


    public void btEditarLctoOnClick(Lcto lcto){
        Intent i = new Intent(getActivity(), LctoCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("lcto", lcto);
        startActivityForResult(i,ID_COMANDO_EDITAR_REG);
    }


    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaLcto = (RecyclerView)view.findViewById(R.id.rvListaLctos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaLcto.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        LctoDAO dao = new LctoDAO(view.getContext());
        lctoAdapter = new LctoAdapter(dao.ListarLctosByFrete(1), this);
        recyclerViewListaLcto.setAdapter(lctoAdapter);
        recyclerViewListaLcto.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo lcto cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("lcto")) {
                        Lcto lcto = new Lcto();
                        lcto = (Lcto) data.getSerializableExtra("lcto");
                        lctoAdapter.adicionarLcto(lcto);

                        Snackbar.make(getView(), "Lançamento salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == ID_COMANDO_EDITAR_REG){

                //retornou com um lcto alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("lcto")) {
                        Lcto lcto = new Lcto();
                        lcto = (Lcto) data.getSerializableExtra("lcto");
                        lctoAdapter.atualizarLcto(lcto);

                        Snackbar.make(getView(), "Lançamento salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
                //nesse caso, retornou que excluiu o lançamento.
                else if (resultCode == 13){

                    if (data.hasExtra("lcto")) {
                        Lcto lcto = new Lcto();
                        lcto = (Lcto) data.getSerializableExtra("lcto");
                        lctoAdapter.removerLcto(lcto);
                        Snackbar.make(getView(), "Lnçamento Excluído com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
            }

        }

    }
}