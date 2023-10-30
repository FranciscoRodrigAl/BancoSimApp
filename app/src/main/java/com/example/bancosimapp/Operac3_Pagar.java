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

public class Operac3_Pagar extends AppCompatActivity {

    EditText et_PagoRut, et_PagoPassword, et_MontoPago;
    Cuenta cuenta;

    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operac3_pagar);
        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        et_PagoRut = findViewById(R.id.et_PagoRut);
        et_PagoPassword = findViewById(R.id.et_PagoPassword);
        et_MontoPago = findViewById(R.id.et_MontoPago);
    }

    public void Pagar(View view){
        try {
            String rut = String.valueOf(et_PagoRut.getText());
            String password = String.valueOf(et_PagoPassword.getText());
            Double montoPago = Double.parseDouble(String.valueOf(et_MontoPago.getText()));
            if(VerificarCredenciales(rut,password)){
                if(cuenta.Pagar(rut,password,montoPago)){
                    saveTransactions("pagos",formato.format(new Date()),montoPago);
                    Toast.makeText(this,"Pago realizado, Saldo:"+String.valueOf(cuenta.getSaldo()),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"No fue posible realizar el pago indicado",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Error en credenciales, intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    public boolean VerificarCredenciales(String rut, String password){
        return cuenta.getRut().equals(rut) && cuenta.getNumSecreto().equals(password);
    }

    public void VerHistorial(View view){
        try {
            Intent i = new Intent(this, Historial.class);
            i.putExtra("tituloHistorial", "Pagos");
            startActivity(i);
        }catch (Exception ex){
            Toast.makeText(this, "Excepcion" + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
        saveUserMoney();
        editor.commit();
    }

    public void saveUserMoney(){
        SharedPreferences preferences = getSharedPreferences("saldos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(cuenta.getRut(), String.valueOf(cuenta.getSaldo()));
        editor.commit();
    }
}