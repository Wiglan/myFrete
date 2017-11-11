package com.wfrete.wfrete2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.model.Frete;

import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class FreteCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;

    // TODO: 09/11/2017 Necessario implementar na tela uma forma de alimentar estes campos abaixo
   // private Date data_abertura;
   // private Date data_encerramento;
   // private int motorista_id;
   // private int veiculo_id;

    EditText edtNroCte;
    EditText edtOrigem;
    EditText edtDestino;
    EditText edtVlr_Ton;
    EditText edtPeso;
    EditText edtVlr_Total;

    Button btCancelar;
    Button btSalvar;

    private Frete freteEditado = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frete);

        edtNroCte = (EditText)findViewById(R.id.edtNro_CteFrete);
        edtOrigem = (EditText)findViewById(R.id.edtOrigemFrete);
        edtDestino = (EditText)findViewById(R.id.edtDestinoFrete);
        edtVlr_Ton = (EditText)findViewById(R.id.edtVlr_TonFrete);
        edtPeso = (EditText)findViewById(R.id.edtPesoFrete);
        edtVlr_Total = (EditText)findViewById(R.id.edtVlr_TotalFrete);

        btSalvar = (Button) findViewById(R.id.btSalvarFrete);
        btCancelar = (Button) findViewById(R.id.btCancelarFrete);


        //se na criacao, tem o motorista, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("frete")){

            freteEditado = (Frete) intent.getSerializableExtra("frete");

            edtNroCte.setText(String.valueOf(freteEditado.getNro_cte()));
            edtOrigem.setText(freteEditado.getOrigem());
            edtDestino.setText(freteEditado.getDestino());
            edtVlr_Ton.setText(String.valueOf(freteEditado.getVlr_ton()));
            edtPeso.setText(String.valueOf(freteEditado.getPeso()));
            edtVlr_Total.setText(String.valueOf(freteEditado.getVlr_total()));


        }
        else {
            edtNroCte.setText("");
            edtOrigem.setText("");
            edtDestino.setText("");
            edtVlr_Ton.setText("");
            edtPeso.setText("");
            edtVlr_Total.setText("");
        }

    }

    public void btCancelarOnClick(View view){
        Intent intent = getIntent();
        setResult(ID_COM_CANCELADO,intent);
        finish();
    }

    public void btSalvarOnClick(View view){

        //escontder o teclado.
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtNroCte.getWindowToken(), 0);

        FreteDAO dao = new FreteDAO(getBaseContext());

        boolean inserido;

        Date data = new Date();

        if(freteEditado != null) {

            inserido = dao.salvar(freteEditado.getId(),
                                  Integer.parseInt(edtNroCte.getText().toString()),
                                  edtOrigem.getText().toString(),
                                  edtDestino.getText().toString(),
                                  Double.parseDouble(edtVlr_Ton.getText().toString()),
                                  Double.parseDouble(edtPeso.getText().toString()),
                                  Double.parseDouble(edtVlr_Total.getText().toString()),
                                  data,
                                          data, 1,1);



        }
        else {

            inserido = dao.salvar(
                    Integer.parseInt(edtNroCte.getText().toString()),
                    edtOrigem.getText().toString(),
                    edtDestino.getText().toString(),
                    Double.parseDouble(edtVlr_Ton.getText().toString()),
                    Double.parseDouble(edtPeso.getText().toString()),
                    Double.parseDouble(edtVlr_Total.getText().toString()),
                    data,
                    data, 1,1);

        }

        if (inserido) {

            if(freteEditado != null) {
                Frete frete = dao.freteById(freteEditado.getId());
                Intent intent = getIntent();
                intent.putExtra("frete", frete);
                setResult(ID_COM_REG_ALTERADO,intent);
            }else {
                Frete frete = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("frete", frete);
                setResult(ID_COM_NOVO_REG_INSERIDO,intent);
            }

            finish();

        }
        else {
            Snackbar.make(view, "Erro ao salvar Frete", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        freteEditado = null;

    }

}
