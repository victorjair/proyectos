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
public class Pension {
    
    int secuencia;
    String beneficiario;
    int empleado;
    float porcentaje;
    String plantel;
    String oficio;
    float montodescuento;
    String status;
    String clavepension;
    String rfc;
    String IdPension;
    String NumeroQuincena;


public Pension (String beneficiario,float porcentaje,String oficio,float montodescuento,String status,int secuencia,String IdPension){
    this.beneficiario = beneficiario;
    this.porcentaje = porcentaje;
    this.oficio = oficio;
    this.montodescuento = montodescuento;
    this.status = status;
    this.secuencia = secuencia;
    this.IdPension = IdPension;
}
public Pension(int secuencia, String beneficiario, int empleado, float porcentaje, String plantel, String oficio, float montodescuento, String status, String clavepension, String rfc, String IdPension, String NumeroQuincena) {
        this.secuencia = secuencia;
        this.beneficiario = beneficiario;
        this.empleado = empleado;
        this.porcentaje = porcentaje;
        this.plantel = plantel;
        this.oficio = oficio;
        this.montodescuento = montodescuento;
        this.status = status;
        this.clavepension = clavepension;
        this.rfc = rfc;
        this.IdPension = IdPension;
        this.NumeroQuincena = NumeroQuincena;
    }
 
   
   
    
    

    public Pension() {
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getPlantel() {
        return plantel;
    }

    public void setPlantel(String plantel) {
        this.plantel = plantel;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public float getMontodescuento() {
        return montodescuento;
    }

    public void setMontodescuento(float montodescuento) {
        this.montodescuento = montodescuento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClavepension() {
        return clavepension;
    }

    public void setClavepension(String clavepension) {
        this.clavepension = clavepension;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIdPension() {
        return IdPension;
    }

    public void setIdPension(String IdPension) {
        this.IdPension = IdPension;
    }

    public String getNumeroQuincena() {
        return NumeroQuincena;
    }

    public void setNumeroQuincena(String NumeroQuincena) {
        this.NumeroQuincena = NumeroQuincena;
    }
            
    
}
