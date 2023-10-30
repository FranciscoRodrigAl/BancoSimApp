package com.example.bancosimapp;

import java.util.Date;

public class Transaccion {
    String fecha;
    String monto;

    public Transaccion() {
    }

    public Transaccion(String fecha, String monto) {
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
