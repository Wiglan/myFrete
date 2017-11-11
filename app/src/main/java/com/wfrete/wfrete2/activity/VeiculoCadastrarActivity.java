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
import com.wfrete.wfrete2.dao.VeiculoDAO;
import com.wfrete.wfrete2.model.Veiculo;
import com.wfrete.wfrete2.model.Veiculo;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class VeiculoCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;

    EditText edtNome;
    EditText edtModelo;
    EditText edtPlaca;
    EditText edtAno;

    Button btCancelar;
    Button btSalvar;

    private Veiculo veiculoEditado = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);

        edtNome = (EditText)findViewById(R.id.edtNomeVeiculo);
        edtModelo = (EditText)findViewById(R.id.edtModeloVeiculo);
        edtPlaca = (EditText)findViewById(R.id.edtPlacaVeiculo);
        edtAno = (EditText)findViewById(R.id.edtAnoVeiculo);

        btSalvar = (Button) findViewById(R.id.btSalvarVeiculo);
        btCancelar = (Button) findViewById(R.id.btCancelarVeiculo);

        //se na criacao, tem o veiculo, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("veiculo")){

            veiculoEditado = (Veiculo) intent.getSerializableExtra("veiculo");

            edtNome.setText(veiculoEditado.getNome());
            edtModelo.setText(veiculoEditado.getModelo());
            edtPlaca.setText(veiculoEditado.getPlaca());
            edtAno.setText(String.valueOf(veiculoEditado.getAno()));

        }
        else {
            edtNome.setText("");
            edtModelo.setText("");
            edtPlaca.setText("");
            edtAno.setText("");
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

        VeiculoDAO dao = new VeiculoDAO(getBaseContext());

        boolean inserido;

        if(veiculoEditado != null) {

            inserido = dao.salvar(veiculoEditado.getId(),
                                  edtNome.getText().toString(),
                                  edtModelo.getText().toString(),
                                  edtPlaca.getText().toString(),
                                  Integer.parseInt(edtAno.getText().toString()));

        }
        else {

            inserido = dao.salvar(edtNome.getText().toString(),
                                    edtModelo.getText().toString(),
                                    edtPlaca.getText().toString(),
                                    Integer.parseInt(edtAno.getText().toString()));
        }

        if (inserido) {

            if(veiculoEditado != null) {
                Veiculo veiculo = dao.veiculoById(veiculoEditado.getId());
                Intent intent = getIntent();
                intent.putExtra("veiculo", veiculo);
                setResult(ID_COM_REG_ALTERADO,intent);
            }else {
                Veiculo veiculo = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("veiculo", veiculo);
                setResult(ID_COM_NOVO_REG_INSERIDO,intent);
            }

            finish();

        }
        else {
            Snackbar.make(view, "Erro ao salvar Veiculo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        veiculoEditado = null;

    }




}
