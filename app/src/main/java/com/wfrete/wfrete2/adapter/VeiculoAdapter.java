package com.wfrete.wfrete2.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.VeiculoListarActivity;
import com.wfrete.wfrete2.dao.VeiculoDAO;
import com.wfrete.wfrete2.holder.VeiculoHolder;
import com.wfrete.wfrete2.model.Veiculo;

import java.util.List;


/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//Clase que liga os dados do veiculo com o layout item_lista.xml
public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoHolder> {

    private Fragment context;

    private final List<Veiculo> veiculos;

    public VeiculoAdapter(List<Veiculo> veiculos, Fragment context) {
        this.veiculos = veiculos;
        this.context = context;
    }

   // public VeiculoAdapter(List<Veiculo> veiculos) {
    //    this.veiculos = veiculos;
   // }

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
    public VeiculoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VeiculoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(VeiculoHolder holder, final int position) {

        holder.nome.setText(veiculos.get(position).getNome());

        final Veiculo veiculo = veiculos.get(position);

        holder.btEditar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if((context) instanceof VeiculoListarActivity){
                    ((VeiculoListarActivity)context).btEditarVeiculoOnClick(veiculo);
                }

            }
        });


        holder.btExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este veiculo?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Veiculo veiculo = veiculos.get(position);
                                VeiculoDAO dao = new VeiculoDAO(view.getContext());
                                boolean sucesso = dao.excluir(veiculo.getId());
                                if (sucesso) {
                                    removerVeiculo(veiculo);
                                    Snackbar.make(view, "Veiculo excluido com sucesso!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Erro ao excluir o veiculo!", Snackbar.LENGTH_LONG)
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


    public void atualizarCliente(Veiculo veiculo){
        veiculos.set(veiculos.indexOf(veiculo), veiculo);
        notifyItemChanged(veiculos.indexOf(veiculo));
    }

    @Override
    public int getItemCount() {
        return veiculos != null ? veiculos.size() : 0;
    }

    public void adicionarVeiculo(Veiculo veiculo){
        veiculos.add(veiculo);
        notifyItemInserted(getItemCount());
    }

    public void atualizarVeiculo(Veiculo veiculo){
        int position = veiculos.indexOf(veiculo);
        veiculos.set(position, veiculo);
        notifyItemChanged(position);
    }

    public void removerVeiculo(Veiculo veiculo){
        int position = veiculos.indexOf(veiculo);
        veiculos.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
