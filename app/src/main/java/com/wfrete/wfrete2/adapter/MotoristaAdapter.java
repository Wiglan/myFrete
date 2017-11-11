package com.wfrete.wfrete2.adapter;


import android.app.Activity;
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

import android.app.Fragment;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.MotoristaListarActivity;
import com.wfrete.wfrete2.dao.MotoristaDAO;
import com.wfrete.wfrete2.holder.MotoristaHolder;
import com.wfrete.wfrete2.model.Motorista;

import java.util.List;


/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//Clase que liga os dados do motorista com o layout item_lista.xml
public class MotoristaAdapter extends RecyclerView.Adapter<MotoristaHolder> {

    private Fragment context;

    private final List<Motorista> motoristas;

    public MotoristaAdapter(List<Motorista> motoristas, Fragment context) {
        this.motoristas = motoristas;
        this.context = context;
    }

   // public MotoristaAdapter(List<Motorista> motoristas) {
    //    this.motoristas = motoristas;
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
    public MotoristaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MotoristaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(MotoristaHolder holder, final int position) {

        holder.nome.setText(motoristas.get(position).getNome());

        final Motorista motorista = motoristas.get(position);

        holder.btEditar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if((context) instanceof MotoristaListarActivity){
                    ((MotoristaListarActivity)context).btEditarMotoristaOnClick(motorista);
                }

            }
        });


        holder.btExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este motorista?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Motorista motorista = motoristas.get(position);
                                MotoristaDAO dao = new MotoristaDAO(view.getContext());
                                boolean sucesso = dao.excluir(motorista.getId());
                                if (sucesso) {
                                    removerMotorista(motorista);
                                    Snackbar.make(view, "Motorista excluido com sucesso!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Erro ao excluir o motorista!", Snackbar.LENGTH_LONG)
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


    public void atualizarCliente(Motorista motorista){
        motoristas.set(motoristas.indexOf(motorista), motorista);
        notifyItemChanged(motoristas.indexOf(motorista));
    }

    @Override
    public int getItemCount() {
        return motoristas != null ? motoristas.size() : 0;
    }

    public void adicionarMotorista(Motorista motorista){
        motoristas.add(motorista);
        notifyItemInserted(getItemCount());
    }

    public void atualizarMotorista(Motorista motorista){
        int position = motoristas.indexOf(motorista);
        motoristas.set(position, motorista);
        notifyItemChanged(position);
    }

    public void removerMotorista(Motorista motorista){
        int position = motoristas.indexOf(motorista);
        motoristas.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
