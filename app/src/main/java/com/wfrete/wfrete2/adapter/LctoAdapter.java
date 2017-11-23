package com.wfrete.wfrete2.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wfrete.wfrete2.util.ItemClickListener;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.LctoListarActivity;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.holder.LctoHolder;
import com.wfrete.wfrete2.model.Categoria;
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

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,dd-MMM");
    private DecimalFormat df = new DecimalFormat("#,###.00");

    private CategoriaDAO categoriaDAO;

    public LctoAdapter(List<Lcto> lctos, Fragment context) {
        this.lctos = lctos;
        this.context = context;

        categoriaDAO = new CategoriaDAO(context.getActivity());
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
                .inflate(R.layout.item_lcto, parent, false));
    }

    @Override
    public void onBindViewHolder(LctoHolder holder, final int position) {

        Categoria categoria = categoriaDAO.categoriaById(lctos.get(position).getCategoria_id());

        holder.txtCategoria.setText(categoria.getNome());
        holder.txtObs.setText(lctos.get(position).getObservacao());

        holder.txtValor.setText("R$ " + df.format(lctos.get(position).getValor()));
        if (categoria.getTipo().equalsIgnoreCase("Receita")){
            holder.txtValor.setTextColor(Color.BLUE);
        }else {
            holder.txtValor.setTextColor(Color.RED);
        }

        String dateStr = dateFormat.format(lctos.get(position).getData());
        dateStr = dateStr.replace("-"," DE ");
        dateStr = dateStr.toUpperCase();
        holder.txtData.setText(dateStr);

        final Lcto lcto = lctos.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if((context) instanceof LctoListarActivity){
                    ((LctoListarActivity)context).btEditarLctoOnClick(lcto);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return lctos != null ? lctos.size() : 0;
    }

    public void adicionarLcto(Lcto lcto){
        lctos.add(lcto);
        notifyItemInserted(getItemCount());

        //notifyAll();
    }

    public void atualizarLcto(Lcto lcto){
        int position = lctos.indexOf(lcto);
        lctos.set(position, lcto);
        notifyItemChanged(position);

        //notifyAll();
    }

    public void removerLcto(Lcto lcto){
        int position = lctos.indexOf(lcto);
        lctos.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);

        //notifyAll();
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
