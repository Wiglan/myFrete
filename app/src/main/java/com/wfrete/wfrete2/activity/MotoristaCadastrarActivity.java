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
import com.wfrete.wfrete2.adapter.MotoristaAdapter;
import com.wfrete.wfrete2.api.controller.MotoristaController;
import com.wfrete.wfrete2.dao.MotoristaDAO;
import com.wfrete.wfrete2.model.Motorista;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class MotoristaCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;

    EditText edtNome;
    EditText edtCpf;
    EditText edtTelefone;

    Button btCancelar;
    Button btSalvar;

    private Motorista motoristaEditado = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);

        edtNome = (EditText)findViewById(R.id.edtNomeMotorista);
        edtCpf = (EditText)findViewById(R.id.edtCpfMotorista);
        edtTelefone = (EditText)findViewById(R.id.edtTelefoneMotorista);

        btSalvar = (Button) findViewById(R.id.btSalvarMotorista);
        btCancelar = (Button) findViewById(R.id.btCancelarMotorista);


        //se na criacao, tem o motorista, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("motorista")){

            motoristaEditado = (Motorista) intent.getSerializableExtra("motorista");

            edtNome.setText(motoristaEditado.getNome());
            edtCpf.setText(motoristaEditado.getCpf());
            edtTelefone.setText(motoristaEditado.getTelefone());

        }
        else {
            edtNome.setText("");
            edtCpf.setText("");
            edtTelefone.setText("");
        }

    }

    public void btCancelarOnClick(View view){
        Intent intent = getIntent();
        setResult(ID_COM_CANCELADO,intent);
        finish();
    }

    public void btSalvarOnClick(View view){

        //escontder o teclado.
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtNome.getWindowToken(), 0);

        MotoristaDAO dao = new MotoristaDAO(getBaseContext());

        boolean inserido;

        if(motoristaEditado != null) {

            inserido = dao.salvar(motoristaEditado.getId(),
                                  edtNome.getText().toString(),
                                  edtCpf.getText().toString(),
                                  edtTelefone.getText().toString(),
                                    motoristaEditado.getS_id(),
                                    null);

        }
        else {

            inserido = dao.salvar(edtNome.getText().toString(),
                                  edtCpf.getText().toString(),
                                  edtTelefone.getText().toString(), 0, null);
        }

        if (inserido) {

            Motorista motorista;
            if(motoristaEditado != null) {
                motorista = dao.motoristaById(motoristaEditado.getId());
                Intent intent = getIntent();
                intent.putExtra("motorista", motorista);
                setResult(ID_COM_REG_ALTERADO,intent);
            }else {
                motorista = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("motorista", motorista);
                setResult(ID_COM_NOVO_REG_INSERIDO,intent);
            }


            //no salvar acima, sempre seta a data de integracao como nulla, ai se deu pra comunicar com o servidor, atualiza a data abaixo.
            MotoristaController.wsSalvarMotorista(this, motorista);

            finish();

        }
        else {
            Snackbar.make(view, "Erro ao salvar Motorista", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        motoristaEditado = null;

    }




}
