package com.wfrete.wfrete2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.adapter.AdapterCategoria;
import com.wfrete.wfrete2.dao.LctoDAO;
import com.wfrete.wfrete2.model.Categoria;
import com.wfrete.wfrete2.model.Lcto;
import com.wfrete.wfrete2.model.Motorista;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class LctoCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;
    private static final int ID_COM_REG_DELETADO = 13;
    private static final int ID_COM_LISTAR = 0;

    private String[] lsCategorias = new String[]{"Categoria", "Combustível", "Peças", "Pneu", "Almoço", "hotel", "Depósitos", "Nova Categoria"};


    private DecimalFormat df = new DecimalFormat("###.00");

    EditText edtVlr;
    TextView txtData;
    TextView txtHorario;
    EditText edtObs;
    TextView txtCategoria;

    Button btCancelar;
    Button btExcluir;
    Button btSalvar;

    ImageView imgCategoria;

    private Lcto lctoEditado = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcto);

        edtVlr = (EditText)findViewById(R.id.edtValorLcto);
        edtObs = (EditText)findViewById(R.id.edtObsLcto);
        txtData = (TextView) findViewById(R.id.txtDataLcto);
        txtHorario = (TextView) findViewById(R.id.txtHorarioLcto);
        txtCategoria = (TextView) findViewById(R.id.txtCategoriaLcto);

        btSalvar = (Button) findViewById(R.id.btSalvarLcto);
        btCancelar = (Button) findViewById(R.id.btCancelarLcto);
        btExcluir = (Button) findViewById(R.id.btExcluirLcto);

        imgCategoria = (ImageView) findViewById(R.id.imgBotaoCategoria);

        //tratar o clique para mostrar as categoiras;
        imgCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoCategoria(v);
            }
        });
        txtCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoCategoria(v);
            }
        });

        //se na criacao, tem o motorista, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("lcto")){

            lctoEditado = (Lcto) intent.getSerializableExtra("lcto");

            edtVlr.setText(String.valueOf(lctoEditado.getValor()));
            edtObs.setText(lctoEditado.getObservacao());

            // TODO: 13/11/2017 Prencher o resto dos campos do lcto.

            btExcluir.setVisibility(View.VISIBLE);

        }
        else {
            edtVlr.setText("");
            edtObs.setText("");

            btExcluir.setVisibility(View.INVISIBLE);
        }

    }

    public void tratarSelecaoCategoria(View view){
        Intent i = new Intent(this, CategoriaListarActivity.class);
        i.putExtra("categoria", txtCategoria.getText().toString());
        startActivityForResult(i,ID_COM_LISTAR);
    }

    public void btCancelarOnClick(View view){
        Intent intent = getIntent();
        setResult(ID_COM_CANCELADO,intent);
        finish();
    }

    public void btExcluirLctoOnClick(View v){
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmação")
                .setMessage("Tem certeza que deseja excluir este Lançamento?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LctoDAO dao = new LctoDAO(getBaseContext());
                        boolean sucesso = dao.excluir(lctoEditado.getId());
                        if (sucesso) {
                            Intent intent = getIntent();
                            intent.putExtra("lcto", lctoEditado);
                            setResult(ID_COM_REG_DELETADO,intent);
                            finish();
                        } else {

                            Snackbar.make(view, "Erro ao Excluir o Lançamento!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }


    public void btSalvarOnClick(View view){

        //escontder o teclado.
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtVlr.getWindowToken(), 0);

        if (!(validarCamposCadastro())){
            return;
        }

        LctoDAO dao = new LctoDAO(getBaseContext());

        boolean inserido;

        Date data = new Date();
        Time hora = null;

        int categoria = 0;

        if(lctoEditado != null) {

            inserido = dao.salvar(lctoEditado.getId(),
                                  lctoEditado.getFrete_id(),
                                  Double.parseDouble(edtVlr.getText().toString()),
                                  categoria,
                                  edtObs.getText().toString(),
                                  data,
                                  hora);
        }
        else {

            inserido = dao.salvar(

                    lctoEditado.getFrete_id(),
                    Double.parseDouble(edtVlr.getText().toString()),
                    categoria,
                    edtObs.getText().toString(),
                    data,
                    hora);
        }

        if (inserido) {

            if(lctoEditado != null) {
                Lcto lcto = dao.lctoById(lctoEditado.getId());
                Intent intent = getIntent();
                intent.putExtra("lcto", lcto);
                setResult(ID_COM_REG_ALTERADO,intent);
            }else {
                Lcto lcto = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("lcto", lcto);
                setResult(ID_COM_NOVO_REG_INSERIDO,intent);
            }

            finish();

        }
        else {
            Snackbar.make(view, "Erro ao salvar Lançamento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        lctoEditado = null;

    }

    public boolean validarCamposCadastro(){

        if ((edtVlr.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Valor do Lançamento.", Toast.LENGTH_SHORT).show();
            edtVlr.requestFocus();
            return false;
        }

        return true;
    }

    public int getIdAutoCompleteList(String str){
        int pos = str.indexOf("-");
        String sub = str.substring(0,pos -1).trim();
        return Integer.parseInt(sub);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (data != null){
            if (requestCode == ID_COM_LISTAR){

                if (resultCode == 9) {

                    txtCategoria.setText("Categoria");
                    edtVlr.setTextColor(Color.BLACK);

                }
                else if (resultCode == 22){
                    if (data.hasExtra("categoria")) {
                        Categoria categoria = new Categoria();
                        categoria = (Categoria) data.getSerializableExtra("categoria");
                        txtCategoria.setText(categoria.getNome());

                        if (categoria.getTipo().equalsIgnoreCase("Receita")){
                            edtVlr.setTextColor(Color.BLUE);
                        }
                        else {
                            edtVlr.setTextColor(Color.RED);
                        }
                    }
                }
            }
        }

    }

}
