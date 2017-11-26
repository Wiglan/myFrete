package com.wfrete.wfrete2.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.wfrete.wfrete2.R;
import com.wfrete.wfrete2.util.Constantes;

/**
 * Created by Desenvolvimento 11 on 23/11/2017.
 */

public class InicioActivity extends Fragment {

   private Button btMotorista;
   private Button btVeiculo;
   private Button btFrete;

   View viewPrimeiroAcesso;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewPrimeiroAcesso = inflater.inflate(R.layout.activity_primeiro_acesso, container, false);
        return viewPrimeiroAcesso;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        btFrete = (Button) viewPrimeiroAcesso.findViewById(R.id.btNovoFrete_Inicio);
        btMotorista = (Button) viewPrimeiroAcesso.findViewById(R.id.btNovoMotorista_Inicio);
        btVeiculo = (Button) viewPrimeiroAcesso.findViewById(R.id.btNovoVeiculo_Inicio);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getActivity(), MotoristaCadastrarActivity.class);
            startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });

        btVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getActivity(), VeiculoCadastrarActivity.class);
            startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });

        btFrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getActivity(), FreteCadastrarActivity.class);
            startActivityForResult(i, Constantes.ID_COMANDO_NOVO_CADASTRO);
            }
        });

    }


}
