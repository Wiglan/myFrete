package com.wfrete.wfrete2.holder;


import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wfrete.wfrete2.R;

import org.w3c.dom.Text;

/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//classe que mapeia os itens do REcicleView

public class MotoristaHolder extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView s_datahora;
    public ImageButton btEditar;
    public ImageButton btExcluir;

    public MotoristaHolder(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.nomeCliente);
        s_datahora = (TextView) itemView.findViewById(R.id.datahoraintegracaoCliente);
        btEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }

}
