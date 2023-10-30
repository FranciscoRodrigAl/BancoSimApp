package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Operac2_Depositar extends AppCompatActivity {

    EditText et_MontoDeposito;
    Cuenta cuenta;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operac2_depositar);
        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        et_MontoDeposito = findViewById(R.id.et_MontoDeposito);
    }

    public void Depositar(View view){
        Double montoDeposito = Double.parseDouble(String.valueOf(et_MontoDeposito.getText()));
        try{
            boolean respuestaDeposito = cuenta.Depositar(montoDeposito);
            if(respuestaDeposito){
                saveTransactions("depositos",formato.format(new Date()),montoDeposito);
                Toast.makeText(this,"Deposito realizado, Saldo:"+String.valueOf(cuenta.getSaldo()),Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void Volver(View view){
        try{
            Intent i = new Intent(this, Operaciones.class);
            i.putExtra("objCuenta",cuenta);
            startActivity(i);
        }catch (Exception ex){
            Toast.makeText(this, "Excepcion" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void VerHistorial(View view){
        try {
            Intent i = new Intent(this, Historial.class);
            i.putExtra("tituloHistorial", "Depositos");
            startActivity(i);
        }catch (Exception ex){
            Toast.makeText(this, "Excepcion" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            try {
                Intent i  = new Intent(this,Operaciones.class);
                i.putExtra("cuenta",cuenta);
                startActivity(i);
            }catch (Exception ex){
                Toast.makeText(this, "Excepcion" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public void saveTransactions(String collectionName,String key, Double value){
        SharedPreferences preferences = getSharedPreferences(collectionName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
        saveUserMoney();
    }

    public void saveUserMoney(){
        SharedPreferences preferences = getSharedPreferences("saldos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(cuenta.getRut(), String.valueOf(cuenta.getSaldo()));
        editor.commit();
    }
}