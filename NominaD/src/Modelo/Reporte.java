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
public class Reporte {
    
    String periodo;
    String numeroquincena;
    String numeroPeriodo;
    String yearPeriodo;
    int numero;
    String NombreReporte;
    String ClaveReporte;
    String PlantelReporte;
    String TipoPlantelReporte;
     String TipoPlaza;
    int folioinicialCFDI; 
    private String Ruta;
    String tipoNomina;
    String Tipocontrato;
    String TipoJornada;
    String TipoRegimen;
    String RiesgoDePuesto;
    String PeriodicidadDePago;
    String CodigoPostal;
    String Serie;
    String RegistroPatronal;
    String OrigenRecursos;
    public Reporte() {
    }

    public Reporte(String periodo, String numeroquincena, String numeroPeriodo, String yearPeriodo, int numero, String NombreReporte, String ClaveReporte, String PlantelReporte, String TipoPlantelReporte, String TipoPlaza, int folioinicialCFDI, String tipoNomina, String Tipocontrato, String TipoJornada, String TipoRegimen, String RiesgoDePuesto, String PeriodicidadDePago, String CodigoPostal, String Serie, String RegistroPatronal, String OrigenRecursos) {
        this.periodo = periodo;
        this.numeroquincena = numeroquincena;
        this.numeroPeriodo = numeroPeriodo;
        this.yearPeriodo = yearPeriodo;
        this.numero = numero;
        this.NombreReporte = NombreReporte;
        this.ClaveReporte = ClaveReporte;
        this.PlantelReporte = PlantelReporte;
        this.TipoPlantelReporte = TipoPlantelReporte;
        this.TipoPlaza = TipoPlaza;
        this.folioinicialCFDI = folioinicialCFDI;
        this.tipoNomina = tipoNomina;
        this.Tipocontrato = Tipocontrato;
        this.TipoJornada = TipoJornada;
        this.TipoRegimen = TipoRegimen;
        this.RiesgoDePuesto = RiesgoDePuesto;
        this.PeriodicidadDePago = PeriodicidadDePago;
        this.CodigoPostal = CodigoPostal;
        this.Serie = Serie;
        this.RegistroPatronal = RegistroPatronal;
        this.OrigenRecursos = OrigenRecursos;
    }

   

   

    
  
    

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombreReporte() {
        return NombreReporte;
    }

    public void setNombreReporte(String NombreReporte) {
        this.NombreReporte = NombreReporte;
    }

    public String getClaveReporte() {
        return ClaveReporte;
    }

    public void setClaveReporte(String ClaveReporte) {
        this.ClaveReporte = ClaveReporte;
    }

    public String getPlantelReporte() {
        return PlantelReporte;
    }

    public void setPlantelReporte(String PlantelReporte) {
        this.PlantelReporte = PlantelReporte;
    }

    public String getTipoPlantelReporte() {
        return TipoPlantelReporte;
    }

    public void setTipoPlantelReporte(String TipoPlantelReporte) {
        this.TipoPlantelReporte = TipoPlantelReporte;
    }

    public String getTipoPlaza() {
        return TipoPlaza;
    }

    public void setTipoPlaza(String TipoPlaza) {
        this.TipoPlaza = TipoPlaza;
    }

    public String getNumeroquincena() {
        return numeroquincena;
    }

    public void setNumeroquincena(String numeroquincena) {
        this.numeroquincena = numeroquincena;
    }

    public String getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(String numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getYearPeriodo() {
        return yearPeriodo;
    }

    public void setYearPeriodo(String yearPeriodo) {
        this.yearPeriodo = yearPeriodo;
    }

    public int getFolioinicialCFDI() {
        return folioinicialCFDI;
    }

    public void setFolioinicialCFDI(int folioinicialCFDI) {
        this.folioinicialCFDI = folioinicialCFDI;
    }

    public String getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    public String getTipocontrato() {
        return Tipocontrato;
    }

    public void setTipocontrato(String Tipocontrato) {
        this.Tipocontrato = Tipocontrato;
    }

    public String getTipoJornada() {
        return TipoJornada;
    }

    public void setTipoJornada(String TipoJornada) {
        this.TipoJornada = TipoJornada;
    }

    public String getTipoRegimen() {
        return TipoRegimen;
    }

    public void setTipoRegimen(String TipoRegimen) {
        this.TipoRegimen = TipoRegimen;
    }

    public String getRiesgoDePuesto() {
        return RiesgoDePuesto;
    }

    public void setRiesgoDePuesto(String RiesgoDePuesto) {
        this.RiesgoDePuesto = RiesgoDePuesto;
    }

    public String getPeriodicidadDePago() {
        return PeriodicidadDePago;
    }

    public void setPeriodicidadDePago(String PeriodicidadDePago) {
        this.PeriodicidadDePago = PeriodicidadDePago;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public String getRegistroPatronal() {
        return RegistroPatronal;
    }

    public void setRegistroPatronal(String RegistroPatronal) {
        this.RegistroPatronal = RegistroPatronal;
    }

    public String getOrigenRecursos() {
        return OrigenRecursos;
    }

    public void setOrigenRecursos(String OrigenRecursos) {
        this.OrigenRecursos = OrigenRecursos;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
    }
    
    
    
    
    
    
}
