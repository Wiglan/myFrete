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
import com.wfrete.wfrete2.adapter.CategoriaAdapter;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.model.Categoria;

/**
 * Created by Desenvolvimento 11 on 22/11/2017.
 */

public class CategoriaListarActivity extends Fragment {

    private static final int ID_COMANDO_NOVO_CADASTRO = 1;
    private static final int ID_COMANDO_EDITAR_REG = 7;
    private FloatingActionButton fab;
    View viewCategoria;

    RecyclerView recyclerViewListaCategoria;
    CategoriaAdapter categoriaAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewCategoria = inflater.inflate(R.layout.lista_categoria, container, false);
        return viewCategoria;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fab = (FloatingActionButton) view.findViewById(R.id.fabCategoria);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CategoriaCadastrarActivity.class);
                startActivityForResult(i,ID_COMANDO_NOVO_CADASTRO);
            }
        });


        configurarRecycler(view);

    }

    public void btEditarCategoriaOnClick(Categoria categoria){

        Intent i = new Intent(getActivity(), CategoriaCadastrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.putExtra("categoria", categoria);
        startActivityForResult(i,ID_COMANDO_EDITAR_REG);

    }


    private void configurarRecycler(View view) {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerViewListaCategoria = (RecyclerView)view.findViewById(R.id.rvListaCategorias);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewListaCategoria.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        CategoriaDAO dao = new CategoriaDAO(view.getContext());
        categoriaAdapter = new CategoriaAdapter(dao.ListarCategorias(), this);
        recyclerViewListaCategoria.setAdapter(categoriaAdapter);
        recyclerViewListaCategoria.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null){
            if (requestCode == ID_COMANDO_NOVO_CADASTRO){

                //retornou com um novo categoria cadastrado.
                if (resultCode == 2) {

                    if (data.hasExtra("categoria")) {
                        Categoria categoria = (Categoria) data.getSerializableExtra("categoria");
                        categoria = new CategoriaDAO(getView().getContext()).categoriaById(categoria.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        categoriaAdapter.adicionarCategoria(categoria);

                        Snackbar.make(getView(), "Categoria salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }else if (requestCode == ID_COMANDO_EDITAR_REG){

                //retornou com um categoria alterado.
                if (resultCode == 5) {

                    if (data.hasExtra("categoria")) {
                        Categoria categoria = (Categoria) data.getSerializableExtra("categoria");
                        categoria = new CategoriaDAO(getView().getContext()).categoriaById(categoria.getId()); //buscar do banco, por que pode ter alterado dados pelo webservice.
                        categoriaAdapter.atualizarCategoria(categoria);

                        Snackbar.make(getView(), "Categoria salvo com sucesso!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }

        }

    }
}
