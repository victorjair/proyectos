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
public class Tasas {
    
    private String periodo;
    private String mes;
    private Double tasaSantander;
    private Double tasaBancomer;
    private Double tasaBanamex;
    private Double tasaHsbc;
    private Double promedio;
    private int Id;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getTasaSantander() {
        return tasaSantander;
    }

    public void setTasaSantander(Double tasaSantander) {
        this.tasaSantander = tasaSantander;
    }

    public Double getTasaBancomer() {
        return tasaBancomer;
    }

    public void setTasaBancomer(Double tasaBancomer) {
        this.tasaBancomer = tasaBancomer;
    }

    public Double getTasaBanamex() {
        return tasaBanamex;
    }

    public void setTasaBanamex(Double tasaBanamex) {
        this.tasaBanamex = tasaBanamex;
    }

    public Double getTasaHsbc() {
        return tasaHsbc;
    }

    public void setTasaHsbc(Double tasaHsbc) {
        this.tasaHsbc = tasaHsbc;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Tasas() {
    }
    
    
    
}
