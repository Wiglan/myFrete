package com.wfrete.wfrete2.holder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wfrete.wfrete2.ItemClickListener;
import com.wfrete.wfrete2.R;

/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//classe que mapeia os itens do REcicleView

public class FreteHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public AppCompatTextView txtNroCte;
    public AppCompatTextView txtVlrTotal;
    public AppCompatTextView txtDataAbertura;
    public AppCompatTextView txtDataEncerramento;
    public AppCompatTextView txtOrigem;
    public AppCompatTextView txtDestino;
    public AppCompatTextView txtCliente;
    public AppCompatTextView txtAlterar;

    private ItemClickListener itemClickListener;

    public FreteHolder(View itemView) {
        super(itemView);
        txtNroCte = (AppCompatTextView) itemView.findViewById(R.id.txtNroCteIF);
        txtVlrTotal = (AppCompatTextView) itemView.findViewById(R.id.txtTotalFreteIF);
        txtDataAbertura = (AppCompatTextView) itemView.findViewById(R.id.txtDataInicioIF);
        txtDataEncerramento = (AppCompatTextView) itemView.findViewById(R.id.txtDataFimIF);
        txtOrigem = (AppCompatTextView) itemView.findViewById(R.id.txtOrigemIF);
        txtDestino = (AppCompatTextView) itemView.findViewById(R.id.txtDestinoIF);
        txtCliente = (AppCompatTextView) itemView.findViewById(R.id.txtNomeClienteIF);
        txtAlterar = (AppCompatTextView) itemView.findViewById(R.id.txtAlterarFreteIF);

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
