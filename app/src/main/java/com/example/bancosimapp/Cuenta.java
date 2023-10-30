package com.example.bancosimapp;

import android.util.Pair;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private String rut;
    private String nombres;
    private String apellidos;
    private Double saldo;
    private String numSecreto;

    public Cuenta() {
    }

    public Cuenta(String rut, String nombres, String apellidos, double saldo, String numSecreto) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.saldo = saldo;
        this.numSecreto = numSecreto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNumSecreto() {
        return numSecreto;
    }

    public void setNumSecreto(String numSecreto) {
        this.numSecreto = numSecreto;
    }

    public boolean Girar(double monto) throws Exception{
        Boolean logragirar = saldo - monto >=0;
        Boolean giroValido = monto >= 0;
        if(logragirar && giroValido){
            saldo -= monto;
        }else if (!giroValido){
            throw new Exception("Monto de giro no debe ser negativo");
        }else if (!logragirar){
            throw new Exception("Saldo insuficiente para realizar giro");
        }
        return logragirar;
    }

    public boolean Depositar(double monto) throws Exception{
        Boolean lograDepositar = monto > 0;
        if(lograDepositar) {
            saldo += monto;
        }else{
            throw new Exception("Monto a depositar debe ser positivo");
        }
        return lograDepositar;
    }

    public boolean Pagar(String rut, String secret, double montoPago) throws Exception{
        Boolean tieneAcceso = this.rut.equals(rut) && this.numSecreto.equals(secret);
        Boolean puedePagar = this.saldo - montoPago >= 0;
        if(tieneAcceso && puedePagar){
            saldo += montoPago;
        } else if(!tieneAcceso){
            throw new Exception("Acceso denegado, número secreto no válido");
        } else if (!puedePagar){
            throw new Exception("Saldo insuficiente para el pago");
        }
        return tieneAcceso && puedePagar;
    }

    public boolean validar(){
        Boolean tieneErrores = false;
        tieneErrores = (this.rut.trim().length() == 0);
        tieneErrores = (this.nombres.trim().length() == 0);
        tieneErrores = (this.apellidos.trim().length() == 0);
        tieneErrores =  (this.numSecreto.trim().length() == 0);
        tieneErrores =(this.saldo < 0);
        return tieneErrores;
    }
}
