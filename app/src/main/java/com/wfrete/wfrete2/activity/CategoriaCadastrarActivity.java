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
import android.widget.RadioButton;
import android.widget.Toast;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.model.Categoria;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class CategoriaCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;

    EditText edtNome;
    private RadioButton rbReceita;
    private RadioButton rbDespesa;

    Button btCancelar;
    Button btSalvar;

    private Categoria categoriaEditada = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        edtNome = (EditText)findViewById(R.id.edtNomeCategoria);

        rbReceita = (RadioButton) findViewById(R.id.rbReceitaCategoria);
        rbDespesa = (RadioButton) findViewById(R.id.rbDespesaCategoria);

        btSalvar = (Button) findViewById(R.id.btSalvarCategoria);
        btCancelar = (Button) findViewById(R.id.btCancelarCategoria);


        //se na criacao, tem o categoria, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("categoria")){

            categoriaEditada = (Categoria) intent.getSerializableExtra("categoria");
            edtNome.setText(categoriaEditada.getNome());

            if (categoriaEditada.getTipo().equalsIgnoreCase("Receita")) {
                rbDespesa.setChecked(false);
                rbReceita.setChecked(true);
            }
            else {
                rbDespesa.setChecked(true);
                rbReceita.setChecked(false);
            }

        }
        else {
            edtNome.setText("");
            rbDespesa.setChecked(true);
            rbReceita.setChecked(false);
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

        CategoriaDAO dao = new CategoriaDAO(getBaseContext());

        if (!(validarCamposCadastro())){
            return;
        }

        String tipo = "";
        if (rbReceita.isChecked()){
            tipo = "Receita";
        }
        else {
            tipo = "Despesa";
        }

        boolean inserido;

        if(categoriaEditada != null) {

            inserido = dao.salvar(categoriaEditada.getId(),
                                  edtNome.getText().toString(),
                                  tipo,  categoriaEditada.getS_id(),
                    null);

        }
        else {

            inserido = dao.salvar(edtNome.getText().toString(),
                                  tipo, 0, null);
        }

        if (inserido) {

            Categoria categoria;

            if(categoriaEditada != null) {
                categoria = dao.categoriaById(categoriaEditada.getId());
                Intent intent = getIntent();
                intent.putExtra("categoria", categoria);
                setResult(ID_COM_REG_ALTERADO,intent);
            }else {
                categoria = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("categoria", categoria);
                setResult(ID_COM_NOVO_REG_INSERIDO,intent);
            }


            //CategoriaController.wsSalvarMotorista(this, categoriaWS);

            finish();

        }
        else {
            Snackbar.make(view, "Erro ao salvar Categoria", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        categoriaEditada = null;

    }

    public boolean validarCamposCadastro(){

        if ((edtNome.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o nome da categoria.", Toast.LENGTH_SHORT).show();
            edtNome.requestFocus();
            return false;
        }

        if (rbReceita.isChecked()){
            return true;
        }
        else if (rbDespesa.isChecked()) {
            return true;
        }else {
            return false;
        }

    }


}
