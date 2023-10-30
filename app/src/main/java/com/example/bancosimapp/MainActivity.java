package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etRut, etNombres, etApellidos, etSaldo, etPassword, etRepitePassword;
    Button btCrearCuenta;
    Cuenta cuentaActual = null;
    Boolean preguntaCredenciales = false;
    Boolean credencialesValidas = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etRut = findViewById(R.id.et_rut);
        etNombres = findViewById(R.id.et_nombres);
        etApellidos = findViewById(R.id.et_apellidos);
        etSaldo = findViewById(R.id.et_montoGiro);
        etPassword = findViewById(R.id.et_password);
        etRepitePassword = findViewById(R.id.et_repitePassword);
        btCrearCuenta = findViewById(R.id.bt_crearCuenta);
        cuentaActual = getCuentaGuardada();
        if(cuentaActual != null){
            preguntaCredenciales = true;
            etNombres.setEnabled(false);
            etNombres.setText(cuentaActual.getNombres());
            etApellidos.setEnabled(false);
            etApellidos.setText(cuentaActual.getApellidos());
            etSaldo.setEnabled(false);
            etSaldo.setText(String.valueOf(cuentaActual.getSaldo()));
            etRepitePassword.setVisibility(View.INVISIBLE);
            btCrearCuenta.setText("Acceder");
        }
    }

    public void CrearCuenta(View view){
        try {
            if(cuentaActual == null ){
                String rut = String.valueOf(etRut.getText());
                String nombres = String.valueOf(etNombres.getText());
                String apellidos = String.valueOf(etApellidos.getText());
                double saldo = Double.parseDouble(String.valueOf(etSaldo.getText()));
                int pass = Integer.parseInt(String.valueOf(etPassword.getText()));
                int repitePass = Integer.parseInt(String.valueOf(etRepitePassword.getText()));
                if (TextUtils.isEmpty(rut)) {
                    etRut.setError("Campo obligatorio");
                    return;
                }
                if (TextUtils.isEmpty(nombres)) {
                    etNombres.setError("Campo obligatorio");
                    return;
                }
                if (TextUtils.isEmpty(apellidos)) {
                    etApellidos.setError("Campo obligatorio");
                    return;
                }
                if (saldo < 0) {
                    etSaldo.setError("Saldo debe ser positivo");
                    return;
                }
                if (pass != repitePass) {
                    etRepitePassword.setError("Password debe coincidir");
                    return;
                }
                cuentaActual = new Cuenta();
                cuentaActual.setRut(rut);
                cuentaActual.setNombres(nombres);
                cuentaActual.setApellidos(apellidos);
                cuentaActual.setSaldo(saldo);
                cuentaActual.setNumSecreto(String.valueOf(pass));
            }else{
                credencialesValidas = PreguntarCredenciales();
                if(!credencialesValidas){
                    Toast.makeText(this,"Credenciales erroneas",Toast.LENGTH_LONG).show();
                }
            }
            boolean cuentaConErrores = cuentaActual.validar();
            if (!cuentaConErrores) {
                if(!preguntaCredenciales){
                    saveData(cuentaActual.getRut(),cuentaActual.getNombres(),cuentaActual.getApellidos(),cuentaActual.getSaldo(),cuentaActual.getNumSecreto());
                }
                if( (preguntaCredenciales && credencialesValidas) || (!preguntaCredenciales) ) {
                    Intent i = new Intent(this, Operaciones.class);
                    i.putExtra("objCuenta", cuentaActual);
                    startActivity(i);
                }
            } else {
                Toast.makeText(this, "Error, cuenta no válida", Toast.LENGTH_SHORT).show();

            }
        }catch(Exception ex){
            Toast.makeText(this, "Excepcion: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void saveData(String rut, String nombres, String apellidos, Double saldo, String password){
        createAndSavePreference("ruts","rut",rut);
        createAndSavePreference("nombres",rut,nombres);
        createAndSavePreference("apellidos",rut,apellidos);
        createAndSavePreference("saldos",rut, String.valueOf(saldo));
        createAndSavePreference("passwords",rut,password);
    }

    public void createAndSavePreference(String preferenceName, String key, String value){
        SharedPreferences preferences = getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, (String) value);
        editor.commit();
    }

    public Cuenta getCuentaGuardada(){
        String rutGuardado = getCollectionPreference("ruts").getString("rut","");
        String nombres = getCollectionPreference("nombres").getString(rutGuardado,"");
        String apellidos = getCollectionPreference("apellidos").getString(rutGuardado,"");
        Double saldo = Double.parseDouble(getCollectionPreference("saldos").getString(rutGuardado,"0"));
        String password = getCollectionPreference("passwords").getString(rutGuardado,"");
        Cuenta cuentaRecuperada = new Cuenta(rutGuardado,nombres,apellidos,saldo,password);
        Boolean cuentaConErrores = cuentaRecuperada.validar();
        if(!cuentaConErrores){
            return cuentaRecuperada;
        }else{
            return null;
        }
    }

    public SharedPreferences getCollectionPreference(String preferenceName){
        return getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
    }

    public boolean PreguntarCredenciales(){
        String rutPreguntado = String.valueOf(etRut.getText());
        String numeroSecretoPreguntado = String.valueOf(etPassword.getText());
        if(cuentaActual != null){
            boolean mismoRut = cuentaActual.getRut().equals(rutPreguntado);
            boolean mismoPassword = cuentaActual.getNumSecreto().equals(numeroSecretoPreguntado);
           return mismoRut && mismoPassword;
        }
        return false;
    }
}