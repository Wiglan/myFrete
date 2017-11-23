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

import com.wfrete.wfrete2.util.ItemClickListener;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.FreteListarActivity;
import com.wfrete.wfrete2.holder.FreteHolder;
import com.wfrete.wfrete2.model.Frete;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Desenvolvimento 11 on 20/08/2017.
 */

//Clase que liga os dados do motorista com o layout item_lista.xml
public class FreteAdapter extends RecyclerView.Adapter<FreteHolder> {

    private Fragment context;
    private final List<Frete> fretes;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#,###.00");

    public FreteAdapter(List<Frete> fretes, Fragment context) {
        this.fretes = fretes;
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
    public FreteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frete, parent, false));
    }

    @Override
    public void onBindViewHolder(FreteHolder holder, final int position) {


        String nroCte =  String.valueOf(fretes.get(position).getNro_cte());
        String dtAbertura = dateFormat.format(fretes.get(position).getData_abertura());
        String dtEncerramento = "Finalizado:   " + dateFormat.format(fretes.get(position).getData_encerramento());
        String vlrTotal = "Total Frete: R$ " + df.format(fretes.get(position).getVlr_total());

        holder.txtNroCte.setText(nroCte);
        holder.txtDataAbertura.setText(dtAbertura);
        holder.txtDataEncerramento.setText(dtEncerramento);
        holder.txtOrigem.setText(fretes.get(position).getOrigem());
        holder.txtDestino.setText(fretes.get(position).getDestino());
        holder.txtVlrTotal.setText(vlrTotal);
        holder.txtCliente.setText(fretes.get(position).getCliente());

        final Frete frete = fretes.get(position);

        holder.txtAlterar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if((context) instanceof FreteListarActivity){
                    ((FreteListarActivity)context).btEditarFreteOnClick(frete);
                }

            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                //if (isLongClick){
                //    Toast.makeText(view.getContext(),"Clique longo: " +  fretes.get(position).getCliente(), Toast.LENGTH_SHORT).show();
               // }
                //else {
                 //   Toast.makeText(view.getContext(),fretes.get(position).getCliente(), Toast.LENGTH_SHORT).show();
                //}

                //Toast.makeText(view.getContext(),"Clique longo: " +  fretes.get(position).getCliente(), Toast.LENGTH_SHORT).show();

                if((context) instanceof FreteListarActivity){
                    ((FreteListarActivity)context).listarLctosFrete(frete);
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
        return fretes != null ? fretes.size() : 0;
    }

    public void adicionarFrete(Frete frete){
        fretes.add(frete);
        notifyItemInserted(getItemCount());
    }

    public void atualizarFrete(Frete frete){
        int position = fretes.indexOf(frete);
        fretes.set(position, frete);
        notifyItemChanged(position);
    }

    public void removerFrete(Frete frete){
        int position = fretes.indexOf(frete);
        fretes.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
        //somente o removed nao funciona, tem que chamar o changed.
    }
}
