/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author victor
 */
public class TablaISR {
    
    float LimiteInferior;
    float LimiteSuperior;
    float CuotaFija;
    float Porcentaje;
    float AplicaDias;
    float Intermedio;
    Double CalculoIsr;
    Double RCalculoIsr;
    float SalarioCalculo;
    String Periodo;
    int Periodotabla;

    public float getLimiteInferior() {
        return LimiteInferior;
    }

    public void setLimiteInferior(float LimiteInferior) {
        this.LimiteInferior = LimiteInferior;
    }

    public float getLimiteSuperior() {
        return LimiteSuperior;
    }

    public void setLimiteSuperior(float LimiteSuperior) {
        this.LimiteSuperior = LimiteSuperior;
    }

    public float getCuotaFija() {
        return CuotaFija;
    }

    public void setCuotaFija(float CuotaFija) {
        this.CuotaFija = CuotaFija;
    }

    public float getPorcentaje() {
        return Porcentaje;
    }

    public void setPorcentaje(float Porcentaje) {
        this.Porcentaje = Porcentaje;
    }

    public float getAplicaDias() {
        return AplicaDias;
    }

    public void setAplicaDias(float AplicaDias) {
        this.AplicaDias = AplicaDias;
    }

    public String getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(String Periodo) {
        this.Periodo = Periodo;
    }

    public float getIntermedio() {
        return Intermedio;
    }

    public void setIntermedio(float Intermedio) {
        this.Intermedio = Intermedio;
    }

    public Double getCalculoIsr() {
        return CalculoIsr;
    }

    public void setCalculoIsr(Double CalculoIsr) {
        this.CalculoIsr = CalculoIsr;
    }

    public Double getRCalculoIsr() {
        return RCalculoIsr;
    }

    public void setRCalculoIsr(Double RCalculoIsr) {
        this.RCalculoIsr = RCalculoIsr;
    }

    public float getSalarioCalculo() {
        return SalarioCalculo;
    }

    public void setSalarioCalculo(float SalarioCalculo) {
        this.SalarioCalculo = SalarioCalculo;
    }

    public int getPeriodotabla() {
        return Periodotabla;
    }

    public void setPeriodotabla(int Periodotabla) {
        this.Periodotabla = Periodotabla;
    }
    
    
    
}
