package com.wfrete.wfrete2.adapter;


import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.util.Funcoes;
import com.wfrete.wfrete2.util.ItemClickListener;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.activity.FreteListarActivity;
import com.wfrete.wfrete2.holder.FreteHolder;
import com.wfrete.wfrete2.model.Frete;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public FreteAdapter(List<Frete> fretes, final Fragment context) {
        this.fretes = fretes;
        this.context = context;
    }

    @Override
    public FreteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frete, parent, false));
    }

    @Override
    public void onBindViewHolder(FreteHolder holder, final int position) {


        String str = Funcoes.formataMsgIntegracao(fretes.get(position).getS_datahora());
        if (str.equalsIgnoreCase("Não Sincronizado")){
            holder.txt_datahora.setTextColor(Color.RED);
        }else{
            holder.txt_datahora.setTextColor(Color.BLUE);
        }
        holder.txt_datahora.setText(str);

        String nroCte =  String.valueOf(fretes.get(position).getNro_cte());
        String dtAbertura = dateFormat.format(fretes.get(position).getData_abertura());

        Date data = fretes.get(position).getData_encerramento();
        String dtEncerramento = "";
        if (data != null){
            dtEncerramento = "Finalizado:   " + dateFormat.format(data);
            holder.txtDataEncerramento.setTextColor(Color.BLACK);
        }
        else {
            dtEncerramento = "Encerrar Frete";
            holder.txtDataEncerramento.setTextColor(Color.BLUE);
        }

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

        holder.txtDataEncerramento.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                int ano;
                int mes;
                int dia;
                Date dataEncerramento = frete.getData_encerramento();

                if (dataEncerramento != null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dataEncerramento);
                    ano = calendar.get(Calendar.YEAR);
                    mes = calendar.get(Calendar.MONTH);
                    dia = calendar.get(Calendar.DAY_OF_MONTH);

                }else {
                    Calendar calendar = Calendar.getInstance();
                    ano = calendar.get(Calendar.YEAR);
                    mes = calendar.get(Calendar.MONTH);
                    dia = calendar.get(Calendar.DAY_OF_MONTH);
                }


                Calendar calendar = Calendar.getInstance();
                calendar.set(ano, mes, dia);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                int idFrete = frete.getId();
                                Calendar dtEncerramento = Calendar.getInstance();
                                dtEncerramento.set(year, monthOfYear, dayOfMonth);
                                Date dt = dtEncerramento.getTime();
                                boolean sucesso = new FreteDAO(context.getActivity()).salvar_dataEncerramento(idFrete,dt);

                                if (sucesso){
                                    frete.setData_encerramento(dt);
                                    atualizarFrete(frete);
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                //definir o intervalo possivel de selecao.
                Calendar min = Calendar.getInstance();
                Calendar max = Calendar.getInstance();
                min.set(max.get(Calendar.YEAR), 0, 1);
                max.set(max.get(Calendar.YEAR) + 1, 11, 31);

                datePickerDialog.setMinDate(min);
                datePickerDialog.setMaxDate(max);

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        boolean sucesso = new FreteDAO(context.getActivity()).salvar_dataEncerramento(frete.getId(),null);
                        if (sucesso){
                            frete.setData_encerramento(null);
                            atualizarFrete(frete);
                        }
                    }
                });
                datePickerDialog.show(context.getFragmentManager(), "datePickerDialog");

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
