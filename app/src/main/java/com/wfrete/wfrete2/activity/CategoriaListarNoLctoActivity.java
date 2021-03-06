package com.wfrete.wfrete2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.CategoriaNoLctoAdapter;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.model.Categoria;
import com.wfrete.wfrete2.util.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 15/11/2017.
 */

public class CategoriaListarNoLctoActivity extends AppCompatActivity{



    private Button btCancelar;
    private Button btNovaCategoria;

    private ListView lvCategorias;

    private List<Categoria> categorias = new ArrayList<Categoria>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_categoria_no_lcto);

        getSupportActionBar().setTitle("Listagem de Categorias");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        btNovaCategoria = (Button) findViewById(R.id.btNovaCategoriaListar);
        btCancelar = (Button) findViewById(R.id.btCancelarCategoriaListar);

        lvCategorias = (ListView) findViewById(R.id.lvCategorias);


        configurarAdapterListCategorias();


        btNovaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CategoriaCadastrarActivity.class);
                startActivityForResult(i,Constantes.ID_COM_CADASTRAR_NOVO);
            }
        });


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                setResult(Constantes.ID_COM_CANCELADO, i);
                finish();
            }
        });


        //tratar o clique no item do listVeiew
        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = getIntent();
                i.putExtra("categoria", categorias.get(position));
                setResult(Constantes.ID_COM_REG_SELECIONADO, i);
                finish();
            }
        });

    }

    private void configurarAdapterListCategorias() {

        categorias = new CategoriaDAO(this).ListarCategorias();
        lvCategorias.setAdapter(new CategoriaNoLctoAdapter(this,categorias));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (data != null){
            if (requestCode == Constantes.ID_COM_CADASTRAR_NOVO){

                //inseriu uma nova categoria
                if (resultCode == 2){

                    if (data.hasExtra("categoria")) {
                        configurarAdapterListCategorias();
                    }
                }
            }
        }

    }


}
