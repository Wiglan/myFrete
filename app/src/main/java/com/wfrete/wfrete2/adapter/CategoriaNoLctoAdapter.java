package com.wfrete.wfrete2.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.model.Categoria;

import java.util.List;

/**
 * Created by Desenvolvimento 11 on 14/11/2017.
 */

public class CategoriaNoLctoAdapter extends BaseAdapter {

    private final List<Categoria> categorias;
    private Activity act;

    public CategoriaNoLctoAdapter(Activity act, List<Categoria> categorias) {
        this.act = act;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int i) {
        return categorias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categorias.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater().inflate(R.layout.item_categoria_no_lcto, parent, false);
        Categoria categoria = categorias.get(position);

        TextView txtNome = (TextView) view.findViewById(R.id.txtItem_categoria_nome);
        TextView txtTipo = (TextView) view.findViewById(R.id.txtItem_categoria_tipo);

        txtNome.setText(categoria.getNome());
        txtTipo.setText(categoria.getTipo());


        if (categoria.getTipo().equalsIgnoreCase("Receita")){
            txtTipo.setTextColor(Color.BLUE);
        }
        else {
            txtTipo.setTextColor(Color.RED);
        }

        return view;
    }
}
