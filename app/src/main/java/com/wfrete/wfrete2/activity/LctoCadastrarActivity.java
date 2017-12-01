package com.wfrete.wfrete2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.api.controller.CategoriaController;
import com.wfrete.wfrete2.api.controller.FreteController;
import com.wfrete.wfrete2.api.controller.LctoController;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.dao.LctoDAO;
import com.wfrete.wfrete2.model.Categoria;
import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.model.Lcto;
import com.wfrete.wfrete2.util.Constantes;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 06/11/2017.
 */

public class LctoCadastrarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
                                                                        TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener{


    private DecimalFormat df = new DecimalFormat("###.00");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    private int frete_id = 0;
    private int ano, mes, dia, hora, minuto;

    EditText edtVlr;
    TextView txtData;
    TextView txtHorario;
    EditText edtObs;
    TextView txtCategoria;

    Button btCancelar;
    Button btExcluir;
    Button btSalvar;

    ImageView imgCategoria;
    ImageView imgData;
    ImageView imgHora;


    private Lcto lctoEditado = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcto);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getSupportActionBar().setTitle("Cadastro de Lançamento");


        edtVlr = (EditText)findViewById(R.id.edtValorLcto);
        edtObs = (EditText)findViewById(R.id.edtObsLcto);
        txtData = (TextView) findViewById(R.id.txtDataLcto);
        txtHorario = (TextView) findViewById(R.id.txtHorarioLcto);
        txtCategoria = (TextView) findViewById(R.id.txtCategoriaLcto);

        btSalvar = (Button) findViewById(R.id.btSalvarLcto);
        btCancelar = (Button) findViewById(R.id.btCancelarLcto);
        btExcluir = (Button) findViewById(R.id.btExcluirLcto);

        imgCategoria = (ImageView) findViewById(R.id.imgBotaoCategoriaLcto);
        imgData = (ImageView) findViewById(R.id.imgBotaoDataLcto);
        imgHora = (ImageView) findViewById(R.id.imgBotaoHoraLcto);

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

        //mostrar o calendario
        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoData(v);
            }
        });
        imgData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoData(v);
            }
        });

        //mostrar o relogio
        txtHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoHora(v);
            }
        });
        imgHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tratarSelecaoHora(v);
            }
        });

        //se na criacao, tem o motorista, entao eh uma edicao de registro.
        Intent intent = getIntent();
        if(intent.hasExtra("lcto")){

            btExcluir.setVisibility(View.VISIBLE);

            lctoEditado = (Lcto) intent.getSerializableExtra("lcto");

            edtVlr.setText(String.valueOf(lctoEditado.getValor()));

            edtObs.setText(lctoEditado.getObservacao());

            Categoria categoria = new CategoriaDAO(this).categoriaById(lctoEditado.getCategoria_id());
            txtCategoria.setText(categoria.getNome());
            if (categoria.getTipo().equalsIgnoreCase("Receita")){
                edtVlr.setTextColor(Color.BLUE);
            }
            else {
                edtVlr.setTextColor(Color.RED);
            }

            Date data = lctoEditado.getData();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            ano = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH);
            dia = calendar.get(Calendar.DAY_OF_MONTH);

            Time hora1 = lctoEditado.getHora();
            calendar.setTime(hora1);
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minuto = calendar.get(Calendar.MINUTE);

            setStringDateTime();
            edtVlr.requestFocus();

        }
        else {
            edtVlr.setText("");
            edtObs.setText("");
            iniciarDataHora(true);
            setStringDateTime();
            btExcluir.setVisibility(View.INVISIBLE);

            edtVlr.requestFocus();
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

    private void tratarSelecaoData(View v) {

        iniciarDataHora(false);
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
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

        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");

    }

    private void tratarSelecaoHora(View v) {

        iniciarDataHora(false);
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia, hora, minuto);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.setOnCancelListener(this);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");

    }

    private void iniciarDataHora( Boolean forcarDataAtual){
       if ((ano == 0) || (forcarDataAtual)){
           Calendar calendar = Calendar.getInstance();
           ano = calendar.get(Calendar.YEAR);
           mes = calendar.get(Calendar.MONTH);
           dia = calendar.get(Calendar.DAY_OF_MONTH);
           hora = calendar.get(Calendar.HOUR_OF_DAY);
           minuto = calendar.get(Calendar.MINUTE);
       }
    }

    public void tratarSelecaoCategoria(View view){
        Intent i = new Intent(this, CategoriaListarNoLctoActivity.class);
        i.putExtra("categoria", txtCategoria.getText().toString());
        startActivityForResult(i, Constantes.ID_COM_LISTAR);
    }

    public void btCancelarOnClick(View view){
        Intent intent = getIntent();
        setResult(Constantes.ID_COM_CANCELADO,intent);
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
                            setResult(Constantes.ID_COM_REG_DELETADO,intent);
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia, hora, minuto);

        Date data = calendar.getTime();
        Time hora =  new Time(data.getTime());

        int categoria = getIdCategoria(txtCategoria.getText().toString().trim());
        if (categoria == 0){
            Toast.makeText(this, "Informe uma categoria válida.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(lctoEditado != null) {

            inserido = dao.salvar(lctoEditado.getId(),
                                  lctoEditado.getFrete_id(),
                                  Double.parseDouble(edtVlr.getText().toString()),
                                  categoria,
                                  edtObs.getText().toString(),
                                  data,
                                  hora, lctoEditado.getS_id(), null);
        }
        else {

            inserido = dao.salvar(frete_id,
                    Double.parseDouble(edtVlr.getText().toString()),
                    categoria,
                    edtObs.getText().toString(),
                    data,
                    hora, 0, null);
        }

        if (inserido) {

            Lcto lcto;

            if(lctoEditado != null) {
                lcto = dao.lctoById(lctoEditado.getId());
                Intent intent = getIntent();
                intent.putExtra("lcto", lcto);
                setResult(Constantes.ID_COM_REG_ALTERADO,intent);
            }else {
                lcto = dao.retornarUltimo();
                Intent intent = getIntent();
                intent.putExtra("lcto", lcto);
                setResult(Constantes.ID_COM_NOVO_REG_INSERIDO,intent);
            }

            boolean permiteWS = true;
            Lcto lctoWS = new Lcto(lcto.getId(), lcto.getFrete_id(),lcto.getValor(),lcto.getCategoria_id(), lcto.getObservacao(),
                                  lcto.getData(), lcto.getHora(), lcto.getS_id(), lcto.getS_datahora());

            //verificar se a categoria ja esta integrada, se nao estiver, precisa integrar antes
            Categoria c = new CategoriaDAO(this).categoriaById(lcto.getCategoria_id());
            if (c.getS_datahora() == null){
                if (!(CategoriaController.wsSalvarCategoria(this, c))){
                    permiteWS = false;
                }
                else {
                    Categoria newCategoria = new CategoriaDAO(this).categoriaById(lcto.getCategoria_id());
                    lctoWS.setCategoria_id(newCategoria.getS_id());
                }

            }
            else {
                lctoWS.setCategoria_id(c.getS_id());
            }

            //verificar se o Frete ja esta integrado, se nao estiver, precisa integrar antes
            Frete frete = new FreteDAO(this).freteById(lctoWS.getFrete_id());
            if (frete.getS_datahora() == null){
                if (!(FreteController.wsSalvarFrete(this, frete))){
                    permiteWS = false;
                }
                else {
                    //se conseguiu integrar o frete, tem que passar o id do frente no servidor para sincronizar.
                    Frete newFrete = new FreteDAO(this).freteById(lctoWS.getFrete_id());
                    lctoWS.setFrete_id(newFrete.getS_id());
                }
            }
            else {
                lctoWS.setFrete_id(frete.getS_id());
            }

            if (permiteWS) {
                LctoController.wsSalvarLcto(this, lctoWS);
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

        //Saber o ID do frete, manter a compatibilidade de abrir o lcto sem ser pela consulta de frete.
        Intent intent = getIntent();
        if(intent.hasExtra("frete_id")) {
            frete_id = intent.getIntExtra("frete_id", 0);
        }


        //priorizar a abertura pela consulta de frete, sempre que tiver um lcto editado, pega o frete do editado, se nao, pega o frete do intent
        if(lctoEditado != null) {
            frete_id = lctoEditado.getFrete_id();
        }

        if (frete_id == 0){
            Toast.makeText(this, "Frete não identificado.", Toast.LENGTH_SHORT).show();
            return  false;
        }

        frete_id = new FreteDAO(this).retornarUltimo().getId();

        return true;
    }

    public int getIdCategoria(String str){

        //isso aqui nao funciona para nomes com acento. 
        //Categoria categoria = new CategoriaDAO(this).categoriaByNome(str);

        //entao tive que pegar todas as categorias e buscar na lista.
        List<Categoria> categorias = new CategoriaDAO(this).ListarCategorias();

        String nome = "";

        for (Categoria categoria : categorias) {

            nome = categoria.getNome().trim();
            if (nome.equalsIgnoreCase(str)){
                return categoria.getId();
            }
        }

        return 0;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (data != null){
            if (requestCode == Constantes.ID_COM_LISTAR){

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

    @Override
    public void onCancel(DialogInterface dialogInterface) {

        iniciarDataHora(true);
        setStringDateTime();

    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        ano = year;
        mes = monthOfYear;
        dia = dayOfMonth;

        setStringDateTime();

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        hora = hourOfDay;
        minuto = minute;

        setStringDateTime();

    }

    public void setStringDateTime(){
        String strData = dateFormat.format(new Date(ano, mes, dia, hora, minuto));
        txtData.setText(strData.replace("-", " de "));

        String srtTime = timeFormat.format(new Date(ano, mes, dia, hora, minuto));
        txtHorario.setText(srtTime);

    }
}
