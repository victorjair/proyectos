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
public class PlazoFijo {

    private String fechaInversion;
    private String nombre;
    private String rfc;
    private String status;
    private String sancion;
    private String Direccion;
    private String Quincena;
    private String TipoInversionista;
    private String FechaInicio;
    private String FechaVencimiento;
    private Double TasaInteres;
    private Double InteresGanadoPagar;
    private Double TotalPagadoPagar;
    private String QuincenaPago;
    private String FechaPago;
    private String Referencia;
   
    private Double TasaTotal;
    private Double Capital;
    private int plazo;
    private String  folio;
    private String  folioReinversion;
   
    private Double Interes;
    private Double Total;
    private Double Provision;
    private Double ProvisionDiaria;
    private Double MontoRetiro;
    private Double MontoAbono;
    private Double RetiroInteres;

    

    
    private String Id;
    private String Usuario;

    public PlazoFijo() {
    }

     
    
    
    public String getFechaInversion() {
        return fechaInversion;
    }

    public void setFechaInversion(String fechaInversion) {
        this.fechaInversion = fechaInversion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getQuincena() {
        return Quincena;
    }

    public void setQuincena(String Quincena) {
        this.Quincena = Quincena;
    }

    public String getTipoInversionista() {
        return TipoInversionista;
    }

    public void setTipoInversionista(String TipoInversionista) {
        this.TipoInversionista = TipoInversionista;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaVencimiento() {
        return FechaVencimiento;
    }

    public void setFechaVencimiento(String FechaVencimiento) {
        this.FechaVencimiento = FechaVencimiento;
    }

    public Double getTasaInteres() {
        return TasaInteres;
    }

    public void setTasaInteres(Double TasaInteres) {
        this.TasaInteres = TasaInteres;
    }

    public Double getTasaTotal() {
        return TasaTotal;
    }

    public void setTasaTotal(Double TasaTotal) {
        this.TasaTotal = TasaTotal;
    }

    public Double getCapital() {
        return Capital;
    }

    public void setCapital(Double Capital) {
        this.Capital = Capital;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Double getInteres() {
        return Interes;
    }

    public void setInteres(Double Interes) {
        this.Interes = Interes;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public Double getProvision() {
        return Provision;
    }

    public void setProvision(Double Provision) {
        this.Provision = Provision;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProvisionDiaria() {
        return ProvisionDiaria;
    }

    public void setProvisionDiaria(Double ProvisionDiaria) {
        this.ProvisionDiaria = ProvisionDiaria;
    }

    public Double getInteresGanadoPagar() {
        return InteresGanadoPagar;
    }

    public void setInteresGanadoPagar(Double InteresGanadoPagar) {
        this.InteresGanadoPagar = InteresGanadoPagar;
    }

    public Double getTotalPagadoPagar() {
        return TotalPagadoPagar;
    }

    public void setTotalPagadoPagar(Double TotalPagadoPagar) {
        this.TotalPagadoPagar = TotalPagadoPagar;
    }

    public String getQuincenaPago() {
        return QuincenaPago;
    }

    public void setQuincenaPago(String QuincenaPago) {
        this.QuincenaPago = QuincenaPago;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String FechaPago) {
        this.FechaPago = FechaPago;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }

    public String getFolioReinversion() {
        return folioReinversion;
    }

    public void setFolioReinversion(String folioReinversion) {
        this.folioReinversion = folioReinversion;
    }
    
    
            
       public Double getMontoRetiro() {
        return MontoRetiro;
    }

    public void setMontoRetiro(Double MontoRetiro) {
        this.MontoRetiro = MontoRetiro;
    }

    public Double getMontoAbono() {
        return MontoAbono;
    }

    public void setMontoAbono(Double MontoAbono) {
        this.MontoAbono = MontoAbono;
    }

    public Double getRetiroInteres() {
        return RetiroInteres;
    }

    public void setRetiroInteres(Double RetiroInteres) {
        this.RetiroInteres = RetiroInteres;
    }     


}
