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
public class Sindicato {
    private String Id;
    private String NombreSindicato;
    private String rfcSindicato;
    private String NombreRepresentante;
    private String rfcRepresentante;
    private String FechaAfiliacion;
    private String QnaAfiliacion;
    private String PuestoRepresentante;
    private String TelefonoSindicato;
    private String Status;
    private String Detalles;

    public Sindicato() {
    }

    public Sindicato(String NombreSindicato, String rfcSindicato, String NombreRepresentante, String rfcRepresentante, String FechaAfiliacion, String QnaAfiliacion, String PuestoRepresentante, String TelefonoSindicato) {
        this.NombreSindicato = NombreSindicato;
        this.rfcSindicato = rfcSindicato;
        this.NombreRepresentante = NombreRepresentante;
        this.rfcRepresentante = rfcRepresentante;
        this.FechaAfiliacion = FechaAfiliacion;
        this.QnaAfiliacion = QnaAfiliacion;
        this.PuestoRepresentante = PuestoRepresentante;
        this.TelefonoSindicato = TelefonoSindicato;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String Detalles) {
        this.Detalles = Detalles;
    }

    
    public String getNombreSindicato() {
        return NombreSindicato;
    }

    public void setNombreSindicato(String NombreSindicato) {
        this.NombreSindicato = NombreSindicato;
    }

    public String getRfcSindicato() {
        return rfcSindicato;
    }

    public void setRfcSindicato(String rfcSindicato) {
        this.rfcSindicato = rfcSindicato;
    }

    public String getNombreRepresentante() {
        return NombreRepresentante;
    }

    public void setNombreRepresentante(String NombreRepresentante) {
        this.NombreRepresentante = NombreRepresentante;
    }

    public String getRfcRepresentante() {
        return rfcRepresentante;
    }

    public void setRfcRepresentante(String rfcRepresentante) {
        this.rfcRepresentante = rfcRepresentante;
    }

    public String getFechaAfiliacion() {
        return FechaAfiliacion;
    }

    public void setFechaAfiliacion(String FechaAfiliacion) {
        this.FechaAfiliacion = FechaAfiliacion;
    }

    public String getQnaAfiliacion() {
        return QnaAfiliacion;
    }

    public void setQnaAfiliacion(String QnaAfiliacion) {
        this.QnaAfiliacion = QnaAfiliacion;
    }

    public String getPuestoRepresentante() {
        return PuestoRepresentante;
    }

    public void setPuestoRepresentante(String PuestoRepresentante) {
        this.PuestoRepresentante = PuestoRepresentante;
    }

    public String getTelefonoSindicato() {
        return TelefonoSindicato;
    }

    public void setTelefonoSindicato(String TelefonoSindicato) {
        this.TelefonoSindicato = TelefonoSindicato;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    
     
     

    
}
