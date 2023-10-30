package com.example.bancosimapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Historial extends AppCompatActivity {

    private ArrayList<Transaccion> movimientos;
    private ArrayAdapter adaptadorMovimientos;
    private ListView lv_movimientos;
    TextView tvTitulo;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        tvTitulo = findViewById(R.id.tv_titulo_historial);
        titulo = getIntent().getExtras().getString("tituloHistorial","");
        movimientos = obtenerListaTransacciones(titulo.toLowerCase());
        tvTitulo.setText(titulo);

        AdaptadorTransacciones adaptador = new AdaptadorTransacciones(this);
        lv_movimientos = findViewById(R.id.lv_movimientos);
        lv_movimientos.setAdapter(adaptador);
    }

    class AdaptadorTransacciones extends ArrayAdapter<Transaccion> {
        AppCompatActivity appCompatActivity;

        AdaptadorTransacciones(AppCompatActivity context){
            super(context,R.layout.transaccion,movimientos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.transaccion, null);

            TextView tvMonto = item.findViewById(R.id.tvMonto);
            tvMonto.setText(String.valueOf(movimientos.get(position).getMonto()));

            TextView tvFecha = item.findViewById(R.id.tvFecha);
            tvFecha.setText(String.valueOf(movimientos.get(position).getFecha()));

            return item;
        }


    }
    public ArrayList<Transaccion> obtenerListaTransacciones(String nombreLista){
        SharedPreferences preference = getSharedPreferences(nombreLista, Context.MODE_PRIVATE);
        Map<String,String> todosLosRegistros = (Map<String, String>) preference.getAll();
        ArrayList<Transaccion> lista = new ArrayList<>();
        for (Map.Entry<String, String> entry: todosLosRegistros.entrySet()){
            String fecha = entry.getKey();
            String monto = entry.getValue();
            Transaccion transaccion = new Transaccion(fecha,monto);
            lista.add(transaccion);
        }
        return lista;
    }
}