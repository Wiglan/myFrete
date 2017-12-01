package com.wfrete.wfrete2.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.LctoAdapter;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.dao.LctoDAO;
import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.model.Lcto;
import com.wfrete.wfrete2.util.Constantes;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class LctoListarActivity extends Fragment {

    private FloatingActionButton fab;
    View viewLcto;

    RecyclerView recyclerViewListaLcto;
    LctoAdapter lctoAdapter;

    private Frete frete_origem = null;

    @SuppressLint("ValidFragment")
    public LctoListarActivity(Frete frete) {
        this.frete_origem = frete;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLcto = inflater.inflate(R.layout.lista_lcto, container, false);
        return viewLcto;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        fab = (FloatingActionButton) view.findViewById(R.id.fabLctos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LctoCadastrarActivity.class);
                i.putExtra("frete_id", frete_origem.getId());
                startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });

        if (frete_origem.getId() == -1){
            frete_origem = new FreteDAO(view.getContext()).retornarUltimo();
        }

        configurarRecycler(view);

        String str = "Lctos do Frete: " +  String.valueOf(frete_origem.getNro_cte());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(str);

    }


    public void btEditarLctoOnClick(Lcto lcto){

        Intent i = new Intent(getActivity(), LctoCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("lcto", lcto);
        startActivityForResult(i,Constantes.ID_COMANDO_EDITAR_REG);

    }


    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaLcto = (RecyclerView)view.findViewById(R.id.rvListaLctos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaLcto.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        LctoDAO dao = new LctoDAO(view.getContext());
        lctoAdapter = new LctoAdapter(dao.ListarLctosByFrete(frete_origem.getId()), this);
        recyclerViewListaLcto.setAdapter(lctoAdapter);
        recyclerViewListaLcto.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == Constantes.ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo lcto cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("lcto")) {
                        Lcto lcto = (Lcto) data.getSerializableExtra("lcto");
                        lcto = new LctoDAO(getView().getContext()).lctoById(lcto.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        
                        lctoAdapter.adicionarLcto(lcto);

                        Snackbar.make(getView(), "Lançamento salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == Constantes.ID_COMANDO_EDITAR_REG){

                //retornou com um lcto alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("lcto")) {

                        Lcto lcto = (Lcto) data.getSerializableExtra("lcto");
                        lcto = new LctoDAO(getView().getContext()).lctoById(lcto.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.

                        lctoAdapter.atualizarLcto(lcto);

                        Snackbar.make(getView(), "Lançamento salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
                //nesse caso, retornou que excluiu o lançamento.
                else if (resultCode == 13){

                    if (data.hasExtra("lcto")) {

                        Lcto lcto = (Lcto) data.getSerializableExtra("lcto");

                        lctoAdapter.removerLcto(lcto);
                        Snackbar.make(getView(), "Lançamento Excluído com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                }
            }

        }

    }
}
