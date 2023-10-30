package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class Historial extends AppCompatActivity {

    private ArrayList movimientos;
    private ArrayAdapter adaptadorMovimientos;
    private ListView lv_movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

    }

    class Transaccion {
        Date fecha;
        Double monto;
    }

    class AdaptadorTransacciones extends ArrayAdapter<Transaccion> {
        AppCompatActivity appCompatActivity;

        AdaptadorTransacciones(AppCompatActivity context){
            super(context,R.layout.);
        }
    }
}