package com.wfrete.wfrete2.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.LctoListarActivity;
import com.wfrete.wfrete2.holder.LctoHolder;
import com.wfrete.wfrete2.model.Lcto;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//Clase que liga os dados do motorista com o layout item_lista.xml
public class LctoAdapter extends RecyclerView.Adapter<LctoHolder> {

    private Fragment context;
    private final List<Lcto> lctos;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#,###.00");

    public LctoAdapter(List<Lcto> lctos, Fragment context) {
        this.lctos = lctos;
        this.context = context;
    }


    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    public LctoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LctoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(LctoHolder holder, final int position) {

        holder.nome.setText(String.valueOf(lctos.get(position).getValor()));

        final Lcto lcto = lctos.get(position);

        holder.btEditar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if((context) instanceof LctoListarActivity){
                    ((LctoListarActivity)context).btEditarLctoOnClick(lcto);
                }

            }
        });


        /*

/*
        holder.btExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este Frete?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Frete frete = fretes.get(position);
                                FreteDAO dao = new FreteDAO(view.getContext());
                                boolean sucesso = dao.excluir(frete.getId());
                                if (sucesso) {
                                    removerFrete(frete);
                                    Snackbar.make(view, "Frete excluido com sucesso!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Erro ao excluir o Frete!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return lctos != null ? lctos.size() : 0;
    }

    public void adicionarLcto(Lcto lcto){
        lctos.add(lcto);
        notifyItemInserted(getItemCount());
    }

    public void atualizarLcto(Lcto lcto){
        int position = lctos.indexOf(lcto);
        lctos.set(position, lcto);
        notifyItemChanged(position);
    }

    public void removerLcto(Lcto lcto){
        int position = lctos.indexOf(lcto);
        lctos.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
