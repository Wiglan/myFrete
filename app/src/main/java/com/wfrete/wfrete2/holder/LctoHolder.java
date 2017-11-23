package com.wfrete.wfrete2.holder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wfrete.wfrete2.util.ItemClickListener;
import com.wfrete.wfrete2.R;

/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//classe que mapeia os itens do REcicleView

public class LctoHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtCategoria;
    public TextView txtObs;
    public TextView txtValor;
    public TextView txtData;

    private ItemClickListener itemClickListener;


    public LctoHolder(View itemView) {
        super(itemView);

        txtCategoria = (TextView) itemView.findViewById(R.id.txtCateogiraItem_Lcto);
        txtData = (TextView) itemView.findViewById(R.id.txtDataItemLcto);
        txtObs = (TextView) itemView.findViewById(R.id.txtObsItemLcto);
        txtValor = (TextView) itemView.findViewById(R.id.txtValorItemLcto);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }


    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }


}
