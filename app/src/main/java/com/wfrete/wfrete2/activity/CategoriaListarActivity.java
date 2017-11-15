package com.wfrete.wfrete2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.AdapterCategoria;
import com.wfrete.wfrete2.model.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 15/11/2017.
 */

public class CategoriaListarActivity extends AppCompatActivity{


    private ListView lvProdutos;
    private final List<Categoria> categorias = new ArrayList<Categoria>();

    private static final int COD_RETORNO = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_categoria);

        lvProdutos = (ListView) findViewById(R.id.lvCategorias);


        Categoria c = new Categoria(1,"Categoria", "Despesa");
        categorias.add(c);

        lvProdutos.setAdapter(new AdapterCategoria(this,categorias));


        //tratar o clique no item do listVeiew
        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tratarEventoClick(position);
            }
        });

    }

    private void tratarEventoClick(int position) {

        int cod = position + 1;
        Intent i = getIntent();
        i.putExtra("codigo", cod);

        setResult(10, i);

        finish();
    }

}
