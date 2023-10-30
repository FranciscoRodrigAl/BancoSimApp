package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Operaciones extends AppCompatActivity {

    Cuenta cuentaActual = new Cuenta();
    TextView rut, nombres, apellidos, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);
        cuentaActual = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        rut = findViewById(R.id.tvRut);
        nombres = findViewById(R.id.tvNombres);
        apellidos = findViewById(R.id.tvApellidos);
        saldo = findViewById(R.id.tvSaldo);

        if (cuentaActual != null) {
            rut.setText("RUT: " + cuentaActual.getRut());
            nombres.setText(cuentaActual.getNombres());
            apellidos.setText(cuentaActual.getApellidos());
            saldo.setText( String.valueOf(cuentaActual.getSaldo()));
        }
    }

    public void Girar(View view) {
        startActivity(sendToActivity(Operac1_Girar.class));
    }

    public void Depositar(View view) {
        startActivity(sendToActivity(Operac2_Depositar.class));
    }

    public void Pagar(View view){
        startActivity(sendToActivity(Operac3_Pagar.class));
    }

    public Intent sendToActivity(Class<?> activityClasss) {
        Intent i = new Intent(this, activityClasss);
        i.putExtra("objCuenta", cuentaActual);
        return i;
    }

    public void saveTransactions(String collectionName,String key, Double value){
        SharedPreferences preferences = getSharedPreferences(collectionName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }
}