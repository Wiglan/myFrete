package com.wfrete.wfrete2.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wfrete.wfrete2.Funcoes;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.CategoriaListarActivity;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.holder.CategoriaHolder;
import com.wfrete.wfrete2.model.Categoria;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//Clase que liga os dados do categoria com o layout item_lista.xml
public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaHolder> {

    private Fragment context;
    private final List<Categoria> categorias;

    public CategoriaAdapter(List<Categoria> categorias, Fragment context) {
        this.categorias = categorias;
        this.context = context;
    }

    @Override
    public CategoriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoriaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoriaHolder holder, final int position) {


        String str = Funcoes.formataMsgIntegracao(categorias.get(position).getS_datahora());
        if (str.equalsIgnoreCase("Não Sincronizado")){
            holder.s_datahora.setTextColor(Color.RED);
        }else{
            holder.s_datahora.setTextColor(Color.BLUE);
        }
        holder.s_datahora.setText(str);

        holder.nome.setText(categorias.get(position).getNome());

        final Categoria categoria = categorias.get(position);

        holder.btEditar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if((context) instanceof CategoriaListarActivity){
                    ((CategoriaListarActivity)context).btEditarCategoriaOnClick(categoria);
                }

            }
        });


        holder.btExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este categoria?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Categoria categoria = categorias.get(position);
                                CategoriaDAO dao = new CategoriaDAO(view.getContext());
                                boolean sucesso = dao.excluir(categoria.getId());
                                if (sucesso) {
                                    removerCategoria(categoria);
                                    Snackbar.make(view, "Categoria excluido com sucesso!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Erro ao excluir o categoria!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

    }


    public void atualizarCliente(Categoria categoria){
        categorias.set(categorias.indexOf(categoria), categoria);
        notifyItemChanged(categorias.indexOf(categoria));
    }

    @Override
    public int getItemCount() {
        return categorias != null ? categorias.size() : 0;
    }

    public void adicionarCategoria(Categoria categoria){
        categorias.add(categoria);
        notifyItemInserted(getItemCount());
    }

    public void atualizarCategoria(Categoria categoria){
        int position = categorias.indexOf(categoria);
        categorias.set(position, categoria);
        notifyItemChanged(position);
    }

    public void removerCategoria(Categoria categoria){
        int position = categorias.indexOf(categoria);
        categorias.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
