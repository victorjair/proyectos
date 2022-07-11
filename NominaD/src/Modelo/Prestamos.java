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
public class Prestamos {
    int folio;
    int empleado;
    float monto;
    float interes;
    float descuento;
    float total;
    String NombrePrestamo;
    String RfcPrestamo;
    String IdPrestamo;
    String plantel;
    int plazo;
    String status;
    float saldo;
    String ClaveDescuento;
    String NumeroquincenaInicio;
    String NumeroquincenaPrestamo;
    int prioridad;

    public Prestamos() {
    }

    public Prestamos( String ClaveDescuento,int folio,float monto,float interes,int plazo,float total,float descuento,
            float saldo,String NumeroquincenaInicio,String status,int prioridad,String IdPrestamo  )
    {
        //this.empleado = empleado;
        
        this.ClaveDescuento = ClaveDescuento;
        this.folio = folio;
        this.monto = monto;
        this.interes = interes;
        this.plazo = plazo;
        this.total = total;
        this.saldo = saldo;
        this.NumeroquincenaInicio = NumeroquincenaInicio;
        this.status = status;
        this.prioridad = prioridad;
        this.IdPrestamo = IdPrestamo;
        
       
        
    }
    public Prestamos(int folio, int empleado, float monto, float interes, float descuento, float total, String NombrePrestamo, String plantel, int plazo, String status, float saldo, String ClaveDescuento, String NumeroquincenaInicio, String NumeroquincenaPrestamo, int prioridad) {
        this.folio = folio;
        this.empleado = empleado;
        this.monto = monto;
        this.interes = interes;
        this.descuento = descuento;
        this.total = total;
        this.NombrePrestamo = NombrePrestamo;
        this.plantel = plantel;
        this.plazo = plazo;
        this.status = status;
        this.saldo = saldo;
        this.ClaveDescuento = ClaveDescuento;
        this.NumeroquincenaInicio = NumeroquincenaInicio;
        this.NumeroquincenaPrestamo = NumeroquincenaPrestamo;
        this.prioridad = prioridad;
    }

    

    

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public float getInteres() {
        return interes;
    }

    public void setInteres(float interes) {
        this.interes = interes;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPlantel() {
        return plantel;
    }

    public void setPlantel(String plantel) {
        this.plantel = plantel;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getClaveDescuento() {
        return ClaveDescuento;
    }

    public void setClaveDescuento(String ClaveDescuento) {
        this.ClaveDescuento = ClaveDescuento;
    }

    public String getNumeroquincenaInicio() {
        return NumeroquincenaInicio;
    }

    public void setNumeroquincenaInicio(String NumeroquincenaInicio) {
        this.NumeroquincenaInicio = NumeroquincenaInicio;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombrePrestamo() {
        return NombrePrestamo;
    }

    public void setNombrePrestamo(String NombrePrestamo) {
        this.NombrePrestamo = NombrePrestamo;
    }

    public String getNumeroquincenaPrestamo() {
        return NumeroquincenaPrestamo;
    }

    public void setNumeroquincenaPrestamo(String NumeroquincenaPrestamo) {
        this.NumeroquincenaPrestamo = NumeroquincenaPrestamo;
    }

    public String getIdPrestamo() {
        return IdPrestamo;
    }

    public void setIdPrestamo(String IdPrestamo) {
        this.IdPrestamo = IdPrestamo;
    }
    
    
}
