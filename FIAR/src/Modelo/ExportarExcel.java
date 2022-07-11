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
public class ExportarExcel {
    
    
    private String rfc;
    private String nombre;
    private String plantel;
    private Double facore;
    private String statusA;
    private String statusP;
    private  int  numeroEmpleado;
    private Double descuento;
    private int folio;
    private Double aplicar;
    private Double total;
    private Double saldo;
    private int plazo;
    private String numero;
    private String periodo;
    private String numeroQuincena;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlantel() {
        return plantel;
    }

    public void setPlantel(String plantel) {
        this.plantel = plantel;
    }

    public Double getFacore() {
        return facore;
    }

    public void setFacore(Double facore) {
        this.facore = facore;
    }

    public String getStatusA() {
        return statusA;
    }

    public void setStatusA(String statusA) {
        this.statusA = statusA;
    }

    public String getStatusP() {
        return statusP;
    }

    public void setStatusP(String statusP) {
        this.statusP = statusP;
    }

    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(int numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public Double getAplicar() {
        return aplicar;
    }

    public void setAplicar(Double aplicar) {
        this.aplicar = aplicar;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public String getNumeroQuincena() {
        return numeroQuincena;
    }

    public void setNumeroQuincena(String numeroQuincena) {
        this.numeroQuincena = numeroQuincena;
    }

    public ExportarExcel() {
    }

    public ExportarExcel(String rfc, String nombre, String plantel, Double facore, String statusA, String statusP, int numeroEmpleado, Double descuento, int folio, Double aplicar, Double total, Double saldo, int plazo, String numero, String periodo, String numeroQuincena) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.plantel = plantel;
        this.facore = facore;
        this.statusA = statusA;
        this.statusP = statusP;
        this.numeroEmpleado = numeroEmpleado;
        this.descuento = descuento;
        this.folio = folio;
        this.aplicar = aplicar;
        this.total = total;
        this.saldo = saldo;
        this.plazo = plazo;
        this.numero = numero;
        this.periodo = periodo;
        this.numeroQuincena = numeroQuincena;
    }

    

   
    
    
    
}
