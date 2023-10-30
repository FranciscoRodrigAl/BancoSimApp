package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Operac1_Girar extends AppCompatActivity {

    EditText et_MontoGiro;
    Cuenta cuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operac1_girar);
        et_MontoGiro = findViewById(R.id.et_montoGiro);
        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
    }

    public void Girar(View view){
        Double montoGiro = Double.parseDouble(String.valueOf(et_MontoGiro.getText()));
        try {
            boolean respuestaGiro = cuenta.Girar(montoGiro);
            if(respuestaGiro){
                saveTransactions("giros",cuenta.getRut(),montoGiro);
                Toast.makeText(this,"Giro realizado, Saldo:"+String.valueOf(cuenta.getSaldo()),Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(this, Historial.class);
        i.putExtra("objGiros", cuenta);
        startActivity(i);
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
    }
}