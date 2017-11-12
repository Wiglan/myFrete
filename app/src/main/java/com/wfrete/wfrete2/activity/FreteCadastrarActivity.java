package com.wfrete.wfrete2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.dao.MotoristaDAO;
import com.wfrete.wfrete2.dao.VeiculoDAO;
import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.model.Motorista;
import com.wfrete.wfrete2.model.Veiculo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class FreteCadastrarActivity extends AppCompatActivity {

    private static final int ID_COM_NOVO_REG_INSERIDO = 2;
    private static final int ID_COM_REG_ALTERADO = 5;
    private static final int ID_COM_CANCELADO = 9;
    private static final int ID_COM_REG_DELETADO = 13;


    private DecimalFormat df = new DecimalFormat("###.00");

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
    AutoCompleteTextView acMotorista;
    AutoCompleteTextView acVeiculo;

    Button btCancelar;
    Button btSalvar;
    Button btExcluir;
    ImageButton btCalcularTotalFrete;

    private Frete freteEditado = null;
    private ArrayList<String> acListaVeiculo = new ArrayList<>();
    private ArrayList<String> acListaMotorista = new ArrayList<>();

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

        acMotorista = (AutoCompleteTextView)findViewById(R.id.acMotoristaFrete);
        acVeiculo = (AutoCompleteTextView) findViewById(R.id.acVeiculoFrete);

        btSalvar = (Button) findViewById(R.id.btSalvarFrete);
        btCancelar = (Button) findViewById(R.id.btCancelarFrete);
        btExcluir = (Button) findViewById(R.id.btExcluirFrete);
        btCalcularTotalFrete = (ImageButton) findViewById(R.id.btnCalculaTotalFrete);

        configurarCamposAutoComplete();

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

            //posicionar no motorista do frete
            String text = "";
            for(int i = 0 ; i < acListaMotorista.size(); i++){
                text = acListaMotorista.get(i);
                if (getIdAutoCompleteList(text) == freteEditado.getMotorista_id()){
                    acMotorista.setText(text);
                }
            }

            //posicionar no veiculo do frete;
            for(int i = 0 ; i < acListaVeiculo.size(); i++){
                text = acListaVeiculo.get(i);
                if (getIdAutoCompleteList(text) == freteEditado.getVeiculo_id()){
                    acVeiculo.setText(text);
                }
            }

            btExcluir.setVisibility(View.VISIBLE);

        }
        else {
            edtNroCte.setText("");
            edtOrigem.setText("");
            edtDestino.setText("");
            edtVlr_Ton.setText("");
            edtPeso.setText("");
            edtVlr_Total.setText("");
            acVeiculo.setText("");
            acMotorista.setText("");

            btExcluir.setVisibility(View.INVISIBLE);

        }

    }

    public void btCancelarOnClick(View view){
        Intent intent = getIntent();
        setResult(ID_COM_CANCELADO,intent);
        finish();
    }

    public void btExcluirFreteOnClick(View v){
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmação")
                .setMessage("Tem certeza que deseja excluir este Frete?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FreteDAO dao = new FreteDAO(getBaseContext());
                        boolean sucesso = dao.excluir(freteEditado.getId());
                        if (sucesso) {
                            Intent intent = getIntent();
                            intent.putExtra("frete", freteEditado);
                            setResult(ID_COM_REG_DELETADO,intent);
                            finish();
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

    public void btCalcularTotalFreteOnClick(View view){

        if ((edtVlr_Ton.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Valor/Tonelada para fazer o cálculo.", Toast.LENGTH_SHORT).show();
            return;
        }


        if ((edtPeso.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Peso para fazer o cálculo.", Toast.LENGTH_SHORT).show();
            return;
        }

        double peso = Double.parseDouble(edtPeso.getText().toString());
        double vlr_ton = Double.parseDouble(edtVlr_Ton.getText().toString());

        String total = df.format((peso / 1000.00) * vlr_ton);
        edtVlr_Total.setText(total.replace(",","."));

    }

    public void btSalvarOnClick(View view){

        //escontder o teclado.
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtNroCte.getWindowToken(), 0);

        if (!(validarCamposCadastro())){
            return;
        }

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
                                  data,
                                  getIdAutoCompleteList(acMotorista.getText().toString()),
                                  getIdAutoCompleteList(acVeiculo.getText().toString()));

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
                    data,
                    getIdAutoCompleteList(acMotorista.getText().toString()),
                    getIdAutoCompleteList(acVeiculo.getText().toString()));

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


    public boolean validarCamposCadastro(){

        if ((edtNroCte.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Número do Cte.", Toast.LENGTH_SHORT).show();
            edtNroCte.requestFocus();
            return false;
        }

        if ((edtVlr_Ton.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Valor/Tonelada.", Toast.LENGTH_SHORT).show();
            edtVlr_Ton.requestFocus();
            return false;
        }

        if ((edtPeso.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Peso.", Toast.LENGTH_SHORT).show();
            edtPeso.requestFocus();
            return false;
        }

        if ((edtVlr_Total.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o Valor Total do Frete.", Toast.LENGTH_SHORT).show();
            edtVlr_Total.requestFocus();
            return false;
        }

        if ((edtOrigem.getText().toString()).equals("")){
            Toast.makeText(this, "Informe a origem do Frete.", Toast.LENGTH_SHORT).show();
            edtOrigem.requestFocus();
            return false;
        }

        if ((edtDestino.getText().toString()).equals("")){
            Toast.makeText(this, "Informe o destino do Frete.", Toast.LENGTH_SHORT).show();
            edtDestino.requestFocus();
            return false;
        }

        if (acListaMotorista.indexOf(acMotorista.getText().toString()) < 0){
            Toast.makeText(this, "Motorista não cadastrado.", Toast.LENGTH_SHORT).show();
            acMotorista.requestFocus();
            return false;
        }

        if (acListaVeiculo.indexOf(acVeiculo.getText().toString()) < 0){
            Toast.makeText(this, "Veículo não cadastrado.", Toast.LENGTH_SHORT).show();
            acVeiculo.requestFocus();
            return false;
        }

        return true;
    }

    public int getIdAutoCompleteList(String str){
        int pos = str.indexOf("-");
        String sub = str.substring(0,pos -1).trim();
        return Integer.parseInt(sub);
    }

    public void configurarCamposAutoComplete(){

        //buscar a lista de motoristas para apresentar no cadastro de frete.
        MotoristaDAO motoristaDAO = new MotoristaDAO(this);
        Motorista motorista = new Motorista();
        List<Motorista> motoristas = motoristaDAO.ListarMotoristas();
        for(int i = 0 ; i < motoristas.size(); i++){
            motorista = motoristas.get(i);
            acListaMotorista.add("" + motorista.getId() + " - " + motorista.getNome());
        }
        ArrayAdapter adapterMotorista = new ArrayAdapter(this,android.R.layout.simple_list_item_1, acListaMotorista);
        acMotorista.setAdapter(adapterMotorista);
        acMotorista.setThreshold(1);


        //buscar a lista de veiculos para apresentar no cadastro de frete.
        VeiculoDAO veiculoDAO = new VeiculoDAO(this);
        Veiculo veiculo = new Veiculo();
        List<Veiculo> veiculos = veiculoDAO.ListarVeiculos();
        for(int i = 0 ; i < veiculos.size(); i++){
            veiculo = veiculos.get(i);
            acListaVeiculo.add("" + veiculo.getId() + " - " + veiculo.getModelo() + " - " + veiculo.getNome());
        }
        ArrayAdapter adapterVeiculo = new ArrayAdapter(this,android.R.layout.simple_list_item_1, acListaVeiculo);
        acVeiculo.setAdapter(adapterVeiculo);
        acVeiculo.setThreshold(1);
    }

}
